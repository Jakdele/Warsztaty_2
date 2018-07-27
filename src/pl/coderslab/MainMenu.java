package pl.coderslab;

import pl.coderslab.admin.AdminTools;
import pl.coderslab.admin.UserAdmin;
import pl.coderslab.model.User;
import pl.coderslab.user.UserTools;

import java.util.Scanner;

import static pl.coderslab.admin.AdminTools.closeApp;
import static pl.coderslab.admin.AdminTools.getInt;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to programming school app");
        System.out.println("This app allows you to manage your users, user groups, exercises and solutions for your student.\n" +
                "If you are a student, you can browse and update your solutions for exercises.");
        System.out.println("Work:\n(1) as admin\n(2) as user\n(0) quit the app");

        switch (getInt(scanner)){
            case 1:
                AdminTools.adminPanel();
                break;
            case 2:
                UserTools.userPanel();
                break;
            case 0:
                closeApp();
                break;
            default:
                System.out.println("Incorrect input - try again.");

        }

        }
    }


