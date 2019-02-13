package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

	private String url;
	private World world;
	
	public Scraper(String url) {
		this.url = url;
	}
	
	public List<World> init() throws IOException {
		List<World> worlds = new ArrayList<World>();
		Document doc = Jsoup.connect(url).get();

		Elements elements = doc.select("tr.server-list__row");
		
		for (Element e: elements) {
			String id;
			String url = e.child(0).child(0).attr("href");
			id = url.substring(url.length()-3);
			
			int population = 0;
			if (e.child(1).text().length() > 0) {
				population = Integer.parseInt(e.child(1).text().substring(0, e.child(1).text().length() - " players".length()));
			} 
			
			String country = e.child(2).text();
			Boolean member = e.child(3).text().equals("Members");
			
			world = new World(id, member, population, country);
			worlds.add(world);
		}
		
		return worlds;
	}
	
	public List<Integer> updateWorlds() throws NumberFormatException, IOException {
		List<Integer> worldPop = new ArrayList<Integer>();
		Document doc = Jsoup.connect(url).get();

		Elements elements = doc.select("tr.server-list__row");
		
		for (Element e: elements) {
			int population = 0;
			
			if (e.child(1).text().length() > 0) {
				population = Integer.parseInt(e.child(1).text().substring(0, e.child(1).text().length() - " players".length()));
			} 
			
			worldPop.add(population);
		}
		
		return worldPop; 
	}
}
