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
	
	int getTotalTime();
	List<Stop> getPossibleConnections();
	List<StopTime> getPossibleConnectionsStopId();
}