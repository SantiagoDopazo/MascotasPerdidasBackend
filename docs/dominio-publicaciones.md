# Dominio de publicaciones

Primera version del dominio para coordinar con servicios, API y persistencia.
No agrega endpoints, tablas, repositorios ni tareas programadas.

## Modelo

- `Publication`: entidad con UUID propio, autor (`User`), tipo, descripcion,
  caracteristicas del animal, ubicacion, fecha del suceso, fotos y ciclo de vida.
  Su autor es quien informa el caso; no necesariamente es el duenio del animal.
- `PetCharacteristics`: objeto de valor con especie (perro o gato), tamanio,
  colores y senias particulares. No establece que dos avisos refieran al mismo animal.
- `Location`: objeto de valor con coordenadas validas y descripcion opcional.
- `Photo`: objeto de valor identificado por su referencia al archivo almacenado.
  No contiene bytes, embeddings ni logica de carga o comunicacion con la IA.
- `PublicationType`: LOST o FOUND, independiente del estado del aviso.

Los objetos de valor son records inmutables. La publicacion controla sus cambios
mediante operaciones y devuelve una copia inmutable de sus fotografias.

## Ciclo de vida

1. Un aviso comienza ActivePublication, sin fecha de desactivacion.
2. `close()` pasa de ActivePublication a ClosedPublication cuando se resuelve el caso.
3. `cancel()` pasa de ActivePublication a CancelledPublication: equivale al borrado logico solicitado
   por el usuario. El objeto, sus fotos y su informacion permanecen disponibles.
4. Ambas operaciones registran la fecha de desactivacion. `isActive()` devuelve
   false: las consultas habituales deberan excluir esos avisos en persistencia.

Las publicaciones cerradas o canceladas permanecen en el sistema.

## Decisiones iniciales a revisar con el equipo

- ClosedPublication y CancelledPublication son terminales: no se admite reapertura, edicion ni otra
  desactivacion. La fecha original de cierre o cancelacion se conserva.
- Autor y tipo no cambian despues de crear el aviso.
- Descripcion, especie, tamanio, colores, ubicacion y fecha del suceso son obligatorios.
  Senias y descripcion de ubicacion son opcionales.
- Todavia no se exige una cantidad minima de fotos; la recomendacion de frente y
  costado del enunciado no se interpreta como una obligacion. No se repiten referencias.
- Quedan por definir datos de contacto, campos particulares de perdida/encontrada,
  tratamiento de fechas aproximadas y que sucede al eliminar una cuenta.

## Limites para las otras capas

- Autorizacion: el servicio/middleware debe comprobar que el solicitante sea el autor
  o un administrador para la publicacion concreta. El rol por si solo no alcanza.
- Persistencia: esta version no tiene mapeo JPA ni contrato de rehidratacion. Hay que
  acordarlos con quien implementa los repositorios, preservando identidad, estado y fechas.
- API: usar DTOs; no serializar directamente `User` (contiene el hash de password)
  ni exponer todo el agregado automaticamente.
- Archivos: validar formato y tamanio al cargar; coordinar su almacenamiento con la
  referencia que usa `Photo`.

## Patrones

El ciclo de vida implementa **State**, siguiendo la estructura de Refactoring Guru:
https://refactoring.guru/design-patterns/state

- Contexto: `Publication`, que conserva datos, estado actual y fecha de desactivacion.
- Contrato: `PublicationState`, con validacion de edicion, cierre, cancelacion y visibilidad.
- Estados concretos: `ActivePublication`, `ClosedPublication` y `CancelledPublication`.

El contexto delega las decisiones al estado mediante polimorfismo. El estado activo
permite editar y devuelve el siguiente estado al cerrar o cancelar. Los estados
terminales rechazan esas operaciones y no son visibles. La publicacion registra la
fecha y aplica el estado devuelto solo despues de una transicion valida. No expone
un setter que permita saltarse las reglas. Los estados son objetos sin datos mutables.

La justificacion es aislar las reglas que varian por estado y poder incorporar su
comportamiento sin distribuir comparaciones de estados por el dominio. Agregar una
nueva transicion a un estado existente aun requiere ajustar esa regla de negocio.
El costo son mas clases para un ciclo pequeno; se acepta por el enfoque academico
del TP y para mantener explicitas las responsabilidades.

`PublicationType`, `Species` y `PetSize` siguen siendo enums: representan conjuntos
de valores y actualmente no seleccionan comportamientos diferentes.

La integracion de IA podria implementar Adapter sobre `PetIdentification`, pero no
forma parte de este cambio. Repository se coordinara con persistencia y no es uno
de los 22 patrones GoF del catalogo de Refactoring Guru.
