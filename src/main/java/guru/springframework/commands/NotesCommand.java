package guru.springframework.commands;

import guru.springframework.domain.Recipe;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class NotesCommand {

    private Long id;
    private String recipeNotes;
    private Recipe recipe;
}
