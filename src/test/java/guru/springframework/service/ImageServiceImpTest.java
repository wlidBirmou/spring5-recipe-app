package guru.springframework.service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImpTest extends TestCase {

    private ImageService imageService;

    @Mock
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.imageService=new ImageServiceImp(this.recipeRepository);
    }

    @Test
    public void testSaveImageFile() throws IOException {
        Long id=1l;
        MultipartFile multipartFile=new MockMultipartFile("imagefile", "testing.txt","text/plain","Spring Framework Guru".getBytes());
        Recipe recipe=Recipe.builder().id(id).description("desc").build();

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor=ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(id,multipartFile);
        verify(recipeRepository,times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe=argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length,savedRecipe.getImage().length);
    }


}