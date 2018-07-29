package pl.coderslab.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group " +
                "id: " + id +
                " name: " + name ;
    }

    //Active Record
    public void saveToDb(){
        try {
            if(this.id==0) {
                String sql = "INSERT INTO user_group(name) VALUES (?)";
                String generatedColumns[] = {"ID"};
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.name);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }

            }else {
                String sql = "UPDATE user_group SET name=? WHERE id =?";
                String generatedColumns[] = {"ID"};
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.name);
                preparedStatement.setInt(2, this.id);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }

            }

        } catch (SQLException e) {
        }
    }
    public static void delete(int id) {
        try {
            if(id!=0) {
                String sql = "DELETE FROM user_group WHERE id =?";
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
        }
    }

    public static Group loadById (int id) { //
        try {
            String sql = "SELECT * FROM user_group where id=?";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) {
                System.out.println("There is no group in the database with that id.");
            }else {
                rs.beforeFirst(); }
            while(rs.next()){
                Group loadedGroup = new Group();
                loadedGroup.id = rs.getInt("id");
                loadedGroup.name = rs.getString("name");
                return  loadedGroup;
            }
        } catch (SQLException e) {
        }
        return null;
    }


    public static void printAllGroups(){
        for(Group group: loadAll()){
            System.out.println(group);
        }
    }

    public static ArrayList<Group> loadAll (){
        try{
            ArrayList<Group> groups = new ArrayList<>();
            String sql = "SELECT * FROM user_group";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Group loadedGroup = new Group();
                loadedGroup.id = rs.getInt("id");
                loadedGroup.name = rs.getString("name");
                groups.add(loadedGroup);
            }
            return groups;
        }catch (SQLException e) {
        }

        return null;
    }

}
