package observers;

import javax.swing.JTextField;

import controller.WorldController;
import observer.Observer;

public class TextFieldTimer implements Observer{

	private JTextField textTimer;
	private WorldController controller;
	
	public TextFieldTimer(WorldController controller) {
		this.controller = controller;
		
		textTimer = new JTextField();
		textTimer.setText("10");
		textTimer.setColumns(3);
		
		controller.addObserver(this);
	}
	
	@Override
	public void update() {
		textTimer.setEditable(!controller.getCurrentState().isWorking());
	}
	
	public JTextField getTextField() {
		return textTimer;
	}
	
}
