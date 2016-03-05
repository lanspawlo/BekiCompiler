package intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.HashMap;

import intermediate.*;

/**
 * Created by lanspawlo on 2/28/16.
 */
public class SymTabEntryImpl extends HashMap<SymTabKey, Object> implements SymTabEntry {
    private String name;
    private SymTab symTab;
    private ArrayList<Integer> lineNumbers;

    public SymTabEntryImpl(String name, SymTab symTab) {
        this.name = name;
        this.symTab = symTab;
        this.lineNumbers = new ArrayList<Integer>();
    }

    public String getName() {
        return name;
    }

    public SymTab getSymTab() {
        return symTab;
    }

    public ArrayList<Integer> getLineNumbers() {
        return lineNumbers;
    }

    public void appendLineNumber(int lineNumber) {
        lineNumbers.add(lineNumber);
    }

    public Object getAttribute(SymTabKey key) {
        return get(key);
    }

    public void setAttribute(SymTabKey key, Object value) {
        put(key, value);
    }
}
