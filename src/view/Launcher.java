package view;

import javax.swing.JOptionPane;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;

import controller.WorldController;

public class Launcher {

	public static void main(String[] args) {
		WorldController w = null;
		try {
			w = new WorldController();
			w.showUI();
			w.updateWorlds();
		} catch (NotFound | ResponseException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Jaunt API has expired, please update OSRS World Tracker to the newest version\n\nhttps://github.com/RealAtix/OSRS-World-Tracker", "Out of date", JOptionPane.ERROR_MESSAGE);
		}
	}

}
