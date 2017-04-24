package com.dictionary.graphical;

import com.dictionary.graphical.frames.AddFrame;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class GraphicalHelper {

    JMenuBar menuBar() {
        JMenuBar menuBar = new JMenuBar();
        Font font = new Font("Verdana", Font.PLAIN, 11);
        JMenu fileMenu = new JMenu("Menu");
        fileMenu.setFont(font);

        JMenuItem newFileItem = new JMenuItem("Add New");
        newFileItem.setFont(font);
        newFileItem.addActionListener(new AddActionListener());
        fileMenu.add(newFileItem);

        JMenuItem deleteFileItem = new JMenuItem("Delete");
        deleteFileItem.setFont(font);
        deleteFileItem.addActionListener(new DeleteActionListener());
        fileMenu.add(deleteFileItem);

        JMenuItem exiteFileItem = new JMenuItem("Exit");
        exiteFileItem.setFont(font);
        exiteFileItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }});
        fileMenu.add(exiteFileItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

    JTextArea textArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        return textArea;
    }

    JScrollPane areaScrollPanel(String title) {
        JScrollPane areaScrollPane = new JScrollPane();
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        areaScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        areaScrollPane.setPreferredSize(new Dimension(20, 200));

        areaScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(title),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        areaScrollPane.getBorder()));

        return areaScrollPane;
    }

    ImageIcon scaleImage(ImageIcon src, int w, int h){
        int original_width = src.getIconWidth();
        int original_height = src.getIconHeight();
        int bound_width = w;
        int bound_height = h;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        BufferedImage resizedImg = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0,0,new_width, new_height);
        g2.drawImage(src.getImage(), 0, 0, new_width, new_height, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
    }

    class DeleteActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int result =  JOptionPane.showConfirmDialog(Graphical.frame,
                        "Delete current entity?",
                        "Delete Entity",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {

                    Graphical.helper.remove(Graphical.list.getSelectedIndex());

                    Graphical.listModel.remove(Graphical.list.getSelectedIndex());


                    try {
                        FileUtils.forceDelete(new File(Graphical.helper.get(Graphical.helper.getCurrent()).getImg()));
                    } catch (Exception ex) {
                        System.out.println("GraphicalHelper:" + ex.toString());
                    }


                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class AddActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new AddFrame().initUI();
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
