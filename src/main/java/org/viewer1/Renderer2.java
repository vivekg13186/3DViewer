package org.viewer1;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;

public class Renderer2 {
    public Camera camera;

    public ShaderProgram shaderProgram;
    public  Mesh mesh ;
    public  String load_mesh;
    public Matrix4f modelMatrix =new Matrix4f();
    private static final int MAX_POINT_LIGHTS = 6;

    private static final int MAX_SPOT_LIGHTS = 6;

    private final float specularPower=10f;

    public void init() throws Exception {
        camera = new Camera();
        // Create shader
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("vertex.glsl"));
        shaderProgram.createFragmentShader(Utils.loadResource("fragment.glsl"));
        shaderProgram.link();

        // Create uniforms for modelView and projection matrices and texture
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("texture_sampler");
        // Create uniform for material
        shaderProgram.createMaterialUniform("material");
        // Create lighting related uniforms
        shaderProgram.createUniform("specularPower");
        shaderProgram.createUniform("ambientLight");
        shaderProgram.createPointLightListUniform("pointLights", MAX_POINT_LIGHTS);
        shaderProgram.createSpotLightListUniform("spotLights", MAX_SPOT_LIGHTS);
        shaderProgram.createDirectionalLightUniform("directionalLight");
        //mesh =MeshLoader.load("/Users/vivek/Developer/3DViewer/src/main/resources/org/viewer/bunny.obj",null)[0];
        loadMesh(new File("/Users/vivek/Downloads/pony-cartoon/source/a3ff7fc6894b4be396d75e70294655b1/Pony_cartoon.obj"));
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }


    public   void loadMesh(File filename) {

        Mesh mesh1 = null;
        try {

            mesh1 = MeshLoader.load(filename.getAbsolutePath(),filename.getParent())[0];
            if(mesh!=null)mesh.cleanUp();
           // camera.radius =mesh1.getBoundingRadius()+5;
            mesh = mesh1;
            camera.angleY=0;
            camera.angleX=0;
            //System.out.println(mesh.getBoundingRadius());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    public void updateRadius(){
        float radius  = mesh.getBoundingRadius();
        float A = (float) Math.toRadians(30);
        float L  = (float) (radius/Math.sin(A));
         camera.reset(L);

        System.out.println(L);
    }

    public void render(GLCanvas window,  Vector3f ambientLight,
                       PointLight[] pointLightList, SpotLight[] spotLightList, DirectionalLight directionalLight) {

        if(load_mesh!=null){
            System.out.println("loading"+load_mesh);
             loadMesh(new File(load_mesh));
             load_mesh=null;
            System.out.println("end"+mesh.getVaoId());
           updateRadius();
        }

        clear();


            glViewport(0, 0, window.getViewportWidth(), window.getViewportWidth());



        shaderProgram.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = window.getProjectionMatrix();
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = camera.getViewMatrix();

        // Update Light Uniforms
        renderLights(viewMatrix, ambientLight, pointLightList, spotLightList, directionalLight);

        shaderProgram.setUniform("texture_sampler", 0);

            // Set model view matrix for this item
            Matrix4f modelViewMatrix = new Matrix4f();
            modelViewMatrix.identity().mul(modelMatrix).mul(viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            // Render the mesh for this game item
            shaderProgram.setUniform("material", mesh.getMaterial());
            mesh.render();


        shaderProgram.unbind();
    }
    private void renderLights(Matrix4f viewMatrix, Vector3f ambientLight,
                              PointLight[] pointLightList, SpotLight[] spotLightList, DirectionalLight directionalLight) {

        shaderProgram.setUniform("ambientLight", ambientLight);
        shaderProgram.setUniform("specularPower", specularPower);

        // Process Point Lights
        int numLights = pointLightList != null ? pointLightList.length : 0;
        for (int i = 0; i < numLights; i++) {
            // Get a copy of the point light object and transform its position to view coordinates
            PointLight currPointLight = new PointLight(pointLightList[i]);
            Vector3f lightPos = currPointLight.getPosition();
            Vector4f aux = new Vector4f(lightPos, 1);
            aux.mul(viewMatrix);
            lightPos.x = aux.x;
            lightPos.y = aux.y;
            lightPos.z = aux.z;
            shaderProgram.setUniform("pointLights", currPointLight, i);
        }

        // Process Spot Ligths
        numLights = spotLightList != null ? spotLightList.length : 0;
        for (int i = 0; i < numLights; i++) {
            // Get a copy of the spot light object and transform its position and cone direction to view coordinates
            SpotLight currSpotLight = new SpotLight(spotLightList[i]);
            Vector4f dir = new Vector4f(currSpotLight.getConeDirection(), 0);
            dir.mul(viewMatrix);
            currSpotLight.setConeDirection(new Vector3f(dir.x, dir.y, dir.z));
            Vector3f lightPos = currSpotLight.getPointLight().getPosition();

            Vector4f aux = new Vector4f(lightPos, 1);
            aux.mul(viewMatrix);
            lightPos.x = aux.x;
            lightPos.y = aux.y;
            lightPos.z = aux.z;

            shaderProgram.setUniform("spotLights", currSpotLight, i);
        }

        // Get a copy of the directional light object and transform its position to view coordinates
        DirectionalLight currDirLight = new DirectionalLight(directionalLight);
        Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
        dir.mul(viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
        shaderProgram.setUniform("directionalLight", currDirLight);

    }

    public Camera getCamera() {
        return camera;
    }


    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
