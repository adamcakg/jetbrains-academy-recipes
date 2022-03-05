package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Setter
    private Long id;

    @JsonIgnore
    @Setter
    @Getter
    private String author;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @Setter
    private LocalDateTime date;

    @NotNull
    @NotEmpty(message = "Ingredients shoud not be empty")
    @ElementCollection(targetClass=String.class)
    private List<String> ingredients;

    @NotNull
    @NotEmpty(message = "Directions shoud not be empty")
    @ElementCollection(targetClass=String.class)
    private List<String> directions;
}
