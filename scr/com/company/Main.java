package com.company;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        Image img = new ImageIcon("scr/resources/icon2.png").getImage();
        JFrame f = new JFrame ("Start to read");  //title
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x in the right corner
        f.setSize(1300,800); // size of the screen
        f.add(new Sea()); //poll of the content
        f.setIconImage(img.getScaledInstance(48, 48, 48));
        f.setVisible(true); // visibility of the game


    }

}
