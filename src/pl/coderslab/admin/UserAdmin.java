package pl.coderslab.admin;

import pl.coderslab.model.User;

import java.util.Scanner;

import static pl.coderslab.admin.AdminTools.closeApp;
import static pl.coderslab.admin.AdminTools.getInt;
import static pl.coderslab.admin.AdminTools.scanner;

public class UserAdmin {

    static void userOptions() {
        while (true) {
            User.loadAll();
            System.out.println("What would you like to do: \n(1) edit user\n(2) add a new user\n(3) delete a user\n(0) quit the app");
            switch (getInt(scanner)) {
                case 1:
                    addUser();
                    break;
                case 2:
                    editUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 0:
                    closeApp();
                    break;
                default:
                    System.out.println("Incorrect input - try again.");
            }
        }
    }

    public static void editUser(){
        int id= getIntFromUser(Type.USER_ID);
        String username = getStringFromUser(Type.NAME);
        String email = getStringFromUser(Type.EMAIL);
        String password = getStringFromUser(Type.PASS);
        int userGroupId= getIntFromUser(Type.GROUP_ID);

        User newUser = new User(id, username, email,password, userGroupId);
        newUser.saveToDb();
    }

    public static void addUser() {
        int id =0;
        String username = getStringFromUser(Type.NAME);
        String email = getStringFromUser(Type.EMAIL);
        String password = getStringFromUser(Type.PASS);
        int userGroupId= getIntFromUser(Type.GROUP_ID);

        User newUser = new User(id, username, email,password, userGroupId);
        newUser.saveToDb();
    }

    public static void deleteUser() {
        int id = getIntFromUser(Type.USER_ID);
        User.delete(id);
    }


    private static String getStringFromUser (Type type){
        switch (type) {
            case NAME:
                System.out.println("Enter new username:");
                break;
            case EMAIL:
                System.out.println("Enter new email:");
                break;
            case PASS:
                System.out.println("Enter new password:");
                break;
            default:
                break;
        }
        return scanner.nextLine();
    }
    private static int getIntFromUser(Type type) {
        switch (type) {
            case USER_ID:
                System.out.println("Enter id of user you want to edit:");
                break;
            case GROUP_ID:
                System.out.println("Enter id of the group user belongs to:");
                break;
        }
        while (!scanner.hasNextInt()){
            scanner.next();
            System.out.println("Wrong input - you have to enter a number");
        }
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    private enum Type{
        NAME,
        PASS,
        EMAIL,
        USER_ID,
        GROUP_ID
    }

}
