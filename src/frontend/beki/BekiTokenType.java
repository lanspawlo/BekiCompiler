package frontend.beki;

import java.util.Hashtable;
import java.util.HashSet;

import frontend.TokenType;

/**
 * Created by lanspawlo on 2/28/16.
 */
public enum BekiTokenType implements TokenType {

    PROGRNAME("JUNGALAN"), START("PIS"), END("POS"), VAR("BARYA"), CONST("LETICIA"),
    INTEGER("JUTAY"), DECIMAL("DAKS"), BOOLEAN("BOOLMA"), CHAR("CHAKA"), STRING("CHARING"),
    TRUE("PAK"), FALSE("WIT"), AND("ANDABELZ"), OR("OKRAY"),
    NOTOP("WIZ"), IF("KRUNG"), ELSEIF("ELSKRUNG"), ELSE("ANSHANELS"), SWITCH("SWITCHIKEL"),
    CASE("KAHON"), DEFAULT("TFIOS"), DO("GORA"), WHILE("WHILEY CYRUS"), FOR("EVERLOO"),
    BREAK("JIWAN"), CONT("CAIRO EGYPT"), IN("ENTOURAGE"), OUT("GIVENCHY"), ENDL("ENDCHY"),
    PI("M.PI"), E("M.E"), VOID("WAS"), RETURN("KERI"),

    ASOP("<-"), AASOP("<-+"), SASOP("<--"), MASOP("<-*"), DASOP("<-/"),
    ADD("+"), SUB("-"), MULT("*"), DIV("/"), MOD("%"), INC("++"), DEC("--"), POW("^"),
    LSTH("<"), GRTH(">"), LSEQ("<="), GREQ(">="), EQUAL("<->"), NOT_EQUAL("</>"),
    LPAR("("), RPAR(")"), LCURL("{"), RCURL("}"), //LBRAC("["), RBRAC("]"),
    COLON(":"), SEMIC(";"), COMMA(","),

    AROP1, AROP2, RELOP, LOGOP,
    ID, NUMCONST, CHARCONST, BOOLCONST, STRINGCONST, DECCONST,
    ERROR, EOL, EOF;

    private static final int FIRST_RESERVED_INDEX = PROGRNAME.ordinal();
    private static final int LAST_RESERVED_INDEX  = RETURN.ordinal();

    private static final int FIRST_SPECIAL_INDEX = ASOP.ordinal();
    private static final int LAST_SPECIAL_INDEX  = COMMA.ordinal();

    private String text;

    BekiTokenType() {
        this.text = this.toString();
    }

    BekiTokenType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Hashtable<String, BekiTokenType> RESERVED_WORDS = new Hashtable<>();
    static {
        BekiTokenType values[] = BekiTokenType.values();
        for (int i = FIRST_RESERVED_INDEX; i <= LAST_RESERVED_INDEX; ++i) {
            RESERVED_WORDS.put(values[i].getText(), values[i]);
        }
    }

    public static Hashtable<String, BekiTokenType> SPECIAL_SYMBOLS = new Hashtable<>();
    static {
        BekiTokenType values[] = BekiTokenType.values();
        for (int i = FIRST_SPECIAL_INDEX; i <= LAST_SPECIAL_INDEX; ++i) {
            SPECIAL_SYMBOLS.put(values[i].getText(), values[i]);
        }
    }

}
