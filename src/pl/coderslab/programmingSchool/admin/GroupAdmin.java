package pl.coderslab.programmingSchool.admin;

import pl.coderslab.programmingSchool.model.Group;

import static pl.coderslab.programmingSchool.admin.AdminTools.closeApp;
import static pl.coderslab.programmingSchool.admin.AdminTools.getInt;
import static pl.coderslab.programmingSchool.admin.AdminTools.scanner;

public class GroupAdmin {

    static void groupOptions() {
        while (true) {
            Group.printAllGroups();
            System.out.println("What would you like to do: \n(1) edit group\n(2) add a new group\n(3)delete a group\n(0) quit the app");
            switch (getInt(scanner)) {
                case 1:
                    addGroup();
                    break;
                case 2:
                    editGroup();
                    break;
                case 3:
                    deleteGroup();
                    break;
                case 0:
                    closeApp();
                    break;
                default:
                    System.out.println("Incorrect input - try again.");
            }
        }

    }

    public static void editGroup(){
        int id= getID();
        String name = getStringFromUser();
        Group newGroup = new Group(id, name);
        newGroup.saveToDb();
    }

    public static void addGroup() {
        int id =0;
        String name = getStringFromUser();
        Group newGroup = new Group(id, name);
        newGroup.saveToDb();
    }

    public static void deleteGroup() {
        int id = getID();
        if(AdminTools.getUserConfirmation() == true && id != 0)
        Group.delete(id);
    }


    private static String getStringFromUser (){
        System.out.println("Enter group id: ");
        return AdminTools.scanner.nextLine();
    }
    public static int getID(){
        System.out.println("Enter group id: ");
        while(!AdminTools.scanner.hasNextInt()){
           AdminTools.scanner.next();
            System.out.println("Id has to be a number. \nEnter id: ");
        }
        int id = AdminTools.scanner.nextInt();
        AdminTools.scanner.nextLine();
        return id;
    }

}
