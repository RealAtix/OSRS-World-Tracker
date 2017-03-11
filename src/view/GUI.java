package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import javax.swing.table.TableRowSorter;

import controller.WorldController;
import model.World;
import observers.ButtonStart;
import observers.Table;
import observers.TextFieldTimer;
import observers.WorldTable;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class GUI extends JFrame{
	private WorldController controller;
	private WorldTableModel tableModel;
	private Table worldTable;
	private JPanel contentPane;
	private JTable table;
	private JPanel panel;
	private JButton btnRefresh;
	private JLabel lblTimer;
	private JButton btnStart;
	private JTextField textTimer;
	private TextFieldTimer worldTextFieldTimer;
	private ButtonStart worldButtonStart;
	private JComboBox comboFilter;


	/**
	 * Create the application.
	 */
	public GUI(WorldController controller) {
		this.controller = controller;
		tableModel = new WorldTable(new ArrayList<World>(), controller);
		worldTable = new Table(controller, tableModel);
		worldTextFieldTimer = new TextFieldTimer(controller);
		worldButtonStart = new ButtonStart(controller);
		
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
		setBounds(100, 100, 541, 541); //541
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		table = worldTable.getTable();
		table.setFillsViewportHeight(true);
		//table.setPreferredSize(new Dimension(300, 541));
		table.setAutoCreateRowSorter(true);
		
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
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnRefresh = new JButton("Refresh");
		
		btnStart = worldButtonStart.getButton();

		textTimer = worldTextFieldTimer.getTextField();
		
		lblTimer = new JLabel("Timer:");
		
		comboFilter = new JComboBox();
		comboFilter.setModel(new DefaultComboBoxModel(new String[] {"All", "P2P", "F2P"}));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTimer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textTimer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStart)
					.addPreferredGap(ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
					.addComponent(comboFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRefresh)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRefresh)
						.addComponent(lblTimer)
						.addComponent(textTimer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStart)
						.addComponent(comboFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}
	
	private void createEvents() {
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.updateWorlds();
				} catch (NumberFormatException | IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.handleTimer(textTimer.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		comboFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JComboBox cb = (JComboBox) e.getSource();
				String filter; 
				if (cb.getSelectedItem().equals("All")) {
					filter = ".*";
				} else if (cb.getSelectedItem().equals("P2P")) {
					filter = "Members";
				} else {
					filter = "Free";
				}
				
				RowFilter<String, Integer> rowFilter = RowFilter.regexFilter(filter, 3);
				((TableRowSorter) table.getRowSorter()).setRowFilter(rowFilter);
			}
		});
	}
}
