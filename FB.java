// FB.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 1
// Student Number: c3374676

import java.io.*;
import java.util.ArrayList;

import javax.xml.transform.Templates;

import java.util.*;

public class FB {

    int timeQuantum = 4;

    int turnaroundTime = 0;
    int waitingTime = 0;
    int completionTime = 0;
    int time = 0;
    int remainingTime = 0;
    int elapsedTime = 0;
    String print = "";

    boolean requeue = false;

    double averageTurnaroundTime = 0;
    double totalTurnaroundTime = 0;
    double averageWaitingTime = 0;
    double totalWaitingTime = 0;

    int processesToBeCompleted = 0;

    int disp = 1;
    String dispTime = "";

    Queue<Process> tempQueue = new LinkedList<Process>();
    PriorityQueue<Process> priorityQueue0 = new PriorityQueue<Process>();
    PriorityQueue<Process> priorityQueue1 = new PriorityQueue<Process>();
    PriorityQueue<Process> priorityQueue2 = new PriorityQueue<Process>();
    PriorityQueue<Process> priorityQueue3 = new PriorityQueue<Process>();
    PriorityQueue<Process> priorityQueue4 = new PriorityQueue<Process>();
    PriorityQueue<Process> priorityQueue5 = new PriorityQueue<Process>();

