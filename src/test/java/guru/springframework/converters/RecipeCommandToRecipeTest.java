package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class RecipeCommandToRecipeTest extends TestCase {

    private RecipeCommandToRecipe recipeCommandToRecipe;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private CategoryCommandToCategory categoryCommandToCategory;
    private NotesCommandToNotes notesCommandToNotes;


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
    private NotesCommand NOTES_COMMAND =NotesCommand.builder().id(1l).recipeNotes("recipeNotes1").build();
    private Set<IngredientCommand> INGREDIENTS_COMMAND=new LinkedHashSet<>();
    private Set<CategoryCommand> CATEGORIES_COMMAND=new LinkedHashSet<>();


    @Before
    public void setUp() throws Exception {
        this.notesCommandToNotes=new NotesCommandToNotes();
        this.ingredientCommandToIngredient=new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.categoryCommandToCategory=new CategoryCommandToCategory();
        this.recipeCommandToRecipe=new RecipeCommandToRecipe(this.ingredientCommandToIngredient,this.categoryCommandToCategory,this.notesCommandToNotes);

        this.INGREDIENTS_COMMAND.add(IngredientCommand.builder().id(1l).description("desc1").build());
        this.INGREDIENTS_COMMAND.add(IngredientCommand.builder().id(2l).description("desc2").build());

        this.CATEGORIES_COMMAND.add(CategoryCommand.builder().id(1l).description("desc1").build());
        this.CATEGORIES_COMMAND.add(CategoryCommand.builder().id(2l).description("desc2").build());
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
                .notes(this.NOTES_COMMAND)
                .ingredients(this.INGREDIENTS_COMMAND)
                .categories(this.CATEGORIES_COMMAND)
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
        assertEquals(this.INGREDIENTS_COMMAND.size(), recipe.getIngredients().size());
        assertEquals(this.CATEGORIES_COMMAND.size(), recipe.getCategories().size());
        assertEquals(this.NOTES_COMMAND.getId(), recipe.getNotes().getId());
        assertEquals(this.NOTES_COMMAND.getRecipeNotes(), recipe.getNotes().getRecipeNotes());
    }
}