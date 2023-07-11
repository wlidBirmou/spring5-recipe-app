package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class IngredientToIngredientCommandTest extends TestCase {

    private IngredientToIngredientCommand ingredientToIngredientCommand ;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;
    private final Long ID=2l;
    private final String DESCRIPTION="Ingred1";
    private final BigDecimal AMOUNT=BigDecimal.valueOf(33.33);


    @Before
    public void setUp() throws Exception {
        this.unitOfMeasureCommand=new UnitOfMeasureToUnitOfMeasureCommand();
        this.ingredientToIngredientCommand=new IngredientToIngredientCommand();

    }

    @Test
    public void testNullConvertion() {
        Ingredient ingredient = null;
        IngredientCommand ingredientCommand=this.ingredientToIngredientCommand.convert(ingredient);
        assertNull(ingredientCommand);
    }

    @Test
    public void testIfEmpty(){
        Ingredient ingredient=Ingredient.builder().build();
        assertNotNull(this.ingredientToIngredientCommand.convert(ingredient));
    }

    @Test
    public void testConvert() {
        Ingredient ingredient=Ingredient.builder()
                .id(this.ID)
                .description(this.DESCRIPTION)
                .amount(this.AMOUNT)
                .recipe(Recipe.builder().id(1l).description("recipe desp").build())
                .unitOfMeasure(UnitOfMeasure.builder().id(1l).description("unit").build())
                .build();

        IngredientCommand ingredientCommand=this.ingredientToIngredientCommand.convert(ingredient);
        assertNotNull(ingredientCommand);
        assertEquals(this.ID, ingredientCommand.getId());
        assertEquals(this.DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(this.AMOUNT, ingredientCommand.getAmount());
        assertEquals(ingredient.getRecipe().getId(),ingredientCommand.getRecipe().getId());
        assertEquals(ingredient.getRecipe().getDescription(),ingredientCommand.getRecipe().getDescription());
        assertEquals(ingredient.getUnitOfMeasure().getId(),ingredientCommand.getUnitOfMeasure().getId());
        assertEquals(ingredient.getUnitOfMeasure().getDescription(),ingredientCommand.getUnitOfMeasure().getDescription());
    }
}