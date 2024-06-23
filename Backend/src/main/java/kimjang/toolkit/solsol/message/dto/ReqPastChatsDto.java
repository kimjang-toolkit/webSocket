package kimjang.toolkit.solsol.message.dto;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class ReqPastChatsDto {
    private final Long roomId;
    private final Pageable pageable;
    public ReqPastChatsDto (Long roomId, int page, int size){
        this.roomId = roomId;
        this.pageable = PageRequest.of(page, size);
    }
}
