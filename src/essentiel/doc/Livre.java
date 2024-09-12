package essentiel.doc;

import java.time.LocalDate;

public class Livre extends Document {
    private String isbn;

    public Livre(String id, String titre, String auteur, LocalDate datePublication, int nombreDePages,
            boolean emprunter, boolean reservation, String isbn) {
        super(id, titre, auteur, datePublication, nombreDePages, emprunter, reservation);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Livre: " + getTitre() + " by " + getAuteur() + " with ISBN " + isbn);
    }

    @Override
    public String toString() {
        return "Livre [ID: " + this.id + ", Title: " + this.titre + ", Author: " + this.auteur +
                ", Publication Date: " + this.datePublication + ", Pages: " + this.nombreDePages + "]";
    }

}
