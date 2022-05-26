package org.viewer;

import org.engine.*;
import org.engine.graph.*;
import org.gui.IGUI;
import org.joml.Vector3f;

import java.io.File;

import static org.lwjgl.opengl.GL11.glClearColor;


public class ViewerGame implements IGameLogic {






    private Mesh mesh;
    private Renderer renderer;
    private GameItem[] gameItems = new GameItem[1];
    private SceneLight sceneLight;
    private IGUI hud;
    private DirectionalLight directionalLight =new DirectionalLight(new Vector3f(1,1,1),new Vector3f(0,-1,0),0.0f);
    public PointLight[] pointLights = new PointLight[6];
    public ViewCamera viewCamera;
    private Controller controller;
    private Grid grid;
    private  float lightIntensity=1.0f;


    public void setLightRadius(float rad){
        rad += 104;
        pointLights[0].getPosition().set(0,rad,0);
        pointLights[1].getPosition().set(0,-rad,0);
        pointLights[2].getPosition().set(0,0,rad);
        pointLights[3].getPosition().set(0,0,-rad);
        pointLights[4].getPosition().set(rad,0,0);
        pointLights[5].getPosition().set(-rad,0,0);

    }

    public void updateCamRadius(float radius){
        float A = (float) Math.toRadians(60.0/2);
        float L = (float) (radius/(Math.sin(A)));
        viewCamera.radius=L;
    }
    public void setLightIntensity(float i){
        for(PointLight p :pointLights){
            p.setIntensity(i);
        }

    }
    public float getCurrentBoundingRadius(){
        try{
            return gameItems[0].getMesh().getBoundingRadius();
        }catch (Exception e){

        }
        return 5;

    }
    @Override
    public void init(Window window) throws Exception {


        renderer  =new Renderer();
        renderer.init(window);
        sceneLight = new SceneLight();
        controller = new Controller(window);



           sceneLight.setDirectionalLight(directionalLight);
        float intensity=lightIntensity;
        Vector3f color = new Vector3f(0.6f,0.6f,0.6f);
        float rad=1;
        pointLights[0]=new PointLight(color, new Vector3f(0,rad,0), intensity);
        pointLights[1]=new PointLight(color, new Vector3f(0,-rad,0), intensity);
        pointLights[2]=new PointLight(color, new Vector3f(0,0,rad), intensity);
        pointLights[3]=new PointLight(color, new Vector3f(0,0,-rad), intensity);
        pointLights[4]=new PointLight(color, new Vector3f(rad,0,0), intensity);
        pointLights[5]=new PointLight(color, new Vector3f(-rad,0,0), intensity);

        sceneLight.setPointLightList(pointLights);
        sceneLight.setAmbientLight(new Vector3f(0f,0f,0f));
        mesh = MeshLoader.loadFirstMesh("/Users/vivek/Developer/3DViewer/src/main/resources/org/viewer1/bunny.obj",null);
        GameItem gi = new GameItem(mesh);
        gameItems[0]=gi;
        viewCamera =new ViewCamera();
        controller.addEventListener(viewCamera);
        grid = new Grid(10);
        hud =new Hud(this);
       hud.init(window);
        setLightRadius(mesh.getBoundingRadius());
    }

    public   void loadMesh(File filename) {


        try {
            Mesh newMesh = null;
            String texturePath = filename.getParent();
            String filePath = filename.getAbsolutePath();
            newMesh =MeshLoader.load(filePath,texturePath)[0];
            gameItems[0].setMesh(newMesh);
            setLightRadius(newMesh.getBoundingRadius());
            updateCamRadius(newMesh.getBoundingRadius());

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public void input(Window window, MouseInput mouseInput) {

    }

    @Override
    public void update(float interval, MouseInput mouseInput) {

    }

    @Override
    public void render(Window window) {
        glClearColor(0.23f, 0.23f, 0.23f, 1);
        renderer.render(window, viewCamera,gameItems,sceneLight);
        hud.render(window);
        grid.render(window,viewCamera);
    }

    @Override
    public void cleanup() {

    }
}
