package essentiel.doc;

import java.time.LocalDate;

public class Livre extends Document {
    private String isbn;

    public Livre(int id, String titre, String auteur, LocalDate datePublication, int nombreDePages, String isbn) {
        super(id, titre, auteur, datePublication, nombreDePages);
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
}
