package org.gui;

public enum Alignment {
    Minimum(0),  ///< Take only as much space as is required.
    Middle(1),      ///< Center align.
    Maximum(2),     ///< Take as much space as is allowed.
    Fill (3)   ;
    private final int value;

     Alignment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
