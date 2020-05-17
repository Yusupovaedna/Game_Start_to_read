package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Sea extends JPanel implements  ActionListener {
    Timer time = new Timer(20, this);
    Image img = new ImageIcon("scr/resources/sea.png").getImage();
    Image area = new ImageIcon("scr/resources/area.png").getImage();
    Player f= new Player();


    public void paint(Graphics g){
        g = (Graphics2D) g;
        g.drawImage(img, f.sea1, 0, null);
        g.drawImage(img, f.sea2, 0, null);
        g.drawImage(f.fish,f.x,f.y,null);
        g.drawImage(area, 450, 590, null);
    }

    public Sea() {
        time.start();
        addKeyListener(new myKeyAdapter());
        setFocusable(true);
    }
    private  class myKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e){
            f.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            f.keyReleased(e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        f.move();
        repaint();
    }
}
