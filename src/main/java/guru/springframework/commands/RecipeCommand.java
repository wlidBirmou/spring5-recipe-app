package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notesCommand;
    private Set<IngredientCommand> ingredientCommands;
    private Set<CategoryCommand> categoryCommands=new LinkedHashSet<>();
}
