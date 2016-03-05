package frontend.beki.tokens;

import frontend.*;
import frontend.beki.*;

import static frontend.beki.BekiTokenType.*;
import static frontend.beki.BekiErrorCode.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class BekiSpecialSymbolToken extends BekiToken {
    public BekiSpecialSymbolToken(Source source) throws Exception {
        super(source);
    }

    protected void extract()
            throws Exception
    {
        char currentChar = currentChar();

        text = Character.toString(currentChar);
        type = null;

        switch (currentChar) {

            case '*':  case '/':  case '%':  case ',':  case ';':
            case '=':  case '(':  case ')':  case ':':
            case '{':  case '}':  case '^': //case '[':  case ']':
            {
                nextChar();
                break;
            }

            case '<': {
                currentChar = nextChar();

                switch (currentChar) {
                    case '-': {
                        text += currentChar;
                        currentChar = nextChar();
                        if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                            text += currentChar;
                            nextChar();
                        }
                        break;
                    }
                    case '=':  case '>': {
                        text += currentChar;
                        nextChar();
                        break;
                    }
                }
                break;
            }

            case '+': {
                currentChar = nextChar();

                if (currentChar == '+') {
                    text += currentChar;
                    nextChar();
                }

                break;
            }
            case '-': {
                currentChar = nextChar();

                if (currentChar == '-') {
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

            default: {
                nextChar();
                type = ERROR;
                value = INVALID_CHARACTER;
            }
        }

        if (type == null) {
            type = SPECIAL_SYMBOLS.get(text);
            if (type == AASOP || type == SASOP || type == MASOP || type == DASOP) {
                type = ASOP;
            } else if (type == ADD || type == MULT) {
                type = AROP1;
            } else if (type == MULT || type == DIV || type == MOD) {
                type = AROP2;
            } else if (type == LSTH || type == GRTH || type == LSEQ || type == GREQ || type == EQUAL || type == NOT_EQUAL) {
                type = RELOP;
            }
        }
    }
}
