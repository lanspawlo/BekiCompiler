package frontend.pascal.tokens;

import frontend.*;
import frontend.pascal.*;

import static frontend.pascal.PascalTokenType.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class PascalWordToken extends PascalToken {
    public PascalWordToken(Source source) throws Exception {
        super(source);
    }

    protected void extract() throws Exception {
        StringBuilder textBuffer = new StringBuilder();
        char currentChar = currentChar();

        while (Character.isLetterOrDigit(currentChar)) {
            textBuffer.append(currentChar);
            currentChar = nextChar();
        }

        text = textBuffer.toString();

        type = (RESERVED_WORDS.contains(text.toLowerCase())) ? PascalTokenType.valueOf(text.toUpperCase()) : IDENTIFIER;                                  // identifier
    }
}
