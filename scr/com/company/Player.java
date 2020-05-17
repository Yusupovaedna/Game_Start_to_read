package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Player  {

    Image fish = new ImageIcon("scr/resources/straight2.png").getImage();
    int v=5;
    int dv=0;
    int s=0;

    int x=30;
    int y=100;

    int  sea1=0;
    int sea2=1100;

    public void move(){
        s+=v;
        if (sea2-v <=0){
            sea1=0;
            sea2=1100;
        }else {
        sea1-=v;
        sea2-=v;
        }
    }

    public void keyPressed(KeyEvent e){
        JOptionPane.showMessageDialog(null, "key pressed");
    }
    public void keyReleased(KeyEvent e){
        JOptionPane.showMessageDialog(null, "key released");
    }


}
