package guru.springframework.controller;

import exceptions.NotFoundException;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.service.RecipeService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest extends TestCase {


    @Mock
    private RecipeService recipeService;
    private RecipeController recipeController;
    private MockMvc mockMvc;
    @Before
    public void setUp(){
       MockitoAnnotations.initMocks(this);
       this.recipeController=new RecipeController(recipeService);
       this.mockMvc= MockMvcBuilders.standaloneSetup(this.recipeController).setControllerAdvice(new ControllerExceptionAdvice()).build();
    }
    @Test
    public void testViewRecipe() throws Exception {
        Recipe recipe= Recipe.builder().id(1l).build();
        when(this.recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/recipes/view/1")).andExpect(status().isOk())
                .andExpect(view().name("recipe/view.html")).andExpect(model().attributeExists("recipe"));
    }
    @Test(expected = NotFoundException.class)
    public void testViewRecipeNotFound() throws Exception {
        when(this.recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/recipes/view/1"))
                .andExpect(view().name("404error"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testNewRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new")).andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
    @Test
    public void testUpdateRecipe() throws Exception {
        RecipeCommand recipeCommand= RecipeCommand.builder().id(1l).build();
        when(this.recipeService.findCommandById(any())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
    @Test
    public void testSaveOrUpdate() throws Exception {
        RecipeCommand recipeCommand= RecipeCommand.builder().id(1l).directions("do it in").build();

        when(this.recipeService.saveCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe/recipe_post").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","some string")
                        .param("directions", "some directions"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/view/1"));
    }

    @Test
    public void testDeleteRecipe() throws Exception {

        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/{id}/ingredient/list"));
        verify(this.recipeService,times(1)).deleteById(anyLong());
    }




}