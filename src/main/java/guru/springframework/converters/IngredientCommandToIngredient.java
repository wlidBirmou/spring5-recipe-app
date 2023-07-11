package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {



    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if(ingredientCommand==null) return null;
        System.out.println(ingredientCommand.getUnitOfMeasure());
        Ingredient ingredient= Ingredient.builder()
                .id(ingredientCommand.getId())
                .description(ingredientCommand.getDescription())
                .amount(ingredientCommand.getAmount())
                .unitOfMeasure(ingredientCommand.getUnitOfMeasure())
                .recipe(ingredientCommand.getRecipe())
                .build();
        return ingredient;
    }
}