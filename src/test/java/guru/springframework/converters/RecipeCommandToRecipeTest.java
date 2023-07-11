package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class RecipeCommandToRecipeTest extends TestCase {

    private RecipeCommandToRecipe recipeCommandToRecipe;

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
    private Notes NOTES = Notes.builder().id(1l).recipeNotes("recipeNotes1").build();
    private Set<Ingredient> INGREDIENTS =new LinkedHashSet<>();
    private Set<Category> CATEGORIES =new LinkedHashSet<>();


    @Before
    public void setUp() throws Exception {
        this.recipeCommandToRecipe=new RecipeCommandToRecipe();

        this.INGREDIENTS.add(Ingredient.builder().id(1l).description("desc1").build());
        this.INGREDIENTS.add(Ingredient.builder().id(2l).description("desc2").build());

        this.CATEGORIES.add(Category.builder().id(1l).description("desc1").build());
        this.CATEGORIES.add(Category.builder().id(2l).description("desc2").build());
    }

    @Test
    public void testNullConvertion() {
        RecipeCommand recipeCommand = null;
        Recipe recipe = this.recipeCommandToRecipe.convert(recipeCommand);
        assertNull(recipe);
    }
    @Test
    public void testIfEmpty(){
        RecipeCommand recipeCommand=RecipeCommand.builder().build();
        assertNotNull(this.recipeCommandToRecipe.convert(recipeCommand));
    }

    @Test
    public void testConvert() {
        RecipeCommand recipeCommand=RecipeCommand.builder()
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
                .notes(this.NOTES)
                .ingredients(this.INGREDIENTS)
                .categories(this.CATEGORIES)
                .build();


        Recipe recipe=this.recipeCommandToRecipe.convert(recipeCommand);
        assertNotNull(recipeCommand);
        assertEquals(this.ID, recipe.getId());
        assertEquals(this.DESCRIPTION, recipe.getDescription());
        assertEquals(this.PREP_TIME, recipe.getPrepTime());
        assertEquals(this.COOK_TIME, recipe.getCookTime());
        assertEquals(this.SERVINGS, recipe.getServings());
        assertEquals(this.SOURCE, recipe.getSource());
        assertEquals(this.URL, recipe.getUrl());
        assertEquals(this.DIRECTIONS, recipe.getDirections());
        assertEquals(this.IMAGE, recipe.getImage());
        assertEquals(this.DIFFICULTY, recipe.getDifficulty());
        assertEquals(this.INGREDIENTS.size(), recipe.getIngredients().size());
        assertEquals(this.CATEGORIES.size(), recipe.getCategories().size());
        assertEquals(this.NOTES.getId(), recipe.getNotes().getId());
        assertEquals(this.NOTES.getRecipeNotes(), recipe.getNotes().getRecipeNotes());
    }
}