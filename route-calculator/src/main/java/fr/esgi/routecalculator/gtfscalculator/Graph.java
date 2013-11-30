package fr.esgi.routecalculator.gtfscalculator;

import java.util.PriorityQueue;

import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;

import fr.esgi.routecalculator.interfaces.gtfs.IPathGtfs;

public class Graph {
	
	Graph (){}


	public String findRoute(String start, String target){
		
		PathGtfsImpl premiereRoute = new PathGtfsImpl(start, target, HibernateUtil.getSessionFactory());
		
		PriorityQueue<PathGtfsImpl> routes = new PriorityQueue<>();
		
		//Premier passage
		for (StopTime toto : premiereRoute.getPossibleConnectionsStopId()) {
			routes.add(new PathGtfsImpl(premiereRoute, toto.getStop().getId().toString(), 0));
		}
		
		
		//IdStop 1629 : Alexandre Dumas 
		StopTime testStoptimes = new StopTime();
		testStoptimes.setArrivalTime(67208);
		Stop testStop = new Stop();
		testStop.setParentStation("1978");
		premiereRoute.setCurrentStopTime(testStoptimes);
		premiereRoute.setCurrentStop(testStop);
		//For the test
		
		//Second et autres passages
		for (StopTime toto : premiereRoute.getPossibleConnectionsStopId()) {
			System.out.println(toto.toString());
		}
		
		return null;
		/*
		Node startNode = nodes.get(start);
		Node endNode = nodes.get(target);
		PriorityQueue<Route> routes = new PriorityQueue<>();
		
		//init firsts routes
		Route route = new Route(startNode);
		for(IRelation r : startNode.getRelations()){
			routes.add(new Route(route, r));
		}
		
		//search
		Route selectedRoute = null;
		do{
			selectedRoute = routes.remove();
			for(IRelation rnext : selectedRoute.getNextPossibleNodeMove(endNode)){
				routes.add(new Route(selectedRoute,rnext));
			}
			
		}while(!selectedRoute.isCompleted(endNode));
		return selectedRoute;
	*/
	}


}
	

