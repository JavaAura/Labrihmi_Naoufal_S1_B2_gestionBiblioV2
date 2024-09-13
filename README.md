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

### Setup Instructions

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/library-management-system.git
   cd library-management-system
2. **Set Up PostgreSQL Database:**
      .- Ensure PostgreSQL is installed and running on your machine.

      .- Create a new database named `Biblio`. You can do this using a SQL client or through the command line:

       ```sql
       CREATE DATABASE Biblio;
      .- import the provided SQL schema to set up the necessary tables.
3. **Compile the Project:**
   -Ensure you have Java 8 installed on your machine.

   -Navigate to the project's root directory using the terminal or command prompt.

   -Compile the Java source files using the following command. Make sure to replace path/to/postgresql.jar with the actual path to the PostgreSQL JDBC driver JAR file:

           ``` javac -d bin -cp "path/to/postgresql.jar" src/**/*.java ```

4. **Run the Application:**
   Run the Application: Execute the compiled Java application using the following command. Replace path/to/postgresql.jar with the actual path to the PostgreSQL JDBC driver JAR file:
           ```java -cp "bin:path/to/postgresql.jar" presentation.ConsoleUIX ```
 
