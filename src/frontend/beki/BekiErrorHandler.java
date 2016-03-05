package frontend.beki;

import frontend.*;
import message.Message;

import static frontend.beki.BekiTokenType.*;
import static frontend.beki.BekiErrorCode.*;
import static message.MessageType.SYNTAX_ERROR;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class BekiErrorHandler {
    private static final int MAX_ERRORS = 25;
    private static int errorCount = 0;

    public int getErrorCount() {
        return  errorCount;
    }

    public void flag(Token token, BekiErrorCode errorCode, Parser parser) {
        parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {token.getLineNumber(), token.getPosition(), token.getText(), errorCode.toString()}));

        if (++errorCount > MAX_ERRORS) {
            abortTranslation(TOO_MANY_ERRORS, parser);
        }
    }

    public void abortTranslation(BekiErrorCode errorCode, Parser parser) {
        String fatalText = "FATAL ERROR: " + errorCode.toString();
        parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {0, 0, "", fatalText}));
        System.exit(errorCode.getStatus());
    }
}
