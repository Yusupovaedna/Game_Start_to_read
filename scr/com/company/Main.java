package com.company;
import javax.swing.*;
public class Main {

    public static void main(String[] args){
        JFrame f = new JFrame ("Start to read");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1300,800);
        f.add(new Sea());
        f.setVisible(true);


    }
}
