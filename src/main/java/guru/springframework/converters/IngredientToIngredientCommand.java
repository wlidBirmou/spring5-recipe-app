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
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {


    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient==null)return null;
        IngredientCommand ingredientCommand= IngredientCommand.builder()
                .id(ingredient.getId())
                .description(ingredient.getDescription())
                .amount(ingredient.getAmount())
                .build();
        ingredientCommand.setUnitOfMeasure(this.unitOfMeasureCommand.convert(ingredient.getUnitOfMeasure()));
        return ingredientCommand;
    }
}