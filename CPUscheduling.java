import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Common input for both algorithms
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] bt = new int[n];
        System.out.println("Enter burst time for each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + ": ");
            bt[i] = sc.nextInt();
        }

        // SJF Scheduling
        SJF(n, bt.clone());
        
        // RR Scheduling
        System.out.print("\nEnter time quantum for RR: ");
        int tq = sc.nextInt();
        RR(n, bt.clone(), tq);
    }

    public static void SJF(int n, int[] bt) {
        System.out.println("\nSJF Scheduling:");
        int[] wt = new int[n];
        int[] tat = new int[n];
        int[] p = new int[n]; // process IDs
        ArrayList<String> gantt = new ArrayList<>();

        // Initialize process IDs
        for (int i =0 ; i < n; i++) {
            p[i] = i + 1;
        }

        // Sort by burst time (SJF)
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (bt[j] < bt[i]) {
                    // Swap burst times
                    int temp = bt[i];
                    bt[i] = bt[j];
                    bt[j] = temp;

                    // Swap process IDs
                    int tempP = p[i];
                    p[i] = p[j];
                    p[j] = tempP;
                }
            }
        }

        // Calculate waiting and turnaround time
        wt[0] = 0;
        gantt.add("P" + p[0] + " (0-" + bt[0] + ")");
        
        for (int i = 1; i < n; i++) {
            wt[i] = wt[i - 1] + bt[i - 1];
            gantt.add("P" + p[i] + " (" + wt[i] + "-" + (wt[i] + bt[i]) + ")");
        }

        // Calculate averages
        float avg_wt = 0, avg_tat = 0;
        for (int i = 0; i < n; i++) {
            tat[i] = wt[i] + bt[i];
            avg_wt += wt[i];
            avg_tat += tat[i];
        }
        avg_wt /= n;
        avg_tat /= n;

        // Print results
        System.out.println("\nProcess\tBT\tWT\tTAT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + p[i] + "\t" + bt[i] + "\t" + wt[i] + "\t" + tat[i]);
        }

        System.out.println("\nAverage Waiting Time: " + avg_wt);
        System.out.println("Average Turnaround Time: " + avg_tat);
        
        System.out.println("\nGantt Chart:");
        System.out.println(String.join(" -> ", gantt));
    }

    public static void RR(int n, int[] bt, int tq) {
        System.out.println("\nRR Scheduling:");
        int[] wt = new int[n];
        int[] tat = new int[n];
        int[] rt = bt.clone();
        ArrayList<String> gantt = new ArrayList<>();
        int time = 0;
        boolean done;

        do {
            done = true;
            for (int i = 0; i < n; i++) {
                if (rt[i] > 0) {
                    done = false;
                    int execTime = Math.min(rt[i], tq);
                    gantt.add("P" + (i + 1) + " (" + time + "-" + (time + execTime) + ")");
                    time += execTime;
                    rt[i] -= execTime;
                    if (rt[i] == 0) {
                        wt[i] = time - bt[i];
                    }
                }
            }
        } while (!done);

        // Calculate averages
        float avg_wt = 0, avg_tat = 0;
        for (int i = 0; i < n; i++) {
            tat[i] = wt[i] + bt[i];
            avg_wt += wt[i];
            avg_tat += tat[i];
        }
        avg_wt /= n;
        avg_tat /= n;

        // Print results
        System.out.println("\nProcess\tBT\tWT\tTAT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t" + bt[i] + "\t" + wt[i] + "\t" + tat[i]);
        }

        System.out.println("\nAverage Waiting Time: " + avg_wt);
        System.out.println("Average Turnaround Time: " + avg_tat);
        
        System.out.println("\nGantt Chart:");
        System.out.println(String.join(" -> ", gantt));
    }
}

/*
 input in output :  3 processes : 5 3 8      , quantum time 4
 
 Minimal CPU Scheduling Algorithm
1. Input
Take number of processes (n)

Take burst times (BT) for each process

For RR, take time quantum (tq)

2. SJF (Shortest Job First)
Sort processes by burst time (shortest first)

Calculate:

Waiting Time (WT) = WT of previous process + its BT

Turnaround Time (TAT) = WT + BT

Compute averages (Avg WT & Avg TAT)

Gantt Chart: Show execution order

3. RR (Round Robin)
Execute each process in a fixed time slice (tq)

Update remaining time after each execution

Calculate WT when process finishes

TAT = WT + BT

Compute averages (Avg WT & Avg TAT)

Gantt Chart: Show execution order with time slices




 */