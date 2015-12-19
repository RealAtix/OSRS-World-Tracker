package view;

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
		}

	}

}
