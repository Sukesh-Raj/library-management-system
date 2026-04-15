import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TransactionService {

    public void issueBook(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter book ID: ");
            int bookId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(sc.nextLine());

            String checkSql = "SELECT quantity FROM books WHERE book_id=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, bookId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                if (quantity <= 0) {
                    System.out.println("Book not available.");
                    return;
                }
            } else {
                System.out.println("Book not found.");
                return;
            }

            LocalDate issueDate = LocalDate.now();
            LocalDate dueDate = issueDate.plusDays(7);

            String issueSql = "INSERT INTO transactions(book_id, user_id, issue_date, due_date) VALUES (?, ?, ?, ?)";
            PreparedStatement issuePs = con.prepareStatement(issueSql);
            issuePs.setInt(1, bookId);
            issuePs.setInt(2, userId);
            issuePs.setDate(3, Date.valueOf(issueDate));
            issuePs.setDate(4, Date.valueOf(dueDate));

            int rows = issuePs.executeUpdate();

            if (rows > 0) {
                String updateQtySql = "UPDATE books SET quantity = quantity - 1 WHERE book_id=?";
                PreparedStatement updatePs = con.prepareStatement(updateQtySql);
                updatePs.setInt(1, bookId);
                updatePs.executeUpdate();

                System.out.println("Book issued successfully. Due date: " + dueDate);
            } else {
                System.out.println("Failed to issue book.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void returnBook(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter transaction ID: ");
            int transactionId = Integer.parseInt(sc.nextLine());

            String findSql = "SELECT book_id, due_date, return_date FROM transactions WHERE transaction_id=?";
            PreparedStatement findPs = con.prepareStatement(findSql);
            findPs.setInt(1, transactionId);
            ResultSet rs = findPs.executeQuery();

            if (!rs.next()) {
                System.out.println("Transaction not found.");
                return;
            }

            if (rs.getDate("return_date") != null) {
                System.out.println("Book already returned.");
                return;
            }

            int bookId = rs.getInt("book_id");
            LocalDate dueDate = rs.getDate("due_date").toLocalDate();
            LocalDate returnDate = LocalDate.now();

            long lateDays = ChronoUnit.DAYS.between(dueDate, returnDate);
            double fine = lateDays > 0 ? lateDays * 5.0 : 0.0;

            String returnSql = "UPDATE transactions SET return_date=?, fine=? WHERE transaction_id=?";
            PreparedStatement returnPs = con.prepareStatement(returnSql);
            returnPs.setDate(1, Date.valueOf(returnDate));
            returnPs.setDouble(2, fine);
            returnPs.setInt(3, transactionId);

            int rows = returnPs.executeUpdate();

            if (rows > 0) {
                String updateQtySql = "UPDATE books SET quantity = quantity + 1 WHERE book_id=?";
                PreparedStatement updatePs = con.prepareStatement(updateQtySql);
                updatePs.setInt(1, bookId);
                updatePs.executeUpdate();

                System.out.println("Book returned successfully.");
                System.out.println("Fine: Rs. " + fine);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewTransactions() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM transactions";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                        rs.getInt("transaction_id") + " | Book ID: " +
                                rs.getInt("book_id") + " | User ID: " +
                                rs.getInt("user_id") + " | Issue: " +
                                rs.getDate("issue_date") + " | Due: " +
                                rs.getDate("due_date") + " | Return: " +
                                rs.getDate("return_date") + " | Fine: " +
                                rs.getDouble("fine")
                );
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}