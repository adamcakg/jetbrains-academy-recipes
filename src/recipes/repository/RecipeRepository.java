package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.model.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findRecipesByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findRecipesByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
