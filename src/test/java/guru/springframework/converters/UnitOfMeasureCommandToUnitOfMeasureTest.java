package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class UnitOfMeasureCommandToUnitOfMeasureTest extends TestCase {

    private final Long ID=3l;
    private final String DESCRIPTION="desc";
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasurecommandToUnitOfMeasure;

    @Before
    public void setUp() throws Exception {
        this.unitOfMeasurecommandToUnitOfMeasure=new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullConvertion() {
        UnitOfMeasure unitOfMeasure = this.unitOfMeasurecommandToUnitOfMeasure.convert(null);
        assertNull(unitOfMeasure);
    }

    @Test
    public void testIfEmpty(){
        UnitOfMeasureCommand unitOfMeasureCommand=UnitOfMeasureCommand.builder().build();
        assertNotNull(this.unitOfMeasurecommandToUnitOfMeasure.convert(unitOfMeasureCommand));
    }

    @Test
    public void testConvert() {
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(this.ID);
        unitOfMeasureCommand.setDescription(this.DESCRIPTION);
        UnitOfMeasure unitOfMeasure = this.unitOfMeasurecommandToUnitOfMeasure.convert(unitOfMeasureCommand);

        assertEquals(this.ID, unitOfMeasure.getId());
        assertEquals(this.DESCRIPTION, unitOfMeasure.getDescription());
    }
}