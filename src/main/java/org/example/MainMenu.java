package org.example;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.control.Button;

//klasa służacą do inicjalizacji głównego menu
public class MainMenu implements Initializable {

    public int id_tmp;

    @FXML
    private Button KoszykButton;

    @FXML
    private TableView<Produkt> TabelaProduktow;

    @FXML
    private TableColumn<Produkt, String> col_kategoria;

    @FXML
    private TableColumn<Produkt, String> col_nazwa;

    @FXML
    private TableColumn<Produkt, String> col_typ;

    @FXML
    private TableColumn<Produkt, Integer> col_cena;

    @FXML
    private Button DodajButton;

    @FXML
    private Button submitSearch;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Button logOutButton;

    @FXML
    private Text warningText;

    @FXML
    private Text valueText;



    public String searchString = null;

    ObservableList<Produkt> listP = FXCollections.observableArrayList();

    Connection dbConnection = null;

    DatabaseHandler dbHandler = new DatabaseHandler();

    public int getId_tmp() {
        return id_tmp;
    }

    public void setId_tmp(int id_tmp) {
        this.id_tmp = id_tmp;
    }

    public MainMenu() {}


    @Override
    //metoda inicjalizująca definicje przycisków i tp
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //obiekt klasy singleton'owej
        UserSession holder = UserSession.getInstance();
        User u = holder.getUser();


        try {
            id_tmp = dbHandler.getCurrentUser_id(u.getUserName());
            printBalance();

        } catch (SQLException throwables) { throwables.printStackTrace(); } //obsługa wyjątku
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        //przy kliknięciu na przycisk wylogowania
        this.logOutButton.setOnAction((ActionEvent) -> {
            //powrócic do okna logowania
            this.openNewScene("loginstage.fxml");
        });

        //przy kliknięciu na przycisk koszyka
        this.KoszykButton.setOnAction((actionEvent) -> {
            //otieramy nowe okno z koszykiem
            this.openNewScene("koszyk.fxml");
        });

        //inicjalizujemy drzewo przy pomocy którego będziemy mogli poszukiwać produkt
        TreeItem<String> root = new TreeItem<>("Kategorie");
        //root.setExpanded(true);

        TreeItem<String> Komputer = new TreeItem<>("Komputery");
        TreeItem<String> Laptop = new TreeItem<>("Laptopy");
        root.getChildren().addAll(Komputer,Laptop);


        TreeItem<String> karty_graf = new TreeItem<>("Karty graficzne");
        TreeItem<String> plyty_glow = new TreeItem<>("Płyty główne");
        TreeItem<String> zasilacze_do_k = new TreeItem<>("Zasilacze do komputera");
        TreeItem<String> pamieci_ram = new TreeItem<>("Pamięci RAM");
        TreeItem<String> procesory = new TreeItem<>("Procesory");
        Komputer.getChildren().addAll(karty_graf, plyty_glow, zasilacze_do_k, pamieci_ram, procesory);

        TreeItem<String> plyty_glow__l = new TreeItem<>("Płyty główne lp");
        TreeItem<String> pamieci_ram__l = new TreeItem<>("Pamięci RAM lp");
        TreeItem<String> procesory__l = new TreeItem<>("Procesory lp");
        Laptop.getChildren().addAll(plyty_glow__l, pamieci_ram__l, procesory__l);

        treeView.setRoot(root);

        TabelaProduktow.setItems(listP);

        //definiujemy metodę która pozwoli obsłużyć zdarzenie przycisku myszką
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
            try {
                handleMouseClicked(event);//wywołujemy metodę która przepisuje nazwe wybranego węzła TreeItem
            } catch (NullPointerException e) {
                System.out.println("Należy kliknąć na węzeł a nie na tło");
            }
        };

        //przy kliknięciu na przycisk zatwierdż
        this.submitSearch.setOnAction(actionEvent -> {
            printResultSearch(this.searchString); //wywołujemy metodę która poszukuję produkt według kluczowego słowa
        });

        //dodajemy nowe zdarzenie dla elementu tableview
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

        //przy kliknięciu na dodanie koszyka
        this.DodajButton.setOnAction((actionEvent) -> {
            //pobieramy wybrany produkt z elementu tableview
            Produkt produkt = TabelaProduktow.getSelectionModel().getSelectedItem();

            try {
                //jeżeli wybrany produkt już jest w koszyku to wypisujemy o tym komunikat
                //w innym przypadku wywołujemy metode setKoszyk w której został obiekt produktu jako argument
                if(dbHandler.setKoszyk(produkt) == -1) {
                    this.warningText.setText("Dany produkt już został dodany do koszyka");
                }
                else {
                    this.warningText.setText(" ");
                }

            } catch (SQLException throwables) { throwables.printStackTrace(); }
            catch (ClassNotFoundException e) { e.printStackTrace(); }
        });


    }


    //metoda która przepisuje nazwę wybranego węzła do zmiennej
    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Akceptuj kliknięcia tylko na komórkach węzłów, a nie na pustych przestrzeniach TreeView
        if (node != null ||(node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            String name = (String) ((TreeItem)treeView.getSelectionModel().getSelectedItem()).getValue();
            System.out.println("Node click: " + name);
            this.searchString = name;  //oznaczamy nazwę wezła jako słowo kluczowe
        }
    }

    //metoda która poszukuje produkt według słowa kluczowego podanego jako argument
    public void printResultSearch(String buffer) {
        try {
            //w tym miejscu zostaje wywołana inna metoda która poszukuje produkty w bazie według słowa kluczowego
            listP = dbHandler.getAllProducts(buffer);
        } catch (SQLException throwables) { throwables.printStackTrace(); }  //obsługa wyjątków
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        //wyładowujemy produkty z listy do tableview
        TabelaProduktow.setItems(listP);
        //określamy komórki w ramach jednej tabeli
        col_kategoria.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Kategoria"));
        col_nazwa.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Nazwa"));
        col_typ.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Typ"));
        col_cena.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("Cena"));
    }

    //metoda wyświetlająća ewentualne środki na koncie
    public void printBalance() throws SQLException, ClassNotFoundException {
        this.valueText.setText("your balance: "+Integer.toString(dbHandler.getBalance())+"zł");
    }

    //metoda służąca do przejscia do nowego okna
    public void openNewScene(String window) {
        this.KoszykButton.getScene().getWindow().hide();
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