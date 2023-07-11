package guru.springframework.service;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImpTest extends TestCase {

    UnitOfMeasureService unitOfMeasureService;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.unitOfMeasureService=new UnitOfMeasureServiceImp(unitOfMeasureRepository,new UnitOfMeasureToUnitOfMeasureCommand(),new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testListAllUnitOfMeasures() {
        Set<UnitOfMeasure> unitOfMeasures=new LinkedHashSet<>();
        unitOfMeasures.add(UnitOfMeasure.builder().id(1l).description("unit1").build());
        unitOfMeasures.add(UnitOfMeasure.builder().id(2l).description("unit2").build());
        unitOfMeasures.add(UnitOfMeasure.builder().id(3l).description("unit3").build());
        when(this.unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        Set<UnitOfMeasureCommand> resultSet=this.unitOfMeasureService.listAllUnitOfMeasures();

        assertEquals(unitOfMeasures.size(),resultSet.size());
        verify(this.unitOfMeasureRepository,times(1)).findAll();

    }
}