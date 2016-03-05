package frontend.beki.tokens;

import frontend.*;
import frontend.beki.*;

import static frontend.beki.BekiTokenType.*;
import static frontend.beki.BekiErrorCode.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class BekiNumberToken extends BekiToken {
    private static final int MAX_EXPONENT = 37;

    public BekiNumberToken(Source source) throws Exception {
        super(source);
    }

    protected void extract() throws Exception {
        StringBuilder textBuffer = new StringBuilder();
        extractNumber(textBuffer);
        text = textBuffer.toString();
    }

    protected void extractNumber(StringBuilder textBuffer) throws Exception {
        String wholeDigits = null;
        String fractionDigits = null;
        char currentChar;

        type = NUMCONST;

        wholeDigits = unsignedIntegerDigits(textBuffer);
        if (type == ERROR) {
            return;
        }

        currentChar = currentChar();
        if (currentChar == '.') {
            type = DECCONST;
            textBuffer.append(currentChar);
            currentChar = nextChar();

            fractionDigits = unsignedIntegerDigits(textBuffer);
            if (type == ERROR) {
                return;
            }
        }

        currentChar = currentChar();

        if (type == NUMCONST) {
            int integerValue = computeIntegerValue(wholeDigits);

            if (type != ERROR) {
                value = new Integer(integerValue);
            }
        } else if (type == DECCONST) {
            float floatValue = computeFloatValue(wholeDigits, fractionDigits);

            if (type != ERROR) {
                value = new Float(floatValue);
            }
        }
    }

    private String unsignedIntegerDigits(StringBuilder textBuffer) throws Exception {
        char currentChar = currentChar();

        if (!Character.isDigit(currentChar)) {
            type = ERROR;
            value = INVALID_NUMBER;
            return null;
        }

        StringBuilder digits = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            textBuffer.append(currentChar);
            digits.append(currentChar);
            currentChar = nextChar();
        }

        return digits.toString();
    }

    private int computeIntegerValue(String digits) {
        if (digits == null) {
            return 0;
        }

        int integerValue = 0;
        int prevValue = -1;
        int index = 0;

        while ((index < digits.length()) && (integerValue >= prevValue)) {
            prevValue = integerValue;
            integerValue = 10*integerValue +
                    Character.getNumericValue(digits.charAt(index++));
        }

        if (integerValue >= prevValue) {
            return integerValue;
        }

        else {
            type = ERROR;
            value = RANGE_INTEGER;
            return 0;
        }
    }

    private float computeFloatValue(String wholeDigits, String fractionDigits) {
        double floatValue = 0.0;
        int exponentValue = 0;
        String digits = wholeDigits;


        if (fractionDigits != null) {
            exponentValue -= fractionDigits.length();
            digits += fractionDigits;
        }

        if (Math.abs(exponentValue + wholeDigits.length()) > MAX_EXPONENT) {
            type = ERROR;
            value = RANGE_REAL;
            return 0.0f;
        }

        int index = 0;
        while (index < digits.length()) {
            floatValue = 10*floatValue +
                    Character.getNumericValue(digits.charAt(index++));
        }

        if (exponentValue != 0) {
            floatValue *= Math.pow(10, exponentValue);
        }

        return (float) floatValue;
    }
}
