package guru.springframework.controller;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.ImageService;
import guru.springframework.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Slf4j
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    @GetMapping("/recipe/{recipeId}/image")
    public String viewUploadForm(@PathVariable Long recipeId, Model model){
        model.addAttribute("recipe",this.recipeService.findCommandById(recipeId));
        return "/recipe/imageuploadform";
     }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile")MultipartFile file){
        imageService.saveImageFile(recipeId,file);
        return "redirect:/recipe/view/"+recipeId;
    }

    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse response){
        try {
        RecipeCommand recipeCommand=this.recipeService.findCommandById(recipeId);
            if(recipeCommand.getImage()!=null) {
                Byte[] boxedImageByte = recipeCommand.getImage();
                byte[] imageByte = new byte[boxedImageByte.length];
                int i = 0;
                for (Byte b : boxedImageByte) imageByte[i++] = b;
                response.setContentType("image/jpeg");
                InputStream inputStream = new ByteArrayInputStream(imageByte);
                IOUtils.copy(inputStream, response.getOutputStream());
            }
        } catch (IOException e) {
            log.debug("IO Error: ",e);
            e.printStackTrace();
        }
    }
}
