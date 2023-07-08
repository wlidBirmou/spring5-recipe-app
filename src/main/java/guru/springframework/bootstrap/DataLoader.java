package guru.springframework.bootstrap;

import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.math.BigDecimal;
import java.util.LinkedHashSet;

//@Component
@Slf4j
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public DataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Recipe guacamole=Recipe.builder().ingredients(new LinkedHashSet<>()).build();
        guacamole.setDescription("Perfect guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDirections("One cut avocado, remove flesh: cut the avocados in half. remove seed score the inside of the avocado");
        guacamole.getIngredients().add(new Ingredient("Ripe avocados", BigDecimal.valueOf(2),unitOfMeasureRepository.findByDescription("Unit").get(),guacamole));
        guacamole.getIngredients().add(new Ingredient("Kosher salt", BigDecimal.valueOf(0.5),unitOfMeasureRepository.findByDescription("Teaspoon").get(),guacamole));
        guacamole.getIngredients().add(new Ingredient("Lemon juice", BigDecimal.valueOf(0.25),unitOfMeasureRepository.findByDescription("Tbsp").get(),guacamole));
        guacamole.getIngredients().add(new Ingredient("Red onion", BigDecimal.valueOf(0.25),unitOfMeasureRepository.findByDescription("Cup").get(),guacamole));
        guacamole.getIngredients().add(new Ingredient("Serrano chiles", BigDecimal.valueOf(1),unitOfMeasureRepository.findByDescription("Unit").get(),guacamole));
        guacamole.getIngredients().add(new Ingredient("Cilantro", BigDecimal.valueOf(2),unitOfMeasureRepository.findByDescription("Tablespoon").get(),guacamole));
        guacamole.getIngredients().add(new Ingredient("Black pepper", BigDecimal.valueOf(1),unitOfMeasureRepository.findByDescription("Dash").get(),guacamole));
        guacamole.getIngredients().add(new Ingredient("Tomato", BigDecimal.valueOf(0.5),unitOfMeasureRepository.findByDescription("Unit").get(),guacamole));

        this.recipeRepository.save(guacamole);

        Recipe grilledChicken=Recipe.builder().ingredients(new LinkedHashSet<>()).build();
        grilledChicken.setDirections("First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!");
        grilledChicken.setPrepTime(60);
        grilledChicken.setCookTime(30);
        Notes grilledChickenNotes=new Notes();
        grilledChickenNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.");
        grilledChicken.setNotes(grilledChickenNotes);
        grilledChicken.setDifficulty(Difficulty.MODERATE);
        grilledChicken.setDescription("Spicy grilled chicken tacos");
        grilledChicken.getIngredients().add(new Ingredient("Ancho chili powder", BigDecimal.valueOf(2),unitOfMeasureRepository.findByDescription("Tablespoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Dried oregano", BigDecimal.valueOf(1),unitOfMeasureRepository.findByDescription("Teaspoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Cumin", BigDecimal.valueOf(1),unitOfMeasureRepository.findByDescription("Teaspoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Sugar", BigDecimal.valueOf(1),unitOfMeasureRepository.findByDescription("Teaspoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Kosher Salt", BigDecimal.valueOf(0.5),unitOfMeasureRepository.findByDescription("Teaspoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Clove garlic", BigDecimal.valueOf(0.5),unitOfMeasureRepository.findByDescription("Unit").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Orange zest", BigDecimal.valueOf(1),unitOfMeasureRepository.findByDescription("Tablespoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Orange juice", BigDecimal.valueOf(3),unitOfMeasureRepository.findByDescription("Tablespoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("olive oil", BigDecimal.valueOf(2),unitOfMeasureRepository.findByDescription("Tablespoon").get(),grilledChicken));
        grilledChicken.getIngredients().add(new Ingredient("Chicken tighs", BigDecimal.valueOf(6),unitOfMeasureRepository.findByDescription("Unit").get(),grilledChicken));

        this.recipeRepository.save(grilledChicken);
        log.debug("Recipes Added....");

    }
}
