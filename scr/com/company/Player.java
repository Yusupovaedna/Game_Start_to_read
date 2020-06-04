package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Player  {
    public static final int MAX_V =10;
    public static final int MAX_TOP =0;
    public static final int MAX_BOTTOM =400;

    public Rectangle getRect (){
        return new Rectangle(x,y,300, 200/3);
    }

    Image fish_c = new ImageIcon("scr/resources/straight2.png").getImage(); //player img
    Image up = new ImageIcon("scr/resources/up1.png").getImage();//player img up
    Image down = new ImageIcon("scr/resources/down1.png").getImage();//player img down
    Image fish = fish_c;
    int v=0;
    int dv=0;
    int s=0;

    int x=30;
    int y=100;
    int dy=0;

    int  sea1=0;
    int sea2=1490;

    public void move(){  //moving of the player
        s+=v;
        v+=dv;
        if (v>=MAX_V) v=MAX_V;
        if (v<=3) v=3;
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

    public void keyPressed(KeyEvent e){  //reactions on pressed but
        int key = e.getKeyCode();
        switch (key){
            case(KeyEvent.VK_RIGHT): dv=1;break;
            case(KeyEvent.VK_LEFT): dv=-1;break;
            default: dv=0;
            case(KeyEvent.VK_UP): dy=5;fish = up;break;
            case(KeyEvent.VK_DOWN): dy=-5;fish = down;break;
        }}
    public void keyReleased(KeyEvent e){ //reactions on released but
        int key = e.getKeyCode();
        switch (key){
            case(KeyEvent.VK_RIGHT  ): dv=0;break;
            case(KeyEvent.VK_LEFT): dv =0;break;

            case(KeyEvent.VK_UP):
            case(KeyEvent.VK_DOWN):
                dy=0;fish = fish_c;break;
            default: dv=0;
        }
    }


}
