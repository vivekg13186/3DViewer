package org.viewer1;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import org.lwjgl.opengl.awt.GLData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame implements ActionListener {

    GLCanvas canvas;

    public void setupMenu(){

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JFrame frame = this;
        JMenuItem menuItem = new JMenuItem(new AbstractAction("Open") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    System.out.println(file.getName());
                    try {
                        canvas.getRenderer().loadMesh(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        menu.add(menuItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);


    }
    public Main(){
        GLData data = new GLData();
        data.majorVersion = 3;
        data.minorVersion = 3;
        data.profile = GLData.Profile.CORE;
        data.samples = 4;
        canvas =new GLCanvas(data);
          setTitle("3d Viewer");
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setLayout(new BorderLayout());
         setPreferredSize(new Dimension(600, 600));
         add(canvas);
         setupMenu();
         pack();
         setVisible(true);
         transferFocus();
        Runnable renderLoop = new Runnable() {
            public void run() {
                if (!canvas.isValid())
                    return;
                canvas.render();
                SwingUtilities.invokeLater(this);
            }
        };
        SwingUtilities.invokeLater(renderLoop);
    }

    public static void main(String[] arg){
        LafManager.install(new DarculaTheme());
Main main=new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
