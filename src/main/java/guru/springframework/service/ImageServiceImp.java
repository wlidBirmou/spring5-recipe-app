package guru.springframework.service;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class ImageServiceImp implements ImageService{
    private final RecipeRepository recipeRepository;
    @Override
    public void saveImageFile(Long recipeId, MultipartFile multipartFile) {
        try {
        Recipe recipe= this.recipeRepository.findById(recipeId).orElseThrow(()-> new RuntimeException("No recipe with the given ID exists"));
        Byte[] byteObjects= new Byte[0];
            byteObjects = new Byte[multipartFile.getBytes().length];
        int i=0;
        for(byte b: multipartFile.getBytes()) byteObjects[i++]=b;
        recipe.setImage(byteObjects);
        this.recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occured",e);
            e.printStackTrace();
        }

        log.debug("received an image file");
    }




}
