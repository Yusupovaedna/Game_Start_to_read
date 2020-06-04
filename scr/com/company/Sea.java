package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;
import java.util.Random;

import java.io.*;
import javax.sound.sampled.*;


public class Sea extends JPanel implements  ActionListener,Runnable {
    Timer time = new Timer(20, this);
    Image img = new ImageIcon("scr/resources/sea.png").getImage();//screen
    Image area = new ImageIcon("scr/resources/area.png").getImage();//area for task

    Player f= new Player();

    int j=0;

    Thread Circlemaker = new Thread(this);// Thread for making circles
    List<Circle> circles = new ArrayList<Circle>();//list with circles for letters

    String word = Text().replaceAll("[\\p{P}\\p{Blank}]","");
    String word2 = Text().replaceAll("[\\p{P}\\p{Blank}]","");
    char[] chars = word.replaceAll("[\\p{P}\\p{Blank} ]","").toCharArray();
    Random rand  =new Random();
    int r=0;
    String t="";


    public Sea() throws IOException, FontFormatException { // constructor
        time.start();
        Circlemaker.start();
        addKeyListener(new myKeyAdapter());
        setFocusable(true);
        play("scr/resources/sound.wav");

    }
    Color darkblue = new Color(00, 00, 33);

    public void paint(Graphics g) {
        g = (Graphics2D) g;

        g.drawImage(img, f.sea1, 0, null);//screen 1
        g.drawImage(img, f.sea2, 0, null);//screen 1
        g.drawImage(f.fish, f.x, f.y, null); //player
        g.drawImage(area, 450, 590, null);//area for task
        Iterator<Circle> i = circles.iterator();// taking of let

        Font font1 = new Font("Arial", Font.BOLD, 80);
        g.setFont(font1);
        g.setColor(darkblue);
        String write = t;
        ((Graphics2D) g).drawString(t, 480,660);

        while(i.hasNext()){
            Circle k = i.next();
            if (k.x >=2400 |k.x<=-100  ) i.remove();
            else {
                Graphics2D g2 = (Graphics2D)g;

                Font font = new Font("Arial", Font.BOLD, 96);
                g2.setFont(font);

                g2.setColor(darkblue);
                g.drawImage(k.img, k.x, k.y, null);
                g2.drawString(k.letter, k.x+45, k.y+100);

            }
        }
    }



    public static void play(String filename)
            {
                try
                {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new File(filename)));
                    clip.start();
                }
                catch (Exception exc)
                {
                    exc.printStackTrace(System.out);
                }
            }
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
    int s=0;
    private void testCollisionWithCircles() {
        Iterator<Circle> i = circles.iterator();
        while(i.hasNext()){
            Circle c= i.next();
            if(f.getRect().intersects(c.getRect())){
                if (word2.substring(0, 1).contains(c.letter)) {
                    s=+1;
                    t+=c.letter;
                    word2 = word2.substring(1);
                    i.remove();
                    if (t.length()>=word.length()) {
                        play("scr/resources/win.wav");
                        JOptionPane.showMessageDialog(null, "You won!!!");
                        System.exit(1);
                    }
                }
                else{
                    play("scr/resources/fail.wav");
                    JOptionPane.showMessageDialog(null, "You failed("+"\n"
                            +"Correct letter is "+word2.substring(0, 1));
                    System.exit(1);
                }

            }
            if (f.x+280 > c.x) i.remove();

        }

    }



    String str;

    public String Text() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("scr/resources/word"), StandardCharsets.UTF_8);
        this.str=list.toString().replaceAll(",", "");
       System.out.println(str);
        return this.str;
    }


    //    String let = word.substring(r, r+1);

    public void run() {   //circle generator

        while(word.length() > r) {
            try { Thread.sleep(6000);
                char k = chars[r];
                char b = (char)(rand.nextInt(26) + 'a');
                if (b == k)  b = (char)(rand.nextInt(26) + 'a');
                char c = (char)(rand.nextInt(26) + 'a');
                if (c == k)  c = (char)(rand.nextInt(26) + 'a');
                String a3=String.valueOf( b);
                String a0=String.valueOf( c);
                if (r%2==0) {
                    circles.add(new Circle(1400, 20, 6, this, String.valueOf(k)));
                    circles.add(new Circle(1400, 220, 6, this,a3));
                    circles.add(new Circle(1400, 420, 6, this,a0));

                }
                else
                {
                    if (r%3==0)
                    {
                        circles.add(new Circle(1400, 20, 6, this,a3));
                        circles.add(new Circle(1400, 220, 6, this,String.valueOf(k)));
                        circles.add(new Circle(1400, 420, 6, this,a0));
                    }
                    else
                        {
                            circles.add(new Circle(1400, 20, 6, this,a0));
                            circles.add(new Circle(1400, 220, 6, this,a3));
                            circles.add(new Circle(1400, 420, 6,
                            this,String.valueOf(k)));
                        }
                }


                r++;


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        }
}

