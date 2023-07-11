package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImpTest extends TestCase {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private IngredientService ingredientService;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.ingredientToIngredientCommand=new IngredientToIngredientCommand();
        this.ingredientCommandToIngredient=new IngredientCommandToIngredient();
        this.ingredientService=new IngredientServiceImp(ingredientRepository,recipeRepository,unitOfMeasureRepository,ingredientToIngredientCommand ,ingredientCommandToIngredient);

    }

    @Test
    public void testFindCommandById() {
        Ingredient ingredient=Ingredient.builder().id(1l).description("desc ingredient").build();
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        IngredientCommand ingredientCommandResult=this.ingredientService.findCommandById(1l);
        assertNotNull(ingredientCommandResult);
        assertEquals(ingredient.getId(), ingredientCommandResult.getId());
        assertEquals(ingredient.getDescription(), ingredientCommandResult.getDescription());
        verify(ingredientRepository,times(1)).findById(anyLong());
    }

    @Test
    public void testFindByRecipeIdAndIngredientId() {
        Recipe recipe=Recipe.builder().id(1l).ingredients(new LinkedHashSet<>()).build();
        Ingredient ingredient=Ingredient.builder().id(1l).build();
        recipe.getIngredients().add(ingredient);

        when(this.recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        IngredientCommand ingredientCommandResult=this.ingredientService.findByRecipeIdAndIngredientId(1l,1l);
        assertEquals(ingredient.getId(),ingredientCommandResult.getId());
        verify(this.recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    public void testSaveCommandUpdate() {
        UnitOfMeasure unitOfMeasure=UnitOfMeasure.builder().id(1l).description("unit").build();
        Recipe recipe=Recipe.builder().id(1l).description("recipeDesc").ingredients(new LinkedHashSet<>()).build();
        Ingredient existingIngredient=Ingredient.builder().id(1l).description("ingredientDesc").amount(BigDecimal.valueOf(10))
                .unitOfMeasure(unitOfMeasure).recipe(recipe).build();
        recipe.addIngredients(existingIngredient);

        when(this.recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(this.recipeRepository.save(any())).thenReturn(recipe);
        when(this.unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));

        IngredientCommand saveIngredientCommand=this.ingredientService.saveCommand(this.ingredientToIngredientCommand.convert(existingIngredient));
        assertEquals(existingIngredient.getId(),saveIngredientCommand.getId());
        assertEquals(existingIngredient.getAmount(),saveIngredientCommand.getAmount());
        assertEquals(existingIngredient.getDescription(),saveIngredientCommand.getDescription());
        assertEquals(existingIngredient.getUnitOfMeasure().getId(),saveIngredientCommand.getUnitOfMeasure().getId());

        verify(this.recipeRepository,times(1)).save(any());
        verify(this.ingredientRepository,times(0 )).save(any());
    }

    @Test
    public void testSaveCommandNewIngredient(){
        UnitOfMeasure unitOfMeasure=UnitOfMeasure.builder().id(1l).description("unit").build();
        Recipe recipe=Recipe.builder().id(1l).description("recipeDesc").ingredients(new LinkedHashSet<>()).build();
        Ingredient existingIngredient=Ingredient.builder().id(1l).description("ingredientDesc").amount(BigDecimal.valueOf(10))
                .unitOfMeasure(unitOfMeasure).recipe(recipe).build();

        when(this.recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(this.recipeRepository.save(any())).thenReturn(recipe);
        when(this.unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));

        IngredientCommand saveIngredientCommand=this. ingredientService.saveCommand(this.ingredientToIngredientCommand.convert(existingIngredient));
        assertEquals(existingIngredient.getId(),saveIngredientCommand.getId());
        assertEquals(existingIngredient.getAmount(),saveIngredientCommand.getAmount());
        assertEquals(existingIngredient.getDescription(),saveIngredientCommand.getDescription());
        assertEquals(existingIngredient.getUnitOfMeasure().getId(),saveIngredientCommand.getUnitOfMeasure().getId());

        verify(this.recipeRepository,times(1)).save(any());
        verify(this.ingredientRepository,times(0 )).save(any());
    }



}