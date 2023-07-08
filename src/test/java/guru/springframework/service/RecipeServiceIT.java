package guru.springframework.service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION="NEW Description";
    @Autowired
     RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;


    @Test
    @Transactional
    public void testSavedDescription()throws Exception{
        Iterable<Recipe> recipes=recipeRepository.findAll();
        Recipe testRecipe=recipes.iterator().next();
        RecipeCommand testRecipeCommand=this.recipeToRecipeCommand.convert(testRecipe);
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand=this.recipeService.save(testRecipeCommand);

        assertEquals(NEW_DESCRIPTION,savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(),savedRecipeCommand.getId());


    }


}
