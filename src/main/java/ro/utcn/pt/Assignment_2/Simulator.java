package ro.utcn.pt.Assignment_2;

import java.util.Random;

import javax.swing.JLabel;

public class Simulator {
	
	private Integer minArrivingTime;
	private Integer maxArrivingTime;
	private Integer minServiceTime;
	private Integer maxServiceTime;
	private Integer nrQueues;
	private Integer simulationInterval;
	
	public Simulator()
	{
		minArrivingTime = 0;
		maxArrivingTime = 0;
		minServiceTime = 0;
		maxServiceTime = 0;
		nrQueues = 0;
		simulationInterval = 0;
		
	}
	
	//SETTERS&GETTERS
	public Integer getMinArrivingTime() {
		return minArrivingTime;
	}
	public void setMinArrivingTime(Integer minArrivingTime) {
		this.minArrivingTime = minArrivingTime;
	}
	public Integer getMaxArrivingTime() {
		return maxArrivingTime;
	}
	public void setMaxArrivingTime(Integer maxArrivingTime) {
		this.maxArrivingTime = maxArrivingTime;
	}
	public Integer getNrQueues() {
		return nrQueues;
	}
	public void setNrQueues(Integer nrQueues) {
		this.nrQueues = nrQueues;
	}
	public Integer getSimulationInterval() {
		return simulationInterval;
	}
	public void setSimulationInterval(Integer simulationInterval) {
		this.simulationInterval = simulationInterval;
	}
	public Integer getMinServiceTime() {
		return minServiceTime;
	}
	public void setMinServiceTime(Integer minServiceTime) {
		this.minServiceTime = minServiceTime;
	}
	public Integer getMaxServiceTime() {
		return maxServiceTime;
	}
	public void setMaxServiceTime(Integer maxServiceTime) {
		this.maxServiceTime = maxServiceTime;
	}

	/**
	 * Acestta metoda generaza id si service time random al clientilor
	 * @return Clientul cu datele generate
	 */
	public Client generareInfoClientRandom()
	{ 
		Client client = new Client();
		Random rn = new Random();
		
		Integer rangeId = 30;
		Integer rangeServiceTime = this.getMaxServiceTime() - this.getMinServiceTime();
		
		client.setServiceTime(rn.nextInt(rangeServiceTime)+this.getMinServiceTime());
		client.setId(rn.nextInt(rangeId) + 1);
	
		return client;
	}


}
