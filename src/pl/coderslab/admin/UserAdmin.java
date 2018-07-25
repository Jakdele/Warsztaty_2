package pl.coderslab.admin;

import pl.coderslab.model.User;

public class UserAdmin {

    static void userOptions() {
        while (true) {
            User.loadAll();
            System.out.println("What would you like to do: edit, add, or delete a user?\nType quit to quit the app.");
            String userAction = AdminTools.scanner.nextLine();
            if (userAction.equals("edit")) {
                editUser();
            } else if (userAction.equals("add")) {
                addUser();
            } else if (userAction.equals("delete")) {
                deleteUser();
            } else if(userAction.equals("quit")){
                AdminTools.closeApp();
            } else {
                System.out.println("Input incorrect, try again");
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
        return AdminTools.scanner.nextLine();
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
        while (!AdminTools.scanner.hasNextInt()){
            AdminTools.scanner.next();
            System.out.println("Wrong input - you have to enter a number");
        }
        int id = AdminTools.scanner.nextInt();
        AdminTools.scanner.nextLine();
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
