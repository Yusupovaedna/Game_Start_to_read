package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Player  {
    public static final int MAX_V =30;
    public static final int MAX_TOP =-10;
    public static final int MAX_BOTTOM =400;


    Image fish = new ImageIcon("scr/resources/straight2.png").getImage();
    int v=0;
    int dv=0;
    int s=0;

    int x=30;
    int y=100;
    int dy=0;

    int  sea1=0;
    int sea2=1500;

    public void move(){
        s+=v;
        v+=dv;
        if (v>=MAX_V) v=MAX_V;
        if (v<=0) v=0;
        y-=dy;
        if(y <= MAX_TOP)  y=MAX_TOP;
        if(y >= MAX_BOTTOM) y=MAX_BOTTOM;
        if (sea2-v <=0){
            sea1=0;
            sea2=1500;
        }else {
        sea1-=v;
        sea2-=v;
        }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case(KeyEvent.VK_RIGHT): dv=1;break;
            case(KeyEvent.VK_LEFT): dv=-1;break;
            default: dv=0;
            case(KeyEvent.VK_UP): dy=5;break;
            case(KeyEvent.VK_DOWN): dy=-5;break;
        }}
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case(KeyEvent.VK_RIGHT  ): dv=0;break;
            case(KeyEvent.VK_LEFT): dv =0;break;

            case(KeyEvent.VK_UP): dy=0;break;
            case(KeyEvent.VK_DOWN): dy=0;break;
            default: dv=0;
        }
    }


}
