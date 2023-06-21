package guru.springframework.controller;

import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }
    @RequestMapping({"/",""})
    public String getIndexPage(Model model){
        log.info("getting index page");
        model.addAttribute("recipes",this.recipeRepository.findAll());
        return "index";
    }
    @RequestMapping("recipes")
    public String getRecipes(Model model){
    model.addAttribute("recipes", this.recipeRepository.findAll());

    return "recipe/index";
    }
}
