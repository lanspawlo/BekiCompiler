package frontend.pascal.tokens;

import frontend.*;
import frontend.pascal.*;

import static frontend.pascal.PascalTokenType.*;
import static frontend.pascal.PascalErrorCode.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class PascalSpecialSymbolToken extends PascalToken {
    public PascalSpecialSymbolToken(Source source) throws Exception {
        super(source);
    }

    protected void extract()
            throws Exception
    {
        char currentChar = currentChar();

        text = Character.toString(currentChar);
        type = null;

        switch (currentChar) {

            case '+':  case '-':  case '*':  case '/':  case ',':
            case ';':  case '\'': case '=':  case '(':  case ')':
            case '[':  case ']':  case '{':  case '}':  case '^': {
                nextChar();
                break;
            }

            case ':': {
                currentChar = nextChar();

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar();
                }

                break;
            }

            case '<': {
                currentChar = nextChar();

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar();
                }
                else if (currentChar == '>') {
                    text += currentChar;
                    nextChar();
                }

                break;
            }

            case '>': {
                currentChar = nextChar();

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar();
                }

                break;
            }

            case '.': {
                currentChar = nextChar();

                if (currentChar == '.') {
                    text += currentChar;
                    nextChar();
                }

                break;
            }

            default: {
                nextChar();
                type = ERROR;
                value = INVALID_CHARACTER;
            }
        }

        if (type == null) {
            type = SPECIAL_SYMBOLS.get(text);
        }
    }
}
