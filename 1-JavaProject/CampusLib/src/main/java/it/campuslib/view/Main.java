/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import it.campuslib.collections.GivebackRegistry;
import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.transactions.Giveback;
import it.campuslib.domain.users.User;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ecoll
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccessView.fxml"));
        Parent root = loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        
       /* GivebackRegistry gb=new GivebackRegistry();
        User u= new User("Elena", "Collazzo", "0612709194", "e.collazzo1@studenti.unisa.it");
        Author a=new Author("Edgar Allan", "Poe");
        ArrayList<Author> au= new ArrayList<>();
        Book book=new Book("9785254856320", "Il corvo", au, 1845, 1 );
        Giveback g;
        g = new Giveback(1, book,u, LocalDate.now(), LocalDate.now());
        
        gb.addGiveback(g);*/
    
    
    }
        
     
        
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
