package guru.springframework.controller;

import exceptions.NotFoundException;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/recipes","/recipe"})
@AllArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;


    @RequestMapping("/view/{id}")
    public ModelAndView viewRecipe(@PathVariable("id") Long id){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("recipe", this.recipeService.findById(id));
        modelAndView.setViewName("recipe/view.html");
        return modelAndView;
    }

    @RequestMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id,Model model){
        RecipeCommand recipeCommand=this.recipeService.findCommandById(id);
        model.addAttribute("recipe",recipeCommand);
        return "recipe/recipeform";
    }


    @PostMapping("recipe_post")
    public String saveOrUpdate(@ModelAttribute  RecipeCommand recipeCommand){
        RecipeCommand savedRecipeCommand=this.recipeService.saveCommand(recipeCommand);
        return "redirect:/recipe/view/"+savedRecipeCommand.getId();
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable("id") Long id){
        this.recipeService.deleteById(id);
        return "redirect:/recipe/{id}/ingredient/list";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(Exception exception){
        ModelAndView modelAndView=new ModelAndView();
        log.error(exception.getMessage());
        modelAndView.addObject("exception",exception);
        modelAndView.setViewName("404error");
        return modelAndView;
    }
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException(Exception exception){
        ModelAndView modelAndView=new ModelAndView();
        log.error(exception.getMessage());
        modelAndView.addObject("exception",exception);
        modelAndView.setViewName("400error");
        return modelAndView;
    }
}
