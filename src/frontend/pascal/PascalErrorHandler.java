package frontend.pascal;

import frontend.*;
import message.Message;

import static frontend.pascal.PascalTokenType.*;
import static frontend.pascal.PascalErrorCode.*;
import static message.MessageType.SYNTAX_ERROR;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class PascalErrorHandler {
    private static final int MAX_ERRORS = 25;
    private static int errorCount = 0;

    public int getErrorCount() {
        return  errorCount;
    }

    public void flag(Token token, PascalErrorCode errorCode, Parser parser) {
        parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {token.getLineNumber(), token.getPosition(), token.getText(), errorCode.toString()}));

        if (++errorCount > MAX_ERRORS) {
            abortTranslation(TOO_MANY_ERRORS, parser);
        }
    }

    public void abortTranslation(PascalErrorCode errorCode, Parser parser) {
        String fatalText = "FATAL ERROR: " + errorCode.toString();
        parser.sendMessage(new Message(SYNTAX_ERROR, new Object[] {0, 0, "", fatalText}));
        System.exit(errorCode.getStatus());
    }
}
