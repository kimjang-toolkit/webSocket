package kimjang.toolkit.solsol.room;


import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.room.service.ChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateRoomNameTest {

    @Autowired
    ChatRoomService chatRoomService;

    @Test
    @DisplayName("10글자_초과")
    public void createRoomName_10글자_초과(){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new CustomerDto(1L ,"오찬솔"), new CustomerDto(2L, "조승효"), new CustomerDto(3L, "강아지"), new CustomerDto(4L, "까미나무 삼계탕")),
                "", "효승이 자니??", new CustomerDto(2L, "조승효"));
        CustomerDto customer = new CustomerDto(2L, "조승효");
        String result = chatRoomService.createRoomName(createChatRoomDto, customer.getId());
        String answer = "오찬솔 강아지 까미...";
        assertEquals(answer, result);
    }

    @Test
    @DisplayName("10글자_미만")
    public void createRoomName_10글자_미만(){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new CustomerDto(1L ,"오찬솔"), new CustomerDto(2L, "조승효"), new CustomerDto(3L, "강아지")),
                 "", "효승이 자니??", new CustomerDto(2L, "조승효"));
        CustomerDto customer = new CustomerDto(2L, "조승효");
        String result = chatRoomService.createRoomName(createChatRoomDto, customer.getId());
        String answer = "오찬솔 강아지";
        assertEquals(answer, result);
    }

    @Test
    @DisplayName("기본_제목_존재")
    public void createRoomName_기본_제목_존재(){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new CustomerDto(1L ,"오찬솔"), new CustomerDto(2L, "조승효"), new CustomerDto(3L, "강아지")),
                "아무거나 방", "효승이 자니??", new CustomerDto(2L, "조승효"));
        CustomerDto customer = new CustomerDto(2L, "조승효");
        String result = chatRoomService.createRoomName(createChatRoomDto, customer.getId());
        String answer = "아무거나 방";
        assertEquals(answer, result);
    }

    @Test
    @DisplayName("2명_일때는_상대이름 ")
    public void createRoomName_2명_일때는_상대이름  (){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new CustomerDto(1L ,"오찬솔"), new CustomerDto(2L, "조승효")),
                "아무거나 방", "효승이 자니??", new CustomerDto(2L, "조승효"));
        CustomerDto customer = new CustomerDto(2L, "조승효");
        String result = chatRoomService.createRoomName(createChatRoomDto, customer.getId());
        String answer = "오찬솔";
        assertEquals(answer, result);
    }
}
