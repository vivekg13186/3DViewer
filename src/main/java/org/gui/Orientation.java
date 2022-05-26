package org.gui;

public enum Orientation {
    Horizontal(0), ///< Layout expands on horizontal axis.
    Vertical(1);
    private final int value;

    Orientation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
