package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Sea extends JPanel implements  ActionListener {
    Timer time = new Timer(20, this);
    Image img = new ImageIcon("scr/resources/sea.png").getImage();
    Player f= new Player();


    public void paint(Graphics g){
        g = (Graphics2D) g;
        g.drawImage(img, f.sea1, 0, null);
        g.drawImage(f.fish,f.x,f.y,null);
    }

    public Sea() {
        time.start();
    }
    public void actionPerformed(ActionEvent e) {
        f.move();
        repaint();
    }
}
