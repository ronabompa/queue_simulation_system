package ro.utcn.pt.Assignment_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;


public class Queue implements Runnable { // extends Thread{
	
	private static final Logger LOGGER = Logger.getLogger( Queue.class.getName() );
	private volatile boolean exit = false;

	private BlockingQueue<Client> clientList = new LinkedBlockingDeque<Client>();
	private Integer idQueue;

	/**
	 * Aceasta metoda returneaza cea mai libera coada
	 *
	 * @param listaCozi - lista cozilor la care clienti se pot inscrie
	 * @return coada cea mai libera
	 */
	public static Queue getCoadaLiberisisima(BlockingQueue<Queue> listaCozi) {
		Integer minServiceTime = 99999;
		Queue qLiberisisima = null;

		for (Queue coada : listaCozi) {
			Integer totalServiceTime = 0;

			for (Client client : coada.getClientList()) {
				totalServiceTime += client.getServiceTime();

			}
			if (minServiceTime > totalServiceTime) {
				minServiceTime = totalServiceTime; // memoram min service time din coada cea mai libera
				qLiberisisima = coada;  // memoram coada cea mai libera
			}
		}

		return qLiberisisima;
	}

	//SETTERS & GETTERS
	public Integer getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Integer idQueue) {
		this.idQueue = idQueue;
	}

	public BlockingQueue<Client> getClientList() {
		return clientList;
	}

	// BlockingQueue e thread safety fara sa-si piarda proprietatile componentelor
	// permite operatii de asteptare a cozii
	// ii ca o lista cu niste operatiuni pt threaduri
	public void setClientList(BlockingQueue<Client> clientList) {
		this.clientList = clientList;
	}

	public String printClients() {
		
		String stringClients = " ";

		for (Client client : this.getClientList()) {
			@SuppressWarnings("deprecation")
			Integer minute = client.getArrivalTime().getMinutes();
			@SuppressWarnings("deprecation")
			Integer secunde = client.getArrivalTime().getSeconds();
			stringClients += "-id:" + client.getId().toString() + "||timp:" + minute   + ":" +  secunde + "--" ;
		}
		
		return stringClients;
	}

	public void run() {
		Simulator simulator = App.simulatorManager.getSim();
		System.out.println(Thread.currentThread());
		while (System.currentTimeMillis() < SimulatorManager.timpEnd && !exit)  // verific daca am ajuns la final
		{
			if (!clientList.isEmpty()) {
				Random rn = new Random();// GENERAM SERVICE TIME
				Integer range = simulator.getMaxServiceTime() - simulator.getMinServiceTime();
				Integer varServiceTime = rn.nextInt(range) + simulator.getMinServiceTime();
				try {                //THREADUL DOARME SERVICE TIMEUL
					Thread.sleep((varServiceTime) * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} // cand iese de aici, s-ar putea ca in timpul asta sa se fi schimbat
				if (!exit) {
					Client client = this.getClientList().peek(); // SETAM DATELE DE IESIRE ALE CLIENTULUI
					Date varFinishTime = new Date();
					client.setFinishTime(varFinishTime);
					client.setServiceTime(varServiceTime);
					client.setWaitingTime((Long.valueOf((client.getFinishTime().getTime() - client.getArrivalTime().getTime() - client.getServiceTime() * 1000) / 1000).intValue()));
					this.getClientList().remove(client);                        // IL SCOATEM DIN LISTA
					LOGGER.info("Clientul " + client.getId() + " a plecat de la ghiseul " + client.getFinishTime() + " dupa ce a a asteptat " + client.getWaitingTime() + " si a fost servit in " + client.getServiceTime());
					SimulatorManager simulatorManager = App.simulatorManager;            // AFISAM CONTINUTUL IN COADA DUPA CE UN CLIENT A PLECAT
					simulatorManager.getInterfataGrafica().afiseazaContinutCoada(this);
				}
			}
		}
	}

	public void stop() {
		exit = true;
	}

}
