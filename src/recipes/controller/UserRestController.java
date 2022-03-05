package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.User;
import recipes.repository.UserRepository;

import javax.validation.Valid;

@RestController
public class UserRestController {

    private final UserRepository repository;

    final PasswordEncoder encoder;

    public UserRestController(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user){
        if(repository.findUserByEmail(user.getEmail()) == null){
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            throw new ResponseStatusException(HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
