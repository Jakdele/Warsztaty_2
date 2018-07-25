package pl.coderslab.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
    private int id;
    private String title;
    private String description;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise() {
    }

    public Exercise(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Exercise id: " + id +
                " title: " + title + '\'' +
                " description: " + description;
    }

    //Active Record
    public void saveToDb(){
        try {
            if(this.id==0) {
                String sql = "INSERT INTO exercise(title, description) VALUES (?, ?)";
                String generatedColumns[] = {"ID"};
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.title);
                preparedStatement.setString(2, this.description);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }

            }else {
                String sql = "UPDATE exercise SET title=?, description=? WHERE id =?";
                String generatedColumns[] = {"ID"};
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.title);
                preparedStatement.setString(2, this.description);
                preparedStatement.setInt(3, this.id);
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
                String sql = "DELETE FROM exercise WHERE id =?";
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
        }
    }

    public static Exercise loadById (int id) { //
        try {
            String sql = "SELECT * FROM exercise where id=?";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = rs.getInt("id");
                loadedExercise.title = rs.getString("title");
                loadedExercise.description = rs.getString("description");
                return  loadedExercise;
            }
        } catch (SQLException e) {
        }
        return null;
    }




    public static ArrayList<Exercise> loadAll (){
        try{
            ArrayList<Exercise> exercises = new ArrayList<>();
            String sql = "SELECT * FROM exercise";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = rs.getInt("id");
                loadedExercise.title = rs.getString("title");
                loadedExercise.description = rs.getString("description");
                exercises.add(loadedExercise);
            }
            for(Exercise exercise: exercises){
                System.out.println(exercise);
            }
            return exercises;

        }catch (SQLException e) {
        }

        return null;
    }

}
