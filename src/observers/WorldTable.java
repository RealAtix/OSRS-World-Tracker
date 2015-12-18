package observers;

import java.util.List;

import controller.WorldController;
import model.World;
import observer.Observer;
import view.WorldTableModel;

public class WorldTable extends WorldTableModel implements Observer {

	private static final long serialVersionUID = -3670447762874591227L;

	public WorldTable(List<World> worlds, WorldController controller) {
		super(worlds, controller);
		controller.addObserver(this);
	}
	
	@Override
	public void update() {
		this.setWorlds(controller.getWorlds());
	}

}
