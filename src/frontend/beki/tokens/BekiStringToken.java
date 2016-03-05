package frontend.beki.tokens;

import frontend.*;
import frontend.beki.*;

import static frontend.Source.EOL;
import static frontend.Source.EOF;
import static frontend.beki.BekiTokenType.*;
import static frontend.beki.BekiErrorCode.*;

/**
 * Created by lanspawlo on 2/28/16.
 * NOT YET IMPLEMENTED:
 * Detect scape characters (tab, newline, etc.)
 */
public class BekiStringToken extends BekiToken {
    public BekiStringToken(Source source) throws Exception {
        super(source);
    }

    protected void extract() throws Exception {
        StringBuilder textBuffer = new StringBuilder();
        StringBuilder valueBuffer = new StringBuilder();

        char currentChar = nextChar();
        textBuffer.append('\"');

        do {
            if (Character.isWhitespace(currentChar)) {
                currentChar = ' ';
            }

            if ((currentChar != '\"') && (currentChar != EOF)) {
                textBuffer.append(currentChar);
                valueBuffer.append(currentChar);
                currentChar = nextChar();
            }

            if (currentChar == '\"') {
                while ((currentChar == '\"') && (peekChar() == '\"')) {
                    textBuffer.append("\"\"");
                    valueBuffer.append(currentChar);
                    currentChar = nextChar();
                    currentChar = nextChar();
                }
            }
        } while ((currentChar != '\"') && (currentChar != EOF));

        if (currentChar == '\"') {
            nextChar();
            textBuffer.append('\"');

            type = STRINGCONST;
            value = valueBuffer.toString();
        }
        else {
            type = ERROR;
            value = UNEXPECTED_EOF;
        }

        text = textBuffer.toString();
    }
}
