package kimjang.toolkit.solsol.message.dto;

import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface GetChatProj {
    String getContent();
    LocalDateTime getCreateDate();
    Long getChatRoom();
    CustomerDto getCustomer();
    interface ChatRoom{
        Long getId();
    }
    interface Customer{
        Long getId();
        String getName();
    }
}
