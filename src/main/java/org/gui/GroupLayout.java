package org.gui;

import org.joml.Vector2i;

import static org.gui.GUIUtil.*;

public class GroupLayout implements Layout {

    private int margin = 15;

    /// The spacing between widgets of this GroupLayout.
    private int spacing = 6;

    /// The spacing between groups of this GroupLayout.
    private int groupSpacing = 14;

    /// The indent amount of a group under its defining Label of this GroupLayout.
    private int groupIndent = 20;

    public GroupLayout() {
    }

    public GroupLayout(int margin, int spacing, int groupSpacing, int groupIndent) {
        this.margin = margin;
        this.spacing = spacing;
        this.groupSpacing = groupSpacing;
        this.groupIndent = groupIndent;
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

    public int getGroupSpacing() {
        return groupSpacing;
    }

    public void setGroupSpacing(int groupSpacing) {
        this.groupSpacing = groupSpacing;
    }

    public int getGroupIndent() {
        return groupIndent;
    }

    public void setGroupIndent(int groupIndent) {
        this.groupIndent = groupIndent;
    }

    @Override
    public void performLayout(long ctx, Widget widget) {
        int height = margin;
        int availableWidth =
                (widget.getFixedWidth() > 0 ? widget.getFixedWidth() : widget.getWidth()) - 2 * margin;

        if (isWindow(widget) && hasTitle(widget)) {
            height += widget.getTheme().windowHeaderHeight - margin / 2;
        }


        boolean first = true, indent = false;

        for (Widget w : widget.getChildren()) {
            if (!w.isVisible()) continue;

            if (!first)
                height += w instanceof Label ? groupSpacing : spacing;

            first = false;
            boolean indentCur = indent && !(w instanceof Label);
            Vector2i ps = new Vector2i(availableWidth - (indentCur ? groupIndent : 0),
                    w.preferredSize(ctx).y());
            Vector2i fs = w.fixedSize();
            Vector2i targetSize = new Vector2i(
                    fs.x > 0 ? fs.x : ps.x,
                    fs.y > 0 ? fs.y : ps.y
            );
            w.setPosition(new Vector2i(margin + (indentCur ? groupIndent : 0), height));
            w.setSize(targetSize);
            w.performLayout(ctx);

            height += targetSize.y();

            if (w instanceof Label) {
                indent = !hasCaption(w);
            }

        }

    }

    @Override
    public Vector2i preferredSize(long ctx, Widget widget) {
        int height = margin, width = 2 * margin;
        if (isWindow(widget) && hasTitle(widget)) {
            height += widget.getTheme().windowHeaderHeight - margin / 2;
        }


        boolean first = true;
        boolean indent = false;
        for (Widget w : widget.getChildren()) {
            if (!w.isVisible()) continue;

            if (!first)
                height += w instanceof Label ? groupSpacing : spacing;

            first = false;
            Vector2i ps = w.preferredSize(ctx);
            Vector2i fs = w.fixedSize();
            Vector2i targetSize = new Vector2i(
                    fs.x > 0 ? fs.x : ps.x,
                    fs.y > 0 ? fs.y : ps.y
            );
            boolean indentCur = indent && !(w instanceof Label);
            height += targetSize.y;
            width = Math.max(width, targetSize.x + 2 * margin + (indentCur ? groupIndent : 0));

            if (w instanceof Label) {
                indent = !hasCaption(w);
            }

        }

        height += margin;
        return new Vector2i(width, height);
    }
}
