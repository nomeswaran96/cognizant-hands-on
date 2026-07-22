import java.util.Arrays;
import java.util.Comparator;

public class LibraryTest {

    // Linear Search by Title (Case-Insensitive)
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null; // Not found
    }

    // Binary Search by Title (Case-Insensitive, assumes array is sorted by title)
    public static Book binarySearchByTitle(Book[] books, String title) {
        int low = 0;
        int high = books.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = books[mid].getTitle().compareToIgnoreCase(title);

            if (comparison == 0) {
                return books[mid];
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; // Not found
    }

    public static void main(String[] args) {
        System.out.println("=== Library Book Search ===");

        // Setup sample catalog
        Book[] books = {
            new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald"),
            new Book("B002", "To Kill a Mockingbird", "Harper Lee"),
            new Book("B003", "1984", "George Orwell"),
            new Book("B004", "Brave New World", "Aldous Huxley"),
            new Book("B005", "The Catcher in the Rye", "J.D. Salinger")
        };

        System.out.println("Catalog:");
        for (Book b : books) {
            System.out.println("  " + b);
        }
        System.out.println();

        // 1. Test Linear Search
        System.out.println("--- Running Linear Search for \"1984\" ---");
        Book search1 = linearSearchByTitle(books, "1984");
        System.out.println("Result: " + (search1 != null ? search1 : "Not Found"));
        System.out.println();

        // Sort catalog by Title for Binary Search
        System.out.println("Sorting catalog by Title...");
        Arrays.sort(books, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Sorted Catalog:");
        for (Book b : books) {
            System.out.println("  " + b);
        }
        System.out.println();

        // 2. Test Binary Search
        System.out.println("--- Running Binary Search for \"To Kill a Mockingbird\" ---");
        Book search2 = binarySearchByTitle(books, "To Kill a Mockingbird");
        System.out.println("Result: " + (search2 != null ? search2 : "Not Found"));
        System.out.println();

        System.out.println("--- Running Binary Search for \"Moby Dick\" (Non-existent) ---");
        Book search3 = binarySearchByTitle(books, "Moby Dick");
        System.out.println("Result: " + (search3 != null ? search3 : "Not Found"));
        System.out.println();

        System.out.println("SUCCESS: Book search algorithms completed.");
    }
}
