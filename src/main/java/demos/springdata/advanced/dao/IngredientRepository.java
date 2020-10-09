package demos.springdata.advanced.dao;

import demos.springdata.advanced.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(String name);

    List<Ingredient> findByNameStartingWith(String s);

//    @Query("SELECT i FROM Ingredient i WHERE i IN :ingredients ORDER BY i.price")
//    List<Ingredient> findWithIngredientsInListOrderByPrice(@Param(value = "ingredients")
//                                                       List<Ingredient> ingredients);

    List<Ingredient> findByNameInOrderByPriceAsc(List<String> names);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.10 WHERE i.name IN :names")
    int updateIngredientsPriceByNames(@Param(value = "names") List<String> names);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ingredient i WHERE i.name IN :names")
    int deleteIngredientsByNames(@Param(value = "names") List<String> names);
}
