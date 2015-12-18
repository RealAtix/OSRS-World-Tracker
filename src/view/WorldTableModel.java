package view;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import controller.WorldController;
import model.World;


public class WorldTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6814340787579137055L;
	private List<World> worlds;
	protected final WorldController controller;
	
	private final String[] columnNames = new String[] {
            "World", "Population", "Difference", "Member"
    };
    private final Class[] columnClass = new Class[] {
        String.class, Integer.class, Integer.class, String.class
    };
    
	public WorldTableModel(List<World> worlds, WorldController controller) {
		this.worlds = worlds;
		this.controller = controller;
	}
	
	public void setWorlds(List<World> worlds){
		this.worlds = worlds;
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		return columnClass[col];
	}
	
	@Override
	public int getColumnCount() {
		 return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return worlds.size();
	}
	
	@Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        World row = worlds.get(rowIndex);
        if (0 == columnIndex) {
            return row.getId();
        }
        else if (1 == columnIndex) {
            return row.getCurrentPopulation();
        }
        else if (2 == columnIndex) {
            return row.getDifference();
        }
        else if (3 == columnIndex) {
        	return row.isMember() ? "Members" : "Free";
        }
        return null;
    }
    
    @Override
	public boolean isCellEditable(int row, int col) { 
    	return false; 
    }
    
}
