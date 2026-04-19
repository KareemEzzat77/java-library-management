package com.library;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
     public static void main(String[] args) {
        BookManager manager = new BookManager();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {

            System.out.println("""
                    1- View all books.
                    2- Add a new book.
                    3- Search for a book by title.
                    4- Search for all matching titles.
                    5- Delete a book by id.
                    6- Update a book by id.
                    7- Exit library.
                    """);

            choice = readValidInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    manager.viewAllBooks();
                    System.out.println("=============================");
                }
                case 2 -> {
                    int id = readValidInt(scanner, "Enter the book's id:  ");
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter the author's name: ");
                    String author = scanner.nextLine();
                    boolean result = manager.addBook(id, title, author);
                    if (result){
                        System.out.println("The book was added successfully!");
                    }else {
                        System.out.println("Book could not be added. ID may already exist or data is invalid.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter the title of the book: ");
                    String title = scanner.nextLine();
                    Book result = manager.searchBookByTitle(title);
                    if (result != null){
                        System.out.println(result);
                        System.out.println("=============================");
                    }else{
                        System.out.println("The book " + title +" wasn't found");
                        System.out.println("=============================");
                    }
                }
                case 4 -> {
                    System.out.print("Enter the title of the book: ");
                    String title = scanner.nextLine();
                    ArrayList<Book> result = manager.searchBooksByTitle(title);
                    if (!result.isEmpty()){
                        for (Book book : result){
                            System.out.println(book);
                        }
                        System.out.println("=============================");
                    }else{
                        System.out.println("there were no matches with this word.");
                        System.out.println("=============================");
                    }
                }
                case 5 -> {
                    int id = readValidInt(scanner, "Enter the book's id:  ");
                    boolean isDeleted = manager.deleteBookById(id);
                    if (isDeleted){
                        System.out.println("The book was deleted successfully!");
                        System.out.println("=============================");
                    }else{
                        System.out.println("There's no book with this id.");
                        System.out.println("=============================");
                    }
                }
                case 6 -> {
                    int id = readValidInt(scanner, "Enter the book's id: ");
                    System.out.print("Enter the new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter the new author: ");
                    String newAuthor = scanner.nextLine();
                    boolean updated = manager.updateBookById(id, newTitle, newAuthor);
                    if (updated){
                        System.out.println("The book has been updated successfully!");
                        System.out.println("=============================");
                    }else {
                        System.out.println("Unfortunately the book hasn't been updated!");
                        System.out.println("=============================");
                    }
                }
                case 7 -> System.out.println("Goodbye!");

                default -> System.out.println("Invalid choice! pls try again");

            }
        } while (choice != 6);


    }
    public static int readValidInt(Scanner scanner, String msg){
        while(true){
            System.out.print(msg);

            if (scanner.hasNextInt()){
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }else {
                System.out.println("please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
}
