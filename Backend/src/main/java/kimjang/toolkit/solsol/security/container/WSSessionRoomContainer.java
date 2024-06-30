package kimjang.toolkit.solsol.security.container;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WSSessionRoomContainer implements SocketSessionContainer{
    private final Map<String, Long> roomContainer = new ConcurrentHashMap<>();

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
}
