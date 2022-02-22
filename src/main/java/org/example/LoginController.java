package org.example;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


//klasa odpowiadająca ze logowanie
public class LoginController {

    public static String username;

    @FXML
    private Button authSignInButton;
    @FXML
    private TextField login_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Button loginSignUpButton;
    @FXML
    private Text autSignInWarning;


    public LoginController() {}

    //metoda inicjalizująca definicje przycisków
    @FXML
    void initialize() {
        //przy kliknięciu na logowanie
        this.authSignInButton.setOnAction((event) -> {
            //pobieramy wpisane dane wejsciowe z pól
            String loginText = this.login_field.getText().trim(); //metoda trim służy do obcięcia spacji na końcu
            String loginPassword = this.password_field.getText().trim();
            //sprawdzenie czy pola nie są puste
            if (!loginText.equals("") && !loginPassword.equals("")) {
                this.autSignInWarning.setText("");
                try {
                    //wywołujemy metode która sprawdza hasło u nazwę użytkownika w bazie
                    // w razie sukcesu przeprowadza nas do menu głownego,
                    // w razie niepowodzenia zwraca komunikat o tym
                    this.loginUser(loginText, loginPassword);
                } catch (SQLException throwables) { //obsługa wyjatków
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                this.autSignInWarning.setText("Login or password is empty"); //zwrócenie komunikatu
            }

        });
        //przy kliknięciu na utworzenie konta
        this.loginSignUpButton.setOnAction((event) -> {
            //przechodzimy do nowego okna
            this.openNewScene("signupstage.fxml");
        });

    }

    //metode która sprawdza hasło u nazwę użytkownika w bazue
    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        //klasa w której zostały zdefiniowane metody obsługiwające zapytania do bazy danych
        DatabaseHandler dbHandler = new DatabaseHandler();
        //klasa odwzorowująca użytkownika(klienta)
        User user = new User();
        //przepisywanie pól z danymi wejściowymi do zmiennych stworzonego obiekta który odwzorowuje użytkownika(klienta
        user.setUserName(loginText);
        user.setPassword(loginPassword);

        /*
        przepisujemy do obiektu ResultSet dane które zwraca nam metoda getUser w razie znalezienia użytkownia z
        odpowiednią nazwą i hasłem
        (wyszukujemy użytkownia z daną nazwą i hasłem jeżeli takowy istnieje)
         */
        ResultSet result = dbHandler.getUser(user);
        int counter = 0;

        try {
            while(result.next()) {
                //w razie znalezienia takiego użytkownika
                //inkrementujemy zmienną
                ++counter;
            }
        } catch (SQLException var8) { //obsługa wyjątku
            var8.printStackTrace();
        }
        //jeśli znależliśmy 1 lub więcej użytkowników z podaną nazwą
        if (counter >= 1) {
            /*
            tworzymy obiekt klasy singleton która da możliwość
            przesyłać dane klienta innym klasom w ramach jego sesji
             */
            UserSession holder = UserSession.getInstance();
            holder.setUser(user);  //przepisanie obiektu klasy użytkownika(klienta) do klasy singleton
            username = loginText;
            openNewScene("menustage.fxml");
        } else {
            //jeśli nie znależliśmy użytkowników z podaną nazwą wypisujemy komunikat
            this.autSignInWarning.setText("Wrong login or password");
        }
    }

    //metoda służąca do przejscia do nowego okna
    public void openNewScene(String window) {
        this.loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        Parent root = (Parent)loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
