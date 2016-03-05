package frontend.pascal;

import frontend.*;
import intermediate.*;
import message.*;

import static frontend.pascal.PascalTokenType.*;
import static frontend.pascal.PascalErrorCode.*;
import static intermediate.symtabimpl.SymTabEntryImpl.*;
import static message.MessageType.*;

/**
 * Created by lanspawlo on 2/9/16.
 */
public class PascalParserTD extends Parser {

    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();

    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }

    public void parse() throws Exception {
        Token token;
        long startTime = System.currentTimeMillis();

        try {
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();

                if (tokenType != ERROR) {
                    if (tokenType == IDENTIFIER) {
                        String name = token.getText().toLowerCase();

                        SymTabEntry entry = symTabStack.lookup(name);
                        if (entry == null) {
                            entry = symTabStack.enterLocal(name);
                        }

                        entry.appendLineNumber(token.getLineNumber());
                    }
                    sendMessage(new Message(TOKEN, new Object[] {token.getLineNumber(), token.getPosition(), tokenType, token.getText(), token.getValue()}));
                } else if (tokenType == ERROR) {
                    errorHandler.flag(token, (PascalErrorCode) token.getValue(), this);
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
