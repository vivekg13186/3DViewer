package org.gui;

import org.engine.Window;

public interface IGUI {

    void init(Window window);
    void render(Window window);
    void dispose();
}
