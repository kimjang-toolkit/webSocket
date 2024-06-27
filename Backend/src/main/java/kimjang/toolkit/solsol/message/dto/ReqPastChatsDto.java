package kimjang.toolkit.solsol.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqPastChatsDto {
    private Long roomId;
    private Pageable page;
    private LocalDateTime roomExitTime;
    public ReqPastChatsDto (Long roomId, int page, int size, LocalDateTime roomExitTime){
        this.roomId = roomId;
        this.page = PageRequest.of(page, size);
        this.roomExitTime = roomExitTime;

    }
}
