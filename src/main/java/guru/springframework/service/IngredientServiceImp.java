package guru.springframework.service;


import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IngredientServiceImp implements IngredientService{
    @Override
    public Set<Ingredient> getIngredients() {
        return null;
    }

    @Override
    public Set<Ingredient> getIngredients(Long recipeId) {
        return null;
    }

    @Override
    public Ingredient findById(Long id) {
        return null;
    }

    @Override
    public IngredientCommand findCommandById(Long id) {
        return null;
    }

    @Override
    public IngredientCommand saveCommand(RecipeCommand recipeCommand) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
