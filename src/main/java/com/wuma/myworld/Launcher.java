package com.wuma.myworld;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 17/11/3.
 */
public class Launcher {
    public Frame frame = new Frame("MyLauncher");
    public String path = "/Users/user/Downloads/DSC_0098_pku.jpg";

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.frame.setSize(200, 200);
        ImageIcon icon = new ImageIcon(launcher.path);
        Image img = icon.getImage();
        launcher.frame.setIconImage(img);

        launcher.frame.setVisible(true);
    }
}
