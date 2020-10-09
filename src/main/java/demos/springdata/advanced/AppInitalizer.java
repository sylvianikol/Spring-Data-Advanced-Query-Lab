package demos.springdata.advanced;

import demos.springdata.advanced.dao.IngredientRepository;
import demos.springdata.advanced.dao.LabelRepository;
import demos.springdata.advanced.dao.ShampooRepository;
import demos.springdata.advanced.entities.Label;
import demos.springdata.advanced.entities.Shampoo;
import demos.springdata.advanced.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppInitalizer implements ApplicationRunner {

    private final ShampooRepository shampooRepository;
    private final LabelRepository labelRepository;
    private final IngredientRepository ingredientRepository;

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Autowired
    public AppInitalizer(ShampooRepository shampooRepository,
                         LabelRepository labelRepository,
                         IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.labelRepository = labelRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 1. Select Shampoos by Size
        // this.selectShampoosBySize();

        // 2. Select Shampoos by Size or Label
        // this.selectShampoosBySizeOrLabel();

        // 3. Select Shampoos by Price
        // this.selectShampoosByPrice();

        // 4. Select Ingredients by Name
        // this.selectIngredientsByName();

        // 5. Select Ingredients by Names
        // this.selectIngredientsByNames();

        // 6. Count Shampoos by Price
        // this.countShampoosByPrice();

        // 7. Select Shampoos by Ingredients
        // this.selectShampoosByIngredients();

        // 8. Select Shampoos by Ingredients Count
        // this.selectShampoosByIngredientsCount();

        // 9. Delete Ingredients by Name
        // this.deleteIngredientsByName();

        // 10. Update Ingredients by Price
        // this.updateIngredientsByPrice();

        // BONUS: Print Shampoo by Pages of 5
        // this.printShampoosByPagesOf5();

    }

    // BONUS: Print Shampoo by Pages of 5
    private void printShampoosByPagesOf5() {
        Page<Shampoo> page = this.shampooRepository
                .findAll(PageRequest.of(0, 5));

        System.out.printf("%nPage %d of %d: %n----------------------%n",
                page.getNumber(), page.getTotalPages());

        page.forEach(System.out::println);

        while (page.hasNext()) {
            page = this.shampooRepository.findAll(page.nextPageable());
            System.out.printf("%nPage %d of %d: %n----------------------%n",
                    page.getNumber(), page.getTotalPages());

            page.forEach(System.out::println);
        }
    }

    // 10. Update Ingredients by Price
    private void updateIngredientsByPrice() throws IOException {
        List<String> names = getNames();

        System.out.println(" ---------- BEFORE PRICE UPDATE: ----------");
        this.ingredientRepository.findByNameInOrderByPriceAsc(names)
                .forEach(System.out::println);

        int updatedRecords = this.ingredientRepository.updateIngredientsPriceByNames(names);

        System.out.println(" ---------- AFTER PRICE UPDATE: ----------");
        if (updatedRecords > 0) {
            System.out.println("Updated records: " + updatedRecords);
            this.ingredientRepository.findByNameInOrderByPriceAsc(names)
                    .forEach(System.out::println);
        }

    }

    // 9. Delete Ingredients by Name
    private void deleteIngredientsByName() throws IOException {

        List<String> names = this.getNames();

        System.out.println(" ---------- BEFORE DELETE: ----------");
        this.ingredientRepository
                .findAll()
                .forEach(System.out::println);

        int deletedRecords = this.ingredientRepository.deleteIngredientsByNames(names);

        System.out.println();
        System.out.println("Records deleted: " + deletedRecords);
        System.out.println(" ---------- AFTER DELETE: ----------");
        if (deletedRecords > 0) {
            this.ingredientRepository
                    .findAll()
                    .forEach(System.out::println);
        }

    }

    // 8. Select Shampoos by Ingredients Count
    private void selectShampoosByIngredientsCount() throws IOException {
        int count = Integer.parseInt(this.reader.readLine());

        this.shampooRepository
                .findByIngredientsCountLowerThan(count)
                .forEach(System.out::println);
    }

    // 7. Select Shampoos by Ingredients
    private void selectShampoosByIngredients() throws IOException {
        String firstIngredient = this.reader.readLine();
        String secondIngredient = this.reader.readLine();

        this.shampooRepository
                .findWithIngredientsInList(List.of(
                        this.ingredientRepository.findByName(firstIngredient),
                        this.ingredientRepository.findByName(secondIngredient))
                ).forEach(s -> System.out.println(s.getBrand()));
    }

    // 6. Count Shampoos by Price
    private void countShampoosByPrice() throws IOException {
        double price = Double.parseDouble(this.reader.readLine());

        System.out.println(this.shampooRepository.countAllByPriceIsLessThan(price));
    }

    // 5. Select Ingredients by Names
    private void selectIngredientsByNames() throws IOException {
        // 1. Varian JPQL

//        List<Ingredient> ingredients = new ArrayList<>();
//
//        while(true) {
//            final String name = this.reader.readLine();
//            if (name == null || "".equals(name.trim())) break;
//
//            Ingredient ingredient = this.ingredientRepository.findByName(name);
//            ingredients.add(ingredient);
//        }
//
//        this.reader.close();
//
//        this.ingredientRepository
//                .findWithIngredientsInListOrderByPrice(ingredients)
//                .forEach(System.out::println);

        // 2. Variant - Query creation from method names

        List<String> names = getNames();

        this.ingredientRepository
                .findByNameInOrderByPriceAsc(names)
                .forEach(System.out::println);
    }

    // 4. Select Ingredients by Name
    private void selectIngredientsByName() throws IOException {
        String s = this.reader.readLine();

        this.ingredientRepository.findByNameStartingWith(s)
                .forEach(System.out::println);
    }

    // 3. Select Shampoos by Price
    private void selectShampoosByPrice() throws IOException {
        double price = Double.parseDouble(this.reader.readLine());

        this.shampooRepository
                .findByPriceGreaterThanOrderByPriceDesc(price)
                .forEach(System.out::println);
    }

    // 2. Select Shampoos by Size or Label
    private void selectShampoosBySizeOrLabel() throws IOException {
        Size size = Size.valueOf(this.reader.readLine().toUpperCase());
        long labelId = Integer.parseInt(this.reader.readLine());

        Label label = this.labelRepository.getOne(labelId);
        // Label label = this.labelRepository.findOneById(labelId);

        this.shampooRepository
                .findBySizeOrLabelOrderByPriceAsc(size, label)
                .forEach(System.out::println);
    }

    // 1. Select Shampoos by Size
    private void selectShampoosBySize() throws IOException {
        String size = this.reader.readLine();

        this.shampooRepository
                .findBySize(Size.valueOf(size.toUpperCase()))
                .forEach(System.out::println);
    }

    // HELPERS
    private List<String> getNames() throws IOException {
        List<String> names = new ArrayList<>();

        while (true) {
            final String name = this.reader.readLine();
            if (name == null || "".equals(name.trim())) break;

            names.add(name);
        }
        return names;
    }
}
