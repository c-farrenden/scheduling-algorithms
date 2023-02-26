// Process.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 1
// Student Number: c3374676

public class Process implements Comparable<Process>{
    // Preset values
    String id;
    int arrive;
    int exec;
	int remainingTime;
    
    // Dynamic values
    int completion;
    int turnaroundTime;
    int waitingTime;
	int elapsedTime;

	int idNumber;

	int completionTime = 0;
	int q = 4;
	// Constructor
    public Process(String id, int arrive, int exec, int remainingTime) {
        this.id = id;
        this.arrive = arrive;
        this.exec = exec;
		this.remainingTime = remainingTime;
    }
	// For priority queue
	@Override
    public int compareTo(Process p) {
        return this.getID().compareTo(p.getID());
    }

    public void setArriveTime(int arrive)
	{
		this.arrive = arrive;
	}

	public int getArriveTime()
	{
		return arrive;
	}

	public void setQuantum(int q)
	{
		this.q = q;
	}

	public int getQuantum()
	{
		return q;
	}

	public void setRemainingTime(int remainingTime)
	{
		this.remainingTime = remainingTime;
	}

	public int getRemainingTime()
	{
		return remainingTime;	
	}

	public String getID()
	{
		return id;
	}

	public int getIDNumber()
	{
		return idNumber;
	}

	public void setIDNumber(int idNumber)
	{
		this.idNumber = idNumber;
	}

    public void setServiceTime(int exec)
	{
		this.exec = exec;
	}

	public int getServiceTime()
	{
		return exec;
	}

    public void setCompletionTime(int completion)
	{
		this.completion = completion;
	}

	public int getCompletionTime()
	{
		return completion;
	}

    public void setTurnaroundTime(int turnaroundTime)
	{
		this.turnaroundTime = turnaroundTime;
	}

	public int getTurnaroundTime()
	{
		return turnaroundTime;
	}

    public void setWaitingTime(int waitingTime)
	{
		this.waitingTime = waitingTime;
	}

	public int getWaitingTime()
	{
		return waitingTime;
	}

	public void setElapsedTime(int elapsedTime)
	{
		this.elapsedTime = elapsedTime;
	}

	public int getElapsedTime()
	{
		return elapsedTime;
	}
}