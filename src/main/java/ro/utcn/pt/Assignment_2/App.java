package ro.utcn.pt.Assignment_2;

import javax.swing.JFrame;



/**
 * @author Rona Dumitrescu
 * @date 29.03.2019
 * 
 */



public class App {
	public static final SimulatorManager simulatorManager = new SimulatorManager();

	private static void interfGraf() {

		InterfataGrafica interfG = new InterfataGrafica();
		interfG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		interfG.setSize(1000, 700);
		interfG.setLocationRelativeTo(null);  // to center it
		interfG.setVisible(true);
	}


	public static void main(String[] args) {
		interfGraf();
	}
}
