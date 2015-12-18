package model;

public class World implements Comparable<World>{

	private String id;
	private boolean member;
	private int currentPopulation;
	private int previousPopulation;
	private int difference;
	private String country;
	
	public World(String id, boolean member, int population, String country) {
		setId(id);
		setMember(member);
		setPopulation(population);
		setCountry(country);
	}

	public String getId() {
		return id;
	}

	public boolean isMember() {
		return member;
	}

	public int getPreviousPopulation() {
		return previousPopulation;
	}
	
	public int getCurrentPopulation() {
		return currentPopulation;
	}
	
	public int getDifference() {
		return difference;
	}

	public String getCountry() {
		return country;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		World other = (World) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setPopulation(int population) {
		previousPopulation = currentPopulation;
		this.currentPopulation = population;
		if (previousPopulation == 0) {
			difference = 0;
		}
		difference = currentPopulation - previousPopulation;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int compareTo(World o) {
		return this.id.compareTo(o.getId());
	}
	
}
