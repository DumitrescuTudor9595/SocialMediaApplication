package service;

import model.User;
import repository.PostRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Scanner;

public class UserService {

    private UserRepository userRepository = new UserRepository();
    private PostRepository postRepository = new PostRepository();
    private Scanner scInt = new Scanner(System.in);
    private Scanner scText = new Scanner(System.in);
    private Scanner scBoolean = new Scanner(System.in);

    public void readAll() {
        List<User> allUsers = userRepository.readAll();
        for (User user: allUsers){
            user.setPosts(postRepository.readUserPosts(user.getId()));
        }
        allUsers.forEach(System.out::println);
    }

    public void readById() {
        int userId = insertUserId();
        User user = userRepository.readById(userId);
        if (user == null) {
            System.out.println("There is no user having the id " + userId);
        } else {
            user.setPosts(postRepository.readUserPosts(userId));
            System.out.println(user);
        }

    }

    public void create() {
        System.out.println("Insert the first name:");
        String firstName = scText.nextLine();
        System.out.println("Insert the last name:");
        String lastName = scText.nextLine();
        System.out.println("Insert the email:");
        String email = scText.nextLine();
        System.out.println("Insert the phone number:");
        String phoneNumber = scText.nextLine();
        boolean userWasSaved = userRepository.create(firstName, lastName, email, phoneNumber);
        if (userWasSaved) {
            System.out.println("The user was saved");
        } else {
            System.out.println("The user was not saved");
        }
    }

    public void update() {
        int userId = insertUserId();
        User user = userRepository.readById(userId);
        if (user == null) {
            System.out.println("There is no user with id " + userId);
        } else {
            boolean weWillModifyTheFirstName = willWeModify("firstName");
            boolean weWillModifyTheLastName = willWeModify("lastName");
            boolean weWillModifyTheEmail = willWeModify("email");
            boolean weWillModifyThePhoneNumber = willWeModify("phoneNumber");

            String newFirstName = user.getFirstName();
            String newLastName = user.getLastName();
            String newEmail = user.getEmail();
            String newPhoneNumber = user.getPhoneNumber();

            if (weWillModifyTheFirstName) {
                newFirstName = provideTheNew("firstName");
            }

            if (weWillModifyTheLastName) {
                newLastName = provideTheNew("lastName");
            }

            if (weWillModifyTheEmail) {
                newEmail = provideTheNew("email");
            }

            if (weWillModifyThePhoneNumber) {
                newPhoneNumber = provideTheNew("phoneNumber");
            }

            boolean userWasModified = userRepository.update(userId, newFirstName, newLastName, newEmail, newPhoneNumber);

            if (userWasModified){
                System.out.println("User modified");
            }else{
                System.out.println("User was Not modified");
            }
        }
    }

    public void delete() {
        int userId = insertUserId();
        boolean userWasDeleted = userRepository.delete(userId);
        if (userWasDeleted){
            System.out.println("User deleted");
        }else{
            System.out.println("User was not deleted");
        }
    }

    private boolean willWeModify(String columnName) {
        System.out.println("Do you want to change the " + columnName + "?");
        return scBoolean.nextBoolean();
    }

    private String provideTheNew(String colunmName) {
        System.out.println("Insert the new " + colunmName);
        return scText.nextLine();
    }

    private int insertUserId(){
        System.out.println("Insert the user id:");
        return scInt.nextInt();
    }

}
