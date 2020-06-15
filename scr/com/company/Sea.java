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
import java.util.*;

import java.io.*;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.Timer;



public class Sea extends JPanel implements  ActionListener,Runnable {
    Timer time = new Timer(40, this);
    Image img = new ImageIcon("scr/resources/sea.png").getImage();//screen
    Image area = new ImageIcon("scr/resources/area.png").getImage();//area for task
    Image ico = new ImageIcon("scr/resources/icon2.png").getImage();
    ImageIcon icon = new ImageIcon(ico);
    Player f= new Player();



    Thread Circlemaker = new Thread(this);// Thread for making circles
    List<Circle> circles = new ArrayList<>();//list with circles for letters

    String word = Text();
    String wordSet ;
    String word2 ;
    char[] chars ;
    String[] w;
    int[] wordLengths = new int[30];
    int totalLength=0;



    Random rand  =new Random();
    int r=0;
    String t="";


    public Sea() throws IOException { // constructor

        Object[] possibleValues = { "Play", "Input words" };
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose option", "Words:",
                JOptionPane.INFORMATION_MESSAGE, icon,
                possibleValues, possibleValues[0]);
        if(selectedValue=="Play"){ this.wordSet = word;}
        else{
            this.wordSet = JOptionPane.showInputDialog(null, "Word");
        }
        this.word2 = wordSet.replaceAll("[\\p{P}\\p{Blank}]","");
        chars = wordSet.replaceAll("[\\p{P}\\p{Blank} ]","").toCharArray();
        System.out.println(wordSet);
        this.w = wordSet.split("[\\p{Blank}\\p{P}]");
        System.out.println(w[0]);
        for (int i=0;i<w.length;i++){
            this.wordLengths[i]=w[i].length();
            totalLength+=w[i].length();
        }
        JOptionPane.showMessageDialog(null, "Use UP, DOWN to move a fish \n" +
                " Use LEFT(<-), RIGHT(->) to change speed"+
                        "\nYour aim is to make a word from letters,that you hear" );

        time.start();
        Circlemaker.start();
        addKeyListener(new myKeyAdapter());
        setFocusable(true);
        play("scr/resources/sound.wav");

        play("scr/resources/letters/"+word2.substring(0,1).toLowerCase()+".wav");


    }


    Color darkblue = new Color(0, 0, 33);

    public void paint(Graphics g) {
        g.drawImage(img, f.sea1, 0, null);//screen 1
        g.drawImage(img, f.sea2, 0, null);//screen 1
        g.drawImage(f.fish, f.x, f.y, null); //player
        g.drawImage(area, 450, 590, null);//area for task
        Iterator<Circle> i = circles.iterator();// taking of let

        Font font1 = new Font("Arial", Font.BOLD, 80);
        g.setFont(font1);
        g.setColor(darkblue);
        g.drawString(t, 480,650);



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

        for (Circle k : circles) { //circles movement
            k.move();
        }
        testCollisionWithCircles();
    }
    int s=0;
    int i=0;
    int j=0;
    private void testCollisionWithCircles() {
        Iterator<Circle> inter = circles.listIterator();
        while(inter.hasNext()){
            Circle c= inter.next();
            if(f.getRect().intersects(c.getRect())){
                if (word2.substring(0, 1).contains(c.letter)) {
                    if(wordLengths[i]<= s) {
                        t="";s=0;i++;}
                    t += c.letter;
                    word2 = word2.substring(1);
                    s++;
                    j++;
                    inter.remove();
                    if (j>= totalLength) {
                        play("scr/resources/win.wav");
                        JOptionPane.showMessageDialog(null, "Well done!!!");

                        System.exit(1);
                    }
                    play("scr/resources/letters/"+word2.substring(0,1).toLowerCase()+".wav");
                }

                else{
                    play("scr/resources/fail.wav");
                    JOptionPane.showMessageDialog(null, "You failed("+"\n"
                            +"Correct letter is "+word2.substring(0, 1));

                    System.exit(1);
                }
            }
            if (f.x+280 > c.x) inter.remove();

        }

    }



    String str;

    public String Text() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("scr/resources/word"), StandardCharsets.UTF_8);
        this.str=list.toString();
        return this.str;
    }



    public void run() {   //circle generator

        while(totalLength > r)
        {
            try { Thread.sleep(8000);

                char k = chars[r];
                char b = (char)(rand.nextInt(26) + 'a');
                if (b == k)  b = (char)(rand.nextInt(26) + 'a');
                char c = (char)(rand.nextInt(26) + 'a');
                if (c == k)  c = (char)(rand.nextInt(26) + 'a');
                String a3=String.valueOf( b);
                String a0=String.valueOf( c);
                if (r%2==0) {
                    circles.add(new Circle(1400, 20, 8, this, String.valueOf(k)));
                    circles.add(new Circle(1400, 220, 8, this,a3));
                    circles.add(new Circle(1400, 420, 8, this,a0));

                }
                else
                {
                    if (r%3==0)
                    {
                        circles.add(new Circle(1400, 20, 8, this,a3));
                        circles.add(new Circle(1400, 220, 8, this,String.valueOf(k)));
                        circles.add(new Circle(1400, 420, 8, this,a0));
                    }
                    else
                        {
                            circles.add(new Circle(1400, 20, 8, this,a0));
                            circles.add(new Circle(1400, 220, 8, this,a3));
                            circles.add(new Circle(1400, 420, 8,
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

