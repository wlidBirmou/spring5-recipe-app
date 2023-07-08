package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class CategoryToCategoryCommandTest extends TestCase {


    private CategoryToCategoryCommand categoryToCategoryCommand ;
    private final Long ID=2l;
    private final String DESCRIPTION="cat one";
    private final Set<Recipe> RECIPES=new LinkedHashSet<>();


    @Before
    public void setUp() throws Exception {
        this.categoryToCategoryCommand=new CategoryToCategoryCommand();
        this.RECIPES.add(Recipe.builder().id(1l).description("desc1").cookTime(30).build());
        this.RECIPES.add(Recipe.builder().id(2l).description("desc2").cookTime(50).build());
        this.RECIPES.add(Recipe.builder().id(3l).description("desc3").cookTime(60).build());

    }

    @Test
    public void testNullConvertion() {
        Category category = null;
        CategoryCommand categoryCommand=this.categoryToCategoryCommand.convert(category);
        assertNull(categoryCommand);
    }

    @Test
    public void testIfEmpty(){
        Category category=Category.builder().build();
        assertNotNull(this.categoryToCategoryCommand.convert(category));
    }

    @Test
    public void testConvert() {
        Category category= new Category();
        category.setId(this.ID);
        category.setDescription(this.DESCRIPTION);
        CategoryCommand categoryCommand=this.categoryToCategoryCommand.convert(category);
        assertNotNull(categoryCommand);
        assertEquals(this.ID, categoryCommand.getId());
        assertEquals(this.DESCRIPTION, categoryCommand.getDescription());
    }
}