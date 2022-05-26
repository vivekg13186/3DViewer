package org.gui;


import org.joml.Vector2i;
import static org.gui.GUIUtil.*;

public class BoxLayout implements Layout {
    private Orientation orientation;
    private Alignment alignment = Alignment.Middle;
    private int margin = 0;
    private int spacing = 0;

    public BoxLayout(Orientation orientation, Alignment alignment, int margin, int spacing) {
        this.orientation = orientation;
        this.alignment = alignment;
        this.margin = margin;
        this.spacing = spacing;
    }

    public BoxLayout(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void performLayout(long ctx, Widget widget) {

        Vector2i fs_w = widget.fixedSize();

        Vector2i containerSize = new Vector2i(fs_w.x > 0 ? fs_w.x : widget.getWidth(), fs_w.y > 0 ? fs_w.y : widget.getHeight());

        int axis1 = orientation.getValue();
        int axis2 = (orientation.getValue() + 1) % 2;
        int position = margin;
        int yOffset = 0;
        if (widget instanceof Window) {
            Window w = (Window) widget;
            if (w.getTitle() != null && !w.getTitle().isEmpty()) {
                if (orientation == Orientation.Vertical) {
                    position += widget.getTheme().windowHeaderHeight - margin / 2;
                } else {
                    yOffset = widget.getTheme().windowHeaderHeight;
                    containerSize.y -= yOffset;
                }
            }
        }

        boolean first = true;
        for (Widget w : widget.getChildren()) {
            if (!w.isVisible()) continue;
            if (first) first = false;
            else {
                position += spacing;
            }
            Vector2i ps = w.preferredSize(ctx);
            Vector2i fs = w.fixedSize();
            Vector2i targetSize = new Vector2i(
                    fs.x > 0 ? fs.x : ps.x,
                    fs.y > 0 ? fs.y : ps.y
            );
            Vector2i pos = new Vector2i(0, yOffset);
            set(pos, axis1, position);
            switch (alignment) {
                case Minimum:
                    add(pos, axis2, margin);
                    break;
                case Middle:
                    add(pos, axis2, (containerSize.get(axis2) - targetSize.get(axis2)) / 2);
                    break;
                case Maximum:
                    add(pos, axis2, (containerSize.get(axis2) - targetSize.get(axis2)) - margin * 2);

                    break;
                case Fill:
                    add(pos, axis2, margin);
                    set(targetSize, axis2, fs.get(axis2) > 0 ? fs.get(axis2) : containerSize.get(axis2) - margin * 2);
                    break;
            }
            w.setPosition(pos);
            w.setSize(targetSize);
            w.performLayout(ctx);
            position += targetSize.get(axis1);
        }

    }


    @Override
    public Vector2i preferredSize(long ctx, Widget widget) {
        Vector2i size = new Vector2i(2 * margin);

        int yOffset = 0;
        if (widget instanceof Window) {
            Window w = (Window) widget;
            if (w.getTitle() != null && !w.getTitle().isEmpty()) {
                if (orientation == Orientation.Vertical) {
                    size.x = widget.getTheme().windowHeaderHeight - margin / 2;
                }
            } else {
                yOffset = widget.getTheme().windowHeaderHeight;
            }
        }


        boolean first = true;
        int axis1 = orientation.getValue();
        int axis2 = (orientation.getValue() + 1) % 2;

        for (Widget w : widget.getChildren()) {
            if (!w.isVisible()) continue;
            if (first) {
                first = false;
            } else {
                add(size, axis1, spacing);
            }
            Vector2i ps = w.preferredSize(ctx);
            Vector2i fs = w.fixedSize();
            Vector2i targetSize = new Vector2i(fs.x > 0 ? fs.x : ps.x, fs.y > 0 ? fs.y : ps.y);

            add(size, axis1, targetSize.get(axis1));
            set(size, axis2, Math.max(size.get(axis2), targetSize.get(axis2) + 2 * margin));

            first = false;


        }
        size.y += yOffset;

        return size;
    }
}
