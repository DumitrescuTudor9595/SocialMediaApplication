package service;

import model.User;
import repository.PostRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {

    private UserRepository userRepository = new UserRepository();
    private PostRepository postRepository = new PostRepository();

    private Scanner scannerNumere = new Scanner(System.in);
    private Scanner scannerText = new Scanner(System.in);
    private Scanner scannerBoolean = new Scanner(System.in);

    public void readAll(){
        ArrayList<User> allUsers = userRepository.readAll();
        for (User user: allUsers){
            user.postari = postRepository.readPostsFromUserWithId(user.id);
        }
        allUsers.forEach(user -> System.out.println(user));
    }

    public void readById(){
        System.out.println("Introduceti idul utilizatorului dorit:");
        int id = scannerNumere.nextInt();

        User userCitit = userRepository.readById(id);

        if (userCitit == null){
            System.out.println("Nu exista nici un user cu idul " + id);
        }else{
            userCitit.postari = postRepository.readPostsFromUserWithId(id);
            System.out.println(userCitit);
        }
    }

    public void create(){
        System.out.println("Introduceti numele:");
        String nume = scannerText.nextLine();
        System.out.println("Introduceti prenumele:");
        String prenume = scannerText.nextLine();
        System.out.println("Introduceti emailul:");
        String email = scannerText.nextLine();
        System.out.println("Introduceti numarul de telefon:");
        String numarTelefon = scannerText.nextLine();
        //trimitem un request cu informatiile pentru a salva in baza de date utilizatorul nou
        boolean succes = userRepository.create(nume,prenume,email,numarTelefon);
        if (succes){
            System.out.println("Utilizator salvat.");
        }else{
            System.out.println("A aparut o eroare.");
        }
    }

    public void update(){
        //introducem idul userului
        System.out.println("Introduceti idul userului:");
        int id = scannerNumere.nextInt();
        //verificam daca userul exista in BD
        User userCitit = userRepository.readById(id);
        if (userCitit != null){
            boolean modificamNumele = modificamProprietatea("nume");
            boolean modificamPrenumele = modificamProprietatea("prenume");
            boolean modificamEmailul = modificamProprietatea("email");
            boolean modificamNumarulDeTelefon = modificamProprietatea("numarTelefon");

            if (modificamNumele){
                System.out.println("Introducei noul nume:");
                String numeNou = scannerText.nextLine();
                userRepository.modifyName(id,numeNou);
            }
            if (modificamPrenumele){
                System.out.println("Introducei noul prenume:");
                String prenumeNou = scannerText.nextLine();
                userRepository.modifyPrenume(id,prenumeNou);
            }
            if (modificamEmailul){
                System.out.println("Introducei noul email:");
                String emailNou = scannerText.nextLine();
                userRepository.modifyEmail(id,emailNou);
            }
            if (modificamNumarulDeTelefon){
                System.out.println("Introducei noul numar de telefon:");
                String numarNou = scannerText.nextLine();
                userRepository.modifyPhoneNumber(id,numarNou);
            }
        }else{
            System.out.println("Nu exista nici un user cu idul " + id);
        }

    }

    public boolean modificamProprietatea(String proprietate){
        System.out.println("Doriti sa modificati proprietatea " + proprietate + " ?");
        return scannerBoolean.nextBoolean();
    }

    public void delete(){
        System.out.println("Introduceti idul userului pe care doriti sa il stergeti:");
        int id = scannerNumere.nextInt();
        boolean succes = userRepository.delete(id);
        System.out.println(succes ? "Utilizatorul a fost sters" : "Utilizatorul nu a fost sters");
    }
}
