package kimjang.toolkit.solsol.config.container;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WSSessionRoomContainer implements SocketSessionContainer{
    private final Map<String, Long> roomContainer = new ConcurrentHashMap<>();
    private final Map<String, String> emailContainer = new ConcurrentHashMap<>();


    @Override
    public Long getSubscribeUri(String session) {
        return roomContainer.getOrDefault(session, null);
    }

    @Override
    public Long unsubscribe(String session) {
        if(!roomContainer.containsKey(session))
            throw new IllegalStateException("구독 중이지 않습니다.");
        return roomContainer.remove(session);
    }

    @Override
    public void subscribe(String session, Long roomId) {
        roomContainer.put(session, roomId);
    }

    @Override
    public void setEmail(String session, String email) {
        emailContainer.put(session, email);
    }

    @Override
    public String getEmail(String session) {
        return emailContainer.getOrDefault(session, null);
    }

    @Override
    public String unsetEmail(String session) {
        if(!emailContainer.containsKey(session))
            throw new IllegalStateException("구독 중이지 않습니다.");
        return emailContainer.remove(session);
    }


}
