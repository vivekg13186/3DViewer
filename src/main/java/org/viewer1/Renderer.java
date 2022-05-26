package org.viewer1;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;

public class Renderer {


    public Camera camera;
    public ShaderProgram shaderProgram;
    public  Mesh mesh ;
    public Matrix4f modelMatrix =new Matrix4f();
    Vector3f lightPos= new Vector3f(0,10,-10);
    Vector3f lightColor = new Vector3f(1,1,1);
    Vector3f  objectColor = new Vector3f(1,1,1);
    public Renderer() throws Exception {
      init();
    }

    public Camera getCamera(){
        return  camera;
    }

    public void init() throws Exception {
        camera = new Camera();
        shaderProgram= new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("pongshader_vs.glsl"));
        shaderProgram.createFragmentShader(Utils.loadResource("pongshader_fs.glsl"));
        shaderProgram.link();
        shaderProgram.createUniform("model");
        shaderProgram.createUniform("view");
        shaderProgram.createUniform("projection");
        shaderProgram.createUniform("lightPos");
        shaderProgram.createUniform("viewPos");
        shaderProgram.createUniform("lightColor");
        shaderProgram.createUniform("objectColor");
        float[] pos = {1.000000f, 1.000000f, -1.000000f,
                1.000000f, -1.000000f, -1.000000f,
                1.000000f, 1.000000f, 1.000000f,
                1.000000f, -1.000000f, 1.000000f,
                -1.000000f, 1.000000f, -1.000000f,
                -1.000000f, -1.000000f, -1.000000f,
                -1.000000f, 1.000000f, 1.000000f,
                -1.000000f, -1.000000f, 1.000000f};

        int[] faces = {1, 1, 1,
                5, 2, 1,
                7, 3, 1,
                3, 4, 1,
                4, 5, 2,
                3, 4, 2,
                7, 6, 2,
                8, 7, 2,
                8, 8, 3,
                7, 9, 3,
                5, 10, 3,
                6, 11, 3,
                6, 12, 4,
                2, 13, 4,
                4, 5, 4,
                8, 14, 4,
                2, 13, 5,
                1, 1, 5,
                3, 4, 5,
                4, 5, 5,
                6, 11, 6,
                5, 10, 6,
                1, 1, 6,
                2, 13, 6};
        float[] normals={ 0.0000f,1.0000f, 0.0000f,
                0.0000f, 0.0000f, 1.0000f,
                -1.0000f, 0.0000f, 0.0000f,
                0.0000f,-1.0000f, 0.0000f,
                1.0000f, 0.0000f, 0.0000f,
                0.0000f, 0.0000f, -1.0000f};

        //mesh =new Mesh(pos,pos,normals,faces);
        mesh =MeshLoader.load("/Users/vivek/Developer/3DViewer/src/main/resources/org/viewer/bunny.obj",null)[0];
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void  render(GLCanvas canvas){
        clear();
        camera.radius=mesh.getBoundingRadius()+125;

        shaderProgram.bind();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = canvas.getProjectionMatrix();


        shaderProgram.setUniform("model", modelMatrix);
        shaderProgram.setUniform("view", viewMatrix);
        shaderProgram.setUniform("projection", projectionMatrix);



        shaderProgram.setUniform("lightPos", lightPos);
        shaderProgram.setUniform("viewPos", camera.getPos());
        shaderProgram.setUniform("lightColor", lightColor);
        shaderProgram.setUniform("objectColor", objectColor);

        mesh.render();
        shaderProgram.unbind();

    }
}
