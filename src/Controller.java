import service.PostService;
import service.UserService;

import java.util.Scanner;

public class Controller {
    private static UserService userService = new UserService();
    private static PostService postService = new PostService();
    static Scanner scText = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("Choose the flow(user/post):");
            String answer = scText.nextLine();
            switch (answer) {
                case "user":
                    startUserFlow();
                    break;
                case "post":
                    startPostsFlow();
                    break;
                default:
                    System.out.println("This is not a valid flow.");
            }

        }
    }

    private static void startUserFlow() {
        String chosenOperation = chooseOperation("RA / RBI / C / U /D");
        switch (chosenOperation) {
            case "RA":
                userService.readAll();
                break;
            case "RBI":
                userService.readById();
                break;
            case "C":
                userService.create();
                break;
            case "U":
                userService.update();
                break;
            case "D":
                userService.delete();
                break;
            default:
                System.out.println("This is not a valid operation");
        }
    }

    private static void startPostsFlow() {
        String chosenOperation = chooseOperation("RA / RBUI / C / U /D");

        switch (chosenOperation) {
            case "RA":
                postService.readAll();
                break;
            case "RBUI":
                postService.readByUserId();
                break;
            case "C":
                postService.create();
                break;
            case "U":
                postService.update();
                break;

            case "D":
                postService.delete();
                break;
            default:
                System.out.println("Not a valid operation");
        }
    }

    private static String chooseOperation(String possibleOperations) {
        System.out.println("Insert the operation (" + possibleOperations + ")");
        return scText.nextLine();
    }
}





//comment_table : id PK AI, post_id, user_id, message, year, month, day, hour, minute
// (user_id,post_id - FOREIGN KEY)

// Comment class: id, postId, userId, message createdAt(LocalDateTime)

//any post will have a lsit of comments

//CREATE
//enter the id of the post to which we want to add a comment
//if the post does not exist, we will display a corresponding message
//if the post exists, enter the id of the user we want to comment on that post
//if the user does NOT exist, we will display a corresponding message.
//if the user exists, we will provide a message for the comment
//send a request to commentRepository in order to create that comment
//depending on the returned answer, we will print a corresponding message

//UPDATE
//the only theng that can be modified is the message
//enter the id of the comment we want to modify
//read that comment using commentRepository
//if the comment does not exist, we will print a corresponding message.
//if the comment exists, we will enter a new message from the keyboard
//we send an update request to commentRepository

//DELETE
//enter the id of the comment we want to delete
//we send a delete request to commentRepository.
//depending on the returned answer, we will print a corresponding message




















