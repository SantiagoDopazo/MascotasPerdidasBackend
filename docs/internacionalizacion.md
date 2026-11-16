# Internacionalizacion de mensajes

Se usa `MessageSource`, incluido en Spring, sin dependencias adicionales.

- `src/main/resources/messages.properties`: mensajes base en ingles y fallback.
- `src/main/resources/messages_es.properties`: traducciones al español, tambien para es-AR.
- `MessageResolver`: componente inyectable que recibe codigo, Locale y argumentos opcionales.

El dominio genera codigos estables en los mensajes de sus excepciones, por ejemplo
`publication.closed.edit`. No depende de Spring ni conoce el idioma del usuario.
Las excepciones mantienen sus tipos actuales: IllegalArgumentException,
IllegalStateException y NullPointerException para argumentos obligatorios nulos.

Ejemplo de uso desde la capa de aplicacion/API:

```java
messageResolver.resolve("publication.closed.edit", Locale.forLanguageTag("es-AR"));
// "No se puede editar una publicación cerrada."
```

Al implementar los endpoints, su manejador de errores debera resolver los codigos
conocidos del dominio usando el Locale de la peticion (por ejemplo, Accept-Language).
Todavia no hay un manejador HTTP de errores ni endpoints de publicaciones: agregar
los bundles no traduce automaticamente las respuestas de excepciones de Spring.
No se debe enviar al resolver el texto de cualquier excepcion inesperada.

Para agregar un idioma, crear `messages_fr.properties`, por ejemplo, con las mismas
claves. Para parametros, usar `{0}`, `{1}`, etc. y pasarlos a `resolve`.
Los idiomas sin traduccion usan los mensajes base en ingles, independientemente del
idioma del sistema operativo. Una clave inexistente lanza NoSuchMessageException,
para que un error de configuracion no se confunda con un mensaje traducido.

Los identificadores Java estan en ingles. El paquete raiz `mascotasperdidas` se
conserva como nombre del proyecto. El mapeo JPA de `User` conserva la tabla `usuarios`
y la columna `nombre` para no requerir cambios en la base existente.
