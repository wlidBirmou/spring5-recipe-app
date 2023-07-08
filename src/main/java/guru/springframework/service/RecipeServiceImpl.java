package guru.springframework.service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet=new HashSet<>();
        this.recipeRepository.findAll().forEach(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional= this.recipeRepository.findById(id);
        if(!recipeOptional.isPresent()) throw new RuntimeException("Recipe with the id:"+id+" not found");
        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand save(RecipeCommand recipeCommand) {
        if(recipeCommand==null) return null;
        Recipe recipe=this.recipeRepository.save(this.recipeCommandToRecipe.convert(recipeCommand));
        return this.recipeToRecipeCommand.convert(recipe);
    }
}
