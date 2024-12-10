# spring-boot-machine-test-nimap
Java Spring Boot project for Category and Product CRUD operations with pagination.

# Inventory Management System

This project is an **Inventory Management System** built using **Spring Boot**. It provides basic CRUD operations for managing categories and products via REST APIs. The project is currently under **development**, and some features like category validation and integration are still in progress.

## Database Configuration

The system uses **MySQL** as the database. Please ensure you have MySQL installed and a database is set up with the following configuration:

- **Database Name**: `inventory_management_nimap`
- **Username**: `root`
- **Password**: `root`
- **Database Type**: MySQL

The project currently includes **category** and **product** APIs that support CRUD operations with **pagination** enabled for better navigation.

## Current Status

- The **Category** and **Product** CRUD operations are functional.
- The **category values** are currently returning `null` in the `/api/products` response as category validation and integration are still in development.
- **Validations** for categories are being implemented.
- The project is actively being worked on, and the source code is committed due to tight timelines.

## API Endpoints

### 1. **Category CRUD Operations**

- **GET all categories**  
  `GET` Request: `http://localhost:8080/api/categories?page=3`  
  *Fetches all categories with pagination (default page size is 2).*

- **Create a new category**  
  `POST` Request: `http://localhost:8080/api/categories`  
  *Request body:*  
  ```json
  {
    "categoryId": 0,
    "categoryName": "Demo Name",
    "categoryDescription": "demo desc"
  }
  ```

- **Get category by Id**  
  `GET` Request: `http://localhost:8080/api/categories/{id}`  
  *Fetches category details by ID.*

- **Update category by Id**  
  `PUT` Request: `http://localhost:8080/api/categories/{id}`  
  *Updates category details by ID.*

- **Delete category by Id**  
  `DELETE` Request: `http://localhost:8080/api/categories/{id}`  
  *Deletes category by ID.*

### 2. **Product CRUD Operations**

- **GET all products**  
  `GET` Request: `http://localhost:8080/api/products?page=2`  
  *Fetches all products with pagination (default page size is 2).*

- **Create a new product**  
  `POST` Request: `http://localhost:8080/api/products`  
  *Request body:*  
  ```json
  {
    "categoryId": 0,
    "productName": "demo name"
  }
  ```

- **Get product by Id**  
  `GET` Request: `http://localhost:8080/api/products/{id}`  
  *Fetches product details by ID.*

- **Update product by Id**  
  `PUT` Request: `http://localhost:8080/api/products/{id}`  
  *Updates product details by ID.*

- **Delete product by Id**  
  `DELETE` Request: `http://localhost:8080/api/products/{id}`  
  *Deletes product by ID.*

## Postman Example Request Format

### 1. **Create a New Category**
- **Request**: `POST http://localhost:8080/api/categories`
  - **Body**:  
  ```json
  {
    "categoryId": 0,
    "categoryName": "Demo Name",
    "categoryDescription": "demo desc"
  }
  ```

### 2. **Create a New Product**
- **Request**: `POST http://localhost:8080/api/products`
  - **Body**:  
  ```json
  {
    "categoryId": 0,
    "productName": "demo name"
  }
  ```

## Pagination Details

- **Category API Pagination**:  
  `GET http://localhost:8080/api/categories?page=3`  
  *Displays 2 records per page for easier navigation through multiple pages.*

- **Product API Pagination**:  
  `GET http://localhost:8080/api/products?page=2`  
  *Displays 2 records per page for easier navigation through multiple pages.*

## How to Run the Project

Follow these steps to set up and run the project:

### 1. Clone the Repository
Clone this repository to your local machine using the following command:

```bash
git clone https://github.com/your-username/inventory-management-system.git
```

### 2. Set Up the MySQL Database
Ensure that you have MySQL running on your local machine and have created a database named `inventory_management_nimap`.

### 3. Update Database Configuration
In the `src/main/resources/application.properties` (or `application.yml`), configure the database settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_management_nimap
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 4. Run the Application
You can run the application using Maven. Open your terminal and navigate to the project directory, then execute:

```bash
./mvnw spring-boot:run
```

Alternatively, if you are using an IDE like **STS** (Spring Tool Suite), you can run the project directly from the IDE.

### 5. Access the APIs
The application will be running on `http://localhost:8080`. You can use **Postman** or **any other API testing tool** to interact with the APIs.

## Acknowledgements

This project is owned by **Shrikant Shinde** and is under development.

## Future Enhancements

- Category and Product **validation** will be added soon.
- **Category integration** in the product API response will be completed once the development phase progresses.
