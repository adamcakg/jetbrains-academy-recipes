package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Setter
    private Long id;
    @NotBlank
    @Getter
    @Email(regexp = ".*@.*\\..*")
    private String email;
    @NotBlank
    @Setter
    @Getter
    @Size(min = 8, message = "Password must be min 8 characters long...")
    private String password;
}
