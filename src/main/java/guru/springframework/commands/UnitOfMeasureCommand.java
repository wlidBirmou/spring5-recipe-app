package guru.springframework.commands;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UnitOfMeasureCommand {

    private Long id;
    private String description;
}
