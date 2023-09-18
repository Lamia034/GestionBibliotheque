import helper.DatabaseConnection;
import java.sql.Connection;
import java.util.Scanner;
import dto.Book;
import dto.Client;
import dto.Borrow;
import implementations.BookImplementation;
import implementations.ClientImplementation;
import implementations.BorrowImplementation;
import java.util.List;

public class Main {
   static Book.Status status;
   static Book  book = new Book();
    static BookImplementation bookI = new BookImplementation();

    static Client  client = new Client();

    static ClientImplementation clientI = new ClientImplementation();

    static Borrow  borrow = new Borrow();
    static BorrowImplementation borrowI = new BorrowImplementation();

    public static void main(String[] args) {


        DatabaseConnection dbConnection = DatabaseConnection.getInstance();


        Connection conn = dbConnection.getConnection();
        System.out.println(conn);
        Scanner scanner = new Scanner(System.in);

        int choice;
        boolean exit = false;

        do {
            // Display the menu options
            System.out.println("-Library Management System-");
            System.out.println("1. Add Book");//done
            System.out.println("2. Search Book");//done
            System.out.println("3. Modify Book");//done
            System.out.println("4. Delete Book");//done
            System.out.println("5. List ALL Books");//done
            System.out.println("6. List available Books");//done
            System.out.println("7. Borrow Book");//done
            System.out.println("8. Return Book");//done

            //for clients
            System.out.println("9. Add Client");//done
            System.out.println("10. Search Client");//done
            System.out.println("11. Modify Client");//done
            System.out.println("12. Delete Client");//done
            System.out.println("13. Generate a rapport");//done
            System.out.println("14. List borrowed Books");//done
            System.out.println("15. Exit"); // exit
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

         //   BookImplementation bookImplementation = new BookImplementation();
            switch (choice) {
                case 1://Add Book
                    System.out.print("Enter book ISBN: ");
                    book.setISBN(scanner.nextLine());

                    System.out.print("Enter book title: ");
                    book.setTitle(scanner.nextLine());

                    System.out.print("Enter author name: ");
                    book.setAuthor(scanner.nextLine());

                    System.out.println("Choose the book status:");
                    System.out.println("1. AVAILABLE");
                    System.out.println("2. BORROWED");
                    System.out.println("3. LOST");

                    int choiceS = scanner.nextInt();
                    scanner.nextLine();


                    switch (choiceS) {
                        case 1:
                            book.setStatus(Book.Status.AVAILABLE);
                            break;
                        case 2:
                            book.setStatus(Book.Status.BORROWED);
                            break;
                        case 3:
                            book.setStatus(Book.Status.LOST);
                            break;
                        default:
                            System.out.println("Invalid choice. Setting status to AVAILABLE by default.");
                            book.setStatus(Book.Status.AVAILABLE);
                            break;
                    }

                    
                  if (bookI.add(book) != null) {
                        System.out.println("Book added successfully!:" );
                      System.out.println("Title: " + book.getTitle() + ",Author: " + book.getAuthor() + ",ISBN: " + book.getISBN() + " and Status: " + book.getStatus());
                    } else {
                        System.out.println("Failed to add the book.");
                    }

                    break;

                case 2://Search Book
                    System.out.print("Enter book ISBN to search: ");
                    String searchISBN = scanner.nextLine();

                    Book foundBook = bookI.searchByISBN(searchISBN);

                    if (foundBook != null) {
                        System.out.println("Book found:");
                        System.out.println("ISBN: " + foundBook.getISBN());
                        System.out.println("Title: " + foundBook.getTitle());
                        System.out.println("Author: " + foundBook.getAuthor());
                        System.out.println("Status: " + foundBook.getStatus());
                    } else {
                        System.out.println("Book not found with ISBN: " + searchISBN);
                    }
                    break;

                case 3://Modify Book
                    System.out.print("Enter book ISBN to update: ");
                    String updateISBN = scanner.nextLine();

                    // Search for the book by ISBN
                    Book bookToUpdate = bookI.searchByISBN(updateISBN);

                    if (bookToUpdate != null) {
                        System.out.println("Book found:");
                        System.out.println("ISBN: " + bookToUpdate.getISBN());
                        System.out.println("Title: " + bookToUpdate.getTitle());
                        System.out.println("Author: " + bookToUpdate.getAuthor());
                        System.out.println("Status: " + bookToUpdate.getStatus());

                        System.out.println("Enter new book information (or leave blank to keep existing information):");
                        System.out.print("Enter new title: ");
                        String newTitle = scanner.nextLine();
                        if (!newTitle.isEmpty()) {
                            bookToUpdate.setTitle(newTitle);
                        }

                        System.out.print("Enter new author: ");
                        String newAuthor = scanner.nextLine();
                        if (!newAuthor.isEmpty()) {
                            bookToUpdate.setAuthor(newAuthor);
                        }



                        System.out.println("Choose the new book status:");
                        System.out.println("1. AVAILABLE");
                        System.out.println("2. BORROWED");
                        System.out.println("3. LOST");

                        int newStatusChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (newStatusChoice) {
                            case 1:
                                status = Book.Status.AVAILABLE;
                                break;
                            case 2:
                                status = Book.Status.BORROWED;
                                break;
                            case 3:
                                status = Book.Status.LOST;
                                break;
                            default:
                                System.out.println("Invalid choice. Setting status to AVAILABLE by default.");
                                status = Book.Status.AVAILABLE;
                                break;
                        }

                        bookToUpdate.setStatus(status);


                        if (bookI.update(bookToUpdate)  != null) {
                            System.out.println("Book updated successfully! with : Title: " + bookToUpdate.getTitle() + ", Author: " + bookToUpdate.getAuthor() + ", ISBN: " + bookToUpdate.getISBN() + " and Status: " + bookToUpdate.getStatus());
                        } else {
                            System.out.println("Failed to update the book.");
                        }
                    } else {
                        System.out.println("Book not found with ISBN: " + updateISBN);
                    }






                    break;

                case 4: // Delete Book
                    System.out.print("Enter book ISBN to delete: ");
                    String deleteISBN = scanner.nextLine();

                    boolean deleted = bookI.deleteByISBN(deleteISBN);

                    if (deleted) {
                        System.out.println("Book with ISBN " + deleteISBN + " deleted successfully.");
                    } else {
                        System.out.println("Book with ISBN " + deleteISBN + " not found or deletion failed.");
                    }
                    break;

                case 5: // List ALL Books
                    List<Book> allBooks = bookI.getAllBooks();

                    if (allBooks != null && !allBooks.isEmpty()) {
                        System.out.println("All Books:");

                        for (Book book : allBooks) {
                            System.out.println("ISBN: " + book.getISBN());
                            System.out.println("Title: " + book.getTitle());
                            System.out.println("Author: " + book.getAuthor());
                            System.out.println("Status: " + book.getStatus());
                            System.out.println();
                        }
                    } else {
                        System.out.println("No books found.");
                    }
                    break;

                case 6: // List Available Books
                    List<Book> availableBooks = bookI.getAvailableBooks();

                    if (availableBooks != null && !availableBooks.isEmpty()) {
                        System.out.println("available Books:");

                        for (Book book : availableBooks) {
                            System.out.println("ISBN: " + book.getISBN());
                            System.out.println("Title: " + book.getTitle());
                            System.out.println("Author: " + book.getAuthor());
                            System.out.println("Status: " + book.getStatus());
                            System.out.println();
                        }
                    } else {
                        System.out.println("No books found.");
                    }
                    break;

                case 7://Borrow Book
                    System.out.println("Does the client have a member number?");
                    System.out.println("1. YES");
                    System.out.println("2. NO");

                    int answer = scanner.nextInt();
                    scanner.nextLine();
                    boolean borrowed = false;
                    switch (answer) {
                        case 1:
                            System.out.print("Enter the client's member number: ");
                            Integer id = scanner.nextInt();

                            System.out.print("Enter book ISBN to borrow: ");
                            String ISBN = scanner.next();

                            borrowed = borrowI.borrowBook(id, ISBN);

                            if (borrowed) {
                                System.out.println("Book borrowed successfully.");
                            } else {
                                System.out.println("THe book is not available.");


                            }

                            break;
                        case 2:
                            System.out.print("Enter the client's name: ");
                            client.setName(scanner.nextLine());

                            System.out.print("Enter the client's phone: ");
                            client.setPhone(scanner.nextLine());

                            Integer generatedId = clientI.add(client);
                            if (generatedId != null) {
                                System.out.println("Client added successfully with code :" + generatedId);
                                System.out.print("Enter the book's ISBN: ");
                                String bookISBN = scanner.nextLine();

                                borrowed = borrowI.borrowBook(generatedId, bookISBN);
                                if (borrowed) {
                                    System.out.println("Book borrowed successfully.");
                                } else {
                                    System.out.println("THe book is not available.");


                                }
                            }else {
                                System.out.println("Failed to add the client.");
                            }


                            break;

                    }


                    break;
                case 8://Return Book
                    System.out.print("Enter the book's isbn: ");
                    String isbn = scanner.nextLine();

                    boolean returned = borrowI.returnBook(isbn);
                    if(returned){
                        System.out.println("the book is back to you're library!");
                    }else{
                        System.out.println("failed to return!");
                    }

                    break;

                case 9://Add client
                    System.out.print("Enter the client's name: ");
                    client.setName(scanner.nextLine());

                    System.out.print("Enter the client's phone: ");
                    client.setPhone(scanner.nextLine());

                    Integer generatedId = clientI.add(client);
                    if (generatedId != null) {
                        System.out.println("Client added successfully with code :" + generatedId);
                    } else {
                        System.out.println("Failed to add the client.");
                    }

                    break;

                case 10://Search client
                    System.out.print("Enter client num member to search: ");
                    String searchNumStr = scanner.nextLine();

                    try {
                        Integer searchNum = Integer.parseInt(searchNumStr);
                        Client foundClient = clientI.searchByNum(searchNum);

                        if (foundClient != null) {
                            System.out.println("Client found:");
                            System.out.println("Num Member: " + foundClient.getNumMember());
                            System.out.println("Name: " + foundClient.getName());
                            System.out.println("Phone: " + foundClient.getPhone());
                        } else {
                            System.out.println("Client not found with num member: " + searchNum);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer for num member.");
                    }
                    break;

                case 11://update client
                    System.out.print("Enter num memeber to update client informatiosn: ");
                    String updateNumStr = scanner.nextLine();


                    try {
                        Integer updateNum = Integer.parseInt(updateNumStr);
                     //   Client foundClient = clientI.searchByNum(updateNum);

                        Client clientToUpdate = clientI.searchByNum(updateNum);

                        if (clientToUpdate != null) {
                            System.out.println("client found:");
                            System.out.println("Number of membership: " + clientToUpdate.getNumMember());
                            System.out.println("Name: " + clientToUpdate.getName());
                            System.out.println("Phone: " + clientToUpdate.getPhone());

                            System.out.println("Enter new client information (or leave blank to keep existing information):");
                            System.out.print("Enter new name: ");
                            String newName = scanner.nextLine();
                            if (!newName.isEmpty()) {
                                clientToUpdate.setName(newName);
                            }

                            System.out.print("Enter new Phone: ");
                            String newPhone = scanner.nextLine();
                            if (!newPhone.isEmpty()) {
                                clientToUpdate.setPhone(newPhone);
                            }


                            if (clientI.update(clientToUpdate) != null) {
                                System.out.println("Client updated successfully! with name:" + clientToUpdate.getName() + " and phone " + clientToUpdate.getPhone()  );
                            } else {
                                System.out.println("Failed to update the client.");
                            }
                        } else {
                            System.out.println("Client not found with Number: " + updateNum);
                        }


                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer for num member.");
                    }



                    break;

                case 12://Delete client
                    System.out.print("Enter client num to delete: ");
                    String deleteN = scanner.nextLine();
                    try {
                        Integer deleteNum = Integer.parseInt(deleteN);


                        boolean delete = clientI.deleteByN(deleteNum);

                        if (delete) {
                            System.out.println("client with Number " + deleteN + " deleted successfully.");
                        } else {
                            System.out.println("client with Number " + deleteN + " not found or deletion failed.");
                        }
                    }catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid integer for num member.");
                        }
                    break;

                case 13://statistics

                    bookI.updateNotReturnedBooks();

                    int avBooks = bookI.getAvailableBookCount();
                    int brBooks = bookI.getBorrowedBookCount();
                    int lsBooks = bookI.getLostBookCount();
                    int nrBooks = bookI.getNRBookCount();
                    int alBooks = (bookI.getAllBooksCount() - bookI.getLostBookCount()) ;


                    String report = "Library Report:\n" +
                            "All Books: " + alBooks + "\n"+
                            "Available Books: " + avBooks + "\n" +
                            "Borrowed Books: " + brBooks + "\n" +
                            "Lost Books: " + lsBooks + "\n" +
                            "Not returned Books: " + nrBooks;

              System.out.print("______________________________");
                                System.out.println(report);
                                System.out.println("______________________________");

                    break;
                case 14:
                    List<Borrow> borrowedBooks = bookI.getBorrowedBooks();

                    if (borrowedBooks != null && !borrowedBooks.isEmpty()) {
                        System.out.println("Borrowed Books:");

                        for (Borrow borrow : borrowedBooks) {
                            Book book = borrow.getBook();
                            Client client = borrow.getClient();

                            System.out.println("ISBN: " + book.getISBN());
                            System.out.println("Title: " + book.getTitle());
                            System.out.println("Author: " + book.getAuthor());
                            System.out.println("Status: " + book.getStatus());
                            System.out.println("Borrower Name: " + client.getName());
                            System.out.println("Borrower Phone: " + client.getPhone());
                            System.out.println("borrowing date: " + borrow.getDateb());
                            System.out.println("return date: " + borrow.getDater());
                            System.out.println();
                        }
                    } else {
                        System.out.println("No books found.");
                    }
                    break;



                case 15:
                    exit = true;
                    break;

            }

        } while (!exit);


        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


