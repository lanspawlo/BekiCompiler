package intermediate.symtabimpl;

import java.util.ArrayList;
import intermediate.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class SymTabStackImpl extends ArrayList<SymTab> implements SymTabStack {
    private int currentNestingLevel;

    public SymTabStackImpl() {
        this.currentNestingLevel = 0;
        add(SymTabFactory.createSymTab(currentNestingLevel));
    }

    public int getCurrentNestingLevel() {
        return currentNestingLevel;
    }

    public SymTab getLocalSymTab() {
        return get(currentNestingLevel);
    }

    public SymTabEntry enterLocal(String name) {
        return get(currentNestingLevel).enter(name);
    }

    public SymTabEntry lookupLocal(String name) {
        return get(currentNestingLevel).lookup(name);
    }

    public SymTabEntry lookup(String name) {
        return lookupLocal(name);
    }
}
