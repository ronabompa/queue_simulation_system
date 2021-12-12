package ro.utcn.pt.Assignment_2;

import java.util.Date;

public class Client {

	private Integer id;
	private Date arrivalTime; // when they show up
	private Integer waitingTime;
	private Integer serviceTime; // how much service they need
	private Date finishTime; // depends on the number of queues, number of clients and their service needs
	
	// CONSTRUCTORI
	public Client()
	{
		this.id = 0;
		this.arrivalTime = null;
		this.serviceTime = 0;
		this.finishTime = null;
		this.waitingTime = 0;
	}
	
	public Client(Integer id, Date arrivalTime, Integer serviceTime, Date finishTime, Integer waitingTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.finishTime = finishTime;
		this.waitingTime = waitingTime;
	}

	// SETTERS&GETTERS
	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
	}

}
