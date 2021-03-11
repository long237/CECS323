/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chunchunmaru
 */
import java.util.Scanner;
public class UserInterface {
    
    public void printNameprompt(){
        System.out.println("Enter a username: ");
    }
    
    public void printDBprompt(){
        System.out.println("Enter a database name: ");
    }
    
    public void printPassprompt(){
        System.out.println("Enter a password: ");
    }
    
    public void printMenu(){
        System.out.println("Print the menu");
    }
    
    public void printAttri(){
        System.out.println("Enter an attribute to display (N to stop): ");
    }
        
    public String getUserInput(){
        Scanner in = new Scanner(System.in);
        String user_input = "";
        try{
            user_input = in.nextLine();
            return user_input;
        }
        catch (Exception e){
            System.out.println("Invalid input, please try again.");
            return "-1";
        }
    }
    

    
}
