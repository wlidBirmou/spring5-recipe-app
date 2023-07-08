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
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {



    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if(category==null) return null;
        CategoryCommand categoryCommand=CategoryCommand.builder().id(category.getId())
                .description(category.getDescription())
                .build();
        return categoryCommand;
     }
}
