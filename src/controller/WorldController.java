package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;

import db.WorldDB;
import model.Scraper;
import model.World;
import observer.Observable;
import observer.Observer;
import view.GUI;

public class WorldController implements Observable{

	private Scraper scraper;
	private WorldDB worlds;
	private List<Observer> observers;
	
	public WorldController() throws NotFound, ResponseException {
		scraper = new Scraper("http://oldschool.runescape.com/slu.ws?order=WMLPA");;
		worlds = new WorldDB();
		observers = new ArrayList<Observer>();
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
	
	public void updateWorlds() throws NumberFormatException, NotFound, ResponseException {
		List<Integer> updates = scraper.updateWorlds();
		for (int i = 0; i < worlds.getWorlds().size(); i++) {
			worlds.getWorlds().get(i).setPopulation(updates.get(i));
		}
		notifyObservers();
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
	
}
