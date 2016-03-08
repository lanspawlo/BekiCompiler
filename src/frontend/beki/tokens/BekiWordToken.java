package frontend.beki.tokens;

import frontend.*;
import frontend.beki.*;

import static frontend.beki.BekiTokenType.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class BekiWordToken extends BekiToken {
    public BekiWordToken(Source source) throws Exception {
        super(source);
    }

    protected void extract() throws Exception {
        StringBuilder textBuffer = new StringBuilder();
        char currentChar = currentChar();

        if (Character.isLetter(currentChar) || currentChar == '_') {
            textBuffer.append(currentChar);
            currentChar = nextChar();
        }

        while (Character.isLetterOrDigit(currentChar) || currentChar == '_' || currentChar == '-') {
            textBuffer.append(currentChar);
            currentChar = nextChar();
        }

        if (Character.isSpaceChar(currentChar)) {
            text = textBuffer.toString();
            if (text.equals("WHILEY") || text.equals("WHILEY")) {
                textBuffer.append(currentChar);
                currentChar = nextChar();
                while (Character.isLetterOrDigit(currentChar)) {
                    textBuffer.append(currentChar);
                    currentChar = nextChar();
                }
            }
        } else if (currentChar == '.') {
            text = textBuffer.toString();
            if (text.equals("M")) {
                textBuffer.append(currentChar);
                currentChar = nextChar();
                while (Character.isLetterOrDigit(currentChar)) {
                    textBuffer.append(currentChar);
                    currentChar = nextChar();
                }
            }
        }

        text = textBuffer.toString();

        type = (RESERVED_WORDS.containsKey(text)) ? RESERVED_WORDS.get(text) : ID;

        if (type == TRUE || type == FALSE) {
            type = BOOLCONST;
        } else if (type == AND || type == OR) {
            type = LOGOP;
        }
    }
}
