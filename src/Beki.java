import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import frontend.*;
import backend.*;
import intermediate.*;
import message.*;
import util.*;

import static frontend.beki.BekiTokenType.STRINGCONST;
import static message.MessageType.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class Beki {
    private Parser parser;
    private Source source;
    private ICode iCode;
    private SymTabStack symTabStack;
    private Backend backend;

    public Beki(String operation, String filePath, String flags) {
        try {
            boolean intermediate = flags.indexOf('i') > -1;
            boolean xref = flags.indexOf('x') > -1;

            source = new Source(new BufferedReader(new FileReader(filePath)));
            source.addMessageListener(new SourceMessageListener());

            parser = FrontendFactory.createParser("Beki", "top-down", source);
            parser.addMessageListener(new ParserMessageListener());

            backend = BackendFactory.createBackend(operation);
            backend.addMessageListener(new BackendMessageListener());

            parser.parse();
            source.close();

            iCode = parser.getICode();
            symTabStack = parser.getSymTabStack();

            if (xref) {
                CrossReferencer crossReferencer = new CrossReferencer();
                crossReferencer.print(symTabStack);
            }

            backend.process(iCode, symTabStack);
        } catch (Exception ex) {
            System.out.println("****** Internal translator error. ******");
            ex.printStackTrace();
        }
    }

    private static final String FLAGS = "[-ix]";
    private static final String USAGE = "Usage: Beki execute|compile " + FLAGS + " <source file path>";

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
//        System.out.println("Enter file name: ");
//
//        String fileName;
//        fileName = scanner.next();
//
//        String op = "compile";
//        String fl = "";
//
//        new Beki(op, fileName, fl);

        try {
            String operation = args[0];

            if (!(operation.equalsIgnoreCase("compile") || operation.equalsIgnoreCase("execute"))) {
                throw new Exception();
            }

            int i = 0;
            String flags = "";

            while ((++i < args.length) && (args[i].charAt(0) == '-')) {
                flags += args[i].substring(1);
            }

            if (i < args.length) {
                String path = args[i];
                new Beki(operation, path, flags);
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            System.out.println(USAGE);
        }
    }

    private static final String SOURCE_LINE_FORMAT = "%03d %s";

    private class SourceMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();
            Object body[] = (Object []) message.getBody();

            switch (type) {
                case SOURCE_LINE: {
                    int lineNumber = (Integer) body[0];
                    String lineText = (String) body[1];

                    System.out.println(String.format(SOURCE_LINE_FORMAT, lineNumber, lineText));
                    break;
                }
            }
        }
    }

    private static final String TOKEN_FORMAT  =
            ">>> %-15s line=%03d, pos=%2d, text=\"%s\"";
    private static final String VALUE_FORMAT =
            ">>>                 value=%s";

    private static final String PARSER_SUMMARY_FORMAT =
            "\n%,20d source lines." +
                    "\n%,20d syntax errors." +
                    "\n%,20.2f seconds total parsing time.\n";

    private static final int PREFIX_WIDTH = 5;

    private class ParserMessageListener implements MessageListener {
        @Override
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case TOKEN: {
                    Object body[] = (Object []) message.getBody();
                    int lineNumber = (Integer) body[0];
                    int positionNumber = (Integer) body[1];
                    TokenType tokenType = (TokenType) body[2];
                    String tokenText = (String) body[3];
                    Object tokenValue = body[4];

                    System.out.println(String.format(TOKEN_FORMAT, tokenType, lineNumber, positionNumber, tokenText));
                    if (tokenValue != null) {
                        if (tokenType == STRINGCONST) {
                            tokenValue = "\"" + tokenValue + "\"";
                        }

                        System.out.println(String.format(VALUE_FORMAT, tokenValue));
                    }
                    break;
                }
                case SYNTAX_ERROR: {
                    Object body[] = (Object []) message.getBody();
                    int lineNumber = (Integer) body[0];
                    int positionNumber = (Integer) body[1];
                    String tokenType = (String) body[2];
                    String errorMessage = (String) body[3];

                    int spaceCount = PREFIX_WIDTH + positionNumber;
                    StringBuilder flagBuffer = new StringBuilder();

                    for (int i = 1; i < spaceCount; ++i) {
                        flagBuffer.append(' ');
                    }

                    flagBuffer.append("^\n");
                    flagBuffer.append("***").append(errorMessage).append(" line = ").append(lineNumber).append(" pos = ").append(positionNumber);

                    if (tokenType != null) {
                        flagBuffer.append(" [at \"").append(tokenType).append("\"]");
                    }

                    System.out.println(flagBuffer.toString());
                    break;
                }
                case PARSER_SUMMARY: {
                    Number body[] = (Number []) message.getBody();
                    int statementCount = (Integer) body[0];
                    int syntaxErrors = (Integer) body[1];
                    float elapsedTime = (Float) body[2];

                    System.out.println(String.format(PARSER_SUMMARY_FORMAT, statementCount, syntaxErrors, elapsedTime));

                    break;
                }
            }
        }
    }

    private static final String INTERPRETER_SUMMARY_FORMAT =
            "\n%,20d statements executed." +
                    "\n%,20d runtime errors." +
                    "\n%,20.2f seconds total execution time.\n";

    private static final String COMPILER_SUMMARY_FORMAT =
            "\n%,20d instructions generated." +
                    "\n%,20.2f seconds total code generation time.\n";


    private class BackendMessageListener implements MessageListener {
        public void messageReceived(Message message) {
            MessageType type = message.getType();

            switch (type) {
                case INTERPRETER_SUMMARY: {
                    Number body[] = (Number[]) message.getBody();
                    int executionCount = (Integer) body[0];
                    int runtimeErrors = (Integer) body[1];
                    float elapsedTime = (Float) body[2];

                    System.out.printf(INTERPRETER_SUMMARY_FORMAT, executionCount, runtimeErrors, elapsedTime);
                    break;
                }
                case COMPILER_SUMMARY: {
                    Number body[] = (Number[]) message.getBody();
                    int instructionCount = (Integer) body[0];
                    float elapsedTime = (Float) body[1];

                    System.out.printf(COMPILER_SUMMARY_FORMAT, instructionCount, elapsedTime);
                    break;
                }
            }
        }
    }
}
