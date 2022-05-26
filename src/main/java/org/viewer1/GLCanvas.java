package org.viewer1;



import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.glClearColor;

public class GLCanvas extends AWTGLCanvas implements KeyListener {

    Matrix4f projectionMatrix;
    Renderer2 renderer;
    DirectionalLight directionalLight =new DirectionalLight(new Vector3f(1,1,1),new Vector3f(0,-1,0),0.0f);

    int width,height;

    public Renderer2 getRenderer() {
        return renderer;
    }

    public GLCanvas(GLData data){
        super(data);
        width = getWidth();
        height = getHeight();
        addKeyListener(this);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                width = getWidth();
                height = getHeight();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }
    public static final float FOV = (float) Math.toRadians(60.0f);

    public int getViewportHeight(){
        return height;
    }

    public int getViewportWidth(){
        return width;
    }

    public static final float Z_NEAR = 0.01f;


    public static  float Z_FAR = 10000.f;
    public PointLight[] pointLights = new PointLight[6];

    @Override
    public void initGL() {
        projectionMatrix = new Matrix4f();
        System.out.println("OpenGL version: " + effective.majorVersion + "." + effective.minorVersion + " (Profile: " + effective.profile + ")");
        createCapabilities();
        glClearColor(0.3f, 0.4f, 0.5f, 1);
        Vector3f color = new Vector3f(1,1,1);
        try {

            renderer = new Renderer2();
            renderer.init();
            addMouseListener(renderer.getCamera());
            addMouseMotionListener(renderer.getCamera());
            addMouseWheelListener(renderer.getCamera());
            float rad=renderer.mesh.getBoundingRadius()*2;
            Z_FAR = renderer.mesh.getBoundingRadius()*5;
            System.out.println("rad "+rad);
            float intensity=1f;
            pointLights[0]=new PointLight(color, new Vector3f(0,rad,0), intensity);
            pointLights[1]=new PointLight(color, new Vector3f(0,-rad,0), intensity);
            pointLights[2]=new PointLight(color, new Vector3f(0,0,rad), intensity);
            pointLights[3]=new PointLight(color, new Vector3f(0,0,-rad), intensity);
            pointLights[4]=new PointLight(color, new Vector3f(rad,0,0), intensity);
            pointLights[5]=new PointLight(color, new Vector3f(-rad,0,0), intensity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void paintGL() {
        renderer.render(this,new Vector3f(0.0f,0.0f,0.0f),pointLights,null,directionalLight);
        swapBuffers();
    }

    public Matrix4f getProjectionMatrix(){
            float aspectRatio = (float) getViewportWidth() / (float) getViewportHeight();
            return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    int i =0;
    @Override
    public void keyReleased(KeyEvent e) {
        String[] f = {
       "/Users/vivek/Developer/3DViewer/src/main/resources/org/viewer1/bunny.obj",
        "/Users/vivek/Downloads/pony-cartoon/source/a3ff7fc6894b4be396d75e70294655b1/Pony_cartoon.obj"
        };
          System.out.println(e.getKeyChar());
          if(e.getKeyChar()=='m'){
             // renderer.load_mesh="/Users/vivek/Developer/3DViewer/src/main/resources/org/viewer/bunny.obj";
              //renderer.load_mesh  ="/Users/vivek/Downloads/pony-cartoon/source/a3ff7fc6894b4be396d75e70294655b1/Pony_cartoon.obj";
            renderer.load_mesh =f[i];
            i = i==0 ? 1:0;

          }
    }
}
