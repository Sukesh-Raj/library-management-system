import java.sql.*;
import java.util.Scanner;

public class BookService {

    public void addBook(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter title: ");
            String title = sc.nextLine();

            System.out.print("Enter author: ");
            String author = sc.nextLine();

            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());

            String sql = "INSERT INTO books(title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, quantity);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Book added successfully." : "Failed to add book.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewBooks() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM books";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                        rs.getInt("book_id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author") + " | Qty: " +
                                rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateBook(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter book ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter new title: ");
            String title = sc.nextLine();

            System.out.print("Enter new author: ");
            String author = sc.nextLine();

            System.out.print("Enter new quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());

            String sql = "UPDATE books SET title=?, author=?, quantity=? WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, quantity);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Book updated successfully." : "Book not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeBook(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter book ID to remove: ");
            int id = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM books WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Book removed successfully." : "Book not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchByTitle(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter title keyword: ");
            String keyword = sc.nextLine();

            String sql = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getInt("book_id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author") + " | Qty: " +
                                rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchByAuthor(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter author keyword: ");
            String keyword = sc.nextLine();

            String sql = "SELECT * FROM books WHERE author LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getInt("book_id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author") + " | Qty: " +
                                rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}