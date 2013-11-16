package fr.esgi.routecalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import fr.esgi.routecalculator.interfaces.IGraph;
import fr.esgi.routecalculator.interfaces.IRelation;

public class Graph implements IGraph {

	Map<String,Node> nodes;
	
	public Graph() {
		super();
		nodes = new HashMap<>();
	}
	
	public void addRoute(String n1, String n2, int weight) {
		Node node1 = nodes.get(n1);
		Node node2 = nodes.get(n2);
		if(node1 == null){
			node1 = new Node(n1);
			nodes.put(node1.getName(), node1);
		}
		if(node2 == null){
			node2 = new Node(n2);
			nodes.put(node2.getName(), node2);
		}
		Relation r = new Relation(node1,node2,weight);
		node1.addRelation(r);
		node2.addRelation(r);
	}

	@Override
	public Route findRoute(String start, String target){
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
	}

}
