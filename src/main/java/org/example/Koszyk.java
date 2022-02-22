package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//klasa odwzorowująca koszyk użytkownika(klienta)
public class Koszyk implements Initializable {

    private int Id_koszyk;
    private int Id_klient;
    private int Id_produkt;
    private int Cena;



    public Koszyk(int Id_koszyk,int Id_klient, int Id_produkt, int Cena) {
        this.Id_koszyk = Id_koszyk;
        this.Id_klient = Id_klient;
        this.Id_produkt = Id_produkt;
        this.Cena = Cena;
    }

    public int getId_koszyk() {
        return Id_koszyk;
    }

    public void setId_koszyk(int id_koszyk) {
        Id_koszyk = id_koszyk;
    }

    public int getId_klient() {
        return Id_klient;
    }

    public void setId_klient(int id_klient) {
        Id_klient = id_klient;
    }

    public int getId_produkt() {
        return Id_produkt;
    }

    public void setId_produkt(int id_produkt) {
        Id_produkt = id_produkt;
    }

    public int getCena() {
        return Cena;
    }

    public void setCena(int cena) {
        Cena = cena;
    }


    public Koszyk() { }

   /* public void setId_koszyk(int id_koszyk) {
        this.Id_koszyk = id_koszyk;
    }

    public int getId_koszyk() {
        return Id_koszyk;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
 */

    ObservableList<Koszyk> listZ = FXCollections.observableArrayList();

    DatabaseHandler dbHandler = new DatabaseHandler();

    @FXML
    private Button PowrotButton;

    @FXML
    private TableView<Koszyk> TabelaZakupow;

    @FXML
    private TableColumn<Koszyk, Integer> col_koszyk_id;

    @FXML
    private TableColumn<Koszyk, Integer> col_produkt_id;

    @FXML
    private TableColumn<Koszyk, Integer> col_cena;

    @FXML
    private Button buyItemsButton;

    @FXML
    private Button deleteItemButton;

    @FXML
    private TextArea Suma;

    @FXML
    private Text valueText;

    @FXML
    private Text warningText;

    MainMenu mainMenu = new MainMenu();

    //metoda inicjalizująca definicje przycisków
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //przypisujemy do listy produkty z koszyka które są w bazie
            listZ = dbHandler.getKoszyk();
            //wyświetlamy sumę należna do zapłaty
            this.Suma.setText(dbHandler.getTotalPrice() +"zł");
            printBalance(); //wywołujemy metode która wyświetla ewentualne środki na koncie

        } catch (SQLException throwables) { throwables.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        //wyładowujemy produkty z listy do tableview
        TabelaZakupow.setItems(listZ);
        //określamy komórki w ramach jednej tabeli
        col_koszyk_id.setCellValueFactory(new PropertyValueFactory<Koszyk, Integer>("Id_koszyk"));
        col_produkt_id.setCellValueFactory(new PropertyValueFactory<Koszyk, Integer>("Id_produkt"));
        col_cena.setCellValueFactory(new PropertyValueFactory<Koszyk, Integer>("Cena"));

        //przy kliknięciu na przycisk zatwierdz
        this.buyItemsButton.setOnAction(actionEvent -> {
            try {
                //sprawdzamy czy użytkownik ma dostarczającą ilość środków na koncie
                if(dbHandler.getTotalPrice() > dbHandler.getBalance()) {
                    this.warningText.setText("Nie wystarczająca ilośc środków");
                }
                else {
                    /*
                    jeśli użytkownik ma wystarczającą ilośc środków to wywółujemy metode która kupuje
                    wszystkie produkty i odejmuje za to pieniędzy
                     */
                    this.warningText.setText("");
                    dbHandler.buyProducts();
                }
            } catch (SQLException throwables) { throwables.printStackTrace(); } //obsługa wyjątków
            catch (ClassNotFoundException e) { e.printStackTrace(); }
        });
        //przy kliknięciu na przycisk usunięcia
        this.deleteItemButton.setOnAction(actionEvent -> {
            //tworzymy obiekt klasy Koszyk w której będzie przechowywać produkt który wybraliśmy
            Koszyk koszyk = TabelaZakupow.getSelectionModel().getSelectedItem();
            DatabaseHandler dbHandler = new DatabaseHandler();
            try {
                //wywołujemy metode która usuwa dany obiekt z produktem
                dbHandler.deleteItem(koszyk);
            } catch (SQLException | ClassNotFoundException throwables) { throwables.printStackTrace(); }
            TabelaZakupow.getItems().remove(koszyk); //usuwanie produktu z table view
        });
        //przy kliknięciu na przycisk powrot
        this.PowrotButton.setOnAction((actionEvent)->{
            //wracamy do głównego menu
            openNewScene("menustage.fxml");
        });
    }

    //metoda wyświetlająca ewentualne środki na koncie
    public void printBalance() throws SQLException, ClassNotFoundException {
        this.valueText.setText("your balance: "+Integer.toString(dbHandler.getBalance())+"zł");
    }

    //metoda służąca do przejscia do nowego okna
    public void openNewScene(String window) {
        this.PowrotButton.getScene().getWindow().hide();
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
        stage.show();
    }

}
