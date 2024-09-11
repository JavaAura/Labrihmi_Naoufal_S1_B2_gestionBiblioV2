package essentiel.doc;

import java.time.LocalDate;
import essentiel.Empruntable;
import essentiel.Reservable;

public abstract class Document implements Empruntable, Reservable {
    protected String id;
    protected String titre;
    protected String auteur;
    protected LocalDate datePublication;
    protected int nombreDePages;
    protected boolean emprunter;
    protected boolean reservation;

    public Document(String id, String titre, String auteur, LocalDate datePublication, int nombreDePages,
            boolean emprunter, boolean reservation) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = datePublication;
        this.nombreDePages = nombreDePages;
        this.emprunter = emprunter;
        this.reservation = reservation;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public int getNombreDePages() {
        return nombreDePages;
    }

    public void setNombreDePages(int nombreDePages) {
        this.nombreDePages = nombreDePages;
    }

    public boolean isEmprunter() {
        return emprunter;
    }

    public void setEmprunter(boolean emprunter) {
        this.emprunter = emprunter;
    }

    public boolean isReservation() {
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    public abstract void afficherDetails();

    @Override
    public void emprunter() {
        if (!this.emprunter) {
            this.emprunter = true;
            System.out.println("Document " + this.titre + " has been borrowed.");
        } else {
            System.out.println("Document " + this.titre + " is already borrowed.");
        }
    }

    @Override
    public void retourner() {
        if (this.emprunter) {
            this.emprunter = false;
            System.out.println("Document " + this.titre + " has been returned.");
        } else {
            System.out.println("Document " + this.titre + " was not borrowed.");
        }
    }

    @Override
    public void reserver() {
        if (!this.reservation) {
            this.reservation = true;
            System.out.println("Document " + this.titre + " has been reserved.");
        } else {
            System.out.println("Document " + this.titre + " is already reserved.");
        }
    }

    @Override
    public void annulerReservation() {
        if (this.reservation) {
            this.reservation = false;
            System.out.println("Reservation for document " + this.titre + " has been canceled.");
        } else {
            System.out.println("Document " + this.titre + " was not reserved.");
        }
    }
}
