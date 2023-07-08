package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class IngredientCommandToIngredientTest extends TestCase {

    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    private final Long ID=2l;
    private final String DESCRIPTION="Ingred1";
    private final BigDecimal AMOUNT=BigDecimal.valueOf(33.33);

    @Before
    public void setUp() throws Exception {
        this.unitOfMeasureCommandToUnitOfMeasure=new UnitOfMeasureCommandToUnitOfMeasure();
        this.ingredientCommandToIngredient=new IngredientCommandToIngredient(this.unitOfMeasureCommandToUnitOfMeasure);
    }

    @Test
    public void testNullConvertion() {
        IngredientCommand ingredientCommand = null;
        Ingredient ingredient=this.ingredientCommandToIngredient.convert(ingredientCommand);
        assertNull(ingredient);
    }

    @Test
    public void testIfEmpty(){
        IngredientCommand ingredientCommand=IngredientCommand.builder().build();
        assertNotNull(this.ingredientCommandToIngredient.convert(ingredientCommand));
    }

    @Test
    public void testConvert() {
        IngredientCommand ingredientCommand=IngredientCommand.builder().description(this.DESCRIPTION).amount(AMOUNT).id(this.ID).build();
        Ingredient ingredient=this.ingredientCommandToIngredient.convert(ingredientCommand);
        assertNotNull(ingredient);
        assertEquals(this.ID, ingredient.getId());
        assertEquals(this.DESCRIPTION, ingredient.getDescription());
        assertEquals(this.AMOUNT, ingredient.getAmount());
    }
}