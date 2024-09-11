package essentiel.doc;

import java.time.LocalDate;

public class JournalScientifique extends Document {
    private String domaineRecherche;

    public JournalScientifique(String id, String titre, String auteur, LocalDate datePublication, int nombreDePages,
            boolean emprunter, boolean reservation, String domaineRecherche) {
        super(id, titre, auteur, datePublication, nombreDePages, emprunter, emprunter);
        this.domaineRecherche = domaineRecherche;
    }

    public String getDomaineRecherche() {
        return domaineRecherche;
    }

    public void setDomaineRecherche(String domaineRecherche) {
        this.domaineRecherche = domaineRecherche;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Journal Scientifique: " + getTitre() + " by " + getAuteur() + " in " + domaineRecherche);
    }

    @Override
    public String toString() {
        return "JournalScientifique [ID: " + this.id + ", Title: " + this.titre + ", Author: " + this.auteur +
                ", Research Domain: " + this.domaineRecherche + ", Publication Date: " + this.datePublication +
                ", Pages: " + this.nombreDePages + "]";
    }

}
