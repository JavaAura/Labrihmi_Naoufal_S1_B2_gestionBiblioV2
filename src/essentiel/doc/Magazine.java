package essentiel.doc;

import java.time.LocalDate;

public class Magazine extends Document {
    private int numero;

    public Magazine(int id, String titre, String auteur, LocalDate datePublication, int nombreDePages, int numero) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Magazine: " + getTitre() + " by " + getAuteur() + ", Issue No. " + numero);
    }
}
