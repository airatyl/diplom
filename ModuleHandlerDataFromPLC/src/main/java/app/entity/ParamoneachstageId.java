package app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ParamoneachstageId implements java.io.Serializable {
    private static final long serialVersionUID = -5687964405822273232L;
    @Column(name = "controlparam", nullable = false, length = 45)
    private String controlparam;

    @Column(name = "moldingstage", nullable = false)
    private Integer moldingstage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ParamoneachstageId entity = (ParamoneachstageId) o;
        return Objects.equals(this.controlparam, entity.controlparam) &&
                Objects.equals(this.moldingstage, entity.moldingstage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controlparam, moldingstage);
    }

}