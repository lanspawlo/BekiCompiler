package frontend.beki.tokens;

import frontend.*;
import frontend.beki.*;

import static frontend.beki.BekiTokenType.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class BekiErrorToken extends BekiToken {

    public BekiErrorToken(Source source, BekiErrorCode errorCode, String tokenText) throws Exception {
        super(source);
        this.text = tokenText;
        this.type = ERROR;
        this.value = errorCode;
    }

    protected void extract() throws Exception {

    }
}
