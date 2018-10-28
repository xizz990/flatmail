public class Ogloszenie {

    private String tytul;
    private String tresc;
    private String link;
    private String dzielnica;
    private String cena;
    private String data;

    public Ogloszenie(String tytul, String tresc, String link, String dzielnica, String cena, String data) {
        this.tytul = tytul;
        this.tresc = tresc;
        this.link = link;
        this.dzielnica = dzielnica;
        this.cena = cena;
        this.data = data;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDzielnica() {
        return dzielnica;
    }

    public void setDzielnica(String dzielnica) {
        this.dzielnica = dzielnica;
    }
}
