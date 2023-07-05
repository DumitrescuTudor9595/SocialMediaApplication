package service;


import model.Post;
import model.User;
import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PostService {

    private PostRepository postRepository = new PostRepository();
    private UserRepository userRepository = new UserRepository();

    private Scanner scInt = new Scanner(System.in);
    private Scanner scText = new Scanner(System.in);
    public void readAll() {
        ArrayList<Post> posts = postRepository.readAll();
        posts.forEach(System.out::println);
    }

    public void readByUserId() {
        int userId = insertUserId();
        User user = userRepository.readById(userId);
        if (user==null){
            System.out.println("There is no user with id " + userId);
        }else{
            ArrayList<Post> userPosts = postRepository.readUserPosts(userId);
            if (userPosts.size()==0){
                System.out.println("That user does not have any posts.");
            }else{
                userPosts.forEach(System.out::println);
            }
        }
    }

    public void create() {
        int userId = insertUserId();
        User user = userRepository.readById(userId);
        if (user==null){
            System.out.println("There is no user with that id.");
        }else{
            System.out.println("Insert the message:");
            String message = scText.nextLine();
            LocalDateTime present = LocalDateTime.now();
            int affectedRows =postRepository.create(userId,message,present);
            if (affectedRows>0){
                System.out.println("Post was created");
            }else{
                System.out.println("Post was not created");
            }
        }

    }

    public void update() {
        int postId= insertPostId();
        Post post = postRepository.readById(postId);
        if (post ==null){
            System.out.println("There is no post with that id");
        }else{
            System.out.println("Insert the new message:");
            String message = scText.nextLine();
            int affectedRows = postRepository.update(postId,message);
            if (affectedRows>0){
                System.out.println("Post updated");
            }else{
                System.out.println("Post was not updated");
            }
        }
    }

    public void delete() {
        int postId = insertPostId();
        int affectedRows = postRepository.delete(postId);
        if (affectedRows>0){
            System.out.println("Post deleted");
        }else{
            System.out.println("Post not deleted");
        }
    }

    private int insertUserId(){
        System.out.println("Insert the user id:");
        return scInt.nextInt();
    }

    private int insertPostId(){
        System.out.println("Insert the post id:");
        return scInt.nextInt();
    }
}
