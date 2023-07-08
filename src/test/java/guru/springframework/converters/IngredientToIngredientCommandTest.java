package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
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
        this.ingredientToIngredientCommand=new IngredientToIngredientCommand(this.unitOfMeasureCommand);

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
        Ingredient ingredient= new Ingredient();
        ingredient.setId(this.ID);
        ingredient.setDescription(this.DESCRIPTION);
        ingredient.setAmount(this.AMOUNT);
        IngredientCommand ingredientCommand=this.ingredientToIngredientCommand.convert(ingredient);
        assertNotNull(ingredientCommand);
        assertEquals(this.ID, ingredientCommand.getId());
        assertEquals(this.DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(this.AMOUNT, ingredientCommand.getAmount());
    }
}