package intermediate;

import java.util.ArrayList;

/**
 * Created by lanspawlo on 2/28/16.
 */
public interface SymTabStack {
    public int getCurrentNestingLevel();
    public SymTab getLocalSymTab();
    public SymTabEntry enterLocal(String name);
    public SymTabEntry lookupLocal(String name);
    public SymTabEntry lookup(String name);
}
