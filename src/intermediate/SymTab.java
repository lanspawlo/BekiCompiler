package intermediate;

import java.util.ArrayList;

/**
 * Created by lanspawlo on 2/9/16.
 */
public interface SymTab {
    public int getNestingLevel();
    public SymTabEntry enter(String name);
    public SymTabEntry lookup(String name);
    public ArrayList<SymTabEntry> sortedEntries();
}
