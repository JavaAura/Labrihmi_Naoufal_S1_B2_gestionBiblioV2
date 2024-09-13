CREATE TABLE document (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255),
    date_publication DATE,
    nombre_de_pages INT,
    emprunter boolean DEFAULT false,
    reservation boolean DEFAULT false
);
CREATE TABLE livre (
    isbn VARCHAR(50)
) INHERITS (document);
CREATE TABLE magazine (
    numero INT
) INHERITS (document);
CREATE TABLE these_universitaire (
    university VARCHAR(255)
) INHERITS (document);
CREATE TABLE journal_scientifique (
    domaine_recherche VARCHAR(255)
) INHERITS (document);
CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    age INT
);
CREATE TABLE etudiant (
    CNE VARCHAR(50)
) INHERITS (utilisateur);
CREATE TABLE professeur (
    CIN VARCHAR(50)
) INHERITS (utilisateur);