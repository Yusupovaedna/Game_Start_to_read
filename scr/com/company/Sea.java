package com.company;

import javax.swing.*;
import java.awt.*;


public class Sea extends JPanel {
    Image img = new ImageIcon("scr/resources/sea.png").getImage();


    @Override
    public void paint(Graphics g){

        g.drawImage(img, 0, 0, null);
    }
}
