package fr.esgi.routecalculator.gtfscalculator;

import java.sql.SQLException;
import java.util.PriorityQueue;

import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;

import fr.esgi.routecalculator.interfaces.gtfs.IPathGtfs;

public class Graph {
	
	Graph (){}
	
	public static int departTime;
	public String arrival;
	public static String parentIdDeparture;

	public String findRoute(String start, String target) throws SQLException{
		
		PathGtfsImpl premiereRoute = new PathGtfsImpl(start, HibernateUtil.getSessionFactory());
		arrival = PathGtfsImpl.getParentStopIdForNameStop(target);
		
		PriorityQueue<PathGtfsImpl> routes = new PriorityQueue<>();
		//Premier passage
		for (StopTime toto : premiereRoute.getPossibleConnectionsStopId()) {
			routes.add(new PathGtfsImpl(premiereRoute, toto));
		}
		
		//search
		PathGtfsImpl selectedRoute = null;
		do{
			selectedRoute = routes.remove();
			if(!selectedRoute.plusDeRoutePossible){
				System.out.println("La priority Queue utilise la stop name : " + selectedRoute.currentStop.getName());
				for(StopTime rnext : selectedRoute.getPossibleConnectionsStopId()){
					if(rnext != null){
						routes.add(new PathGtfsImpl(selectedRoute,rnext));
					}
				}
			}
		}while(!selectedRoute.isCompleted(arrival));
		
		while(selectedRoute.last != null){
			System.out.println(selectedRoute.getCurrentStop().getName());
			selectedRoute = selectedRoute.last;
		}
			
		return target;

	}


}
	

