package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import db.WorldDB;
import model.Scraper;
import model.World;
import observer.Observable;
import observer.Observer;
import state.StateStopped;
import state.StateTimer;
import state.StateWorking;
import view.GUI;

public class WorldController implements Observable{

	private Scraper scraper;
	private WorldDB worlds;
	private List<Observer> observers;
	
	private StateTimer state;
	private StateWorking stateWorking;
	private StateStopped stateStopped;
	
	public WorldController() throws IOException {
		scraper = new Scraper("http://oldschool.runescape.com/slu.ws?order=WMLPA");;
		worlds = new WorldDB();
		observers = new ArrayList<Observer>();
		
		stateWorking = new StateWorking();
		stateStopped = new StateStopped();
		state = stateStopped;
		
		worlds.setWorlds(scraper.init());
	}
	
	public void showUI() {
		GUI gui = new GUI(this);
	}
	
	public void disp(int seconds) throws NumberFormatException, Exception {
		worlds.setWorlds(scraper.init());
		Thread.sleep(seconds * 1000);
		worlds.updatePopulation(scraper.updateWorlds());
		Set<World> sortedWorlds = new TreeSet<World>(worlds.getWorlds());
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		

		for (World w : sortedWorlds) {
			System.out.println("World " + w.getId() + " | " + w.getCurrentPopulation() + " people | " + w.getDifference() + " | " + w.isMember() + " | " + w.getCountry());
		}

	}
	
	public void updateWorlds() throws NumberFormatException, IOException {
		List<Integer> updates = scraper.updateWorlds();
		for (int i = 0; i < worlds.getWorlds().size(); i++) {
			worlds.getWorlds().get(i).setPopulation(updates.get(i));
		}
		notifyObservers();
	}
	
	public void handleTimer(String s) throws Exception {
		if (s.equals("0") || s == null) {
			throw new Exception("Invalid character");
		}
		
		int seconds = Integer.parseInt(s);
		if (seconds < 1) {
			throw new Exception("Minimum time is 1");
		}
		
		if (state.equals(stateStopped)) {
			state = stateWorking;
			notifyObservers();
			time(seconds);
		} else {
			state = stateStopped;
			notifyObservers();
		}
	}
	
	public void time(int seconds) {
		if (state.equals(stateWorking)) {
			Thread t = new Thread(new Runnable() {
         	    public void run() {
         	    	while (state.equals(stateWorking)) {
	        			try {
	        				updateWorlds();
	        				Thread.sleep(seconds * 1000);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
         	    	}
         	    }
         	 });
         	 t.start();
		}
	}
	
	public StateTimer getCurrentState() {
		return state;
	}
	
	public List<World> getWorlds() {
		return worlds.getWorlds();
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}

	@Override
	public List<Observer> getObservers() {
		return observers;
	}

	@Override
	public void setObservers(List<Observer> o) {
		observers = o;
		
	}

	public int getWarningValue() {
		return 5;
	}
	
}
