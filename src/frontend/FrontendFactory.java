package frontend;

import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalScanner;
import frontend.beki.BekiParserTD;
import frontend.beki.BekiScanner;

/**
 * Created by lanspawlo on 2/9/16.
 */
public class FrontendFactory {
    public static Parser createParser(String language, String type, Source source) throws Exception {
        if (language.equalsIgnoreCase("Pascal") && type.equalsIgnoreCase("top-down")) {
            Scanner scanner = new PascalScanner(source);
            return new PascalParserTD(scanner);
        } else if (language.equalsIgnoreCase("Beki") && type.equalsIgnoreCase("top-down")) {
            Scanner scanner = new BekiScanner(source);
            return new BekiParserTD(scanner);
        } else if (!(language.equalsIgnoreCase("Pascal") || language.equalsIgnoreCase("Beki"))) {
            throw new Exception("Parse factory: Invalid language '" + language + "'");
        } else {
            throw new Exception("Parse factory: Invalid type '" + language + "'");
        }
    }
}
