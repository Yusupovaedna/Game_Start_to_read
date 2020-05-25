package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;
import java.util.Random;


public class Sea extends JPanel implements  ActionListener,Runnable {
    Timer time = new Timer(20, this);
    Image img = new ImageIcon("scr/resources/sea.png").getImage();//screen
    Image area = new ImageIcon("scr/resources/area.png").getImage();//area for task
    Player f= new Player();

    int j=0;

    Thread Circlemaker = new Thread(this);// Thread for making circles
    List<Circle> circles = new ArrayList<Circle>();//list with circles for letters



    public Sea() throws IOException { // constructor
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
                Graphics2D g2 = (Graphics2D)g;
                Font font = new Font("Serif", Font.PLAIN, 96);
                g2.setFont(font);
                g.drawImage(k.img, k.x, k.y, null);
                g2.drawString(k.letter, k.x+50, k.y+100);

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

    private int testCollisionWithCircles() {
        Iterator<Circle> i = circles.iterator();
        while(i.hasNext()){
            Circle c= i.next();
            if(f.getRect().intersects(c.getRect())){
                i.remove();
                return 1;
            }

        }
        return 0;
    }
    int r=0;

    String str;

    public String Text() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("scr/resources/word"), StandardCharsets.UTF_8);
        this.str=list.toString().replaceAll(",", "");
       System.out.println(str);
        return this.str;
    }


    //    String let = word.substring(r, r+1);
    String word = Text();
    char[] chars = word.replaceAll("[\\p{P}]","").toCharArray();

    public void run() {   //circle generator

        while(word.length() > r) {
            try {Random rand  =new Random();
                char k = chars[r];
                int b = ((int) k)+ 3;
                int c = b+6;
                String a3=String.valueOf((char) b);
                String a0=String.valueOf((char) c);
                if (r%2==0) {
                    circles.add(new Circle(1400, 20, 2, this, String.valueOf(k)));
                    circles.add(new Circle(1400, 220, 2, this,a3));
                    circles.add(new Circle(1400, 420, 2, this,a0));

                }
                else
                {
                    if (r%3==0)
                    {
                        circles.add(new Circle(1400, 20, 2, this,a3));
                        circles.add(new Circle(1400, 220, 2, this,String.valueOf(k)));
                        circles.add(new Circle(1400, 420, 2, this,a0));
                    }
                    else
                        {
                            circles.add(new Circle(1400, 20, 2, this,a0));
                            circles.add(new Circle(1400, 220, 2, this,a3));
                            circles.add(new Circle(1400, 420, 2,
                            this,String.valueOf(k)));
                        }
                }


                if (testCollisionWithCircles()==0) Thread.sleep(10000);
                else Thread.sleep(0);
                r++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }
}

