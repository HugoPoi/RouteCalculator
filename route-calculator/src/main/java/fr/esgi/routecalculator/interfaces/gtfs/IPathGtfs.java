package fr.esgi.routecalculator.interfaces.gtfs;

import java.util.List;

import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;

public interface IPathGtfs extends Comparable<IPathGtfs>{
	
	IPathGtfs getParent();
	Stop getCurrentStop();
	Trip getCurrentTrip();
	
	int getCurrentStopId();
	int getCurrentTripId();
	public void setParentStopIdDeparture (String id);
	public void setCurrentStopId (String id);
	public void setCurrentStopTime (StopTime stopTime);
	public void setCurrentStop (Stop stop);
	
	public int compareTo(IPathGtfs o);
	

	int getTotalTime();
	List<Stop> getPossibleConnections();
	public List<StopTime> getPossibleConnectionsStopId();
}
