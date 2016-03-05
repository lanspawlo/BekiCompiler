package intermediate.symtabimpl;

import intermediate.SymTabKey;

/**
 * Created by lanspawlo on 2/28/16.
 */
public enum SymTabKeyImpl implements SymTabKey {
    CONSTANT_VALUE,

    ROUTINE_CODE, ROUTINE_SYMTAB, ROUTINE_ICODE,
    ROUTINE_PARMS, ROUTINE_ROUTINES,

    DATA_VALUE
}
