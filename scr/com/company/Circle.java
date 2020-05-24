package com.company;

import javax.swing.*;
import java.awt.*;

public class Circle {
    int x;
    int y;
    int v;
    Image img = new ImageIcon("scr/resources/circle3.png").getImage();
    Sea sea;
    public Rectangle getRect (){
        return new Rectangle(x,y,150,150 );
    }

    public Circle(int x, int y, int v, Sea sea){
        this.x=x;
        this.y=y;
        this.v=v;
        this.sea = sea;
    }
    public void move(){
        x=x-sea.f.v+v;

    }


}
