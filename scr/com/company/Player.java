package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Player  {

    Image fish = new ImageIcon("scr/resources/straight2.png").getImage();
    int v=0;
    int dv=0;
    int s=0;

    int x=100;
    int y=30;

    int  sea1=0;

    public void move(){
        s+=v;
        sea1-=v;

    }


}
