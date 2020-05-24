package com.company;
import javax.swing.*;
public class Main {

    public static void main(String[] args){
        JFrame f = new JFrame ("Start to read");  //title
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x in the right corner
        f.setSize(1300,800); // size of the screen
        f.add(new Sea()); //poll of the content
        f.setVisible(true); // visibility of the game


    }
}
