// FCFS.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 1
// Student Number: c3374676

import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class FCFS {
    
    int turnaroundTime = 0;
    int waitingTime = 0;
    int completionTime = 0;
    
    int time = 0;

    double averageTurnaroundTime = 0;
    double totalTurnaroundTime = 0;
    double averageWaitingTime = 0;
    double totalWaitingTime = 0;
    
    String dispTime = "";
    // Algorithm
    void FirstComeFirstServe(int disp, ArrayList<Process> processesFCFS)
    {
        // Loop through processesFCFS and check arrival times and swap until processesFCFS are in order
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            for(int j = 0; j < processesFCFS.size(); j++)
            {
                if(processesFCFS.get(i).getArriveTime() < processesFCFS.get(j).getArriveTime())
                {
                    Collections.swap(processesFCFS, i, j);
                }
            }
        }
        // Loop through processesFCFS
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            // Run dispatcher and add to time
            time += disp;
            // If arrival time of process is less than time, run process
            if(processesFCFS.get(i).getArriveTime() < time)
            {
                // Set completiton time to dispatcher time plus service time and elapsed time to service time and loop through to calculate total time run
                dispTime += "T" + time + ": " + processesFCFS.get(i).getID() + String.format("%n"); 
                int elapsedTime = processesFCFS.get(i).getServiceTime();
                completionTime += disp + processesFCFS.get(i).getServiceTime();
                processesFCFS.get(i).setCompletionTime(completionTime);
                for(int j = 0; j < elapsedTime; j++)
                {
                    time++;
                }
            }
        }
        // Calculate turnaround time from completion and arrival time differentials
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            turnaroundTime = processesFCFS.get(i).getCompletionTime() - processesFCFS.get(i).getArriveTime();
            processesFCFS.get(i).setTurnaroundTime(turnaroundTime);
        }
        // Calculate turnaround time from turnaround and service time differentials
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            waitingTime = processesFCFS.get(i).getTurnaroundTime() - processesFCFS.get(i).getServiceTime();
            processesFCFS.get(i).setWaitingTime(waitingTime);
        }
        // Calculate total turnaround and waiting time
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            totalTurnaroundTime += processesFCFS.get(i).getTurnaroundTime();
            totalWaitingTime += processesFCFS.get(i).getWaitingTime();
        }
        // Calculate average from totals and size of processesFCFS
        averageTurnaroundTime = totalTurnaroundTime / processesFCFS.size();
        averageWaitingTime = totalWaitingTime / processesFCFS.size();
        // Sort list in order if needed for output
        Collections.sort(processesFCFS);
        // Print results and loop through processesFCFS to get each process turnaround and waiting times
        System.out.println("FCFS:");
        System.out.println(dispTime);
        System.out.println("Process " + "Turnaround Time " + "Waiting Time");
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            //System.out.println(processesFCFS.get(i).getID() + String.format("%6c", ' ') + processesFCFS.get(i).getTurnaroundTime() + String.format("%14c", ' ') + processesFCFS.get(i).getWaitingTime());
            System.out.println(String.format("%-7s %-15d %-2d", processesFCFS.get(i).getID(), processesFCFS.get(i).getTurnaroundTime(), processesFCFS.get(i).getWaitingTime()));
        }
        System.out.println("");
    }

    void calculateCompletionTimes(int disp, ArrayList<Process> processesFCFS)
    {
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            time += disp;
            if(processesFCFS.get(i).getArriveTime() < time)
            {
                // store this below in a process object and then print later
                //System.out.println("T" + time + ": " + processesFCFS.get(i).getID()); 
                int elapsedTime = processesFCFS.get(i).getServiceTime();
                completionTime += disp + processesFCFS.get(i).getServiceTime();
                processesFCFS.get(i).setCompletionTime(completionTime);
                for(int j = 0; j < elapsedTime; j++)
                {
                    time++;
                }
                System.out.println("Completion time: " + processesFCFS.get(i).getCompletionTime());
                System.out.println("Time: " + time);
            }
        }
    }
    // Function for setting summary results
    void summary(ArrayList<String> summary)
    {
        //summary.add("FCFS" + String.format("%12c", ' ') + String.format("%.2f", averageTurnaroundTime) + String.format("%21c", ' ') + String.format("%.2f", averageWaitingTime));
        summary.add(String.format("%-15s %-25.2f %-2.2f", "FCFS", averageTurnaroundTime, averageWaitingTime));
    }
    
    void calculateTurnaroundTimes(int disp, ArrayList<Process> processesFCFS)
    {
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            turnaroundTime = processesFCFS.get(i).getCompletionTime() - processesFCFS.get(i).getArriveTime();
            processesFCFS.get(i).setTurnaroundTime(turnaroundTime);
            System.out.println("Turnaround time: " + processesFCFS.get(i).getTurnaroundTime());
        }
    }

    void calculateWaitingTimes(int disp, ArrayList<Process> processesFCFS)
    {
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            waitingTime = processesFCFS.get(i).getTurnaroundTime() - processesFCFS.get(i).getServiceTime();
            processesFCFS.get(i).setWaitingTime(waitingTime);
            System.out.println("Waiting Time: " + processesFCFS.get(i).getWaitingTime());
        }

        System.out.println("FCFS:");
        System.out.println("Process " + "Turnaround Time " + "Waiting Time");
        for(int i = 0; i < processesFCFS.size(); i++)
        {
            //System.out.println(processesFCFS.get(i).getID() + String.format("%6c", ' ') + processesFCFS.get(i).getTurnaroundTime() + String.format("%14c", ' ') + processesFCFS.get(i).getWaitingTime());
            System.out.println(String.format("%-7s %-15d %-2d", processesFCFS.get(i).getID(), processesFCFS.get(i).getTurnaroundTime(), processesFCFS.get(i).getWaitingTime()));
        }
    }
}