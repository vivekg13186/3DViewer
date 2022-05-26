package org.viewer1;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SimpleRender implements Runnable {

    int vertexCount=0;
    int VAO_ID,POS_BUFFER_ID,NORMAL_BUFFER_ID,INDEX_BUFFER_ID;
     int programId;

      int vertexShaderId;

      int fragmentShaderId;

    public SimpleRender() {

 setMesh();
 setShader();
    }

    void setMesh(){
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

        vertexCount = faces.length;
        VAO_ID = glGenVertexArrays();
        glBindVertexArray(VAO_ID);

        POS_BUFFER_ID = createBuffer(pos,0);
        NORMAL_BUFFER_ID = createBuffer(normals,2);
        INDEX_BUFFER_ID = createIndexBuffer(faces);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    private int createIndexBuffer(int[] data){
       int vboId = glGenBuffers();
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(data.length);
        indicesBuffer.put(data).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
     return vboId;
    }

    private int createBuffer(float[] data,int attrb){
        int vboId = glGenBuffers();
        FloatBuffer posBuffer = MemoryUtil.memAllocFloat(data.length);
        posBuffer.put(data).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(attrb);
        glVertexAttribPointer(attrb, 3, GL_FLOAT, false, 0, 0);
        return  vboId;
    }
    void setShader(){
        programId = glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Could not create Shader");
        }
    }
    int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    @Override
    public void run() {

    }
}
