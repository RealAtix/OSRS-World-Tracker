package observers;

import javax.swing.JTable;

import controller.WorldController;
import observer.Observer;
import view.WorldTableModel;

public class Table implements Observer{

	private JTable table;
	private WorldController controller;
	private WorldTableModel model;
	
	public Table(WorldController controller, WorldTableModel model) {
		this.controller = controller;
		this.model = model;
		
		table = new JTable(model);
		controller.addObserver(this);
	}
	
	@Override
	public void update() {
		
	}

	
	public JTable getTable() {
		return table;
	}
	
}
