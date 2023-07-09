package guru.springframework.controller;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;



public class IngredientControllerTest extends TestCase {

    @Mock
    private IngredientService ingredientService;
    @Mock
    private RecipeService recipeService;
    private IngredientController ingredientController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.ingredientController=new IngredientController(recipeService,ingredientService);
    }

    @Test
    public void testListIngredents() throws Exception {
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.ingredientController).build();
        when(recipeService.findCommandById(anyLong())).thenReturn(RecipeCommand.builder().id(1l).description("desc").build());
        mockMvc.perform(get("/recipe/1/ingredient/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

    }
}