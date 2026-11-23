package mascotasperdidas.publication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PublicationRepository extends JpaRepository<Publication, Long>, JpaSpecificationExecutor<Publication> {    @Query("select p from Publication p where type(p.state) = :stateType order by p.createdAt desc, p.id desc")
    Page<Publication> findByStateType(Class<? extends PublicationState> stateType, Pageable pageable);

    default Page<Publication> findActive(Pageable pageable) {
        return findByStateType(ActivePublication.class, pageable);
    }
    Page<Publication> findByAuthorIdOrderByCreatedAtDescIdDesc(Long authorId, Pageable pageable);

    default Page<Publication> findFeed(PublicationFilter filter, Pageable pageable) {
        Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt", "id");
        Pageable ordered = pageable;
        if (pageable.isPaged() && pageable.getSort().isUnsorted()) {
            ordered = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);
        }
        return findAll(PublicationSpecifications.activeWith(filter), ordered);
    }

}
