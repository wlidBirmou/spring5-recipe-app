package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findCommandById(Long id);
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveCommand(IngredientCommand ingredientCommand);
    void deleteById(Long recipeId, Long IngredientId);
}
