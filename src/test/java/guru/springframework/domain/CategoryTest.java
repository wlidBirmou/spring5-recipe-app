package guru.springframework.domain;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest extends TestCase {

    Category category;

    @Before
    public void setUp(){
        category=new Category();
    }

    @Test
    public void testGetId() {
        Long id=4L;
        category.setId(4l);
        assertEquals(id, category.getId());
    }

    @Test
    public void testGetDescription() {
    }

    @Test
    public void testGetRecipes() {
    }
}