package org.gui;

import org.joml.Vector2i;

public class GUIUtil {

public static boolean hasCaption(Widget w){
    if(w instanceof  Label){
        Label  label = (Label) w;
        String title  = label.getCaption();
        return title!=null && !title.isEmpty();
    }
    return false;


}
    public static  boolean isWindow(Widget widget){
        return  widget instanceof Window;
    }

    public static boolean hasTitle(Widget widget){
        Window w = (Window) widget;
        String title  = w.getTitle();
        if(title!=null && !title.isEmpty()){
            return true;
        }
        return false;
    }
    public static  Vector2i add(Vector2i v, int index, int val) {
        if (index == 0) {
            v.x += val;
        } else {
            v.y += val;
        }
        return v;
    }

    public static Vector2i set(Vector2i v, int index, int val) {
        if (index == 0) {
            v.x = val;
        } else {
            v.y = val;
        }
        return v;
    }

}
