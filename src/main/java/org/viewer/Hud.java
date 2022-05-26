package org.viewer;


import imgui.ImGui;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import org.engine.Window;
import org.gui.ImGUIApplication;
import org.lwjgl.system.CallbackI;

import java.io.File;

public class Hud extends ImGUIApplication {

    ViewerGame game;
    float[] v,v1;
    public Hud(ViewerGame game){
         v = new float[1];
        v[0]=1;
        v1 = new float[1];
        v1[0]=1;
        this.game=game;
    }
    private void drawFileOpenDialog() {


        // display
        if (ImGuiFileDialog.display("ChooseFileDlgKey", ImGuiFileDialogFlags.None, 100, 100, 1000, 1000)) {
            // action if OK
            if (ImGuiFileDialog.isOk()) {
                //std::string filePathName = ImGuiFileDialog::Instance()->GetFilePathName();
                // std::string filePath = ImGuiFileDialog::Instance()->GetCurrentPath();
                // action
                File file = new File(ImGuiFileDialog.getFilePathName());
                game.loadMesh(file);
            }

            // close
            ImGuiFileDialog.close();
        }
    }

    private void drawMenu() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("File")) {
                if (ImGui.menuItem("Open", "Ctrl+O")) {
                    System.out.println("OPen clicked");
                    ImGuiFileDialog.openDialog("ChooseFileDlgKey", "Choose File", ".obj,.fbx,.gflt", ".", "", 1, ImGuiFileDialog.getUserDatas(), ImGuiFileDialogFlags.None);

                }
                ImGui.endMenu();
            }
            ImGui.endMainMenuBar();
        }
        // ImGui.showDemoWindow();
        drawFileOpenDialog();
    }

    @Override
    public void draw(Window window) {

        ImGui.pushFont(robotoFont);
        drawMenu();
        ImGui.begin("Setting");

        if(ImGui.sliderFloat("Light",v,0.0f,1f)){
            System.out.println(v[0]);
            game.setLightIntensity(v[0]);
        }

        v1[0]=game.viewCamera.radius;
        if(ImGui.sliderFloat("Radius",v1,game.getCurrentBoundingRadius(),game.getCurrentBoundingRadius()+100)){

            game.updateCamRadius(v1[0]);
        }
        ImGui.end();
        ImGui.popFont();
    }
}
