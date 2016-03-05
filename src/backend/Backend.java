package backend;

import intermediate.*;
import message.*;

/**
 * Created by lanspawlo on 2/9/16.
 */
public abstract class Backend implements MessageProducer {

    protected static MessageHandler messageHandler;

    static {
        messageHandler = new MessageHandler();
    }

    protected SymTab symTab;
    protected ICode iCode;

    public ICode getICode() {
        return iCode;
    }

    public SymTab getSymTab() {
        return symTab;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public abstract void process(ICode iCode, SymTabStack symTabStack) throws Exception;

    public void addMessageListener(MessageListener listener) {
        messageHandler.addListener(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        messageHandler.removeListener(listener);
    }

    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }
}
