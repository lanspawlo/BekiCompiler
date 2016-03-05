package backend.interpreter;

import backend.*;
import intermediate.*;
import message.*;
import static message.MessageType.INTERPRETER_SUMMARY;

/**
 * Created by lanspawlo on 2/9/16.
 */
public class Executor extends Backend {

    public void process(ICode icode, SymTabStack symTabStack) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int instructionCount = 0;

        sendMessage(new Message(INTERPRETER_SUMMARY, new Number[] {instructionCount, elapsedTime}));
    }
}
