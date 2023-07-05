package guru.springframework.service;

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

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        recipeService=new RecipeServiceImpl(this.recipeRepository);

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
}