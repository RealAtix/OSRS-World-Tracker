package observer;

import java.util.List;

import observer.Observer;

public interface Observable {
	
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
	public void setObservers(List<Observer> o);
	public List<Observer> getObservers();
}
