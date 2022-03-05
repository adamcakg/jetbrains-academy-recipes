package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.repository.RecipeRepository;
import recipes.model.Recipe;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class RecipesRestController {

    private final RecipeRepository repository;

    @Autowired
    public RecipesRestController(@Autowired RecipeRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/api/recipe/new")
    public String newRecipe(@Valid @RequestBody Recipe newRecipe){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        newRecipe.setDate(LocalDateTime.now());
        newRecipe.setAuthor(auth.getName());
        Recipe recipe = repository.save(newRecipe);
        return "{\"id\": "+ recipe.getId() +"}";
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id){
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Recipe recipe = repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(recipe.getAuthor() == null || recipe.getAuthor().equals(auth.getName())){
            repository.delete(recipe);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe newRecipe){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        newRecipe.setId(id);
        newRecipe.setDate(LocalDateTime.now());
        Recipe recipe = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(recipe.getAuthor().equals(auth.getName())){
            repository.save(newRecipe);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> search(@RequestParam Map<String, String> params){
        if (params.size() != 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (params.containsKey("category")){
            return repository.findRecipesByCategoryIgnoreCaseOrderByDateDesc(params.get("category"));
        }else if (params.containsKey("name")){
            return repository.findRecipesByNameContainingIgnoreCaseOrderByDateDesc(params.get("name"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
