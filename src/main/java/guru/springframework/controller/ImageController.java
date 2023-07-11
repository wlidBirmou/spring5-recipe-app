package guru.springframework.controller;


import guru.springframework.service.ImageService;
import guru.springframework.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
}
