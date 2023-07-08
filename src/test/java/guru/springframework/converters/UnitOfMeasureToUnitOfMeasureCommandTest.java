package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class UnitOfMeasureToUnitOfMeasureCommandTest extends TestCase {

    private final Long ID=3l;
    private final String DESCRIPTION="desc";
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Before
    public void setUp() throws Exception {
        this.unitOfMeasureToUnitOfMeasureCommand=new UnitOfMeasureToUnitOfMeasureCommand();
    }


    @Test
    public void testNullConvertion() {
        UnitOfMeasureCommand unitOfMeasureCommand = this.unitOfMeasureToUnitOfMeasureCommand.convert(null);
        assertNull(unitOfMeasureCommand);
    }

    @Test
    public void testIfEmpty(){
        UnitOfMeasure unitOfMeasure=UnitOfMeasure.builder().build();
        assertNotNull(this.unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure));
    }

    @Test
    public void testConvert() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .id(this.ID)
                .description(this.DESCRIPTION).build();
        UnitOfMeasureCommand unitOfMeasureCommand = this.unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);

        assertEquals(this.ID, unitOfMeasureCommand.getId());
        assertEquals(this.DESCRIPTION, unitOfMeasureCommand.getDescription());
    }
}