package org.gui;

import org.joml.Vector2i;


public interface Layout {


    void performLayout(long ctx, Widget widget);
    Vector2i preferredSize(long ctx,  Widget widget);
}
