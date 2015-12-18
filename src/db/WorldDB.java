package db;

import java.util.List;
import java.util.ArrayList;

import model.World;

public class WorldDB {

	private List<World> worlds;
	
	public WorldDB() {
		worlds = new ArrayList<World>();
	}
	
	public void setWorlds(List<World> worlds) {
		this.worlds = worlds;
	}
	
	public void updatePopulation(List<Integer> population) throws Exception {
		if (population.size() != worlds.size()) throw new Exception("Parsing error");
		int index = 0;
		for (World w : worlds) {
			w.setPopulation(population.get(index));
			index++;
		}
	}
	
	public List<World> getWorlds() {
		return worlds;
	}
	
}
