package kimjang.toolkit.solsol.room;


import kimjang.toolkit.solsol.user.dto.UserDto;
import kimjang.toolkit.solsol.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.room.service.CreateRoomName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateRoomNameTest {


    @Test
    @DisplayName("10글자_초과")
    public void createRoomName_10글자_초과(){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new UserDto(1L ,"오찬솔"), new UserDto(2L, "조승효"), new UserDto(3L, "강아지"), new UserDto(4L, "까미나무 삼계탕")),
                "", "효승이 자니??", new UserDto(2L, "조승효"));
        UserDto customer = new UserDto(2L, "조승효");
        String result = CreateRoomName.withParticipationsName(createChatRoomDto, customer.getId());
        String answer = "오찬솔 강아지 까미...";
        assertEquals(answer, result);
    }

    @Test
    @DisplayName("10글자_미만")
    public void createRoomName_10글자_미만(){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new UserDto(1L ,"오찬솔"), new UserDto(2L, "조승효"), new UserDto(3L, "강아지")),
                 "", "효승이 자니??", new UserDto(2L, "조승효"));
        UserDto customer = new UserDto(2L, "조승효");
        String result = CreateRoomName.withParticipationsName(createChatRoomDto, customer.getId());
        String answer = "오찬솔 강아지";
        assertEquals(answer, result);
    }

    @Test
    @DisplayName("기본_제목_존재")
    public void createRoomName_기본_제목_존재(){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new UserDto(1L ,"오찬솔"), new UserDto(2L, "조승효"), new UserDto(3L, "강아지")),
                "아무거나 방", "효승이 자니??", new UserDto(2L, "조승효"));
        UserDto customer = new UserDto(2L, "조승효");
        String result = CreateRoomName.withParticipationsName(createChatRoomDto, customer.getId());
        String answer = "아무거나 방";
        assertEquals(answer, result);
    }

    @Test
    @DisplayName("2명_일때는_상대이름 ")
    public void createRoomName_2명_일때는_상대이름  (){
        CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(
                Arrays.asList(new UserDto(1L ,"오찬솔"), new UserDto(2L, "조승효")),
                "아무거나 방", "효승이 자니??", new UserDto(2L, "조승효"));
        UserDto customer = new UserDto(2L, "조승효");
        String result = CreateRoomName.withParticipationsName(createChatRoomDto, customer.getId());
        String answer = "오찬솔";
        assertEquals(answer, result);
    }
}
