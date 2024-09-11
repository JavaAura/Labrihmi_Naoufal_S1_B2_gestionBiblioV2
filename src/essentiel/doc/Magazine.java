package essentiel.doc;

import java.time.LocalDate;

public class Magazine extends Document {
    private int numero;

    public Magazine(String id, String titre, String auteur, LocalDate datePublication, int nombreDePages,
            boolean emprunter, boolean reservation, int numero) {
        super(id, titre, auteur, datePublication, nombreDePages, emprunter, emprunter);
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

    @Override
    public String toString() {
        return "Magazine [ID: " + this.id + ", Title: " + this.titre + ", Author: " + this.auteur +
                ", Publication Date: " + this.datePublication + ", Pages: " + this.nombreDePages + "]";
    }

}
