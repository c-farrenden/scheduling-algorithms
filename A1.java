// A1.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 1
// Student Number: c3374676

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import java.util.*;

public class A1 {
    public static void main(String args[]) throws FileNotFoundException {
        // Scanner instance to read in text file data
        Scanner sc = new Scanner(new File(args[0]));
        // DISP
        int disp = 0;
        // ID
        String id = "";
        // Arrive
        int arriveTime = 0;
        // ExecSize
        int serviceTime = 0;
        int remainingTime = 0;
        // DONT NEED THESE?
        int turnaroundTime = 0;
        int waitingTime = 0;
        int completionTime = 0;
        double averageTurnaroundTime = 0;
        double averageWaitingTime = 0;
        // Check to see if entire process has been read and added
        boolean idCheck = false;
        boolean arriveCheck = false;
        boolean serviceCheck = false;
        // Lists for adding processes for each algorithm
        ArrayList<Process> processesFCFS = new ArrayList<Process>();
        Queue<Process> processesRR = new LinkedList<Process>();
        Queue<Process> processesNRR = new LinkedList<Process>();
        //PriorityQueue<Process> processesFB = new PriorityQueue<Process>();
        Queue<Process> processesFB = new LinkedList<Process>();
        ArrayList<String> summary = new ArrayList<String>();
        // Create algorithm objects
        FCFS fcfs = new FCFS();
        RR rr = new RR();
        NRR nrr = new NRR();
        FB fb = new FB();
        // Loop through text file
        while(sc.hasNextLine())
        {
            // Split strings from actual values needing to be read and set
            String textID = sc.nextLine();
            String lines[] = textID.split(" ");
            // Check if line contains the corresponding string to indicate value to be set
            if(textID.contains("DISP"))
            {
                disp = Integer.parseInt(lines[1]);
            }
            else if(textID.contains("ID"))
            {
                id = lines[1];
                idCheck = true;
            }
            else if(textID.contains("Arrive"))
            {
                arriveTime = Integer.parseInt(lines[1]);
                arriveCheck = true;
            }
            else if(textID.contains("ExecSize"))
            {
                serviceTime = Integer.parseInt(lines[1]);
                remainingTime = Integer.parseInt(lines[1]);
                serviceCheck = true;
            }
            // If process contains all of the above, then create a new process object and add to corresponding data structure
            if(idCheck = true && arriveCheck == true && serviceCheck == true)
            {
                Process process = new Process(id, arriveTime, serviceTime, remainingTime);
                processesFCFS.add(process);
                processesRR.add(process);
                processesNRR.add(process);
                processesFB.add(process);
                idCheck = false;
                arriveCheck = false;
                serviceCheck = false;
            }
        }
        // Run algoirthms
        fcfs.FirstComeFirstServe(disp, processesFCFS);
        rr.RoundRobin(disp, processesRR);
        nrr.NarrowRoundRobin(disp, processesNRR);
        fb.FeedbackConstant(disp, processesFB);
        // Summarise each algorithm and their performance
        fcfs.summary(summary);
        rr.summary(summary);
        nrr.summary(summary);
        fb.summary(summary);
        System.out.println("Summary");
        System.out.println("Algorithm" + String.format("%7c", ' ') + "Average Turnaround Time" + String.format("%3c", ' ') + "Average Waiting Time");
        // Loop through summary list and display average times for each algorithm
        for(int i = 0; i < summary.size(); i++)
        {
            System.out.println(summary.get(i));
        }
    }  
}