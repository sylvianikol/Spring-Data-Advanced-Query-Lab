package demos.springdata.advanced.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "shampoos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shampoo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brand;
    private double price;

    @Enumerated(EnumType.ORDINAL)
    private Size size;

    // JPA defaults -> -TO-MANY = LAZY / -TO-ONE = EAGER
    @ManyToOne(optional = true, cascade = {PERSIST, REFRESH})
    private Label label;

    @ManyToMany(cascade = {PERSIST, REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "shampoo_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shampoo)) return false;
        Shampoo shampoo = (Shampoo) o;
        return id == shampoo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2flv.",
                this.getBrand(),
                this.getSize(),
                this.getPrice());
    }
}
