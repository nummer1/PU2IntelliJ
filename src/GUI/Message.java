package GUI;

/**
 * Created by andreaswilhelmflatt on 23/03/2017.
 */
public class Message {

    private String sender;
    private String content;

    public Message(String sender, String content) {
        this.sender = new String(sender);
        this.content = new String(content);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
