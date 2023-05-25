# Book Store Application #

The Book Store Application is a comprehensive platform that enables users to sign in and log in to their accounts, create personalized lists of books, search for books by ID and title, update book information, and delete books from their lists. The application utilizes a robust tech stack, including Java Spring Boot, MySQL, ReactJS, Docker, and Postman. This README provides an overview of the application's features, setup instructions, and key components.

## Features ##

* User Authentication: Users can sign up for an account and log in securely to access personalized features.

* Book Management: Users can create their own lists of books, search for books by ID and title, update book information, and remove books from their lists.
* Responsive User Interface: The application offers a user-friendly and responsive interface built with ReactJS, ensuring a seamless browsing experience on various devices.

## Technologies Used ##

* Java Spring Boot: Provides a powerful backend framework for developing scalable and efficient server-side logic.
* MySQL: Serves as the database management system for storing user information, book details, and lists of books.
* ReactJS: Enables the creation of dynamic and interactive user interfaces, ensuring an intuitive and engaging user experience.
* Docker: Simplifies the deployment process by packaging the application and its dependencies into containers, ensuring consistent deployment across different environments.
* Postman: Facilitates API testing and validation, ensuring reliable and robust communication between frontend and backend components.

## Prerequisites ##

* Java Development Kit (JDK) 8 or higher
* Node.js and npm (Node Package Manager)
* MySQL database
* Docker (optional)
* Intellij IDEA
* VSCode

## Getting Started ##

* Clone the repository: git clone <repository-url>
* Set up the database: Create a MySQL database and update the database configuration in the application properties file.
* Build and run the backend:
* Navigate to the backend directory: cd backend
* Build the application: ./mvnw clean install
* Run the application: ./mvnw spring-boot:run
* Build and run the frontend:
* Navigate to the frontend directory: cd frontend
* Install dependencies: npm install
* Start the application: npm run dev
* Access the application in your browser at http://localhost:5173/
  
## API Documentation ##
For API documentation and testing, you can import the provided Postman collection into Postman. The collection includes sample requests and responses for each API endpoint.

## Deployment with Docker ##
To deploy the application using Docker:

Build the Docker image: docker build -t book-store-app .
Run the Docker container: docker run -p 8080:8080 book-store-app
The application will be accessible at http://localhost:8080.
