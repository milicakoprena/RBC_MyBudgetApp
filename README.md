# MyBudget App

## Introduction

MyBudget is a budgeting application designed to help users manage their finances effectively. It offers the following features:

- **Track Income and Expenses:** Keep an eye on your financial activities across multiple accounts.
- **Multi-Currency Support:** Add accounts in different currencies and manage transactions effortlessly.
- **Flexible Currency Options:** Choose a default currency from a range of available currencies.
- **Add Transactions:** Easily record both expenses and deposits, providing a clear overview of your financial situation.


# Installation
To set up and run the MyBudget application, follow these steps:

### Prerequisites

1. **Java Development Kit (JDK):** Ensure you have JDK 11 or higher installed for running the Spring Boot application.
   
2. **Node.js and npm:** Ensure you have Node.js (version 18.14.2 or higher) and npm (version 6.14.17 or higher) installed for managing Angular dependencies and running the Angular application.

3. **Angular CLI:** Ensure you have Angular CLI (version 17.3.3 or higher) installed for building and serving the Angular application.

### Clone the Repository
- First, clone the repository to your local machine using Git. Open your command line interface and run:
     ```bash
     git clone <repo-url>
     ```
   - Navigate to the project directory:
     ```bash
     cd <repo-directory>
     ```

## Spring Boot Setup

 1. **Build and Run the Application:**
   - **Using Maven:**
     - You can build and run the Spring Boot application with the following command:
       ```bash
       ./mvnw spring-boot:run
       ```
     - This command will download dependencies, compile the project, and start the Spring Boot application.

  

  2. **Access the Application:**
   - By default, the Spring Boot application will run on port 8080. Open your web browser and navigate to `http://localhost:8080` to see the application in action.

## Angular Setup

To set up and run the Angular frontend application, follow these steps:
   1. **Navigate to the project directory:**
      ```bash
        cd <angular-project-directory>
   2. **Install dependencies:**
   - Install the project dependencies listed in package.json:
      ```bash
     npm install
   3. **Run the Application::**
   - Start the Angular development server:
      ```bash
     ng serve
   - By default, the application will be available at http://localhost:4200. Open this URL in your web browser to view the application.


## Dependencies / Libraries

### Spring Boot
- **Spring Boot Starter Data JPA**: Provides support for JPA-based data access.

- **Spring Boot Starter Web**: Provides support for building web applications, including RESTful applications.

- **Spring Boot Starter Web Services**: Provides support for creating SOAP-based web services.

- **MySQL Connector/J**: MySQL database driver for Java applications.

- **Lombok**: Reduces boilerplate code in Java, such as getters, setters, and constructors.

- **Spring Boot Starter Validation**: Provides support for Java Bean validation.

- **Model Mapper**: Library used for object mapping.
  
- **Spring OXM**: Provides support for Object/XML Mapping.
  
- **Spring Boot Starter WebFlux**: Provides support for building reactive web applications.
### Angular
- **Angular Material**: A UI component library for Angular applications.

## Contributing

To contribute, fork the repository, create a branch, make your changes, push them, and submit a pull request with a clear description of your updates.

