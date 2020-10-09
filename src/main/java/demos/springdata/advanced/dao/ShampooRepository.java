package demos.springdata.advanced.dao;

import demos.springdata.advanced.entities.Ingredient;
import demos.springdata.advanced.entities.Label;
import demos.springdata.advanced.entities.Shampoo;
import demos.springdata.advanced.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findBySize(Size size);

    List<Shampoo> findBySizeOrLabelOrderByPriceAsc(Size size, Label label);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(double price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i IN :ingredients")
    List<Shampoo> findWithIngredientsInList(@Param(value = "ingredients")
                                                    List<Ingredient> ingredients);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size < :count")
    List<Shampoo> findByIngredientsCountLowerThan(@Param(value = "count") int count);

    int countAllByPriceIsLessThan(double price);
}
