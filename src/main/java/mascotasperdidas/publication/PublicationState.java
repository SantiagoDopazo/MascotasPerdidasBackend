package mascotasperdidas.publication;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.DiscriminatorFormula;

@Embeddable
@DiscriminatorFormula("etapa")
public abstract class PublicationState {
    @Column(name = "etapa", nullable = false, length = 20)
    private String code;

    protected PublicationState(String code) {
        this.code = code;
    }

    public abstract void validateEditing();

    public abstract PublicationState close();

    public abstract PublicationState cancel();

    public abstract boolean isVisible();
}
