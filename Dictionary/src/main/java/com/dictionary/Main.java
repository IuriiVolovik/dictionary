package com.dictionary;

import com.dictionary.graphical.Graphical;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);

                try {
                    new Graphical().initUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
