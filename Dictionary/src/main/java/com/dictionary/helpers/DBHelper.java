package com.dictionary.helpers;

import com.dictionary.graphical.Graphical;
import com.dictionary.properties.PropReader;
import com.dictionary.structure.Entity;


import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DBHelper extends EntityHelper {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement stmt;


    private static final String GET_ENTITIES   =
            "select dict.id, dict.title,  descr.description, img.image\n" +
                    "from dictionary dict\n" +
                    "JOIN descriptions descr ON dict.id = descr.id\n" +
                    "JOIN images img ON dict.id = img.id";


    private static final String ADD_DICTIONARY = "insert into dictionary VALUES(? , ?)";
    private static final String ADD_DESCRIPTION = "insert into descriptions VALUES(? , ?)";
    private static final String ADD_IMAGE = "insert into images VALUES(? , ?)";

    private static final String REMOVE_DICTIONARY = "delete from dictionary where id = ?";
    private static final String REMOVE_DESCRIPTION = "delete from descriptions where id = ?";
    private static final String REMOVE__IMAGE = "delete from images where id = ?";

    public DBHelper() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        entities = new ArrayList<Entity>();

        String url = PropReader.get("project.database.url");
        String user = PropReader.get("project.database.user");
        String password = PropReader.get("project.database.password");

        conn = DriverManager.getConnection(url, user, password);

        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(GET_ENTITIES);


        while(rs.next()) {
            size++;
            Entity entity = new Entity();
            entity.setId(Integer.parseInt(rs.getString("id")));
            entity.setTitle(rs.getString("title"));
            entity.setDescription(rs.getString("description"));
            entity.setImg(rs.getString("image"));
            entities.add(entity);
        }
    }

    @Override
    public boolean add(String title, String description, String image) throws SQLException {
        Entity entity = new Entity();
        entity.setId(new Random().nextInt(90000 - 10000 + 1) + 90000);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setImg(image);

        entities.add(entity);
        size++;

        int id = entity.getId();

        try {
            preparedStatement = conn.prepareStatement(ADD_DICTIONARY);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.execute();

            preparedStatement = conn.prepareStatement(ADD_DESCRIPTION);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.execute();

            preparedStatement = conn.prepareStatement(ADD_IMAGE);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, entity.getImg());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean remove(int index) throws SQLException {
        Entity entity = Graphical.helper.get(index);
        entities.remove(entity);
        size--;

        int id = entity.getId();

        try {
            preparedStatement = conn.prepareStatement(REMOVE_DICTIONARY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement = conn.prepareStatement(REMOVE_DESCRIPTION);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement = conn.prepareStatement(REMOVE__IMAGE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public void disconnect() {
        try {
            if(conn != null) {
                conn.close();
            }

            if(preparedStatement != null) {
                preparedStatement.close();
            }

            if(stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
