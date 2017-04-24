package com.dictionary.graphical;

import com.dictionary.helpers.DBHelper;
import com.dictionary.properties.PropReader;
import com.dictionary.structure.Entity;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class Graphical {

    static JFrame frame;
    static public JList list;
    static public DBHelper helper;
    final JLabel jLabel  = new JLabel();
    final JLabel title = new JLabel();
    GraphicalHelper guiHelper = new GraphicalHelper();
    static JPanel right;
    static JScrollPane scrollPane;
    JTextArea description;

    public static final DefaultListModel listModel = new DefaultListModel();

    public void initUI() throws ParserConfigurationException, SAXException, IOException,
            SQLException, ClassNotFoundException {

        helper = new DBHelper();

        frame = new JFrame(PropReader.get("project.name"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 550));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(panel());

        frame.setJMenuBar(guiHelper.menuBar());
    }


    private JPanel panel() {
        JPanel content = new JPanel();
        content.setBorder(new EmptyBorder(0, 20, 20, 20));

        content.setLayout(new BorderLayout());
        content.add(leftPanel(), BorderLayout.WEST);
        content.add(rightPanel(), BorderLayout.EAST);


        description = guiHelper.textArea();
        JScrollPane areaScrollPanel = guiHelper.areaScrollPanel("Description");

        areaScrollPanel.setViewportView(description);
        content.add(areaScrollPanel, BorderLayout.SOUTH);

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                int index = list.getSelectedIndex();
                if (index >= 0) {
                    helper.setCurrent(index - 1);
                    description.setText(helper.get(index).getDescription());

                    String img = helper.get(index).getImg();

                    try {
                        ImageIcon image = guiHelper.scaleImage(new ImageIcon(img), 450, 450);
                        jLabel.setIcon(image);
                        jLabel.setText(null);
                    } catch (IllegalArgumentException ex) {
                        jLabel.setIcon(null);
                        jLabel.setText("File: " + img + " not found.");
                    }

                    title.setText(helper.get(index).getTitle());
                }
            }
        });

        return content;
    }

    private JPanel leftPanel() {
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout(10, 10));
        left.add(title, BorderLayout.NORTH);
        left.add(jLabel, BorderLayout.LINE_END);

        Border border = left.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);

        left.setBorder(new CompoundBorder(border, margin));

        return left;
    }

    private JPanel rightPanel() {
        right = new JPanel();
        right.setLayout(new BorderLayout(5, 5));
        right.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        right.setPreferredSize(new Dimension(250, 50));

        JTextField textField = createTextField();
        textField.setSize(25, 15);
        right.add(textField, BorderLayout.NORTH);

        for(Entity entity : helper) {
            listModel.addElement(entity.getTitle());
        }
        list = new JList(listModel);

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(220, 50));
        scrollPane.setColumnHeaderView(list);

        right.add(scrollPane, BorderLayout.LINE_END);

        Border border = right.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);

        right.setBorder(new CompoundBorder(border, margin));

        return right;
    }

    private JTextField createTextField() {
        final JTextField field = new JTextField(15);
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = field.getText();
                filterModel((DefaultListModel<String>)list.getModel(), filter);
            }
        });
        return field;
    }


    public void filterModel(DefaultListModel<String> model, String filter) {
        for (Entity entity : helper) {
            String title = entity.getTitle();
            if (!title.startsWith(filter)) {
                if (model.contains(title)) {
                    model.removeElement(title);
                }
            } else {
                if (!model.contains(title)) {
                    model.addElement(title);
                }
            }
        }
    }

}
