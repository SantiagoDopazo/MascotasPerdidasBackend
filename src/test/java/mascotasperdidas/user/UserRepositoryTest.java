package mascotasperdidas.user;

import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class UserRepositoryTest {
    // El hash ya calculado se recibe como dato; BCrypt se incorporara con el registro.
    private static final String PASSWORD_HASH = "test-hash";

    @Autowired
    private UserRepository users;

    @Autowired
    private EntityManager entityManager;

    @Test
    void savesAndReloadsUserFromPostgres() {
        String email = newEmail();
        User saved = users.saveAndFlush(new User("Santiago", email, PASSWORD_HASH));
        UUID id = saved.getId();
        assertThat(id).isNotNull();

        // Vaciar el contexto para verificar la lectura real, no el objeto en memoria.
        entityManager.clear();
        User loaded = users.findById(id).orElseThrow();
        assertThat(loaded.getName()).isEqualTo("Santiago");
        assertThat(loaded.getEmail()).isEqualTo(email);
        assertThat(loaded.getPasswordHash()).isEqualTo(PASSWORD_HASH);
    }

    @Test
    void normalizesAndFindsEmail() {
        String email = newEmail();
        users.saveAndFlush(new User("Santiago", " " + email.toUpperCase(java.util.Locale.ROOT) + " ", PASSWORD_HASH));
        entityManager.clear();

        assertThat(users.findByEmail(email)).isPresent();
        assertThat(users.existsByEmail(email)).isTrue();
    }

    @Test
    void rejectsDuplicateEmailIgnoringCase() {
        String email = newEmail();
        users.saveAndFlush(new User("Santiago", email, PASSWORD_HASH));

        assertThatThrownBy(() -> users.saveAndFlush(
                new User("Another user", email.toUpperCase(java.util.Locale.ROOT), PASSWORD_HASH)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void rejectsMissingPasswordHash() {
        assertThatThrownBy(() -> users.saveAndFlush(new User("Santiago", newEmail(), null)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void returnsEmptyForUnknownEmail() {
        String email = newEmail();
        assertThat(users.findByEmail(email)).isEmpty();
        assertThat(users.existsByEmail(email)).isFalse();
    }

    private static String newEmail() {
        return UUID.randomUUID() + "@example.com";
    }
}
