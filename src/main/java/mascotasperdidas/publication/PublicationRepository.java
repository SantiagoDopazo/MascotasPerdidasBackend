package mascotasperdidas.publication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    @Query("select p from Publication p where type(p.state) = :stateType order by p.createdAt desc, p.id desc")
    Page<Publication> findByStateType(Class<? extends PublicationState> stateType, Pageable pageable);

    default Page<Publication> findActive(Pageable pageable) {
        return findByStateType(ActivePublication.class, pageable);
    }
}
