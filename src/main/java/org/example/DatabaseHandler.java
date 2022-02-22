package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


/*
w tej klasie podłaczamy naszą bazę danych
do aplikacji oraz definiujemy różną działalność z nią
*/

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    //metoda opisująca  nawiązywanie połączenia z bazą
    //oraz zwracająca obiekt połaczenia
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {

        /*
        do metody getConection wstawiamy stałę które są zdefiniowane w klasie Configs
        w danych stałych wpisane są dane które służa do połączenia z bazą
         */
        dbConnection = DriverManager.getConnection(URL, dbUser, dbPass); //łączenie
        return dbConnection;
    }


    //metoda zapisująca użytkownika do bazy
    public void signUpUser(User user) throws SQLException, ClassNotFoundException {

        //owijamy zapytanie sql'owe do obiektu String
        String insert = "insert into \"public\".\"Klient\"(\"Id_klient\",\"firstName\",\"surName\",\"userName\",\"password\",\"saldo\") values(?,?,?,?,?,?)";

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            //wstawiamy zamiast symbolów oznaczonych jako ? dane z klasy użytkownika
            prSt.setInt(1, user.getId_user());
            prSt.setString(2, user.getFirstName());
            prSt.setString(3, user.getSurName());
            prSt.setString(4, user.getUserName());
            prSt.setString(5, user.getPassword());
            //standardowo przypisumey dla każdego nowego użytkownika kwotę 3000 zl
            prSt.setInt(6, 3000);

            prSt.executeUpdate();//metoda służąca do wykonywania aktualizaji danych w bazie
        } catch (SQLException e) {  //obsługa wyjątku
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    //metoda zwracająca nowy identyfikator (stary identyfikator + 1)
    public int getNewUser_id() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        Integer id_user = null;

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu wyciągamy ostatnio stworzony identyfikator klienta
        String select = "select max(\"Id_klient\") from \"public\".\"Klient\"";

        //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
        PreparedStatement prSt2 = getDbConnection().prepareStatement(select);

        resSet = prSt2.executeQuery(); //metoda służąca do wykonywania zapytania w bazie
        while(resSet.next()) {
            id_user = resSet.getInt("max") + 1; //przepisujemy stary identyfikator + 1
        }
        return id_user; //zwracamy nowy identyfiaktor
    }

    //metoda zwracająca identyfiaktor użytkownika która wyszukuje ten identyfikator za nazwą użytkownika
    public int getCurrentUser_id(String userName) throws SQLException,ClassNotFoundException {
        ResultSet resultSet = null;
        Integer id_user = null;

        //owijamy zapytanie sql'owe do obiektu String
        String select = "select \"Id_klient\" from \"public\".\"Klient\" where \"userName\" like '"+userName+"'";
        //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
        PreparedStatement prSt2 = getDbConnection().prepareStatement(select);

        resultSet = prSt2.executeQuery();//metoda służąca do wykonywania zapytania w bazie
        if(resultSet.next())
            id_user = resultSet.getInt("Id_klient");

        return id_user; //zwracamy dany identyfikator jako int
    }



    //metoda która służy do poszukiwania użytkowników w bazie, zwraca rekord z bazy
    public ResultSet getUser(User user) {
        ResultSet resSet = null;

        //owijamy zapytanie sql'owe do obiektu String
        String select = "select * from \"public\".\"Klient\" where \"userName\" =? and \"password\" =?";

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            //wstawiamy zamiast symbolów oznaczonych jako ? dane z klasy użytkownika
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();//metoda służąca do wykonywania zapytania w bazie
        } catch (SQLException e) {//obsługa wyjątków
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    //metoda zwracająca liste produktów które są pogrupowane według słówa kluczowego podanego w argumencie
    public ObservableList<Produkt> getAllProducts(String searchString) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        ResultSet resSet = null;

        //tworzymy obiekt listy w której będziemy przechowywać produkty
        ObservableList<Produkt> list = FXCollections.observableArrayList();

        String select = null;

        //w zależności od słowa kluczowego owijamy zapytanie sql'owe
        if(searchString.compareTo("Kategorie") == 0) {
            select = "select * from \"public\".\"Produkt\"";
        }
        else if(searchString.compareTo("Komputery") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Komputery'";
        }
        else if(searchString.compareTo("Laptopy") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Laptopy'";
        }
        else if(searchString.compareTo("Karty graficzne") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Komputery' and \"Typ\" like 'Karty graficzne'";
        }
        else if(searchString.compareTo("Płyty główne") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Komputery' and \"Typ\" like 'Płyty główne'";
        }
        else if(searchString.compareTo("Zasilacze do komputera") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Komputery' and \"Typ\" like 'Zasilacze do komputera'";
        }
        else if(searchString.compareTo("Pamięci RAM") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Komputery' and \"Typ\" like 'Pamięci RAM'";
        }
        else if(searchString.compareTo("Procesory") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Komputery' and \"Typ\" like 'Procesory'";
        }

        else if(searchString.compareTo("Płyty główne lp") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Laptopy' and \"Typ\" like 'Płyty główne lp'";
        }
        else if(searchString.compareTo("Pamięci RAM lp") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Laptopy' and \"Typ\" like 'Pamięci RAM lp'";
        }
        else if(searchString.compareTo("Procesory lp") == 0) {
            select = "select * from \"public\".\"Produkt\" where \"Kategoria\" like 'Laptopy' and \"Typ\" like 'Procesory lp'";
        }

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            PreparedStatement prSt = connection.prepareStatement(select);

            //metoda służąca do wykonywania zapytania w bazie
            resSet = prSt.executeQuery();

            //w razie znalezienia danych rekordów z podanym słowem kluczowym
            while (resSet.next()) {
                //wstawiamy do listy dane kolummn z opisanymi nazwami
                list.add( new Produkt(
                        Integer.parseInt(resSet.getString("Id_produkt")),
                        resSet.getString("Kategoria"),
                        resSet.getString("Nazwa"),
                        resSet.getString("Typ"),
                        Integer.parseInt(resSet.getString("Cena"))
                        )
                );
            }
        } catch (SQLException throwables) { //obsługa wyjątków
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return list;
    }

    //metoda służąca do dodania produktu do koszyka
    public int setKoszyk(Produkt produkt) throws SQLException,ClassNotFoundException {
        ResultSet resSet = null;
        //tworzymy obiekt listy w której będziemy przechowywać pojedyncze koszyki z produktem
        ObservableList<Koszyk> list = FXCollections.observableArrayList();
        //klasa singleton
        UserSession holder = UserSession.getInstance();
        User u = holder.getUser();
        PreparedStatement prSt;
        int id_koszyk = 0;

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu sprawdzamy czy dany produkt już jest w tabeli Koszyk
        String select = "select \"Id_produkt\" from \"public\".\"Koszyk\" where \"Id_produkt\" = "+produkt.getId();
        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
             prSt = dbConnection.prepareStatement(select);
             resSet = prSt.executeQuery();; //metoda służąca do wykonywania zapytania w bazie
        } catch (SQLException throwables) {//obsługa wyjątków
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //jeżeli dany produkt istnieje w koszuku to zwracamy -1
        if(resSet.next())
            return -1;

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu pobieramy ostatnią wartość identyfikatora koszyka
        select = "select max(\"Id_koszyk\") from \"public\".\"Koszyk\"";
        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(select);
            resSet = prSt.executeQuery(); //metoda służąca do wykonywania zapytania w bazie
            //pobieramy nowy identyfikator (stary identyfikator +1)
            if(resSet.next())
                 id_koszyk = resSet.getInt("max") + 1;

        } catch (SQLException throwables) { //obsługa wyjątkoów
            throwables.printStackTrace();
        } catch (NumberFormatException e) { e.printStackTrace(); }
        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu wstawiemy do tabeli Koszyk id produkt oraz id użytkownika
        String insert = "insert into \"public\".\"Koszyk\"(\"Id_koszyk\",\"Id_klient\",\"Id_produkt\",\"Cena\") values(?,?,?,?)";

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(insert);
            //wstawiamy zamiast symbolów oznaczonych jako ? identyfikator koszyka
            prSt.setInt(1,id_koszyk);
            //identyfikator użytkownika
            prSt.setInt(2,getCurrentUser_id(u.getUserName()));
            //identyfikator produktu
            prSt.setInt(3,produkt.getId());
            //cenę produktu
            prSt.setInt(4,produkt.getCena());

            prSt.executeUpdate(); //metoda służąca do wykonywania aktualizaji danych w bazie
            System.out.println("do koszyka dodano produkt: "+
                    "produkt: "+produkt.getNazwa()+"\n"+
                    "cena produktu: "+produkt.getCena()+"\n"
            );
        } catch(SQLException e) { e.printStackTrace(); }

        return 0;
    }

    //metoda zwracająca liste koszyków do których użytkownik dodaje produkty
    public ObservableList<Koszyk> getKoszyk() throws SQLException,ClassNotFoundException {
        //obiekt klasy singleton'owej
        UserSession holder = UserSession.getInstance();
        User u = holder.getUser();
        int id_koszyk = getCurrentUser_id(u.getUserName());
        ResultSet resSet = null;

        //tworzymy obiekt listy w której będziemy przechowywać pojedyncze koszyki z produktem
        ObservableList<Koszyk> list = FXCollections.observableArrayList();

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu pobieramy rekordy produktów z koszyków które należa użytkownikowi w ramach danej sesji
        String select = "select * from public.\"Koszyk\" K, public.\"Produkt\" P where P.\"Id_produkt\" = K.\"Id_produkt\" and \"Id_klient\"=?";

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            //wstawiamy zamiast symbolu oznaczonego jako ? identyfikator danego użytkownika
            prSt.setInt(1,getCurrentUser_id(u.getUserName()));

            resSet = prSt.executeQuery();  //metoda służąca do wykonywania zapytania w bazie

            //jeżeli istnieją produkty które wybraliśmy
            while(resSet.next()) {
                //dodajemy do listy z koszykami
                list.add(new Koszyk(
                                resSet.getInt("Id_koszyk"),
                                resSet.getInt("Id_klient"),
                                resSet.getInt("Id_produkt"),
                                resSet.getInt("Cena")
                        )
                );
            }

        } catch(SQLException e) { e.printStackTrace(); } //obsługa wyjątku

        return list;
    }

    //metoda zwracająca sumę należną do zapłaty dla ewentualnego użytkownika
    public int getTotalPrice() throws SQLException, ClassNotFoundException {
        int totalPrice = 0;
        UserSession holder = UserSession.getInstance();
        User u = holder.getUser();
        ResultSet resSet = null;

        //owijamy zapytanie sql'owe do obiektu String
        String select = "select sum(\"Cena\") from public.\"Koszyk\" where \"Id_klient\" =?";

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            PreparedStatement prSt = dbConnection.prepareStatement(select);

            //wstawiamy zamiast symbolu oznaczonego jako ? identyfikator ewentualnego użytkownika
            prSt.setInt(1, getCurrentUser_id(u.getUserName()));

            resSet = prSt.executeQuery();//metoda służąca do wykonywania zapytania w bazie

            //pobieramy rekord z należna sumą do zapłaty
            if(resSet.next())
                totalPrice = resSet.getInt("sum");

        } catch(SQLException e) { e.printStackTrace(); }


        return totalPrice; //zwracamy sumę
    }

    //metoda służąca do zakupu produktów z koszyka
    public void buyProducts() throws SQLException, ClassNotFoundException {
        int balance = 0;
        int iterator = 0;
        int counter = 0;
        UserSession holder = UserSession.getInstance();
        User u = holder.getUser();
        PreparedStatement prSt;
        ResultSet resSet = null;
        int[] product_id = new int[100];

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu wyciągamy wszystkie produkty z koszyku który należy ewentualnemu użytkownikowi
        String select = "select \"Id_produkt\" from public.\"Koszyk\" where \"Id_klient\" =?";
        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(select);
            //wstawiamy zamiast symbolu oznaczonego jako ? identyfikator ewentualnego użytkownika
            prSt.setInt(1, getCurrentUser_id(u.getUserName()));

            //metoda służąca do wykonywania zapytania w bazie
            resSet = prSt.executeQuery();
            while(resSet.next()) {
                //zlicamy identyfikatory produktów oraz przepisujemy ich do tablicy
                product_id[iterator++] = resSet.getInt("Id_produkt");
                counter++;
            }
        } catch (SQLException | ClassNotFoundException throwables) { throwables.printStackTrace(); } //obsługa wyjątku

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu wyciągamy rachunek konta ewentualnego użytkownika
        select = "select \"saldo\" from public.\"Klient\" where \"Id_klient\" =?";
        try{
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(select);
            //wstawiamy zamiast symbolu oznaczonego jako ? identyfikator ewentualnego użytkownika
            prSt.setInt(1,getCurrentUser_id(u.getUserName()));
            resSet = prSt.executeQuery(); //metoda służąca do wykonywania zapytania w bazie

            //przepisujemy rekord z rachunkiem konta do zmienej
            if(resSet.next())
                balance = resSet.getInt("saldo");

        } catch (SQLException | ClassNotFoundException throwables) { throwables.printStackTrace();}

        //odejmujemy sumę należna do zapłaty od środków użytkownika na koncie
        balance = balance - this.getTotalPrice();

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu aktualizujemy rachunek konta użytkownika
        String update = "update public.\"Klient\" set \"saldo\" = ? where \"Id_klient\" = ?";
        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(update);
            //wstawiamy zamiast symbolów oznaczonych jako ? ewentualną kwote rachunku oraz identyfikator użytkownika
            prSt.setInt(1,balance);
            prSt.setInt(2,getCurrentUser_id(u.getUserName()));
            prSt.executeUpdate(); //metoda służąca do wykonywania aktualizaji danych w bazie
        } catch (SQLException | ClassNotFoundException throwables) { throwables.printStackTrace();}

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu usuwamy rekord produktu z koszyka ponieważ klient już kupił dany produkt
        String delete = "delete from public.\"Koszyk\" where \"Id_klient\" = ?";
        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(delete);
            //wstawiamy zamiast symbolu oznaczonego jako ? identyfikator ewentualnego użytkownika
            prSt.setInt(1, getCurrentUser_id(u.getUserName()));

            prSt.executeUpdate();//metoda służąca do wykonywania aktualizaji danych w bazie
        } catch (SQLException | ClassNotFoundException throwables) { throwables.printStackTrace(); }


        //inicjujemy zmienna String która posłóży bufforem dla owijanego zapytania które usunie kilka rekordów za jeden raz
        String multipleDelete = "";
        /*
        owijamy zapytanie sql'owe do obiektu String,
        w danym zapytaniu usuwamy produkt z całej bazy  ponieważ klient już kupił dany produkt
         */
        delete = "delete from public.\"Produkt\" where ";
        for(iterator = 0; iterator < counter; iterator++) { // od 0 do ilośći produktów -1

            if(iterator != counter -1)
                multipleDelete =  multipleDelete+" \"Id_produkt\" = "+Integer.toString(product_id[iterator])+" or ";
            else
                multipleDelete =  multipleDelete+" \"Id_produkt\" = "+Integer.toString(product_id[iterator]);
        }
        //łączymy dwie połowy jednego zapytania
        delete = delete + multipleDelete;
        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(delete);

            prSt.executeUpdate(); //metoda służąca do wykonywania aktualizaji danych w bazie
        } catch (SQLException throwables) { throwables.printStackTrace(); }

    }

    //metoda służąca do usuwania produktu z koszyka
    public void deleteItem(Koszyk koszyk) throws SQLException, ClassNotFoundException {
        PreparedStatement prSt;
        UserSession holder = UserSession.getInstance();
        User u = holder.getUser();

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu usuwamy rekord produktu z koszyka który należy ewentualnemu użytkownikowi
        String delete = "delete from \"public\".\"Koszyk\" where \"Id_produkt\" = ? and \"Id_klient\" =?";

        System.out.println("will be deleted: "+koszyk.getId_produkt()+" of user: "+getCurrentUser_id(u.getUserName()));

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(delete);
            //wstawiamy zamiast symbolów oznaczonych jako ? identyfikatory koszyka, produktu oraz  użytkownika
            prSt.setInt(1,koszyk.getId_produkt());
            prSt.setInt(2,getCurrentUser_id(u.getUserName()));

            prSt.executeUpdate();  //metoda służąca do wykonywania aktualizaji danych w bazie
        }catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); }
    }

    //metodą zwracająca ewentualne środki na koncie ewentualnego użytkownika
    public int getBalance() throws SQLException, ClassNotFoundException {
        PreparedStatement prSt;
        UserSession holder = UserSession.getInstance();
        User u = holder.getUser();
        ResultSet resSet;
        int balance = 0;

        //owijamy zapytanie sql'owe do obiektu String
        //w danym zapytaniu wyciągamy saldo ewentualnego użytkownika
        String select = "select \"saldo\" from public.\"Klient\" where \"Id_klient\" = "+getCurrentUser_id(u.getUserName());

        try {
            //przepisujemy przygotowane zapytanie do obiektu służącego do komunikacji z bazą
            prSt = dbConnection.prepareStatement(select);
            //metoda służąca do wykonywania zapytania w bazie
            resSet = prSt.executeQuery();
            //pobieramy rachunek konta użytkownika
            if(resSet.next())
                balance = resSet.getInt("saldo");

        } catch (SQLException throwables) { throwables.printStackTrace(); }

        return balance; //zwracamy ewentualne środki
    }

}
