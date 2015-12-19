package observers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

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
	}
	
	@Override
	public void update() {

	}

	
	public JTable getTable() {
		return table;
	}
	
}
