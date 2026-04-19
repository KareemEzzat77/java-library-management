package com.library;


import java.sql.*;
import java.util.ArrayList;


public class BookManager {

    /*private final ArrayList<Book> books = new ArrayList<>();
    private static final String FILENAME = "books.txt";*/

    /*public void loadFromFile(){
        File file = new File(FILENAME);
        String[] parts;
        int id;
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()){
                parts = scanner.nextLine().split(",");
                 if (parts.length == 3) {
                     try {
                         id = Integer.parseInt(parts[0].trim());
                     } catch (NumberFormatException e) {
                         continue;
                     }
                     String title = parts[1].trim();
                     String author = parts[2].trim();
                     Book newBook = new Book(id, title, author);
                     books.add(newBook);
                 }
            }
        }catch (FileNotFoundException e){
            System.out.println("No saved books file found. Starting with an empty library.");
        }
    }

    public void saveToFile(Book book){
        try {
            FileWriter writer = new FileWriter(FILENAME, true);
            writer.write(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error happened while saving.");
        }
    }
    public void reWriteFile(){
        try {
            FileWriter writer = new FileWriter(FILENAME);
            for (Book book : books){
                writer.write(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error rewriting the file.");
        }
    }*/
    public boolean addBook(int id, String title, String author) {
        title = title.trim();
        author = author.trim();
        if (id <= 0 || title.isEmpty() || author.isEmpty()) {
            return false;
        }
        String query = "insert into books(id, title , author) values(?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
        ){
            stmt.setInt(1, id);
            stmt.setString(2, title);
            stmt.setString(3, author);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.out.println("Failed to add book to database.");
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Book> getAllBooks(){
        ArrayList<Book> books = new ArrayList<>();
        String query = "Select id, title, author from books";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()
        ){
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Book book = new Book(id, title, author);
                books.add(book);
            }

        }catch (SQLException e) {
            System.out.println("Failed to fetch books from database.");
            e.printStackTrace();
        }
        return books;
    }

    public void viewAllBooks(){
        ArrayList<Book> books = getAllBooks();
        if(!books.isEmpty()) {
            for (Book book : books) {
                System.out.println(book);
            }
        }else {
            System.out.println("Our library is empty atm, come back later.");
        }
    }

    public Book searchBookByTitle(String wantedTitle){
        wantedTitle = wantedTitle.trim().toLowerCase();
        if (!wantedTitle.isEmpty()){
            String query = "select id, title, author from books where lower(title) = ?";
            try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
            ){
                stmt.setString(1, wantedTitle);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    return new Book(id, title, author);
                }
            } catch (SQLException e) {
                System.out.println("Failed to search book in database.");
                e.printStackTrace();
            }
        }
        return null;
    }


    public ArrayList<Book> searchBooksByTitle(String wantedTitle){
        ArrayList<Book> books = new ArrayList<>();
        wantedTitle = wantedTitle.trim().toLowerCase();
        if (!wantedTitle.isEmpty()) {
            String query = "select id, title, author from books where lower(title) like ?";
            try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
            ){
                stmt.setString(1, "%" + wantedTitle + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    Book book = new Book(id, title, author);
                    books.add(book);
                }
            } catch (SQLException e) {
                System.out.println("Error searching for a book");
                e.printStackTrace();
            }
        }
        return books;
    }

    public boolean updateBookById(int id, String newTitle, String newAuthor){
        newTitle = newTitle.trim();
        newAuthor = newAuthor.trim();
        if (id <= 0 || newTitle.isEmpty() || newAuthor.isEmpty()){
            return false;
        }
            String query = "update books set title = ? , author = ? where id = ?";
            try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
            ){
                stmt.setInt(3, id);
                stmt.setString(1, newTitle);
                stmt.setString(2, newAuthor);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected == 1;
            } catch (SQLException e) {
                System.out.println("Couldn't update the book");
                e.printStackTrace();
                return false;
            }

    }

    public boolean deleteBookById(int id){
        String query = "delete from books where id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)
        ){
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        } catch (Exception e) {
            System.out.println("Failed to delete book from database.");
            e.printStackTrace();
            return false;
        }
    }


}