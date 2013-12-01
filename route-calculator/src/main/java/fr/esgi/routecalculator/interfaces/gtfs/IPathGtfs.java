package fr.esgi.routecalculator.interfaces.gtfs;

import java.util.List;

import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;

public interface IPathGtfs extends Comparable<IPathGtfs>{
	
	IPathGtfs getParent();
	Stop getCurrentStop();
	Trip getCurrentTrip();
	
	public int compareTo(IPathGtfs o);
	int getTotalTime();
	
	Boolean isCompleted();
	
	public List<StopTime> getPossibleConnectionsStopId();
}
