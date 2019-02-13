package view;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

import controller.WorldController;

public class Launcher {
	
	private final static String VERSION = "v1.2.2";

	public static void main(String[] args) {
		WorldController w = null;
		try {
			w = new WorldController();
			w.showUI();
			w.updateWorlds();
			if (updateAvailable() == true) { JOptionPane.showMessageDialog(null, "A new version has been released, you can find the newest version at\nhttps://github.com/RealAtix/OSRS-World-Tracker", "Update", JOptionPane.WARNING_MESSAGE); }
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Can't reach oldschool.runescape.com, check your internet connection", "Host unreachable", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Boolean updateAvailable() throws IOException {
		String json = Jsoup.connect("https://api.github.com/repos/RealAtix/OSRS-World-Tracker/releases/latest").ignoreContentType(true).execute().body();
		String version = json.substring(json.indexOf("tag_name") + 11, json.indexOf("target_commitish") - 3);
		if (!VERSION.equals(version)) {
			return true;
		}
		return false;
	}

}
