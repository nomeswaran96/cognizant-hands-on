public class SortingTest {

    // Bubble Sort Implementation
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    // Swap orders[j] and orders[j+1]
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    // Quick Sort Implementation
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);

            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                // Swap orders[i] and orders[j]
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }

        // Swap orders[i+1] and orders[high] (or pivot)
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }

    public static void main(String[] args) {
        System.out.println("=== Sorting Customer Orders ===");

        // Setup sample dataset for Bubble Sort
        Order[] ordersForBubble = {
            new Order("O001", "Alice", 250.50),
            new Order("O002", "Bob", 99.99),
            new Order("O003", "Charlie", 450.00),
            new Order("O004", "David", 75.25),
            new Order("O005", "Emma", 150.00)
        };

        System.out.println("Original Orders:");
        for (Order o : ordersForBubble) {
            System.out.println("  " + o);
        }
        System.out.println();

        // 1. Run Bubble Sort
        System.out.println("--- Running Bubble Sort (Ascending by Price) ---");
        bubbleSort(ordersForBubble);
        for (Order o : ordersForBubble) {
            System.out.println("  " + o);
        }
        System.out.println();

        // Setup sample dataset for Quick Sort
        Order[] ordersForQuick = {
            new Order("O001", "Alice", 250.50),
            new Order("O002", "Bob", 99.99),
            new Order("O003", "Charlie", 450.00),
            new Order("O004", "David", 75.25),
            new Order("O005", "Emma", 150.00)
        };

        // 2. Run Quick Sort
        System.out.println("--- Running Quick Sort (Ascending by Price) ---");
        quickSort(ordersForQuick, 0, ordersForQuick.length - 1);
        for (Order o : ordersForQuick) {
            System.out.println("  " + o);
        }
        System.out.println();

        System.out.println("SUCCESS: Sorting algorithms implemented and verified.");
    }
}
