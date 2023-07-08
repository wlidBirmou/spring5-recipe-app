package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class RecipeToRecipeCommandTest extends TestCase {

    private RecipeToRecipeCommand recipeToRecipeCommand;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    private final Long ID=3l;
    private final String DESCRIPTION="desc";
    private final Integer PREP_TIME=30;
    private final Integer COOK_TIME=40;
    private final Integer SERVINGS=50;
    private final String SOURCE="src";
    private final String URL="http://som.com";
    private final String DIRECTIONS="directions";
    private final Byte[] IMAGE={1,1,0,1,1};
    private final Difficulty DIFFICULTY=Difficulty.MODERATE;
    private final Notes NOTES= Notes.builder().id(1l).recipeNotes("recipeNotes1").build();
    private Set<Ingredient> INGREDIENTS=new LinkedHashSet<>();
    private Set<Category> CATEGORIES=new LinkedHashSet<>();




    @Before
    public void setUp() throws Exception {
        this.ingredientToIngredientCommand=new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.categoryToCategoryCommand=new CategoryToCategoryCommand();
        this.recipeToRecipeCommand=new RecipeToRecipeCommand(
                this.ingredientToIngredientCommand,
                this.categoryToCategoryCommand,new NotesToNotesCommand());

    }

    @Test
    public void testNullConvertion() {
        Recipe recipe = null;
        RecipeCommand recipeCommand = this.recipeToRecipeCommand.convert(recipe);
        this.INGREDIENTS.add(Ingredient.builder().id(1l).description("desc1").build());
        this.INGREDIENTS.add(Ingredient.builder().id(2l).description("desc2").build());

        this.CATEGORIES.add(Category.builder().id(1l).description("desc1").build());
        this.CATEGORIES.add(Category.builder().id(2l).description("desc2").build());

    }
    @Test
    public void testIfEmpty(){
        Recipe recipe=Recipe.builder().build();
        assertNotNull(this.recipeToRecipeCommand.convert(recipe));
    }

    @Test
    public void testConvert() {
        Recipe recipe=Recipe.builder()
                .id(this.ID)
                .description(this.DESCRIPTION)
                .prepTime(this.PREP_TIME)
                .cookTime(this.COOK_TIME)
                .servings(this.SERVINGS)
                .source(this.SOURCE)
                .url(this.URL)
                .directions(this.DIRECTIONS)
                .image(this.IMAGE)
                .difficulty(this.DIFFICULTY)
                .ingredients(this.INGREDIENTS)
                .categories(this.CATEGORIES)
                .notes(this.NOTES)
                .build();
        RecipeCommand recipeCommand=this.recipeToRecipeCommand.convert(recipe);
        assertNotNull(recipe);
        assertEquals(this.ID, recipeCommand.getId());
        assertEquals(this.DESCRIPTION, recipeCommand.getDescription());
        assertEquals(this.PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(this.COOK_TIME, recipeCommand.getCookTime());
        assertEquals(this.SERVINGS, recipeCommand.getServings());
        assertEquals(this.SOURCE, recipeCommand.getSource());
        assertEquals(this.URL, recipeCommand.getUrl());
        assertEquals(this.DIRECTIONS, recipeCommand.getDirections());
        assertEquals(this.IMAGE, recipeCommand.getImage());
        assertEquals(this.DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(this.INGREDIENTS.size(), recipeCommand.getIngredientCommands().size());
        assertEquals(this.CATEGORIES.size(), recipeCommand.getCategoryCommands().size());
    }
}