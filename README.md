# Library-Management_System
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
         Request Body: Updated book details in JSON format.
         
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
         Get Books by Publisher ID or Publisher Name
      
      10. API: /api/books/by-publisher/{publisherIdOrPublisherName}
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
      Without an publisher, we cannot create a book because each book must have an publisher. A book is authored by an individual, and without an publisher, a book cannot exist.
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



