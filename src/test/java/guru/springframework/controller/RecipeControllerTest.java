package guru.springframework.controller;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.service.RecipeService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeControllerTest extends TestCase {


    @Mock
    private RecipeService recipeService;
    private RecipeController recipeController;

    @Before
    public void setUp(){
       MockitoAnnotations.initMocks(this);
       this.recipeController=new RecipeController(recipeService);
    }
    @Test
    public void testViewRecipe() throws Exception {
        Recipe recipe= Recipe.builder().id(1l).build();
        when(this.recipeService.findById(any())).thenReturn(recipe);
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.recipeController).build();
        mockMvc.perform(get("/recipes/view/1")).andExpect(status().isOk())
                .andExpect(view().name("recipe/view.html")).andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testNewRecipe() throws Exception {
        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(this.recipeController).build();
        mockMvc.perform(get("/recipe/new")).andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
    @Test
    public void testUpdateRecipe() throws Exception {
        RecipeCommand recipeCommand= RecipeCommand.builder().id(1l).build();
        when(this.recipeService.findCommandById(any())).thenReturn(recipeCommand);
        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(this.recipeController).build();
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
    @Test
    public void testSaveOrUpdate() throws Exception {
        RecipeCommand recipeCommand= RecipeCommand.builder().id(1l).build();
        when(this.recipeService.saveCommand(any())).thenReturn(recipeCommand);

        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(this.recipeController).build();
        mockMvc.perform(post("/recipe/recipe_post").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/view/1"));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        RecipeCommand recipeCommand= RecipeCommand.builder().id(1l).build();


        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(this.recipeController).build();
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/{id}/ingredient/list"));
        verify(this.recipeService,times(1)).deleteById(anyLong());


    }


}