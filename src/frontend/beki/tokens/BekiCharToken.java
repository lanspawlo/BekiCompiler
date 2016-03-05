package frontend.beki.tokens;

import frontend.*;
import frontend.beki.*;

import static frontend.Source.EOL;
import static frontend.Source.EOF;
import static frontend.beki.BekiTokenType.*;
import static frontend.beki.BekiErrorCode.*;

/**
 * Created by lanspawlo on 3/5/16.
 */
public class BekiCharToken extends BekiToken {
    public BekiCharToken(Source source) throws Exception {
        super(source);
    }

    protected void extract() throws Exception {
        StringBuilder textBuffer = new StringBuilder();
        StringBuilder valueBuffer = new StringBuilder();

        char currentChar = nextChar();
        textBuffer.append('\'');

        if (currentChar != EOF) {
            textBuffer.append(currentChar);
            valueBuffer.append(currentChar);
            currentChar = nextChar();
        }

        if (currentChar == '\'') {
            nextChar();
            textBuffer.append('\'');

            type = CHARCONST;
            value = valueBuffer.toString();
        }
        else {
            type = ERROR;
            value = UNEXPECTED_EOF;
        }

        text = textBuffer.toString();
    }
}
