package guru.springframework.controller;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/list")
    public String listIngredients(@PathVariable Long recipeId, Model model){
        log.debug("Getting ingredient list from recipe with Id:"+recipeId);
        model.addAttribute("recipe", this.recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/view")
    public String viewIngredient(@PathVariable("recipeId") Long recipeId,@PathVariable("ingredientId") Long ingredientId, Model model){
        log.debug("Getting ingredient with id:"+ingredientId+"of recipe id:"+recipeId);
        model.addAttribute("ingredient",this.ingredientService.findByRecipeIdAndIngredientId(recipeId,ingredientId));
        return "recipe/ingredient/view";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/new")
    public String newIngredient(@PathVariable("recipeId") Long recipeId, Model model){
        IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setRecipe(this.recipeService.findById(recipeId));
        ingredientCommand.setUnitOfMeasure(this.unitOfMeasureService.getUnit());
        model.addAttribute("unitOfMeasureList",this.unitOfMeasureService.listAllUnitOfMeasures());
        model.addAttribute("ingredient",ingredientCommand);
        return "recipe/ingredient/ingredientform";
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable("recipeId") Long recipeId,@PathVariable("ingredientId") Long ingredientId, Model model){
        IngredientCommand ingredientCommand=this.ingredientService.findByRecipeIdAndIngredientId(recipeId,ingredientId);

        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("unitOfMeasureList",this.unitOfMeasureService.listAllUnitOfMeasures());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        log.debug("Getting ingredient with id: "+ingredientCommand.getUnitOfMeasure());
        IngredientCommand savedCommand=this.ingredientService.saveCommand(ingredientCommand);
    return "redirect:/recipe/"+savedCommand.getRecipe().getId()+"/ingredient/"+savedCommand.getId()+"/view";
    }

    }
