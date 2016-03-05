package frontend.beki;

import frontend.*;
import frontend.beki.tokens.*;

import static frontend.Source.EOF;
import static frontend.beki.BekiTokenType.*;
import static frontend.beki.BekiErrorCode.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class BekiScanner extends Scanner {

    public BekiScanner(Source source) {
        super(source);
    }

    protected Token extractToken() throws Exception {
        skipWhiteSpace();

        Token token;
        char currentChar = currentChar();

        if (currentChar == EOF) {
            token = new EofToken(source);
        }
        else if (Character.isLetter(currentChar) || currentChar == '_') {
            token = new BekiWordToken(source);
        }
        else if (Character.isDigit(currentChar)) {
            token = new BekiNumberToken(source);
        }
        else if (currentChar == '\'') {
            token = new BekiCharToken(source);
        }
        else if (currentChar == '\"') {
            token = new BekiStringToken(source);
        }
        else if (BekiTokenType.SPECIAL_SYMBOLS.containsKey(Character.toString(currentChar))) {
            token = new BekiSpecialSymbolToken(source);
        }
        else {
            token = new BekiErrorToken(source, INVALID_CHARACTER, Character.toString(currentChar));
            nextChar();
        }
        return token;
    }

    private void skipWhiteSpace() throws Exception {
        char currentChar = currentChar();

        while (Character.isWhitespace(currentChar) || (currentChar == '#')) {
            if (currentChar == '#') {
                do {
                    currentChar = nextChar();
                } while ((currentChar != '#') && (currentChar != EOF));

                if (currentChar == '#') {
                    currentChar = nextChar();
                }
            }
            else {
                currentChar = nextChar();
            }
        }
    }
}
