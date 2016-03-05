package frontend.pascal.tokens;

import frontend.*;
import frontend.pascal.*;

import static frontend.pascal.PascalTokenType.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class PascalErrorToken extends PascalToken {

    public PascalErrorToken(Source source, PascalErrorCode errorCode, String tokenText) throws Exception {
        super(source);
        this.text = tokenText;
        this.type = ERROR;
        this.value = errorCode;
    }

    protected void extract() throws Exception {

    }
}
