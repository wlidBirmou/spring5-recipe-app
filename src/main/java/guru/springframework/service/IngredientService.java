package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;

import java.util.Set;

public interface IngredientService {


    Set<Ingredient> getIngredients();
    Set<Ingredient> getIngredients(Long recipeID);
    Ingredient findById(Long id);
    IngredientCommand findCommandById(Long id);
    IngredientCommand saveCommand(RecipeCommand recipeCommand);
    void deleteById(Long id);}
