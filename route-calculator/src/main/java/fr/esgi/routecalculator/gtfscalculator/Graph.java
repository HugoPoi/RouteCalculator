package fr.esgi.routecalculator.gtfscalculator;

import java.util.Date;
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
		Date startTime = new Date();
		do{
			selectedRoute = routes.remove();
			for (StopTime possibility: selectedRoute.getPossibleConnectionsStopId()) {
					PathGtfsImpl newRoute = new PathGtfsImpl(selectedRoute, possibility);
					routes.add(newRoute);
			}
			
			if(new Date().getTime() - startTime.getTime() > 5000){
				System.out.println("Queued routes : " +routes.size());
				System.out.println("Best Time : " +routes.peek().getTotalTime());
				System.out.println("Where : " +routes.peek().getCurrentStop().getName());
				startTime = new Date();
			}
			
		}while(!selectedRoute.isCompleted());
		System.out.println("Total time for best : "+selectedRoute.getTotalTime());
		System.out.println(selectedRoute);
	}


}
	

