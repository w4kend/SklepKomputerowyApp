package org.example;

//klasa odwzorowująca produkt w sklepie
public class Produkt extends Koszyk {


    private int Id_produkt;
    private String Kategoria;
    private String Nazwa;
    private String Typ;
    private int Cena;


        public  Produkt(int Id_produkt, String Kategoria, String Nazwa, String Typ, int Cena) {     
            this.Id_produkt = Id_produkt;
            this.Kategoria = Kategoria;
            this.Nazwa = Nazwa;
            this.Typ = Typ;
            this.Cena = Cena;
       }

        public Produkt() {}

        public int getId() {
            return Id_produkt;
        }
        public void setId() {
            this.Id_produkt = Id_produkt;
        }

        public String getKategoria() {
            return Kategoria;
        }
        public void setKategoria() {
            this.Kategoria = Kategoria;
        }

        public String getNazwa() {
            return Nazwa;
        }
        public void setNazwa() {
            this.Nazwa = Nazwa;
        }

        public String getTyp() {
            return Typ;
        }
        public void setTyp() {
            this.Typ = Typ;
        }

        public int getCena() {
           return Cena;
        }
        public void setCena() {
              this.Cena = Cena;
        }

}
