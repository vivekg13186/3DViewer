package org.engine.graph;

import imgui.ImGui;
import org.engine.Window;
import org.joml.Vector2f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

public   class Controller {


    private ArrayList<InputEventListener> eventList =  new ArrayList<>();

    private Window window;
    private final Input input;

    public Controller(Window window){
        this.window = window;
        input = new Input(window);
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle,x,y)->{
            if(ImGui.getIO().getWantCaptureMouse()){
                return;
            }
            if(input.isLeftMouseButtonPressed()){

                for(InputEventListener e:eventList){
                    e.mouseDrag(input,(float) x, (float) y);
                }
            }else {

                for(InputEventListener e:eventList){
                    e.mouseMove(input,(float) x, (float) y);
                }
            }
        });
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            if(ImGui.getIO().getWantCaptureMouse()){
                return;
            }
           if(entered) {

               for(InputEventListener e:eventList){
                   e.mouseEntered(input);
               }
           }
           else {
               for(InputEventListener e:eventList){
                   e.mouseExit(input);
               }
           }
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            if(ImGui.getIO().getWantCaptureMouse()){
                return;
            }
            if(action==GLFW_PRESS){
                for(InputEventListener e:eventList){
                    e.mouseButtonPressed(input);
                }

            }else{
                for(InputEventListener e:eventList){
                    e.mouseButtonReleased(input);
                }

            }
        });
        glfwSetScrollCallback(window.getWindowHandle(),(l, v, v1) -> {
            if(ImGui.getIO().getWantCaptureMouse()){
            return;
        }

            for(InputEventListener e:eventList){
                e.mouseScroll(input,(float) v,(float) v1);
            }

        });

        glfwSetKeyCallback(window.getWindowHandle(),(long win, int key, int scancode, int action, int mods)->{
            if(ImGui.getIO().getWantCaptureKeyboard()){
                return;
            }
           if(action==GLFW_PRESS){
               for(InputEventListener e:eventList){
                   e.onKeyPressEnter(input,key,mods);
               }
           }else if(action==GLFW_RELEASE){
               for(InputEventListener e:eventList){
                   e.onKeyRelease(input,key,mods);
               }

           }else if(action==GLFW_REPEAT){
               for(InputEventListener e:eventList){
                   e.onKeyPress(input,key,mods);
               }
           }
         });
    }


    public void addEventListener(InputEventListener i){
        eventList.add(i);
    }

}
