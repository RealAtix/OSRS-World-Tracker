package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;

import controller.WorldController;
import model.World;
import observer.Observable;
import observers.WorldTable;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;


public class GUI extends JFrame{
	private WorldController controller;
	private WorldTableModel worldTable;
	private JPanel contentPane;
	private JTable table;
	private JPanel panel;
	private JButton btnRefresh;


	/**
	 * Create the application.
	 */
	public GUI(WorldController controller) {
		setResizable(false);
		this.controller = controller;
		worldTable = new WorldTable(new ArrayList<World>(), controller);
		
		setupUI();
		createEvents();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void setupUI() {
		setTitle("World Tracker");
		setBounds(100, 100, 541, 541);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		table = new JTable(worldTable);
		table.setFillsViewportHeight(true);
		table.setPreferredSize(new Dimension(300, 500));
		table.setAutoCreateRowSorter(true);
//		Dimension d = table.getPreferredSize();
//		table.setPreferredScrollableViewportSize(d);
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.LEFT);
		
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setCellRenderer(dtcr);
		
		col = table.getColumnModel().getColumn(1);
		col.setCellRenderer(dtcr);
		
		col = table.getColumnModel().getColumn(2);
		col.setCellRenderer(dtcr);
		
		col = table.getColumnModel().getColumn(3);
		col.setCellRenderer(dtcr);
		
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.WEST);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnRefresh = new JButton("Refresh");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnRefresh)
					.addContainerGap(331, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnRefresh)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}
	
	private void createEvents() {
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.updateWorlds();
				} catch (NumberFormatException | NotFound | ResponseException e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
