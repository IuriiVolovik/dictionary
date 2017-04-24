package com.dictionary.graphical.frames;

import com.dictionary.graphical.Graphical;
import com.dictionary.helpers.DBHelper;
import com.dictionary.helpers.EntityHelper;
import com.dictionary.structure.Entity;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by iuvo0215 on 20.04.2017.
 */
public class AddFrame {

    static JFrame frame;

    public void initUI() {
        // Creating instance of JFrame
        frame = new JFrame("Create Entity");

        // Setting the width and height of frame
        frame.setSize(640, 380);

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                e.getWindow().dispose();
            }
        });

        /* Creating panel. This is same as a div tag in HTML
         * We can create several panels and add them to specific
         * positions in a JFrame. Inside panels we can add text
         * fields, buttons and other components.
         */
        JPanel panel = new JPanel();
        // adding panel to frame
        frame.add(panel);
        /* calling user defined method for adding components
         * to the panel.
         */


        placeComponents(panel);

        frame.setLocationRelativeTo(null);

        // Setting the frame visibility to true
        frame.setVisible(true);

        frame.setResizable(false);
    }



    private static void placeComponents(JPanel panel) {

        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        panel.setLayout(null);

        // Creating JLabel
        JLabel titleLabel = new JLabel("Title");
        /* This method specifies the location and size
         * of component. setBounds(x, y, width, height)
         * here (x,y) are cordinates from the top left
         * corner and remaining two arguments are the width
         * and height of the component.
         */
        titleLabel.setBounds(10,20,80,25);
        panel.add(titleLabel);

        /* Creating text field where title is supposed to
         * enter entity title.
         */
        final JTextField titleText = new JTextField(50);
        titleText.setBounds(100,20,305,25);
        panel.add(titleText);




        // Same process for description label and text field.
        JLabel imageLabel = new JLabel("Image");
        imageLabel.setBounds(10,50,80,25);
        panel.add(imageLabel);

        //This is similar to text field
        final JTextField imageText = new JTextField(20);
        imageText.setBounds(100,50,200,25);
        panel.add(imageText);

        // Creating button
        JButton browseButton = new JButton("Browse");
        browseButton.setBounds(310, 50, 80, 25);
        panel.add(browseButton);


        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    imageText.setText(file.getAbsolutePath());
                }
            }});

        // Same process for description label and text field.
        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(10,80,80,25);
        panel.add(descriptionLabel);

        final JTextArea descriptionArea = new JTextArea("Enter Description...", 5, 10);
        descriptionArea.setBounds(100,80,500,200);
        panel.add(descriptionArea);


        // Creating button
        JButton createButton = new JButton("Create");
        createButton.setBounds(420, 300, 80, 25);
        panel.add(createButton);






        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    DBHelper helper = Graphical.helper;

                    String image = imageText.getText();
                    String srcFile = image.substring(image.lastIndexOf("\\") + 1, image.length());

                    if(!image.isEmpty())
                    FileUtils.copyFile(new File(image),
                            new File("img/" + srcFile));



                    Graphical.listModel.addElement(titleText.getText());

                    Graphical.helper.add(titleText.getText(), descriptionArea.getText(), "img/" + srcFile);


                } catch (Exception ex) {
                        ex.printStackTrace();
                }



                frame.setVisible(false);
                frame.dispose();

            }});


        // Creating button
        JButton canelButton = new JButton("Cancel");
        canelButton.setBounds(520, 300, 80, 25);
        panel.add(canelButton);

        canelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }});
    }
}


