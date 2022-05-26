package org.gui;

import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class GridLayout implements Layout{
    private Orientation orientation=Orientation.Horizontal;
    private List<Alignment> defaultAlignment =new ArrayList(2);
    private List<List<Alignment>> alignment =new ArrayList();
    private int resolution=2;
    private Vector2i spacing;
    private int margin;

    public GridLayout() {
        defaultAlignment.set(0,Alignment.Middle);
        defaultAlignment.set(0,Alignment.Middle);
    }

    public GridLayout(Orientation orientation, Alignment alignment,  int resolution, Vector2i spacing, int margin) {
        this.orientation = orientation;
        defaultAlignment.set(0,alignment);
        defaultAlignment.set(0,alignment);
        this.resolution = resolution;
        this.spacing = spacing;
        this.margin = margin;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public Vector2i getSpacing() {
        return spacing;
    }

    public void setSpacing(Vector2i spacing) {
        this.spacing = spacing;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public  Alignment getAlignment(int axis, int item)  {
        if(axis <alignment.size() && item < alignment.get(axis).size()){
            return alignment.get(axis).get(item);
        }else
            return defaultAlignment.get(axis);
    }

    /// Sets the Alignment of the columns.
    void setColAlignment(Alignment value) { defaultAlignment.set(0,value); }

    /// Sets the Alignment of the rows.
    void setRowAlignment(Alignment value) { defaultAlignment.set(1,value); }

    /// Use this to set variable Alignment for columns.
    void setColAlignment(List<Alignment> value) { alignment.set(0,value); }

    /// Use this to set variable Alignment for rows.
    void setRowAlignment(List<Alignment> value) { alignment.set(1,value); }
    @Override
    public void performLayout(long ctx, Widget widget) {

    }

    @Override
    public Vector2i preferredSize(long ctx, Widget widget) {
        return null;
    }

    private void computeLayout(long ctx, Widget widget, int[] grid) {
    }
}
