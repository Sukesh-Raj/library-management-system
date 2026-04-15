import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BookService bookService = new BookService();
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();

        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Remove Book");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Search Book by Author");
            System.out.println("7. Register User");
            System.out.println("8. View Users");
            System.out.println("9. Issue Book");
            System.out.println("10. Return Book");
            System.out.println("11. View Transactions");
            System.out.println("12. Exit");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> bookService.addBook(sc);
                case 2 -> bookService.viewBooks();
                case 3 -> bookService.updateBook(sc);
                case 4 -> bookService.removeBook(sc);
                case 5 -> bookService.searchByTitle(sc);
                case 6 -> bookService.searchByAuthor(sc);
                case 7 -> userService.registerUser(sc);
                case 8 -> userService.viewUsers();
                case 9 -> transactionService.issueBook(sc);
                case 10 -> transactionService.returnBook(sc);
                case 11 -> transactionService.viewTransactions();
                case 12 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}