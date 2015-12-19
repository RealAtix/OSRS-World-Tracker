package observers;

import javax.swing.JButton;

import controller.WorldController;
import observer.Observer;

public class ButtonStart implements Observer{

	private JButton btnStart;
	private WorldController controller;
	
	public ButtonStart(WorldController controller) {
		this.controller = controller;
		
		btnStart = new JButton("Start");
		
		controller.addObserver(this);
	}
	
	@Override
	public void update() {
		btnStart.setText((controller.getCurrentState().isWorking() ? "Stop" : "Start"));
	}
	
	public JButton getButton() {
		return btnStart;
	}
	
}
