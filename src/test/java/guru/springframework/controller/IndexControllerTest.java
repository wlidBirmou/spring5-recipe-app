package guru.springframework.controller;

import guru.springframework.repositories.RecipeRepository;
import guru.springframework.service.RecipeService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest extends TestCase {

    @Mock
    private RecipeService recipeService;
    private IndexController  indexController;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private Model model;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this.recipeRepository);
        MockitoAnnotations.initMocks(this.model);
        MockitoAnnotations.initMocks(this.recipeService);
        this.indexController=new IndexController(this.recipeService);
    }


    @Test
    public void testGetIndexPage() {
        assertEquals(this.indexController.getIndexPage(model),"index");
        Mockito.verify(this.recipeService,times(1)).getRecipes();
        Mockito.verify(this.model,times(1)).addAttribute(Mockito.eq("recipes"),Mockito.anySet());
    }

    @Test
    public void testMockMVC() throws Exception{

    }
}