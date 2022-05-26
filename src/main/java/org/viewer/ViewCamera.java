package org.viewer;

import org.engine.Window;
import org.engine.graph.Controller;
import org.engine.graph.ICamera;
import org.engine.graph.Input;
import org.engine.graph.InputEventListener;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.event.MouseEvent;

public class ViewCamera  implements ICamera, InputEventListener {
    int last_mx = 0, last_my = 0, cur_mx = 0, cur_my = 0;
    boolean arcball_on = false;


    Matrix4f viewMatrix=new Matrix4f();
    float radius = 5;
    float angleX=0;
    float angleY=0;
    float prevRX=0;
    float prevRY=0;
    Vector3f center = new Vector3f();
    Vector3f pos = new Vector3f();


    private  Window  window;
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Vector3f getCenter() {
        return center;
    }

    public void setCenter(Vector3f center) {
        this.center = center;
    }
    public ViewCamera( ){


    }
    @Override
    public void mouseButtonPressed(Input input) {
        //System.out.println("mouseButtonPressed");
        if(input.isLeftMouseButtonPressed()){
            arcball_on=true;
            Vector2f v = input.getMousePosition();
            last_mx = cur_mx= (int) v.x;
            last_my = cur_my= (int) v.y;
        }
    }

    @Override
    public void mouseButtonReleased(Input input) {
      //  System.out.println("mouseButtonReleased");
        if(arcball_on){

            angleX = prevRX;
            angleY = prevRY;

        }
        arcball_on=false;
    }

    @Override
    public void mouseEntered(Input input) {
        //System.out.println("mouseEntered");
    }

    @Override
    public void mouseExit(Input input) {
        //System.out.println("mouseExit");
    }

    @Override
    public void mouseDrag(Input input,float x, float y) {
        //System.out.println("mouseDrag");

        if(arcball_on){
            Vector2f v = input.getMousePosition();
            cur_mx = (int) v.x;
            cur_my = (int) v.y;
        }
    }

    @Override
    public void mouseMove(Input input,float x, float y) {
        //System.out.println("mouseMove");
    }

    @Override
    public void mouseScroll(Input input,float x, float y) {
        //System.out.println("mouseScroll");
        double f = y;
        if(f>0){
            radius+=0.1;
        }else{
            radius-=0.1;
        }
        //radius = Math.max(radius,2);
        //radius = Math.min(10,radius);
        System.out.println(radius);
    }

    @Override
    public void onKeyPressEnter(Input i, int keycode, int mod) {
        System.out.println(keycode);
    }

    @Override
    public void onKeyRelease(Input i, int keycode, int mod) {
        System.out.println(keycode);
    }

    @Override
    public void onKeyPress(Input i, int keycode, int mod) {
        System.out.println(keycode);
    }

    @Override
    public Matrix4f getViewMatrix() {
        if(arcball_on){
            float speed= -0.01f;
            float dx = (float) ((last_mx-cur_mx)*speed);
            float dy = (float) ((last_my-cur_my)*speed);
            prevRX = angleX+dx;
            prevRY = angleY+dy;
        }
        viewMatrix.identity();
        return viewMatrix.arcball(radius,center,prevRY ,prevRX);
    }
}
