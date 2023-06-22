package guru.springframework.controller;

import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {


    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }
    @RequestMapping({"/",""})
    public String getIndexPage(Model model){
        log.info("getting index page");
        model.addAttribute("recipes",this.recipeService.getRecipes());
        return "index";
    }
    @RequestMapping("recipes")
    public String getRecipes(Model model){
    model.addAttribute("recipes", this.recipeService.getRecipes());

    return "recipe/index";
    }
}
