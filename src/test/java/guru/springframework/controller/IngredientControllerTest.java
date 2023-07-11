package guru.springframework.controller;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



public class IngredientControllerTest extends TestCase {

    @Mock
    private IngredientService ingredientService;
    @Mock
    private RecipeService recipeService;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;
    private IngredientController ingredientController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.ingredientController=new IngredientController(recipeService,ingredientService,unitOfMeasureService);
    }

    @Test
    public void testListIngredients() throws Exception {
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.ingredientController).build();
        when(recipeService.findCommandById(anyLong())).thenReturn(RecipeCommand.builder().id(1l).description("desc").build());
        mockMvc.perform(get("/recipe/1/ingredient/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

    }
    @Test
    public void testViewIngredient() throws Exception {
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.ingredientController).build();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(IngredientCommand.builder().id(1l).description("desc ingredient").build());
        mockMvc.perform(get("/recipe/1/ingredient/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/view"))
                .andExpect(model().attributeExists("ingredient"));

        verifyNoMoreInteractions(this.recipeService);
        verify(this.ingredientService,times(1)).findByRecipeIdAndIngredientId(anyLong(),anyLong());

    }

    @Test
    public void testNewIngredientForm() throws Exception {


        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.ingredientController).build();
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasureList"))
                .andExpect(model().attribute("ingredient",notNullValue()))
                .andExpect(model().attribute("unitOfMeasureList",notNullValue()));
    }

    @Test
    public void testUpdateIngredient() throws Exception {

        Set<UnitOfMeasureCommand>  measureCommands=new LinkedHashSet<>();
        measureCommands.add(UnitOfMeasureCommand.builder().id(1l).description("desc1").build());
        measureCommands.add(UnitOfMeasureCommand.builder().id(2l).description("desc2").build());
        measureCommands.add(UnitOfMeasureCommand.builder().id(3l).description("desc3").build());

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(IngredientCommand.builder().id(1l).description("desc ingredient")
                .recipe(Recipe.builder().id(1l).description("desc").build())
                        .unitOfMeasure(UnitOfMeasure.builder().id(1l).description("desc").build())
                .build());
        when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(measureCommands);

        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.ingredientController).build();
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasureList"))
                .andExpect(model().attribute("ingredient",notNullValue()))
                .andExpect(model().attribute("ingredient",hasProperty("unitOfMeasure",notNullValue())))
                .andExpect(model().attribute("ingredient",hasProperty("recipe",notNullValue())))
                .andExpect(model().attribute("unitOfMeasureList",notNullValue()));

        verifyNoMoreInteractions(this.recipeService);
        verify(this.ingredientService,times(1)).findByRecipeIdAndIngredientId(anyLong(),anyLong());
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        Recipe recipe=Recipe.builder().id(1l).description("desc").ingredients(new LinkedHashSet<>()).build();
        IngredientCommand ingredientCommand=IngredientCommand.builder().id(1l).description("desc ingredients").recipe(recipe).build();

        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredientCommand);

        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.ingredientController).build();
        mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection());
        verify(this.ingredientService,times(1)).deleteById(anyLong(),anyLong());
    }


}