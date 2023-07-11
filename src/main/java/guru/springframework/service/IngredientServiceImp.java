package guru.springframework.service;


import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class IngredientServiceImp implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Override
    public IngredientCommand findCommandById(Long id) {
        Optional<Ingredient> ingredientOptional = this.ingredientRepository.findById(id);
        if (ingredientOptional.isPresent()) return this.ingredientToIngredientCommand.convert(ingredientOptional.get());
        else throw new RuntimeException("There is no ingredient with that id");
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Optional<IngredientCommand> ingredientOptional = recipeOptional.get().getIngredients().stream().filter(i -> i.getId().equals(ingredientId)).map(i -> this.ingredientToIngredientCommand.convert(i)).findFirst();
            if (ingredientOptional.isPresent()) return ingredientOptional.get();
            else throw new RuntimeException("No ingredient with the specified id exists in the recipe");
        } else throw new RuntimeException("No recipe with the provided id exists.");
    }

    @Override
    @Transactional
    public IngredientCommand saveCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(ingredientCommand.getRecipe().getId());
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(i -> i.getId().equals(ingredientCommand.getId())).findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredient=ingredientOptional.get();
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setUnitOfMeasure(this.unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() ->new RuntimeException("Unit of measure not found")));
                ingredient.setAmount(ingredientCommand.getAmount());
            } else {
                recipe.addIngredients(this.ingredientCommandToIngredient.convert(ingredientCommand));
            }
            this.recipeRepository.save(recipe);

        } else {
            throw new RuntimeException("The provided recipe doesn't exist.");
        }


        return ingredientCommand;
    }

}
