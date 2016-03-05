package frontend.beki;

import frontend.*;
import intermediate.*;
import message.*;

import static frontend.beki.BekiTokenType.*;
import static frontend.beki.BekiErrorCode.*;
import static message.MessageType.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class BekiParserTD extends Parser {

    protected static BekiErrorHandler errorHandler = new BekiErrorHandler();

    public BekiParserTD(Scanner scanner) {
        super(scanner);
    }

    public void parse() throws Exception {
        Token token;
        long startTime = System.currentTimeMillis();

        try {
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();

                if (tokenType != ERROR) {
                    if (tokenType == ID) {
                        String name = token.getText();

                        SymTabEntry entry = symTabStack.lookup(name);
                        if (entry == null) {
                            entry = symTabStack.enterLocal(name);
                        }

                        entry.appendLineNumber(token.getLineNumber());
                    }
                    sendMessage(new Message(TOKEN, new Object[] {token.getLineNumber(), token.getPosition(), tokenType, token.getText(), token.getValue()}));
                } else if (tokenType == ERROR) {
                    errorHandler.flag(token, (BekiErrorCode) token.getValue(), this);
                }
            }

            float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
            sendMessage(new Message(PARSER_SUMMARY, new Number[] {token.getLineNumber(), getErrorCount(), elapsedTime}));
        } catch (java.io.IOException ex) {
            errorHandler.abortTranslation(IO_ERROR, this);
        }
    }

    public int getErrorCount() {
        return errorHandler.getErrorCount();
    }
}
