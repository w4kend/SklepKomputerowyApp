package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

//klasa odpowiadająca za tworzenia konta użytkownika
public class SignupController {
    @FXML
    private Button authSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField signUpSurname;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private PasswordField password_field1;

    @FXML
    private Button authSignUpButton1;

    @FXML
    private Text signUpWarning;

    @FXML
    private Text signUpWarning1;

    @FXML
        //metoda inicjalizująca definicje przycisków
    void initialize() {
        //przy kliknięciu na zatwierdzenie tworzenia użytkownika
        authSignUpButton.setOnAction(actionEvent -> {
            //sprawdzenie czy hasłą takie same
            if(!password_field.getText().equals(password_field1.getText())){
                //jeżeli nie to wypisujemy o tym komunikat
                this.signUpWarning1.setText(" ");
                this.signUpWarning.setText("Please make sure your password match.");
                System.out.println(password_field.getText() + " " +password_field1.getText());
            } else {
                //w innym przypadku wywołujemy metode która wykonuje utworzenie konta dla nowego użytkownika
                try {
                    signUpNewUser();
                } catch (SQLException throwables) { //obsługa wyjątków
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        this.authSignUpButton1.setOnAction(actionEvent -> {
            this.openNewScene("loginstage.fxml");
        });
    }

    //metoda która wykonuje utworzenie konta dla nowego użytkownika
    private void signUpNewUser() throws SQLException, ClassNotFoundException {
        //klasa w której zostały zdefiniowane metody obsługiwające zapytania do bazy danych
        DatabaseHandler dbHandler = new DatabaseHandler();
        int counter = 0;

        //pobieramy następna wartość+1 (nową) identyfikatora użytkownika
        int id_user = dbHandler.getNewUser_id();
        String firstName = signUpFirstName.getText(); //pobieranie pozostałych danych z pól
        String surName = signUpSurname.getText();
        String userName = login_field.getText();
        String password = password_field.getText();

        //wywołanie konstruktora klasy user
        User user = new User(id_user,firstName, surName, userName, password);
        /*
        przepisujemy do obiektu ResultSet dane które zwraca nam metoda getUser w razie znalezienia użytkownia z
        odpowiednią nazwą i hasłem
        (wyszukujemy użytkownia z daną nazwą i hasłem jeżeli takowy istnieje)
         */
        ResultSet result = dbHandler.getUser(user);



        System.out.println(user.getId_user()+"\n"+
                        user.getFirstName()+"\n"+
                        user.getSurName()+"\n"+
                        user.getUserName()+"\n"+
                        user.getPassword()+"\n"
        );

        while(result.next())
                //w razie znalezienia takiego użytkownika
                //inkrementujemy zmienną
               ++counter;


        //w razie inkrementacji zmiennej będzie oznaczało że użytkownik z taką nazwą już istnieje
        //i musimy stworzyć konto i inną nazwą użytkownika
        if(counter > 0) {
            this.signUpWarning1.setText(" ");
            signUpWarning.setText("This username exists, you  must put another ");
            id_user -= 1;  //przepisujemy zmienej identyfikator wcześniej stworzonego użytkownika
        } else {
            //jeżeli użytkownika z taką nazwą w bazie nie istnije
            this.signUpWarning.setText(" ");
            //to tworzymy danego użytkownika(klienta)
            dbHandler.signUpUser(user);
            this.signUpWarning1.setText("Account created successfully");
        }
    }
    //metoda służąca do przejscia do nowego okna

    public void openNewScene(String window){
        authSignUpButton1.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));


        try {
            loader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}