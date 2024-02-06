# Library-Management_System
Reference - Click [here](https://github.com/SUDHAKAR445/Assignment_Sudhakar/tree/main/Back%20end/library) to see my commit changes history which was done in my Assignment_Sudhakar repo.
# Problem Statement
    * Book cataloging, borrowing, returns.
    * User roles: Member, Librarian, Admin.
    * SQL for book and member information.

# Understanding
This Library Management System is designed to catalog books, facilitate borrowing, and handle returns. It includes three user roles: Member, Librarian, and Admin.

    User Roles
    Member:
        Can browse the catalog.
        Borrow books.
        Return books.
        
    Librarian:
        All member capabilities.
        Catalog new books.
        Manage book inventory.
        Track borrowed books.
      
    Admin:
        All librarian capabilities.
        Manage member accounts.
        Administer user roles.
# Database Connection
   The Spring Boot configuration establishes a connection to a MySQL database named "library_management_system_db" on the local machine, using the "root" user. **spring.jpa.hibernate.ddl-auto=update** instructs Hibernate to automatically update the database schema based on the entity model during development.
   
# Table used
    * Book
    * Author
    * Publisher
    * Genre
    * Transactions
    * User

# Book 
    Fields:
        bookId, title, genre, isbn, publication_year, created_at, availableQuantity, publisher
     
## Relationships
 
    **Genre Relationship:**
    Many books can belong to one genre. The relationship is defined through the genre attribute, utilizing a "Many-to-One" association. The foreign key "name" in the books table references the primary key in the genres table.
    
    **Author Relationship:**
    Multiple books can share the same author. The author attribute signifies a "Many-to-One" relationship. The foreign key "author_id" in the books table points to the primary key in the authors table.
    
    **Publisher Relationship:**
    Each book is associated with a single publisher. The publisher attribute establishes a "Many-to-One" relationship, with the foreign key "publisher_id" in the books table referencing the primary key in the publishers table.

## Book Endpoints
      1. Get All Books
         API: /api/books/all
         Feature: Retrieve a list of all books in the library.
         
      2. Get Book by ID or ISBN
         API: /api/books/by-id-or-isbn/{bookIdOrIsbn}
         Feature: Retrieve book details by providing either the book ID or ISBN.
         
      3. Create Book
         API: /api/books/create
         Feature: Add a new book to the library.
         Request Body: Book details in JSON format.
      
      4. Update Book by ID or ISBN
         API: /api/books/update/{bookIdOrIsbn}
         Feature: Update existing book details by providing either the book ID or ISBN.
         
      5. Delete Book by ID or ISBN
         API: /api/books/delete/{bookIdOrIsbn}
         Feature: Delete a book from the library by providing either the book ID or ISBN.
         
      6. Get Books by Genre
         API: /api/books/by-genre/{genre}
         Feature: Retrieve books belonging to a specific genre.
         
      &. Get Books by Title
         API: /api/books/by-title/{title}
         Feature: Retrieve books based on a specific title.
         
      9. Get Books by Author ID or Author Name
         API: /api/books/by-author/{authorIdOrAuthorName}
         Feature: Retrieve books by providing either the author ID or author name.
         
      10. Get Books by Publisher ID or Publisher Name
         API: /api/books/by-publisher/{publisherIdOrPublisherName}
         Feature: Retrieve books by providing either the publisher ID or publisher name.
         
      11. Get Books by Publication Year
         API: /api/books/byPublicationYear/{year}
         Feature: Retrieve books published in a specific year.
         
      12. Get Books by Publication Year and Genre
         API: /api/books/byPublicationYearWithGenre/{year}/{genre}
         Feature: Retrieve books published in a specific year and belonging to a specific genre.
         
      13. Get Books by Publication Year Range
         API: /api/books/byPublicationYearRange/{startYear}/{endYear}
         Feature: Retrieve books published within a specified range of years.
         
      14. Get Books by Publication Year, Genre, and Range
         API: /api/books/byPublicationYearAndGenre/{startYear}/{endYear}/{genre}
         Feature: Retrieve books published within a specified range of years and belonging to a specific genre.
         
      15. Calculate Total Book Count
         API: /api/books/totalBookCount
         Feature: Calculate and retrieve the total number of books in the library.
         
      16. Calculate Total Books in Genre
         API: /api/books/totalBookInGenre
         Feature: Calculate and retrieve the total number of books in each genre.
      
      17. Calculate Total Books in Genre Vice
         API: /api/books/totalBookInGenreVice
         Feature: Calculate and retrieve the total number of books in each genre, presented in a map.    
# Author
      Fields:
         authorId, authorName
## Author Condition
    Without an author, we cannot create a book because each book must have an author. A book is authored by an individual, and without an author, a book cannot exist.
## Author Endpoints
      1. Get All Authors
         API: /api/authors/all
         Feature: Retrieve a list of all authors in the library.
         
      2. Get Author by ID
         API: /api/authors/get/{authorId}
         Feature: Retrieve details of a specific author by providing their ID.
         
      3. Create Author
         API: /api/authors/create
         Feature: Add a new author to the library.
         
      4. Update Author by ID
         API: /api/authors/update/{authorId}
         Feature: Update information for a specific author.
         
      5. Delete Author by ID
         API: /api/authors/delete/{authorId}
         Feature: Delete an author from the library.

# Publisher
      Fields:
         publisherId, publisherName
## Publisher Condition
      Without an publisher, we cannot create a book because each book must have an publisher. A book is published by an individual, and without an publisher, a book cannot exist.
## Publisher Endpoints
      1. Get All Publishers
         API: /api/publishers/all
         Feature: Retrieve a list of all publishers in the library.
         
      2. Get Publisher by ID
         API: /api/publishers/get/{publisherId}
         Feature: Retrieve details of a specific publisher by providing their ID.
         
      3. Create Publisher
         API: /api/publishers/create
         Feature: Add a new publisher to the library.
         
      4. Update Publisher by ID
         API: /api/publishers/update/{publisherId}
         Feature: Update information for a specific publisher.
         
      5. Delete Publisher by ID
         API: /api/publishers/delete/{publisherId}
         Feature: Delete an publisher from the library.

# Genre
      Fields:
         genre, rack_number, floor, description
## Genre Condition
      Without an genre, we cannot create a book because each book must belong to any one genre and without an genre, a book cannot exist. 
## Genre Endpoints
      1. Create Genre
         API: /api/genres/create
         Feature: Add a new genre to the library.
         
      2. Update Genre by Name
         API: /api/genres/update/{name}
         Feature: Update existing genre details by providing the genre name.
         
      3. Delete Genre by Name
         API: /api/genres/delete/{name}
         Feature: Delete a genre from the library by providing the genre name.
         
      4. Get All Genres
         API: /api/genres/all
         Feature: Retrieve a list of all genres in the library.
         
      5. Get Genre by Name
         API: /api/genres/get/{name}
         Feature: Retrieve genre details by providing the genre name.
         
      6. Get Genres by Rack Number
         API: /api/genres/by-rack/{rackNumber}
         Feature: Retrieve genres located on a specific rack.
         
      7. Get Genres by Floor
         API: /api/genres/by-floor/{floor}
         Feature: Retrieve genres located on a specific floor.
         
      8. Get Genres by Rack Number and Floor
         API: /api/genres/by-rack-floor/{rackNumber}/{floor}
         Feature: Retrieve genres located on a specific rack and floor.

# Transaction
      In transaction, I handled the book borrowing and book returning logic.
      Fields:
         user, book, borrowDate, returnDate, fine, note, status ('BORROW', 'RETURN')
## Transaction Condition 
      To borrow a book, ensure the user exists, the book is available, and the user hasn't exceeded the transaction limit or borrowed the book before.
      When returning a book, verify the user exists, the book ID is valid, there's an existing "BORROW" transaction, and the user has indeed borrowed the book.
## Transaction Endpoints
      1. Get All Transactions
         API: /api/transactions/all
         Feature: Retrieve a list of all transactions in the library.
      
      2. Get Transactions by Username or Email
         API: /api/transactions/user/{usernameOrEmail}
         Feature: Retrieve transactions associated with a specific user by providing their username or email.
      
      3. Create Transaction
         API: /api/transactions/create
         Feature: Initiate a new transaction, indicating the borrowing of a book.
      
      4. Return Book
         API: /api/transactions/return/{usernameOrEmail}/{bookId}
         Feature: Mark a book as returned in an existing transaction for a specific user.
      
      5. Get Transactions by Status
         API: /api/transactions/status/{status}
         Feature: Retrieve transactions based on their status (e.g., "ISSUED," "RETURNED").
      
      6. Get Transactions by Username or Email with Status
         API: /api/transactions/user/status/{usernameOrEmail}/{status}
         Feature: Retrieve transactions for a specific user with a given status.

# User
      Fields:
         firstname, lastname, username, password, email, Role ('ADMIN','LIBRARIAN','MEMBER')
## User Condition
      Ensure the user (identified by username or email) exists in the system before performing any operations.
      Filter users based on their assigned role to retrieve specific user groups for administrative purposes.
      If the user need to delete, then we need delete the entire reference of the user in the database. Otherwise it throw an exception like user id act as a foreign key
## User Endpoints
      1. Get All Users
         API: /api/users/all
         Feature: Retrieve a list of all users in the system.
      
      2. Get User by Username or Email
         API: /api/users/{usernameOrEmail}
         Feature: Retrieve user details by providing either the username or email.
      
      3. Update User by Username or Email
         API: /api/users/update/{usernameOrEmail}
         Feature: Update existing user details by providing either the username or email.
      
      4. Delete User by Username or Email
         API: /api/users/delete/{usernameOrEmail}
         Feature: Delete a user from the system by providing either the username or email.
      
      5. Get Users by Role
         API: /api/users/by-role/{role}
         Feature: Retrieve users based on their assigned role.
 
## Workflow for Library Management System

      1. Browse Books (Member)
         Member logs in.
         Member browses the catalog of available books.
         Member can filter books by genre, author, publisher, title, publication year, etc.
         Member views book details.
         
      2. Borrow Book (Member)
         Member selects a book to borrow.
         System checks if the book is available.
         Member initiates the borrowing process.
         System records the transaction and updates the book's availability.
         Member receives confirmation.
         
      3. Return Book (Member)
         Member logs in.
         Member views their borrowed books.
         Member selects a book to return.
         Member initiates the return process.
         System records the return transaction and updates the book's availability.
         Member receives confirmation.
         
      4. Catalog New Book (Librarian)
         Librarian logs in.
         Librarian adds a new book to the catalog.
         System validates book details and updates the database.
         
      5. Manage Book Inventory (Librarian)
         Librarian logs in.
         Librarian views and updates book details.
         Librarian can update quantity, edit book information, or remove a book from the catalog.
         
      6. Track Borrowed Books (Librarian)
         Librarian logs in.
         Librarian views a list of borrowed books.
         Librarian can filter and search for specific transactions.
         Librarian manages book transactions.
         
      7. Manage Member Accounts (Admin)
         Admin logs in.
         Admin views a list of all users.
         Admin can add, edit, or delete member accounts.
         
      8. Administer User Roles (Admin)
         Admin logs in.
         Admin views user roles.
         Admin can assign or modify user roles (Member, Librarian, Admin).
         
      9. Reports and Analytics (Librarian/Admin)
         Librarian/Admin can generate reports on total book count, books in each genre, transaction history, etc.
         Librarian/Admin can analyze data to make informed decisions.
         
      10. Exception Handling
         The system handles exceptions, such as invalid user input, insufficient book quantity, unauthorized access, etc.
         Proper error messages are displayed to users.
