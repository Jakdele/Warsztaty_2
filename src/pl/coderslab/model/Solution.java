package pl.coderslab.model;

import java.sql.*;
import java.util.ArrayList;

public class Solution {
    private int id;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private int exercise_id;
    private int users_id;

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Solution(int id, Timestamp created, Timestamp updated, String description) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.description = description;
    }

    public Solution(int id, int users_id, String description) {
        this.id = id;
        this.description = description;
        this.users_id = users_id;
    }

    public Solution(int id, int exercise_id, int users_id) {
        this.id = id;
        this.exercise_id = exercise_id;
        this.users_id = users_id;
    }

    public Solution(int id, Timestamp created, Timestamp updated, String description, int exercise_id, int users_id) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;
        this.users_id = users_id;
    }

    public Solution() {
    }

    @Override
    public String toString() {
        return "Solution " +
                "id: " + id +
                " created: " + created +
                " updated: " + updated +
                " description: '" + description + '\'' +
                " exercise_id: " + exercise_id +
                " users_id: " + users_id ;
    }

    //Active Record
    public void saveToDb(){
        try {
            if(this.id==0) {
                String sql = "INSERT INTO solution(exercise_id, users_id) VALUES ( ?, ?)";
                String generatedColumns[] = {"ID"};
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql, generatedColumns);
                preparedStatement.setInt(1, this.exercise_id);
                preparedStatement.setInt(2, this.users_id);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                    System.out.println("Added new solution, id: " + this.id);
                }


            }else {
                String sql = "UPDATE solution SET description=?, users_id=? WHERE id =?";
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setString(1, this.description);
                preparedStatement.setInt(2, this.users_id);
                preparedStatement.setInt(3, this.id);
                preparedStatement.executeUpdate();
                int rows = preparedStatement.executeUpdate();
                if(rows>0) {
                    System.err.println("Solution updated.");
                } else {
                    System.out.println("Solution id not found.");
                }

            }

        } catch (SQLException e) {
        }
    }
    public void delete() {
        try {
            if(this.id!=0) {
                String sql = "DELETE FROM solution WHERE id =?";
                PreparedStatement preparedStatement;
                preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
        }
    }

    public static Solution loadById (int id) { //
        try {
            String sql = "SELECT * FROM solution where id=?";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Solution loadedSolution = new Solution();
                loadedSolution.id = rs.getInt("id");
                loadedSolution.created = rs.getTimestamp("created");
                loadedSolution.updated = rs.getTimestamp("updated");
                loadedSolution.description = rs.getString("description");
                loadedSolution.exercise_id = rs.getInt("exercise_id");
                loadedSolution.users_id = rs.getInt("users_id");
                return  loadedSolution;
            }
        } catch (SQLException e) {
        }
        return null;
    }




    public static ArrayList<Solution> loadAll (){
        try{
            ArrayList<Solution> solutions = new ArrayList<>();
            String sql = "SELECT * FROM solution";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = rs.getInt("id");
                loadedSolution.created = rs.getTimestamp("created");
                loadedSolution.updated = rs.getTimestamp("updated");
                loadedSolution.description = rs.getString("description");
                loadedSolution.exercise_id = rs.getInt("exercise_id");
                loadedSolution.users_id = rs.getInt("users_id");
                solutions.add(loadedSolution);
            }
            for(Solution solution:solutions){
                System.out.println(solution);
            }
            return solutions;

        }catch (SQLException e) {
        }

        return null;
    }
    public static ArrayList<Solution> loadAllByUserID (int users_id){
        try{
            ArrayList<Solution> solutions = new ArrayList<>();
            String sql = "SELECT * FROM solution JOIN users ON solution.users_id=users.id WHERE users.id=? ORDER BY created DESC";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, users_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) {
                System.out.println("You have no solutions in database.");
            }else {
                rs.beforeFirst(); }
            while(rs.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = rs.getInt("id");
                loadedSolution.created = rs.getTimestamp("created");
                loadedSolution.updated = rs.getTimestamp("updated");
                loadedSolution.description = rs.getString("description");
                loadedSolution.exercise_id = rs.getInt("exercise_id");
                loadedSolution.users_id = rs.getInt("users_id");
                solutions.add(loadedSolution);
            }
            for(Solution solution:solutions){
                System.out.println(solution);
            }
            return solutions;

        }catch (SQLException e) {
        }
        return null;
    }
    public static ArrayList<Solution> loadAllNotDone (int userId) {
        try {
            ArrayList<Solution> solutions = new ArrayList<>();
            String sql = "SELECT * FROM solution JOIN users ON solution.users_id=users.id WHERE users.id=? AND solution.description IS NULL ORDER BY created DESC";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) {
                System.out.println("You have no exercises pending");
            }else {
                rs.beforeFirst(); }
            while (rs.next()) {
                    Solution loadedSolution = new Solution();
                    loadedSolution.id = rs.getInt("id");
                    loadedSolution.created = rs.getTimestamp("created");
                    loadedSolution.updated = rs.getTimestamp("updated");
                    loadedSolution.description = rs.getString("description");
                    loadedSolution.exercise_id = rs.getInt("exercise_id");
                    loadedSolution.users_id = rs.getInt("users_id");
                    solutions.add(loadedSolution);
            }
            for (Solution solution : solutions) {
                System.out.println(solution);
            }
            return solutions;

        } catch (SQLException e) {
        }
        return null;
    }

    public static ArrayList<Solution> loadAllByExerciseId (int exercise_id){
        try{
            ArrayList<Solution> solutions = new ArrayList<>();
            String sql = "SELECT * FROM solution JOIN exercise ON solution.exercise_id=exercise.id WHERE exercise.id=?";
            PreparedStatement preparedStatement;
            preparedStatement = DBManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, exercise_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = rs.getInt("id");
                loadedSolution.created = rs.getTimestamp("created");
                loadedSolution.updated = rs.getTimestamp("updated");
                loadedSolution.description = rs.getString("description");
                loadedSolution.exercise_id = rs.getInt("exercise_id");
                loadedSolution.users_id = rs.getInt("users_id");
                solutions.add(loadedSolution);
            }
            return solutions;

        }catch (SQLException e) {
        }

        return null;
    }



}
