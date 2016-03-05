package frontend;

import message.*;

import static message.MessageType.SOURCE_LINE;

import java.io.BufferedReader;
import java.io.IOException;



/**
 * Created by lanspawlo on 2/9/16.
 */
public class Source implements MessageProducer {
    public static final char EOL = '\n';
    public static final char EOF = (char) 0;

    private BufferedReader reader;
    private String line;
    private int lineNum;
    private int currentPos;

    private static MessageHandler messageHandler;

    public Source(BufferedReader reader) throws IOException {
        this.lineNum = 0;
        this.currentPos = -2;
        this.reader = reader;
        this.messageHandler = new MessageHandler();
    }

    public char currentChar() throws Exception {
        if (currentPos == -2) {
            readLine();
            return nextChar();
        } else if (line == null) {
            return EOF;
        } else if ((currentPos == -1) || (currentPos == line.length())) {
            return EOL;
        } else if (currentPos > line.length()) {
            readLine();
            return nextChar();
        } else {
            return line.charAt(currentPos);
        }
    }

    public char nextChar() throws Exception {
        ++currentPos;
        return currentChar();
    }

    public char peekChar() throws Exception {
        currentChar();
        if (line == null) {
            return EOF;
        }
        int nextPos = currentPos + 1;
        return nextPos < line.length() ? line.charAt(nextPos) : EOL;
    }

    private void readLine() throws IOException {
        line = reader.readLine();
        currentPos = -1;

        if (line != null) {
            ++lineNum;
        }

        if (line != null) {
            sendMessage(new Message(SOURCE_LINE, new Object[] {lineNum, line}));
        }
    }

    public void close() throws Exception {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getPosition() {
        return currentPos;
    }

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
