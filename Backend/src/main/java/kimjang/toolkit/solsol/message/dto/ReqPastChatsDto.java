package kimjang.toolkit.solsol.message.dto;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
public class ReqPastChatsDto {
    private final Long roomId;
    private final Pageable page;
    private final LocalDateTime roomExitTime;
    public ReqPastChatsDto (Long roomId, int page, int size, LocalDateTime roomExitTime){
        this.roomId = roomId;
        this.page = PageRequest.of(page, size);
        this.roomExitTime = roomExitTime;
    }
}
