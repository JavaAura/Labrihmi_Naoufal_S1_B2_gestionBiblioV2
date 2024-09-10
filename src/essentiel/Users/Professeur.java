package essentiel.Users;

public class Professeur extends Utilisateur {
    private String cin;

    public Professeur(String id, String name, String email, int age, String cin) {
        super(id, name, email, age);
        this.cin = cin;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    @Override
    public String toString() {
        return String.format("%s, CIN: %s", super.toString(), cin);
    }

    @Override
    public String getUserType() {
        return "Professeur";
    }
}
