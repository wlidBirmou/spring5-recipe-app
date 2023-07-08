package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class CategoryCommandToCategoryTest extends TestCase {


    private CategoryCommandToCategory categoryCommandToCategory;
    private final Long ID = 2l;
    private final String DESCRIPTION = "cat one";
    private final Set<RecipeCommand> RECIPE_COMMANDS=new LinkedHashSet<>();

    @Before
    public void setUp() throws Exception {
        this.categoryCommandToCategory=new CategoryCommandToCategory();
        this.RECIPE_COMMANDS.add(RecipeCommand.builder().id(1l).description("desc1").cookTime(30).build());
        this.RECIPE_COMMANDS.add(RecipeCommand.builder().id(2l).description("desc2").cookTime(50).build());
        this.RECIPE_COMMANDS.add(RecipeCommand.builder().id(3l).description("desc3").cookTime(60).build());
    }

    @Test
    public void testNullConvertion() {
        Category category = this.categoryCommandToCategory.convert(null);
        assertNull(category);

    }

    @Test
    public void testIfEmpty(){
        CategoryCommand categoryCommand=CategoryCommand.builder().build();
        assertNotNull(this.categoryCommandToCategory.convert(categoryCommand));
    }

    @Test
    public void testConvert() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(this.ID);
        categoryCommand.setDescription(this.DESCRIPTION);

        Category category = this.categoryCommandToCategory.convert(categoryCommand);

        assertEquals(this.ID, category.getId());
        assertEquals(this.DESCRIPTION, category.getDescription());
    }

}