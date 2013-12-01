package fr.esgi.routecalculator.gtfscalculator;

import java.util.GregorianCalendar;
import java.util.PriorityQueue;

import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;

import fr.esgi.routecalculator.interfaces.gtfs.IPathGtfs;

public class Graph {
	
	Graph (){}


	public void findRoute(String start, String target){
		
		PriorityQueue<PathGtfsImpl> routes = new PriorityQueue<>();
		routes.add(new PathGtfsImpl(start, target, new GregorianCalendar()));
		
		PathGtfsImpl selectedRoute = null;
		do{
			selectedRoute = routes.remove();
			for (StopTime possibility: selectedRoute.getPossibleConnectionsStopId()) {
				try {
					PathGtfsImpl newRoute = new PathGtfsImpl(selectedRoute, possibility);
					routes.add(newRoute);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}while(!selectedRoute.isCompleted());
		
		System.out.println(selectedRoute);
	}


}
	

