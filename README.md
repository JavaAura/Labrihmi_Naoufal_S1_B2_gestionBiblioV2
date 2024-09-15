# Library Management System

## Project Overview

This project is a library management system designed to manage documents and users. It includes features for document management, user management, borrowing, and reservations. The system uses a PostgreSQL database for data persistence.

### Features

- **Document Management:** Create, read, update, and delete documents.
- **User Management:** Create, read, update, and delete users with different roles.
- **Borrowing Management:** Borrow and return documents.
- **Reservation Management:** Reserve and cancel reservations on documents.

### Requirements

- Java 8
- PostgreSQL database
- PostgreSQL JDBC Driver (included in `lib/postgresql-42.7.4.jar`)

### Setup Instructions

1.  **Clone the Repository:**

    Open a terminal or command prompt and run the following commands:

    ```bash
    git clone https://github.com/JavaAura/Labrihmi_Naoufal_S1_B2_gestionBiblioV2
    cd Labrihmi_Naoufal_S1_B2_gestionBiblioV2
    ```

2.  **Set Up PostgreSQL Database:**
    .- Ensure PostgreSQL is installed and running on your machine.

    .- Create a new database named `Biblio`. You can do this using a SQL client or through the command line:

    ```
    CREATE DATABASE Biblio;
    ```

    .- import the Biblio.sql file to set up the necessary tables.

3.  **Configure Database Credentials:**
    The application uses environment variables to manage database credentials (DB_USERNAME and DB_PASSWORD). Before running the application, ensure that the following environment variables are set in your system:

        -For Linux/macOS: Add the following to your .bashrc, .bash_profile, or .zshrc file and then run source to load the new configuration:
        ```
            export DB_USERNAME='your_postgres_username'
            export DB_PASSWORD='your_postgres_password'
        ```
        -For Windows: Set the environment variables using the command prompt:
        ```
            setx DB_USERNAME "your_postgres_username"
            setx DB_PASSWORD "your_postgres_password"
        ```
        Or use the system's environment variable settings (Control Panel -> System -> Advanced System Settings -> Environment Variables).

4.  **Run the Application:**
    The application is packaged as a JAR file. To run it, use the following command from the project root:
    `java -cp "lib/postgresql-42.7.4.jar:Labrihmi_Naoufal_S1_B2_gestionBiblioV2.jar" Main`
