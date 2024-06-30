package kimjang.toolkit.solsol.security.container;

public interface SocketSessionContainer {
    Long getSubscribeUri(String session);
    Long unsubscribe(String session);
    void subscribe(String session,  Long roomId);
}
