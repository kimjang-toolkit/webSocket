package kimjang.toolkit.solsol.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendsDto {
    private List<Long> friendIds;
}
