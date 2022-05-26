package org.viewer1;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.awt.event.*;


public class Camera implements MouseMotionListener,MouseListener , MouseWheelListener {
    int last_mx = 0, last_my = 0, cur_mx = 0, cur_my = 0;
    boolean arcball_on = false;


    Matrix4f viewMatrix=new Matrix4f();
    float radius = 1000;
    float angleX=0;
    float angleY=0;
    float prevRX=0;
    float prevRY=0;
    Vector3f center = new Vector3f();
    Vector3f pos = new Vector3f();

    public void reset(float radius){
        this. radius = radius;
          angleX=0;
          angleY=0;
          prevRX=0;
          prevRY=0;
        center.set(0,0,0);
        pos.set(0,0,0);
    }
    public Matrix4f getViewMatrix(){


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

    public Vector3f getPos(){
        return  viewMatrix.getTranslation(pos);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(arcball_on){
            cur_mx =e.getX();
            cur_my =e.getY();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
            arcball_on=true;
            last_mx = cur_mx=e.getX();
            last_my = cur_my= e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(arcball_on){
           /* AxisAngle4f axisAngle4f=new AxisAngle4f();
            axisAngle4f= viewMatrix.getRotation(axisAngle4f);
            angleX=  axisAngle4f.x;
            angleY=axisAngle4f.y;*/
            angleX = prevRX;
            angleY = prevRY;

        }
        arcball_on=false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double f = e.getPreciseWheelRotation();
        if(f>0){
            radius+=0.1;
        }else{
            radius-=0.1;
        }
        //radius = Math.max(radius,2);
        //radius = Math.min(10,radius);
        System.out.println(radius);
    }
}
