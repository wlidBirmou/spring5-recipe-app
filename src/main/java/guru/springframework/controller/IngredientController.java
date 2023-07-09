package guru.springframework.controller;

import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/list")
    public String listIngredients(@PathVariable Long recipeId, Model model){
        log.debug("Getting ingredient list from recipe with Id:"+recipeId);
        model.addAttribute("recipe", this.recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

}
