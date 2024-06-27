package kimjang.toolkit.solsol.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kimjang.toolkit.solsol.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @Pattern(regexp = "^[/\\,'₩`{}|\"#\\$%^&*()]+$", message = "Name contains invalid characters.")
    @Size(max = 10, message = "Name must be less than 10 characters.")
    private String name;
    public static UserDto toDto(User user){
        return new UserDto(user.getId(), user.getName());
    }
}
