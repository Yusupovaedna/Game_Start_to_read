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
    Timer time = new Timer(20, this);
    Image img = new ImageIcon("scr/resources/sea.png").getImage();//screen
    Image area = new ImageIcon("scr/resources/area.png").getImage();//area for task
    Image icon1 = new ImageIcon("scr/resources/icon.mpo").getImage();
    Image ico = icon1.getScaledInstance(50, 30, Image.SCALE_SMOOTH ) ;
    ImageIcon icon = new ImageIcon(ico);
    Player f= new Player();




    int j=0;

    Thread Circlemaker = new Thread(this);// Thread for making circles
    List<Circle> circles = new ArrayList<Circle>();//list with circles for letters
    String wordSet="11345";
    String word = Text().replaceAll("[\\p{P}\\p{Blank}]","");
    String word2 ="11";
    char[] chars = {' '};


    Random rand  =new Random();
    int r=0;
    String t="";


    public Sea() throws IOException, FontFormatException { // constructor
        time.start();
        Circlemaker.start();
        addKeyListener(new myKeyAdapter());
        setFocusable(true);
       // play("scr/resources/sound.wav");

        Object[] possibleValues = { "Play", "Input words" };
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose option", "Words:",
                JOptionPane.INFORMATION_MESSAGE, icon,
                possibleValues, possibleValues[0]);
        if(selectedValue=="Play"){ this.wordSet = word;}
        else{
            this.wordSet = (String) JOptionPane.showInputDialog(null, "Word");
        }
        this.word2 = wordSet.replaceAll("[\\p{P}\\p{Blank}]","");
        chars = wordSet.replaceAll("[\\p{P}\\p{Blank} ]","").toCharArray();


        new TextToSpeech("Find" + word2.substring(0,1)+"e");

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
        Iterator<Circle> i = circles.iterator();
        while(i.hasNext()){ //circles movement
            Circle k = i.next();
        k.move();}
        try {
            testCollisionWithCircles();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    int s=0;
    private void testCollisionWithCircles() throws IOException {
        Iterator<Circle> i = circles.iterator();
        while(i.hasNext()){
            Circle c= i.next();
            if(f.getRect().intersects(c.getRect())){
                if (word2.substring(0, 1).contains(c.letter)) {
                    s=+1;
                    t+=c.letter;
                    word2 = word2.substring(1);
                    i.remove();
                    if (t.length()>= wordSet.length()) {
                        play("scr/resources/win.wav");
                        JOptionPane.showMessageDialog(null, "Well done!!!");
                        System.exit(1);
                    }
                    new TextToSpeech("Find"+word2.substring(0,1));


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



    public void run() {   //circle generator

        while(wordSet.length() > r)
        {
            try { Thread.sleep(6000);
                System.out.println(chars);
                System.out.println(chars[r]);

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

