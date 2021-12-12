package ro.utcn.pt.Assignment_2;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class SimulatorManager implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(SimulatorManager.class.getName());
	protected static Long timpStart;  // timpul de start al simularii
	protected static Long timpEnd;    // timpul de finish al simularii
	private InterfataGrafica interfataGrafica;
	private Simulator sim;
	private BlockingQueue<Queue> listaCozi;
	private ScheduledThreadPoolExecutor scheduledExecutor;


	//SETTERS & GETTERS
	public InterfataGrafica getInterfataGrafica() {
		return interfataGrafica;
	}

	public void setInterfataGrafica(InterfataGrafica interfataGrafica) {
		this.interfataGrafica = interfataGrafica;
	}

	public BlockingQueue<Queue> getListaCozi() {
		return listaCozi;
	}

	public void setListaCozi(BlockingQueue<Queue> listaCozi) {
		this.listaCozi = listaCozi;
	}

	public Simulator getSim() {
		return sim;
	}

	public void setSim(Simulator sim) {
		this.sim = sim;
	}

	/**
	 * Aceasta metoda adauga cozi in lista de cozi
	 *
	 * @param simulator -
	 */
	protected void initCozi(Simulator simulator) {
		sim = simulator;
		listaCozi = new ArrayBlockingQueue<Queue>(simulator.getNrQueues());
		for (int i = 0; i < simulator.getNrQueues(); i++) {
			Queue queue = new Queue();
			queue.setIdQueue(i + 1);
			listaCozi.add(queue);
		}
	}

	protected void schedule(Simulator simulator) {

		// Le face threadurilor o programre
		scheduledExecutor = new ScheduledThreadPoolExecutor(simulator.getNrQueues());
		sim = simulator;

		// AICI PORNIM UN THREAD DE TIPUL SIMULATOR
		scheduledExecutor.schedule(this, 0, TimeUnit.MILLISECONDS);
		LOGGER.info("Am inceput simularea la ora " + new Date());



	}

	public void run() {
		timpStart = System.currentTimeMillis();  // startul simultarii
		timpEnd = timpStart + sim.getSimulationInterval() * 1000;

		// PORNIM THREADURI PT FIECARE COADA
		for (Queue queue : listaCozi) {
			scheduledExecutor.schedule(queue, 0, TimeUnit.MILLISECONDS);
		}
		int k =0;
		while (System.currentTimeMillis() < timpEnd)  // verific daca am ajuns la final
		{
			k++;
			Client client = sim.generareInfoClientRandom();
			client.setArrivalTime(new Date());  // momentul in care ajunge
			client.setId(k);
			Queue queue = Queue.getCoadaLiberisisima(getListaCozi()); // verificam care e coada cea mai libera
			queue.getClientList().add(client); // adaugam clientul in cea mai libera coada
			LOGGER.info("Clientul " + client.getId() + " a ajuns la coada" + queue.getIdQueue());

			interfataGrafica.afiseazaContinutCoada(queue);
			try {
				Random rn = new Random();
				int range = sim.getMaxArrivingTime() - sim.getMinArrivingTime();
				Thread.sleep((rn.nextInt(range) + sim.getMinArrivingTime()) * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (Queue queue : listaCozi) {
			queue.stop();
		}
		LOGGER.info("Simularea s-a incheiat la ora " + new Date());
	}


}
