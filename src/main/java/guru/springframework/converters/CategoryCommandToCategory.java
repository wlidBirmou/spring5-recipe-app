package guru.springframework.converters;


import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {



    @Nullable
    @Synchronized
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if(categoryCommand==null) return null;
        Category category=Category.builder().id(categoryCommand.getId())
                .description(categoryCommand.getDescription())
                .build();
        return category;
    }
}
