package view;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import controller.WorldController;

public class Launcher {

	public static void main(String[] args) {
		WorldController w = null;
		try {
			w = new WorldController();
			w.showUI();
			w.updateWorlds();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Can't reach oldschool.runescape.com, check your internet connection", "Host unreachable", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Jaunt API has expired, please update OSRS World Tracker to the newest version\n\nhttps://github.com/RealAtix/OSRS-World-Tracker", "Out of date", JOptionPane.ERROR_MESSAGE);
		}
	}

}
