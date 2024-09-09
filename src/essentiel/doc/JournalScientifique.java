package essentiel.doc;

import java.time.LocalDate;

public class JournalScientifique extends Document {
    private String domaineRecherche;

    public JournalScientifique(String id, String titre, String auteur, LocalDate datePublication, int nombreDePages,
            String domaineRecherche) {
        super(id, titre, auteur, datePublication, nombreDePages);
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
}
