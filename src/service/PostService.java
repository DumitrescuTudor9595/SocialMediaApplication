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

    private Scanner scNumere = new Scanner(System.in);
    private Scanner scText = new Scanner(System.in);

    public void readAll() {
        ArrayList<Post> allPosts = postRepository.readAll();
        for (Post post : allPosts){
            System.out.println(post);
        }
    }

    public void readUserPosts() {
        System.out.println("Introduceti idul userului pentru a-i returna postarile");
        int userId = scNumere.nextInt();
        User userCitit = userRepository.readById(userId);
        if (userCitit == null){
            System.out.println("Nu exista nici un user cu idul " + userId);
        }else{
            ArrayList<Post> postarileUserului = postRepository.readPostsFromUserWithId(userId);
            postarileUserului.forEach(postare -> System.out.println(postare));
        }
    }

    public void create() {
        System.out.println("Introduceti idul userului:");
        int userId = scNumere.nextInt();
        User userCitit = userRepository.readById(userId);
        if (userCitit == null){
            System.out.println("Nu exista nici un user cu idul " + userId);
        }else{
            System.out.println("Introduceti mesajul:");
            String mesaj = scText.nextLine();
            LocalDateTime createdAt = LocalDateTime.now();
            int affectedRows = postRepository.create(userId,mesaj,createdAt);
            if (affectedRows>0){
                System.out.println("Postarea a fost salvata");
            }else{
                System.out.println("Postarea nu a putut fi salvata");
            }
        }

    }

    public void update() {
        //introducem idul postarii care dorim sa fie modificata.
        System.out.println("Introduceti idul postarii:");
        int postId = scNumere.nextInt();
        //verificam prin intermediul postRepository daca acea postare exista.
        Post postare = postRepository.readById(postId);
        if (postare ==null){
            System.out.println("Nu exista nici o postare cu idul " + postId);
        }else{
            System.out.println("Introduceti noul mesaj:");
            String mesajNou = scText.nextLine();
            int affectedRows = postRepository.update(postId,mesajNou);
            if (affectedRows>0){
                System.out.println("Postarea a fost modificata");
            }else{
                System.out.println("Postarea nu a putut fi modificata.");
            }
        }
    }

    public void delete() {
        //introducem idul postarii pe care dorim sa o stergem
        System.out.println("Introduceti idul postarii pe care doriti sa o stergeti:");
        int postId = scNumere.nextInt();
        //trimitem un request de stergere catre postRepository
        int affectedRows = postRepository.delete(postId);
        //in functie de raspuns, vom printa un mesaj corespunzator.
        System.out.println("Postarea " + (affectedRows==0? "nu":"") + " a fost stearsa");
    }
}
