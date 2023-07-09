package guru.springframework.service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.*;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeRepository recipeRepository;

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


    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        RecipeToRecipeCommand recipeToRecipeCommand=new RecipeToRecipeCommand(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand(),new NotesToNotesCommand());
        RecipeCommandToRecipe recipeCommandToRecipe=new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure())
                ,new CategoryCommandToCategory(),new NotesCommandToNotes());
        recipeService=new RecipeServiceImpl(this.recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);
    }

    @Test
    public void testGetRecipes() {

        Set<Recipe> recipes= new LinkedHashSet<>();
        recipes.add(Recipe.builder().id(1l).description("recipe1").build());
        recipes.add(Recipe.builder().id(2l).description("recipe2").build());
        when(recipeRepository.findAll()).thenReturn(recipes);
        Set<Recipe> resultSet=this.recipeService.getRecipes();
        assertNotNull(resultSet);
        assertEquals(recipes,resultSet);
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Recipe recipe=Recipe.builder().id(1l).description("recipe1").build();
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        Recipe recipeResult=this.recipeService.findById(1l);
        assertNotNull(recipeResult);
        assertEquals(recipe,recipeResult);
        verify(recipeRepository,times(1)).findById(any());
    }


    @Test
    public void testFindCommandById() {
        Recipe recipe=Recipe.builder().id(1l).description("recipe1").build();
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        RecipeCommand recipeCommandResult=this.recipeService.findCommandById(1l);
        assertNotNull(recipeCommandResult);
        assertEquals(recipe.getId(),recipeCommandResult.getId());
        assertEquals(recipe.getDescription(),recipeCommandResult.getDescription());
        verify(recipeRepository,times(1)).findById(any());
    }

    @Test
    public void testSave(){
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
                .build();
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
                .build();
        when(recipeRepository.save(any())).thenReturn(recipe);
        RecipeCommand savedRecipeCommand=this.recipeService.saveCommand(recipeCommand);
        assertEquals(recipeCommand.getId(),savedRecipeCommand.getId());
        assertEquals(recipeCommand.getDescription(),savedRecipeCommand.getDescription());
        assertEquals(recipeCommand.getUrl(),savedRecipeCommand.getUrl());
        assertEquals(recipeCommand.getPrepTime(),savedRecipeCommand.getPrepTime());
        assertEquals(recipeCommand.getCookTime(),savedRecipeCommand.getCookTime());
        assertEquals(recipeCommand.getDirections(),savedRecipeCommand.getDirections());
        assertEquals(recipeCommand.getImage(),savedRecipeCommand.getImage());
        assertEquals(recipeCommand.getDifficulty(),savedRecipeCommand.getDifficulty());
        assertEquals(recipeCommand.getServings(),savedRecipeCommand.getServings());
        verify(this.recipeRepository,times(1)).save(any());
    }



    @Test
    public void testSaveNull(){
        RecipeCommand recipeCommand=null;
        when(recipeRepository.save(null)).thenReturn(null);
        assertNull(this.recipeService.saveCommand(recipeCommand));
        verify(this.recipeRepository,times(0)).save(any());

    }
}