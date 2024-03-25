package com.onlineshopping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    private String username;
    private String password;

    private static List<User> userList = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static boolean userExists(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void addToUserList(User user){
        userList.add(user);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void saveUserListToFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));

        for (User user : userList) {
            writer.write(user.getUsername() + "," + user.getPassword() + "\n");
        }

        writer.close();
    }

    public static void loadUserListFromFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            Scanner scanner = new Scanner(path);
            while (scanner.hasNextLine()) {
                String[] userDetails = scanner.nextLine().split(",");
                User user = new User(userDetails[0], userDetails[1]);
                userList.add(user);
            }
            scanner.close();
        }
    }

    public static User getUserByUsername(String username)

    {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }



    public String getUsername() {
        return username;
    }



    public String getPassword() {
        return password;
    }


}
