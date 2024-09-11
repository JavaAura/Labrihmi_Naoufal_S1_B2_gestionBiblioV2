package essentiel.doc;

import java.time.LocalDate;

public class TheseUniversitaire extends Document {
    protected String university;

    public TheseUniversitaire(String id, String titre, String auteur, LocalDate datePublication, int nombreDePages,
            boolean emprunter, boolean reservation, String university) {
        super(id, titre, auteur, datePublication, nombreDePages, emprunter, emprunter);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Thèse Universitaire: " + getTitre() + " by " + getAuteur() + " from " + university);
    }

    @Override
    public String toString() {
        return "TheseUniversitaire [ID: " + this.id + ", Title: " + this.titre + ", Author: " + this.auteur +
                ", University: " + this.university + ", Publication Date: " + this.datePublication + ", Pages: "
                + this.nombreDePages + "]";
    }

}
