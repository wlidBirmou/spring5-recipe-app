package guru.springframework.commands;


import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class CategoryCommand {
    private Long id;
    private String description;

}
