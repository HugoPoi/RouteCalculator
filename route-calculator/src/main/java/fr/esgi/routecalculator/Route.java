package fr.esgi.routecalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import fr.esgi.routecalculator.interfaces.INode;
import fr.esgi.routecalculator.interfaces.IRelation;
import fr.esgi.routecalculator.interfaces.IRoute;

public class Route implements IRoute{
	
	Route last;
	INode current;
	
	int totalweight;
	
	public Route(Route inLast, IRelation nextMove){
		last = inLast;
		totalweight = inLast.totalweight + nextMove.getWeight();
		if(nextMove.getEndNode() == last.current){
			current = nextMove.getStartNode();
		}else{
			current = nextMove.getEndNode();
		}
	}
	public Route(INode start){
		last = null;
		current = start;
		totalweight = 0;
	}
	
	public List<IRelation> getNextPossibleNodeMove(INode target){
		List<IRelation> possibilities = new ArrayList<>();
		if(current == target) return possibilities;
		for(IRelation r : current.getRelations()){
			if(r.getEndNode() == current){
				if(r.getStartNode() != last.current){
					possibilities.add(r);
				}
			}else{
				if(r.getEndNode() != last.current){
					possibilities.add(r);
				}
			}
		}
		return possibilities;
	}
	
	public boolean isCompleted(INode target){
		return (current == target);
	}

	@Override
	public int compareTo(IRoute o) {
		if(this.totalweight > o.getTotalWeight()){
			return 1;
		}
		if(this.totalweight < o.getTotalWeight()){
			return -1;
		}
		return 0;
	}
	@Override
	public int getTotalWeight() {
		return totalweight;
	}
	
	public Stack<INode> export(){
		Stack<INode> out = new Stack<>();
		Route enext = this;
		do{
			out.push(enext.current);
			enext = enext.last;
		}
		while(enext != null);
		return out;
	}
	
}
