package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import java.util.List;




public class Sea extends JPanel implements  ActionListener,Runnable {
    Timer time = new Timer(20, this);
    Image img = new ImageIcon("scr/resources/sea.png").getImage();//screen
    Image area = new ImageIcon("scr/resources/area.png").getImage();//area for task
    Player f= new Player();

    Thread Circlemaker = new Thread(this);// Thread for making circles
    List<Circle> circles = new ArrayList<Circle>();//list with circles for letters



    public Sea() { // constructor
        time.start();
        Circlemaker.start();
        addKeyListener(new myKeyAdapter());
        setFocusable(true);
    }

    public void paint(Graphics g) {
        g = (Graphics2D) g;
        g.drawImage(img, f.sea1, 0, null);//screen 1
        g.drawImage(img, f.sea2, 0, null);//screen 1
        g.drawImage(f.fish, f.x, f.y, null); //player
        g.drawImage(area, 450, 590, null);//area for task
        Iterator<Circle> i = circles.iterator();// taking of let
        while(i.hasNext()){
            Circle k = i.next();
            if (k.x >=2400 |k.x<=-100  ) i.remove();
            else {
                g.drawImage(k.img, k.x, k.y, null);
            }
        }}



    private  class myKeyAdapter extends KeyAdapter {  //reaction on buttons

        public void keyPressed(KeyEvent e){
            f.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            f.keyReleased(e);
        }
    }

    public void actionPerformed(ActionEvent e) { //moving of the background
        f.move();
        repaint();
        Iterator<Circle> i = circles.iterator();
        while(i.hasNext()){ //circles movement
            Circle k = i.next();
        k.move();}
        testCollisionWithCircles();
    }

    private void testCollisionWithCircles() {
        Iterator<Circle> i = circles.iterator();
        while(i.hasNext()){
            Circle c= i.next();
            if(f.getRect().intersects(c.getRect())){
                i.remove();

            }
        }


    }


    public void run() {   //circle generator

        while(true) {
            try {
                Thread.sleep(10000);

                circles.add(new Circle(1400, 20, 2, this));
                circles.add(new Circle(1400, 220, 2, this));
                circles.add(new Circle(1400, 420, 2, this));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }
}

