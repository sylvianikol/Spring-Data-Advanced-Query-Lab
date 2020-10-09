package demos.springdata.advanced.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title;
    private String subtitle;

    // JPA defaults -> -TO-MANY = LAZY / -TO-ONE = EAGER
    @OneToMany(mappedBy = "label", fetch = FetchType.EAGER)
    private Set<Shampoo> shampoos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label)) return false;
        Label label = (Label) o;
        return id == label.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.getTitle(), this.getSubtitle());
    }
}
