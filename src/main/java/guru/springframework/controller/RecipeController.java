package guru.springframework.controller;

import guru.springframework.domain.Recipe;
import guru.springframework.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;


    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/view/{id}")
    public ModelAndView viewRecipe(@PathVariable("id") Long id){
        ModelAndView modelAndView=new ModelAndView();
        Recipe recipe=this.recipeService.findById(id);
        modelAndView.setViewName("recipe/view.html");
        modelAndView.addObject("recipe", recipe);
        return modelAndView;
    }
}
