package essentiel.Users;

public class Etudiant extends Utilisateur {
    private String cne;

    public Etudiant(String id, String name, String email, int age, String cne) {
        super(id, name, email, age);
        this.cne = cne;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    @Override
    public String toString() {
        return String.format("%s, CNE: %s", super.toString(), cne);
    }

    @Override
    public String getUserType() {
        return "Etudiant";
    }
}
