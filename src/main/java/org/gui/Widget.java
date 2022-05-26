package org.gui;

import org.joml.Vector2i;

import java.util.List;

public class Widget {

    private Theme theme;
    private List<Widget> children;

    private boolean visible;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int width,height;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public List<Widget> getChildren() {
        return children;
    }

    public void setChildren(List<Widget> children) {
        this.children = children;
    }

    public Vector2i preferredSize(long ctx) {
        return null;
        }

    public Vector2i fixedSize() {
        return null;
    }

    public void setPosition(Vector2i pos) {
    }

    public void setSize(Vector2i targetSize) {
    }

    public void performLayout(long ctx) {
    }


    public int getFixedWidth() {
        return 0;
    }
}
