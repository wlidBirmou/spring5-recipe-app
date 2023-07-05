package guru.springframework.controller;

import guru.springframework.domain.Recipe;
import guru.springframework.service.RecipeService;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest extends TestCase {


    @Mock
    private RecipeService recipeService;
    private RecipeController recipeController;

    @Before
    public void setUp(){
       MockitoAnnotations.initMocks(this);
       this.recipeController=new RecipeController(recipeService);
    }
    public void testViewRecipe() throws Exception {
        Recipe recipe= Recipe.builder().id(1l).build();
        when(this.recipeService.findById(any())).thenReturn(recipe);
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.recipeController).build();
        mockMvc.perform(get("/recipes/view/1")).andExpect(status().isOk())
                .andExpect(view().name("recipe/view.html")).andExpect(model().attributeExists("recipe"));
    }

}