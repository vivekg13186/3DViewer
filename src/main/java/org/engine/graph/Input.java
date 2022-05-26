package org.engine.graph;

import org.engine.Window;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private Window window;
    private final Vector2f mouse_pos =new Vector2f();
    public Input(Window window){
        this.window = window;
    }
    public final boolean isButtonPressed(int button){
        return org.lwjgl.glfw.GLFW.glfwGetMouseButton(window.getWindowHandle(),button)==GLFW_PRESS;
    }

    public  final boolean isLeftMouseButtonPressed(){
        return isButtonPressed(GLFW_MOUSE_BUTTON_1);
    }
    public  final boolean isRightMouseButtonPressed(){
        return isButtonPressed(GLFW_MOUSE_BUTTON_2);
    }
    public final Vector2f getMousePosition(){
        double[] x = new double[1];
        double[]y = new double[1];
        org.lwjgl.glfw.GLFW.glfwGetCursorPos(window.getWindowHandle(),x,y);
        mouse_pos.set((float) x[0],(float) y[0]);
        return mouse_pos;
    }

}
