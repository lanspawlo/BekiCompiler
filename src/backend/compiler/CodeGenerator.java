package backend.compiler;

import backend.Backend;
import intermediate.ICode;
import intermediate.SymTab;
import intermediate.SymTabStack;
import message.*;

import static message.MessageType.COMPILER_SUMMARY;

/**
 * Created by lanspawlo on 2/9/16.
 */
public class CodeGenerator extends Backend {

    public void process(ICode icode, SymTabStack symTabStack) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int instructionCount = 0;

        sendMessage(new Message(COMPILER_SUMMARY, new Number[] {instructionCount, elapsedTime}));
    }
}
