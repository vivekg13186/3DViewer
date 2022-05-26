package org.viewer;

import org.engine.Utils;
import org.engine.Window;
import org.engine.graph.ICamera;
import org.engine.graph.ShaderProgram;
import org.engine.graph.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryUtil;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Grid {

    int vaoId,vboId,vboId2,length;
    ShaderProgram shaderProgram;
    Transformation transformation =new Transformation();
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;
    private Vector4f color =new Vector4f(0.302f,0.302f,0.302f,1);
    private Matrix4f model=new Matrix4f();
    public Grid(int slices) throws Exception {
        ArrayList<Float> vertices=new ArrayList<>();
        ArrayList<Integer> indices=new ArrayList<>();

        for(int j=-slices/2; j<=slices/2; ++j) {
            for(int i=-slices/2; i<=slices/2; ++i) {
                //float x = (float)i/(float)slices;
                float x =i;
                float y = 0;
                //float z = (float)j/(float)slices;
                float z = j;
                vertices.add(x);
                vertices.add(y);
                vertices.add(z);
            }
        }

        for(int j=0; j<slices; ++j) {
            for(int i=0; i<slices; ++i) {

                int row1 =  j    * (slices+1);
                int row2 = (j+1) * (slices+1);

                indices.add(row1+i);
                indices.add( row1+i+1);
                        indices.add(row1+i+1);
                                indices.add(row2+i+1);
                indices.add(row2+i+1);
                indices.add(row2+i);
                indices.add(row2+i);
                indices.add(row1+i);

            }
        }

          vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

          vboId = glGenBuffers();

        FloatBuffer posBuffer = MemoryUtil.memAllocFloat(vertices.size());
        posBuffer.put( Utils.listToArray(vertices)).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

          vboId2 = glGenBuffers();

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.size());
        indicesBuffer.put(Utils.listIntToArray(indices)).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId2);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        MemoryUtil.memFree(indicesBuffer);
        MemoryUtil.memFree(posBuffer);
        length=indices.size();
        shaderProgram =new ShaderProgram();
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/grid_fs.glsl"));
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/grid_vs.glsl"));
        shaderProgram.link();
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("lineColor");
        model.translate(-0.5f,0,-0.5f);
        model.scale(1);
    }
    public void render(Window window, ICamera camera) {
        //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //glViewport(0, 0, window.getWidth(), window.getHeight());
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        Matrix4f viewMatrix = camera.getViewMatrix();
        shaderProgram.bind();
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        Matrix4f mv = new Matrix4f(viewMatrix);

        shaderProgram.setUniform("modelViewMatrix", mv.mul(model));
        shaderProgram.setUniform("lineColor", color);

        glBindVertexArray(vaoId);
        glDrawElements(GL_LINES, length, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
        shaderProgram.unbind();

    }
}
