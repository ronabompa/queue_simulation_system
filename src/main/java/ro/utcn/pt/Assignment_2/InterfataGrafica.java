package ro.utcn.pt.Assignment_2;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

public class InterfataGrafica extends JFrame implements ActionListener {
	
	private static final Logger LOGGER = Logger.getLogger( InterfataGrafica.class.getName() );
	
	private JLabel minArrivingTimeLabel;
	private JLabel maxArrivingTimeLabel;
	private JLabel minServiceTimeLabel;
	private JLabel maxServiceTimeLabel;
	private JLabel nrQueuesLabel;
	private JLabel simulationIntervalLabel;

	private JTextField minArrivingTimeJTextField;
	private JTextField maxArrivingTimeJTextField;
	private JTextField minServiceTimeJTextField;
	private JTextField maxServiceTimeJTextField;
	private JTextField nrQueuesJTextField;
	private JTextField simulationIntervalJTextField;

	private JButton start;

	private static JPanel panel1;

	private ConcurrentMap<Queue, JTextField> coadaJText = new ConcurrentHashMap<Queue, JTextField>();
	// ConcurrentMap e schimbat
		public InterfataGrafica() {
			super("Queing");
			setLayout(new FlowLayout());

			//PANELS
			panel1 = new JPanel();
			panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));

			//LABELS
			minArrivingTimeLabel = new JLabel("Introduceti min Arrival Time intre clienti");
			maxArrivingTimeLabel = new JLabel("Introduceti max Arrival Time intre clienti");
			minServiceTimeLabel = new JLabel("Introduceti min Service Time");
			maxServiceTimeLabel = new JLabel("Introduceti max Service Time");
			nrQueuesLabel = new JLabel("Introduceti numarul de cozi");
			simulationIntervalLabel = new JLabel("Introduceti timpul de simulare");

			//JTEXFIELD
			final JTextField minArrivingTimeJTextField = new JTextField(30);
			final JTextField maxArrivingTimeJTextField = new JTextField(30);
			final JTextField minServiceTimeJTextField = new JTextField(30);
			final JTextField maxServiceTimeJTextField = new JTextField(30);
			final JTextField nrQueuesJTextField = new JTextField(30);
			final JTextField simulationIntervalJTextField = new JTextField(30);

			//BUTTONS
			final JButton start = new JButton();
			start.setText("START");

			// ADDING PANELS TO FRAME
			add(panel1);
			//add(panel2);

			// ADDING COMPONENTS TO PANELS
			panel1.add(minArrivingTimeLabel);
			panel1.add(minArrivingTimeJTextField);

			panel1.add(maxArrivingTimeLabel);
			panel1.add(maxArrivingTimeJTextField);

			panel1.add(minServiceTimeLabel);
			panel1.add(minServiceTimeJTextField);

			panel1.add(maxServiceTimeLabel);
			panel1.add(maxServiceTimeJTextField);

			panel1.add(nrQueuesLabel);
			panel1.add(nrQueuesJTextField);

			panel1.add(simulationIntervalLabel);
			panel1.add(simulationIntervalJTextField);

			panel1.add(start);

			final InterfataGrafica interfataGrafica = this;

			// ADDING ACTION LISTENER TO START
			start.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Simulator simulator = new Simulator();
					SimulatorManager simulatorManager = App.simulatorManager;

					// SETEM INFORMATIILE DIN UI
					simulator.setMinArrivingTime(Integer.parseInt(minArrivingTimeJTextField.getText()));
					simulator.setMaxArrivingTime(Integer.parseInt(maxArrivingTimeJTextField.getText()));
					simulator.setMinServiceTime(Integer.parseInt(minServiceTimeJTextField.getText()));
					simulator.setMaxServiceTime(Integer.parseInt(maxServiceTimeJTextField.getText()));
					simulator.setNrQueues(Integer.parseInt(nrQueuesJTextField.getText()));
					simulator.setSimulationInterval(Integer.parseInt(simulationIntervalJTextField.getText()));

					simulatorManager.initCozi(simulator);  // INITIALIZAM COZILE

					int j = 0;
					for (Queue q : simulatorManager.getListaCozi()) {

						j++;

						final JLabel queueJLabel = new JLabel("Queue" + j);
						JTextField jTextQ = adaugaQueueInterfata();  // punem JTexturile cozilor in interfata

						panel1.add(queueJLabel);
						panel1.add(jTextQ);
						coadaJText.put(q, jTextQ);

						LOGGER.info("Am initializat Coada" + j);

					}

					panel1.revalidate();  // refresh pentru panel

					simulatorManager.setInterfataGrafica(interfataGrafica);
					simulatorManager.schedule(simulator); //pojo - clase care au numai atribute

				}
			});
		}

		// cu syncronized se asteapta unele pe altele
		/**
		 * Adauga JTextFieldurile (cozile) in interfata
		 *
		 * @return
		 */
		protected synchronized static JTextField adaugaQueueInterfata() {
			final JTextField queueJTextField = new JTextField(70);
			queueJTextField.setEditable(false);
			return queueJTextField;
		}

		/**
		 * Aceasta metoda afiseaza continutul cozii pe care o primeste ca parametru in JTextFieldul corespunzator ei
		 *
		 * @param queue
		 */
		protected synchronized void afiseazaContinutCoada(Queue queue) {
			for (Map.Entry<Queue, JTextField> entry : coadaJText.entrySet()) {

				if (queue.equals(entry.getKey())) {
					entry.getValue().setText(queue.printClients());
				}
			}
		}

		public void actionPerformed(ActionEvent e) {

		}

	}

