package kimjang.toolkit.solsol.message;

public class HelloMessage {

	private String chat;

	public HelloMessage() {
	}

	public HelloMessage(String name) {
		this.chat = name;
	}

	public String getName() {
		return chat;
	}

	public void setName(String name) {
		this.chat = name;
	}
}