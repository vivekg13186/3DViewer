package org.engine.graph;

public interface InputEventListener {

    void mouseButtonPressed(Input i);
    void mouseButtonReleased(Input i);
    void mouseEntered(Input i);
    void mouseExit(Input i);
    void mouseDrag(Input i,float x,float y);
    void mouseMove(Input i,float x,float y);
    void mouseScroll(Input i,float x,float y);

    void onKeyPressEnter(Input i,int keycode,int mod);
    void onKeyRelease(Input i,int keycode,int mod);
    void onKeyPress(Input i,int keycode,int mod);

}
