package message;

/**
 * Created by lanspawlo on 2/9/16.
 */
public interface MessageProducer {
    public void addMessageListener(MessageListener listener);
    public void removeMessageListener(MessageListener listener);
    public void sendMessage(Message message);
}
