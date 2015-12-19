package state;

public class StateStopped implements StateTimer {

	@Override
	public boolean isWorking() {
		return false;
	}

}
