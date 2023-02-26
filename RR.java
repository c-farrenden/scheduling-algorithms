// RR.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 1
// Student Number: c3374676

import java.io.*;
import java.util.ArrayList;

import javax.xml.transform.Templates;

import java.util.*;

public class RR {

    int timeQuantum = 4;

    int turnaroundTime = 0;
    int waitingTime = 0;
    int completionTime = 0;
    int time = 0;
    int remainingTime = 0;

    boolean empty = false;
    boolean requeue = false;

    double averageTurnaroundTime = 0;
    double totalTurnaroundTime = 0;
    double averageWaitingTime = 0;
    double totalWaitingTime = 0;

    int processesToBeCompleted = 0;

    String print = "";

    String dispTime = "";
    // Create temp queue
    Queue<Process> tempQueue = new LinkedList<Process>();
    // Algorithm
    void RoundRobin(int disp, Queue<Process> processesRR)
    {
        ArrayList<Process> processesList = new ArrayList<Process>();
        // Add all processes to array list
        while(processesRR.isEmpty() == false)
        {
            processesList.add(processesRR.peek());
            processesRR.remove();
        }
        // Loop through array list and sort in order of arrival times
        for(int i = 0; i < processesList.size(); i++)
        {
            for(int j = 0; j < processesList.size(); j++)
            {
                if(processesList.get(i).getArriveTime() < processesList.get(j).getArriveTime())
                {
                    Collections.swap(processesList, i, j);
                }
            }
        }
        // Add processes back to main queue
        for(int i = 0; i < processesList.size(); i++)
        {
            processesRR.add(processesList.get(i));
        }
        // Clear array list
        processesList.clear();
        // Calculate processes that need to be completed
        for(int i = 0; i < processesRR.size(); i++)
        {
            if(processesRR.peek().getRemainingTime() != 0)
            {
                processesToBeCompleted++;
            }
        }
        // Loop until all processes have been completed
        while(processesToBeCompleted != 0)
        {
            int size = processesRR.size();
            // Loop through processes in queue and add any processes to queue for scheduling (targeted for: if arrival is 0)
            for(int z = 0; z < size; z++)
            {
                if(processesRR.peek().getArriveTime() == time)
                {
                    tempQueue.add(processesRR.peek());
                    processesRR.remove();
                }
            }
            // Check if tempQueue isnt empty and whether process still has to be completed
            if(tempQueue.size() > 0)
            {
                if(tempQueue.peek().getRemainingTime() != 0) 
                {
                    time += disp;
                    dispTime += "T" + time + ": " + tempQueue.peek().getID() + String.format("%n");  
                    int serviceTime = tempQueue.peek().getRemainingTime();
                    // Loop through for time quantum, increment time and set time remaining for process
                    for(int j = 0; j < timeQuantum; j++)
                    {
                        // If service time is not 0, remove by 1
                        if(serviceTime != 0)
                        {
                            serviceTime--;
                            // If process is equal to arrival time, add that process to the queue for scheduling
                            if(processesRR.size() > 0)
                            {
                                if(processesRR.peek().getArriveTime() == time)
                                {
                                    // Add to next spot in queue
                                    tempQueue.add(processesRR.peek());
                                    // Remove from main queue
                                    processesRR.remove();
                                }
                            }
                            time++;
                            tempQueue.peek().setRemainingTime(serviceTime);
                            requeue = true;
                            // If only process with none waiting, run process again by setting j back to 0 (resets loop)
                            if(tempQueue.size() == 1)
                            {
                                j = 0;
                            }
                        }
                        // If process has completed, stop the loop, reduce processes to be completed and set/calculate turnaround and waiting times
                        else
                        {
                            j = timeQuantum;
                            processesToBeCompleted--;
                            tempQueue.peek().setCompletionTime(time);
                            turnaroundTime = tempQueue.peek().getCompletionTime() - tempQueue.peek().getArriveTime();
                            tempQueue.peek().setTurnaroundTime(turnaroundTime);
                            waitingTime = tempQueue.peek().getTurnaroundTime() - tempQueue.peek().getServiceTime();
                            tempQueue.peek().setWaitingTime(waitingTime);
                            print += tempQueue.peek().getID() + String.format("%6c", ' ') + tempQueue.peek().getTurnaroundTime() + String.format("%15c", ' ') + tempQueue.peek().getWaitingTime() + String.format("%n");
                            processesRR.add(tempQueue.peek());
                            tempQueue.remove();
                            requeue = false;
                        }
                    }
                    // If process worked on is not finished, add to the back of the tempQueue
                    if(tempQueue.size() > 0)
                    {
                        if(requeue == true) 
                        {
                            Queue<Process> tempQueue2 = new LinkedList<Process>();
                            tempQueue2.add(tempQueue.peek());
                            tempQueue.remove();
                            tempQueue.add(tempQueue2.peek());
                            tempQueue2.remove();
                        }
                    }   
                }
            }
        }
        // Loop through processes queue and calculate total turnaround and waiting times
        for(int i = 0; i < processesRR.size(); i++)
        {
            processesRR.peek().setRemainingTime(processesRR.peek().getServiceTime());
            tempQueue.add(processesRR.peek());
            processesRR.remove();
            totalTurnaroundTime += tempQueue.peek().getTurnaroundTime();
            totalWaitingTime += tempQueue.peek().getWaitingTime();
            processesRR.add(tempQueue.peek());
            tempQueue.remove();
        }
        // Caclulate average turnaround and waiting times
        averageTurnaroundTime = totalTurnaroundTime / processesRR.size();
        averageWaitingTime = totalWaitingTime / processesRR.size();
        // Loop through processes queue until empty and add to array list for sorting
        while(processesRR.isEmpty() == false)
        {
            processesList.add(processesRR.peek());
            processesRR.remove();
        }
        // Sort processes in order
        Collections.sort(processesList);
        // Loop through array list and add all processes back to original queue
        for(int i = 0; i < processesList.size(); i++)
        {
            processesRR.add(processesList.get(i));
        }
        // Loop through processes and print out turnaround and waiting times
        System.out.println("RR:");
        System.out.println(dispTime);
        System.out.println("Process " + "Turnaround Time " + "Waiting Time");
        while(processesRR.isEmpty() == false)
        {
            System.out.println(String.format("%-7s %-15d %-2d", processesRR.peek().getID(), processesRR.peek().getTurnaroundTime(), processesRR.peek().getWaitingTime()));
            processesRR.remove();
        }
        System.out.println("");
    }
    // Function for setting summary results
    void summary(ArrayList<String> summary)
    {
        summary.add(String.format("%-15s %-25.2f %-2.2f", "RR", averageTurnaroundTime, averageWaitingTime));
    }
}