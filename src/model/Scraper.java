package model;

import java.util.ArrayList;
import java.util.List;
import com.jaunt.*;

public class Scraper {

	private String html;
	private Elements ef2p;
	private Elements ep2p;
	private World world;
	
	public Scraper(String html) {
		this.html = html;
	}
	
	public List<World> init() throws ResponseException, NotFound {
		List<World> worlds = new ArrayList<World>();
		UserAgent userAgent =  new UserAgent();
		userAgent.visit(this.html);

		ef2p = userAgent.doc.findEvery("<tr class='server-list__row'");
		ep2p = userAgent.doc.findEvery("<tr class='server-list__row server-list__row--members'>");
		
		for (Element e: ef2p) {
			String id;
			if (e.getElement(0).getElement(0).getText().length() == 12) {
				id = "30" + e.getElement(0).getElement(0).getText().substring(11, 12);
			} else {
				id = "3" + e.getElement(0).getElement(0).getText().substring(11, 13);
			}
			
			int population = 0;
			if (e.getElement(1).getText().length() > 0) {
				population = Integer.parseInt(e.getElement(1).getText().substring(0, e.getElement(1).getText().length() - " players".length()));
			} 
			
			String country = e.getElement(2).getText();
			
			world = new World(id, false, population, country);
			worlds.add(world);
		}
		
		for (Element e: ep2p) {
			String id;
			
			if (e.getElement(0).getElement(0).getText().equals("World 666")) {
				continue; // Jagex using "World 666" instead of the usual "Old School xx".. nice consistency
			}
			
			if (e.getElement(0).getElement(0).getText().length() == 12) {
				id = "30" + e.getElement(0).getElement(0).getText().substring(11, 12);
			} else {
				id = "3" + e.getElement(0).getElement(0).getText().substring(11, 13);
			}
			
			
			int population = 0;
			if (e.getElement(1).getText().length() > 0) {
				population = Integer.parseInt(e.getElement(1).getText().substring(0, e.getElement(1).getText().length() - " players".length()));
			} 
			
			String country = e.getElement(2).getText();
			
			world = new World(id, true, population, country);
			worlds.add(world);
		}
		
		return worlds;
	}
	
	public List<Integer> updateWorlds() throws ResponseException, NumberFormatException, NotFound {
		List<Integer> worldPop = new ArrayList<Integer>();
		UserAgent userAgent =  new UserAgent();
		userAgent.visit(this.html);

		ef2p = userAgent.doc.findEvery("<tr class='server-list__row'");
		ep2p = userAgent.doc.findEvery("<tr class='server-list__row server-list__row--members'>");
		
		for (Element e: ef2p) {
			int population = 0;
			
			if (e.getElement(1).getText().length() > 0) {
				population = Integer.parseInt(e.getElement(1).getText().substring(0, e.getElement(1).getText().length() - " players".length()));
			} 
			
			worldPop.add(population);
		}
		
		for (Element e: ep2p) {
			int population = 0;
			
			if (e.getElement(0).getElement(0).getText().equals("World 666")) {
				continue; //skip entry when World 666
			}
			if (e.getElement(1).getText().length() > 0) {
				population = Integer.parseInt(e.getElement(1).getText().substring(0, e.getElement(1).getText().length() - " players".length()));
			} 
			
			worldPop.add(population);
		}
		
		return worldPop; 
	}
}
