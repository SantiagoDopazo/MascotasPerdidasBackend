package mascotasperdidas.publication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Page<Publication> findByStateOrderByCreatedAtDescIdDesc(PublicationState state, Pageable pageable);

    default Page<Publication> findActive(Pageable pageable) {
        return findByStateOrderByCreatedAtDescIdDesc(new ActivePublication(), pageable);
    }
}
