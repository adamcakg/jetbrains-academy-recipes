package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.model.Recipe;
import recipes.model.User;

public interface UserRepository  extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
}
