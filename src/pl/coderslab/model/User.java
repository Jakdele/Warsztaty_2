package pl.coderslab.model;


import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private int user_group_id;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String setPassword (String password){
        return this.password = BCrypt.hashpw(password,BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "User id: " + id + " username: " + username + " email: " + email + " password: " + password;
    }

    public User(int id, String username, String email, String password, int user_group_id) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = setPassword(password);
        this.user_group_id = user_group_id;
    }

    public User() { //empty constructor for filling with data from database
    }


    //Active Record
    public void saveToDb(){
        try {
        if(this.id==0) {
            String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?, ?, ?, ?)";
            String generatedColumns[] = {"ID"};
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.user_group_id);
            int rows = preparedStatement.executeUpdate();
            if(rows==0) {
                System.err.println("Group id not found.");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
                System.out.println("Added new user, id: " + this.id);
            }

        }else {
            String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? WHERE id =?";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.user_group_id);
            preparedStatement.setInt(5, this.id);
            int rows = preparedStatement.executeUpdate();
            if(rows>0) {
                System.out.println("Updated date for user id=" + id);
            }else{
                System.err.println("Id not found.");
            }
        }

        } catch (SQLException e) {
        }
    }
    public static void delete(int id) {
        try {
            if(id!=0) {
                String sql = "DELETE FROM users WHERE id =?";
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Deleted user with id: " + id);
            }

        } catch (SQLException e) {
        }
    }

    public static User loadById (int id) { //
        try {
            String sql = "SELECT * FROM users where id=?";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) {
                System.out.println("There is no user in the database with that id.");
            }else {
                rs.beforeFirst(); }
            while(rs.next()){
                User loadedUser = new User();
                loadedUser.id = rs.getInt("id");
                loadedUser.username = rs.getString("username");
                loadedUser.email = rs.getString("email");
                loadedUser.password = rs.getString("password");
                loadedUser.user_group_id = rs.getInt("user_group_id");
                return  loadedUser;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public static void printAllUsers(){
        for(User user: loadAll()){
            System.out.println(user);
        }
    }



    public static ArrayList<User> loadAll (){
        try{
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement;
        preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            User loadedUser = new User();
            loadedUser.id = rs.getInt("id");
            loadedUser.username = rs.getString("username");
            loadedUser.email = rs.getString("email");
            loadedUser.password = rs.getString("password");
            users.add(loadedUser);
        }
        return users;
        }catch (SQLException e) {
        }

        return null;
    }

    public static ArrayList<User> loadAllByGroupId (int userGroupId){
        try{
            ArrayList<User> users = new ArrayList<>();
            String sql = "SELECT * FROM users JOIN user_group ON users.user_group_id=user_group.id WHERE user_group.id=?";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userGroupId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                User loadedUser = new User();
                loadedUser.id = rs.getInt("id");
                loadedUser.username = rs.getString("username");
                loadedUser.email = rs.getString("email");
                loadedUser.password = rs.getString("password");
                users.add(loadedUser);
            }
            return users;
        }catch (SQLException e) {
        }

        return null;
    }


}
