import java.util.Scanner;

public class BankersAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of processes (n): ");
        int n = scanner.nextInt();
        System.out.print("Enter number of resources (m): ");
        int m = scanner.nextInt();

        int[][] max = new int[n][m];
        System.out.println("Enter maximum matrix (n*m):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        int[][] allocation = new int[n][m];
        System.out.println("Enter allocation matrix (n*m):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        int[] available = new int[m];
        System.out.println("Enter available vector (m):");
        for (int j = 0; j < m; j++) {
            available[j] = scanner.nextInt();
        }

        int[][] need = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        System.out.println("Need Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }

        boolean[] finished = new boolean[n];
        int[] safeSequence = new int[n];
        int count = 0;
        while (count < n) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (!finished[i]) {
                    boolean canAllocate = true;
                    for (int j = 0; j < m; j++) {
                        if (need[i][j] > available[j]) {
                            canAllocate = false;
                            break;
                        }
                    }
                    if (canAllocate) {
                        System.out.print("Available after P" + i + ": ");
                        for (int j = 0; j < m; j++) {
                            available[j] += allocation[i][j];
                            System.out.print(available[j] + " ");
                        }
                        System.out.println();
                        safeSequence[count++] = i;
                        finished[i] = true;
                        found = true;
                    }
                }
            }
            if (!found)
                break;
        }

        if (count == n) {
            System.out.println("System is in safe state.");
            System.out.print("Safe sequence: ");
            for (int i = 0; i < n; i++) {
                System.out.print("P" + safeSequence[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println("System is not in safe state.");
        }
        scanner.close();
    }

}

/* 
 * INPUT : 
                Enter number of processes (n): 5
                Enter number of resources (m): 3
                Enter maximum matrix (n*m):
                7 5 3 
                3 2 2
                9 0 2
                2 2 2 
                4 3 3 
                Enter allocation matrix (n*m):
                0 1 0
                2 0 0
                3 0 2
                2 1 1
                0 0 2
                Enter available vector (m):
                3 3 2
                
 * ALgorithm :
                1. Initialize:
                Work = Copy of Available resources (Work[m] = Available[m])
                Finish = Array marking unfinished processes (Finish[n] = [false, ..., false])
                Safe Sequence = Empty list to track process execution order

                2. Find a Runnable Process:
                Search for any process i where:
                Need[i] ≤ Work (process can be satisfied with current resources)
                Finish[i] == false (process not yet completed)
                If no such process exists → exit to Step 4

                3. Execute & Update:
                Release resources:
                Work = Work + Allocation[i] (process i finishes, returns its resources)
                Mark as completed:
                Finish[i] = true
                Record execution order:
                Add i to Safe Sequence
                Repeat from Step 2

                4. Safety Check:
                If all Finish[i] == true → System is Safe
                Output Safe Sequence (order of execution)

                Else → System is Unsafe (deadlock possible)

*
  
 */