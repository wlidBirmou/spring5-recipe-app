package guru.springframework.controller;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.ImageService;
import guru.springframework.service.RecipeService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest extends TestCase {

    private ImageController imageController;
    @Mock
    private ImageService imageService;
    @Mock
    private RecipeService recipeService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.imageController=new ImageController(this.imageService,this.recipeService);
    }

    @Test
    public void testViewImageForm() throws Exception {
        RecipeCommand recipeCommand=RecipeCommand.builder().id(1l).description("description").build();

        when(this.recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(this.imageController).build();
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(this.recipeService,times(1)).findCommandById(anyLong());
        verifyZeroInteractions(this.imageService);

    }

    @Test
    public void testHandleImagePost() throws Exception {
        MockMultipartFile multipartFile=new MockMultipartFile("imagefile","testing.txt","text/plain","Recipe project".getBytes());

        MockMvc mockMvc=MockMvcBuilders.standaloneSetup(this.imageController).build();
        mockMvc.perform(multipart("/recipe/1/image").file((multipartFile)))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location","/recipe/1/view"));
        verify(this.imageService,times(1)).saveImageFile(anyLong(),any());
    }

}