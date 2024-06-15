package kimjang.toolkit.solsol.customer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    @Pattern(regexp = "^[/\\,'₩`{}|\"#\\$%^&*()]+$", message = "Name contains invalid characters.")
    @Size(max = 10, message = "Name must be less than 10 characters.")
    private String name;
}