    ArrayList<Process> processesList = new ArrayList<Process>();
    // Algorithm
    void FeedbackConstant(int disp, Queue<Process> processesFB)
    {
        // Add all processes to array list
        while(processesFB.isEmpty() == false)
        {
            processesList.add(processesFB.peek());
            processesFB.remove();
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
            processesFB.add(processesList.get(i));
        }
        // Clear array list
        processesList.clear();
        // Calculate processes that need to be completed
        for(int i = 0; i < processesFB.size(); i++)
        {
            if(processesFB.peek().getRemainingTime() != 0)
            {
                processesToBeCompleted++;
            }
        }
        // Loop until all processes have been completed
        while(processesToBeCompleted != 0)
        {
            int size = processesFB.size();

            for(int z = 0; z < size; z++)
            {
                // If arrived add to priority queue0 first
                if(processesFB.peek().getArriveTime() == time)
                {
                    priorityQueue0.add(processesFB.peek());
                    processesFB.remove();
                }
            }
            // Check if priority queue 0 is empty
            if(priorityQueue0.size() > 0)
            {
                if(priorityQueue0.peek().getRemainingTime() != 0)
                {
                    time += disp;
                    dispTime += "T" + time + ": " + priorityQueue0.peek().getID() + String.format("%n");  
                    int serviceTime = priorityQueue0.peek().getRemainingTime();
                    // Loop through for time quantum, increment time and set time remaining for process
                    for(int j = 0; j < timeQuantum; j++)
                    {
                        // If service time is not 0, remove by 1
                        if(serviceTime != 0)
                        {
                            serviceTime--;
                            // If process is equal to arrival time, add that process to first priority queue
                            if(processesFB.size() > 0)
                            {
                                if(processesFB.peek().getArriveTime() == time)
                                {
                                    // Add to next spot in queue
                                    priorityQueue0.add(processesFB.peek());
                                    processesFB.remove();
                                }
                            }
                            time++;
                            priorityQueue0.peek().setRemainingTime(serviceTime);
                            requeue = true;
                        }
                        // If process has completed, stop the loop, reduce processes to be completed and set/calculate turnaround and waiting times
                        else
                        {
                            j = timeQuantum;
                            processesToBeCompleted--;
                            priorityQueue0.peek().setCompletionTime(time);
                            turnaroundTime = priorityQueue0.peek().getCompletionTime() - priorityQueue0.peek().getArriveTime();
                            priorityQueue0.peek().setTurnaroundTime(turnaroundTime);
                            waitingTime = priorityQueue0.peek().getTurnaroundTime() - priorityQueue0.peek().getServiceTime();
                            priorityQueue0.peek().setWaitingTime(waitingTime);
                            print += priorityQueue0.peek().getID() + String.format("%6c", ' ') + priorityQueue0.peek().getTurnaroundTime() + String.format("%15c", ' ') + priorityQueue0.peek().getWaitingTime() + String.format("%n");
                            processesFB.add(priorityQueue0.peek());
                            priorityQueue0.remove();
                            requeue = false;
                        }
                    }
                    // If process has not be completed, add to the next lowest priority queue
                    if(priorityQueue0.size() > 0)
                    {
                        if(requeue == true)
                        {
                            priorityQueue1.add(priorityQueue0.peek());
                            priorityQueue0.remove();
                        }
                    }
                }
            }  
            else if(priorityQueue0.size() == 0 && priorityQueue1.size() > 0)
            {
                if(priorityQueue1.peek().getRemainingTime() != 0)
                {
                    time += disp;
                    dispTime += "T" + time + ": " + priorityQueue1.peek().getID() + String.format("%n");  
                    int serviceTime = priorityQueue1.peek().getRemainingTime();
                    for(int j = 0; j < timeQuantum; j++)
                    {
                        // if service time is not 0, remove by 1
                        if(serviceTime != 0)
                        {
                            serviceTime--;
                            if(processesFB.size() > 0)
                            {
                                if(processesFB.peek().getArriveTime() == time)
                                {
                                    priorityQueue0.add(processesFB.peek());
                                    processesFB.remove();
                                }
                            }
                            time++;
                            priorityQueue1.peek().setRemainingTime(serviceTime);
                            requeue = true;
                        }
                        else
                        {
                            j = timeQuantum;
                            processesToBeCompleted--;
                            priorityQueue1.peek().setCompletionTime(time);
                            turnaroundTime = priorityQueue1.peek().getCompletionTime() - priorityQueue1.peek().getArriveTime();
                            priorityQueue1.peek().setTurnaroundTime(turnaroundTime);
                            waitingTime = priorityQueue1.peek().getTurnaroundTime() - priorityQueue1.peek().getServiceTime();
                            priorityQueue1.peek().setWaitingTime(waitingTime);
                            print += priorityQueue1.peek().getID() + String.format("%6c", ' ') + priorityQueue1.peek().getTurnaroundTime() + String.format("%15c", ' ') + priorityQueue1.peek().getWaitingTime() + String.format("%n");
                            processesFB.add(priorityQueue1.peek());
                            priorityQueue1.remove();
                            requeue = false;
                        }
                    }

                    if(requeue == true)
                    {
                        if(priorityQueue1.size() > 0)
                        {
                            priorityQueue2.add(priorityQueue1.peek());
                            priorityQueue1.remove();
                        }
                    }
                }
            }
            // Check to ensure higher priority queues have completed
            else if(priorityQueue0.size() == 0 && priorityQueue1.size() == 0 && priorityQueue2.size() > 0)
            {
                if(priorityQueue2.peek().getRemainingTime() != 0)
                {
                    time += disp;
                    dispTime += "T" + time + ": " + priorityQueue2.peek().getID() + String.format("%n");  
                    int serviceTime = priorityQueue2.peek().getRemainingTime();
                    for(int j = 0; j < timeQuantum; j++)
                    {
                        if(serviceTime != 0)
                        {
                            serviceTime--;
                            if(processesFB.size() > 0)
                            {
                                if(processesFB.peek().getArriveTime() == time)
                                {
                                    priorityQueue0.add(processesFB.peek());
                                    processesFB.remove();
                                }
                        }
                            time++;
                            priorityQueue2.peek().setRemainingTime(serviceTime);
                            requeue = true;
                        }
                        else
                        {
                            j = timeQuantum;
                            processesToBeCompleted--;
                            priorityQueue2.peek().setCompletionTime(time);
                            turnaroundTime = priorityQueue2.peek().getCompletionTime() - priorityQueue2.peek().getArriveTime();
                            priorityQueue2.peek().setTurnaroundTime(turnaroundTime);
                            waitingTime = priorityQueue2.peek().getTurnaroundTime() - priorityQueue2.peek().getServiceTime();
                            priorityQueue2.peek().setWaitingTime(waitingTime);
                            print += priorityQueue2.peek().getID() + String.format("%6c", ' ') + priorityQueue2.peek().getTurnaroundTime() + String.format("%15c", ' ') + priorityQueue2.peek().getWaitingTime() + String.format("%n");
                            processesFB.add(priorityQueue2.peek());
                            priorityQueue2.remove();
                            requeue = false;
                        }
                    }

                    if(requeue == true)
                    {
                        if(priorityQueue2.size() > 0)
                        {
                            priorityQueue3.add(priorityQueue2.peek());
                            priorityQueue2.remove();
                        }
                    }
                }
            }
            // Check to ensure higher priority queues have completed
            else if(priorityQueue0.size() == 0 && priorityQueue1.size() == 0 && priorityQueue2.size() == 0 && priorityQueue3.size() > 0)
            {
                if(priorityQueue3.peek().getRemainingTime() != 0)
                {
                    time += disp;
                    dispTime += "T" + time + ": " + priorityQueue3.peek().getID() + String.format("%n");  
                    int serviceTime = priorityQueue3.peek().getRemainingTime();
                    for(int j = 0; j < timeQuantum; j++)
                    {
                        if(serviceTime != 0)
                        {
                            serviceTime--;
                            if(processesFB.size() > 0)
                            {
                                if(processesFB.peek().getArriveTime() == time)
                                {
                                    priorityQueue0.add(processesFB.peek());
                                    processesFB.remove();
                                }
                            }   
                            time++;
                            priorityQueue3.peek().setRemainingTime(serviceTime);
                            requeue = true;
                        }
                        else
                        {
                            j = timeQuantum;
                            processesToBeCompleted--;
                            priorityQueue3.peek().setCompletionTime(time);
                            turnaroundTime = priorityQueue3.peek().getCompletionTime() - priorityQueue3.peek().getArriveTime();
                            priorityQueue3.peek().setTurnaroundTime(turnaroundTime);
                            waitingTime = priorityQueue3.peek().getTurnaroundTime() - priorityQueue3.peek().getServiceTime();
                            priorityQueue3.peek().setWaitingTime(waitingTime);
                            print += priorityQueue3.peek().getID() + String.format("%6c", ' ') + priorityQueue3.peek().getTurnaroundTime() + String.format("%15c", ' ') + priorityQueue3.peek().getWaitingTime() + String.format("%n");
                            processesFB.add(priorityQueue3.peek());
                            priorityQueue3.remove();
                            requeue = false;
                        }
                    }

                    if(requeue == true) 
                    {
                        if(priorityQueue3.size() > 0)
                        {
                            priorityQueue4.add(priorityQueue3.peek());
                            priorityQueue3.remove();
                        }
                    }
                }
            }
            // Check to ensure higher priority queues have completed
            else if(priorityQueue0.size() == 0 && priorityQueue1.size() == 0 && priorityQueue2.size() == 0 && priorityQueue3.size() == 0 && priorityQueue4.size() > 0)
            {
                if(priorityQueue4.peek().getRemainingTime() != 0)
                {
                    time += disp;
                    dispTime += "T" + time + ": " + priorityQueue4.peek().getID() + String.format("%n");  
                    int serviceTime = priorityQueue4.peek().getRemainingTime();
                    for(int j = 0; j < timeQuantum; j++)
                    {
                        if(serviceTime != 0)
                        {
                            serviceTime--;
                            if(processesFB.size() > 0)
                            {
                                if(processesFB.peek().getArriveTime() == time)
                                {
                                    priorityQueue0.add(processesFB.peek());
                                    processesFB.remove();
                                }
                            }
                            time++;
                            priorityQueue4.peek().setRemainingTime(serviceTime);
                            requeue = true;
                        }
                        else
                        {
                            j = timeQuantum;
                            processesToBeCompleted--;
                            priorityQueue4.peek().setCompletionTime(time);
                            turnaroundTime = priorityQueue4.peek().getCompletionTime() - priorityQueue4.peek().getArriveTime();
                            priorityQueue4.peek().setTurnaroundTime(turnaroundTime);
                            waitingTime = priorityQueue4.peek().getTurnaroundTime() - priorityQueue4.peek().getServiceTime();
                            priorityQueue4.peek().setWaitingTime(waitingTime);
                            print += priorityQueue4.peek().getID() + String.format("%6c", ' ') + priorityQueue4.peek().getTurnaroundTime() + String.format("%15c", ' ') + priorityQueue4.peek().getWaitingTime() + String.format("%n");
                            processesFB.add(priorityQueue4.peek());
                            priorityQueue4.remove();
                            requeue = false;
                        }
                    }

                    if(requeue == true)
                    {
                        if(priorityQueue4.size() > 0)
                        {
                            priorityQueue5.add(priorityQueue4.peek());
                            priorityQueue4.remove();
                        }
                    }
                }
            }
            // Check to ensure higher priority queues have completed
            else if(priorityQueue0.size() == 0 && priorityQueue1.size() == 0 && priorityQueue2.size() == 0 && priorityQueue3.size() == 0 && priorityQueue4.size() == 0 && priorityQueue5.size() > 0)
            {
                if(priorityQueue5.peek().getRemainingTime() != 0)
                {
                    time += disp;
                    dispTime += "T" + time + ": " + priorityQueue5.peek().getID() + String.format("%n");  
                    int serviceTime = priorityQueue5.peek().getRemainingTime();
                    for(int j = 0; j < timeQuantum; j++)
                    {
                        if(serviceTime != 0)
                        {
                            serviceTime--;
                            if(processesFB.size() > 0)
                            {
                                if(processesFB.peek().getArriveTime() == time)
                                {
                                    priorityQueue0.add(processesFB.peek());
                                    processesFB.remove();
                                }
                        }
                            time++;
                            priorityQueue5.peek().setRemainingTime(serviceTime);
                            requeue = true;
                        }
                        else
                        {
                            j = timeQuantum;
                            processesToBeCompleted--;
                            priorityQueue5.peek().setCompletionTime(time);
                            turnaroundTime = priorityQueue5.peek().getCompletionTime() - priorityQueue5.peek().getArriveTime();
                            priorityQueue5.peek().setTurnaroundTime(turnaroundTime);
                            waitingTime = priorityQueue5.peek().getTurnaroundTime() - priorityQueue5.peek().getServiceTime();
                            priorityQueue5.peek().setWaitingTime(waitingTime);
                            print += priorityQueue5.peek().getID() + String.format("%6c", ' ') + priorityQueue5.peek().getTurnaroundTime() + String.format("%15c", ' ') + priorityQueue5.peek().getWaitingTime() + String.format("%n");
                            processesFB.add(priorityQueue5.peek());
                            priorityQueue5.remove();
                            requeue = false;
                        }
                    }
                    // In this case it becomes a process of RR as this is the lowest priority queue, remove and read the process to the back for running again
                    if(requeue == true)
                    {
                        if(priorityQueue5.size() > 0)
                        {
                            tempQueue.add(priorityQueue5.peek());
                            priorityQueue5.remove();
                            priorityQueue5.add(tempQueue.peek());
                            tempQueue.remove();
                        }
                    }
                }
            }
        }
        // Loop through processes queue and calculate total turnaround and waiting times
        for(int i = 0; i < processesFB.size(); i++)
        {
            processesFB.peek().setRemainingTime(processesFB.peek().getServiceTime());
            tempQueue.add(processesFB.peek());
            processesFB.remove();
            totalTurnaroundTime += tempQueue.peek().getTurnaroundTime();
            totalWaitingTime += tempQueue.peek().getWaitingTime();
            processesFB.add(tempQueue.peek());
            tempQueue.remove();
        }
        // Caclulate average turnaround and waiting times
        averageTurnaroundTime = totalTurnaroundTime / processesFB.size();
        averageWaitingTime = totalWaitingTime / processesFB.size();
        // Loop through main processes queue until empty and add to array list for sorting
        while(processesFB.isEmpty() == false)
        {
            processesList.add(processesFB.peek());
            processesFB.remove();
        }
        // Sort processes in order
        Collections.sort(processesList);
        // Loop through array list and add all processes back to original queue
        for(int i = 0; i < processesList.size(); i++)
        {
            processesFB.add(processesList.get(i));
        }
        // Print processes and their turnaround and waiting times
        System.out.println("FB (constant):");
        System.out.println(dispTime);
        System.out.println("Process " + "Turnaround Time " + "Waiting Time");
        while(processesFB.isEmpty() == false)
        {
            System.out.println(String.format("%-7s %-15d %-2d", processesFB.peek().getID(), processesFB.peek().getTurnaroundTime(), processesFB.peek().getWaitingTime()));
            processesFB.remove();
        }
        System.out.println();
    }
    // Function for setting summary results
    void summary(ArrayList<String> summary)
    {
        summary.add(String.format("%-15s %-25.2f %-2.2f", "FB (constant)", averageTurnaroundTime, averageWaitingTime));
    }
}