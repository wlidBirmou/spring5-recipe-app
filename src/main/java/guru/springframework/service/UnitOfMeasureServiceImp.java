package guru.springframework.service;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UnitOfMeasureServiceImp implements UnitOfMeasureService{

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitOfMeasures() {
        Iterable<UnitOfMeasure> unitOfMeasureIterable= this.unitOfMeasureRepository.findAll();
        Set<UnitOfMeasureCommand> resultSet=StreamSupport.stream(unitOfMeasureIterable.spliterator(),false)
                .map(uom -> this.unitOfMeasureToUnitOfMeasureCommand.convert(uom)).collect(Collectors.toSet());
        return resultSet;
    }

    @Override
    public UnitOfMeasure getUnit() {
    return StreamSupport.stream(this.unitOfMeasureRepository.findAll().spliterator(),false).findAny().orElseThrow(
            () -> new RuntimeException("No unit of measure exist."));
    }
}
