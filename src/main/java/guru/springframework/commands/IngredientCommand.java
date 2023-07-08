package guru.springframework.commands;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class IngredientCommand {

    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasureCommand;
    private RecipeCommand recipeCommand;
}
