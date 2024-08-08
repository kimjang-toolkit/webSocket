package kimjang.toolkit.solsol.config.container;

public interface SocketSessionContainer {
    Long getSubscribeUri(String session);
    Long unsubscribe(String session);
    void subscribe(String session,  Long roomId);
    void setEmail(String session, String email);
    String getEmail(String session);
    String unsetEmail(String session);

}
