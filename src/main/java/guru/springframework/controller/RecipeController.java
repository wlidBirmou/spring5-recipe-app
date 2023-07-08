package guru.springframework.controller;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/recipes","/recipe"})
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

    @RequestMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe_post")
    public String saveOrUpdate(@ModelAttribute  RecipeCommand recipeCommand){
        System.out.println(recipeCommand.getId());
        System.out.println(recipeCommand.getDescription());
        System.out.println(recipeCommand.getDirections());
        System.out.println(recipeCommand.getNotesCommand());
        RecipeCommand savedRecipeCommand=this.recipeService.save(recipeCommand);
        System.out.println(savedRecipeCommand.getId());
        return "redirect:/recipe/view/"+savedRecipeCommand.getId();

    }
}
