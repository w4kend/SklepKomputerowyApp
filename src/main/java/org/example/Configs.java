package org.example;
//stałę które  służą do przechowywania danych wejśćiowych dla połączenia z bazą
public class Configs {
    protected final String dbHost = "localhost";
    protected final String dbPort = "5432";
    protected final String dbUser = "postgres";
    protected final String dbPass = "sysdba325";
    protected final String dbName = "postgres";
    protected final String URL  = "jdbc:postgresql://"+dbHost+":"+dbPort+"/"+dbName;
}
