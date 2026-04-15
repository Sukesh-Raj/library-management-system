import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserService {

    public void registerUser(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter email: ");
            String email = sc.nextLine();

            String sql = "INSERT INTO users(name, email) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "User registered successfully." : "Failed to register.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewUsers() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                        rs.getInt("user_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email")
                );
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}