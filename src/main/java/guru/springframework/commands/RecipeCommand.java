package guru.springframework.commands;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @Size(min=3,max=255)
    private String description;
    @Min(1)
    @Max(999)
    private Integer prepTime;
    @Min(1)
    @Max(999)
    private Integer cookTime;
    @Min(1)
    @Max(999)
    private Integer servings;
    private String source;
    @URL
    private String url;
    @NotBlank
    private String directions;
    private Byte[] image;
    private Difficulty difficulty;
    private Notes notes;
    private Set<Ingredient> ingredients;
    private Set<Category> categories =new LinkedHashSet<>();
}
