package algo.graph;

import algo.graph.interfaces.INode;
import algo.graph.interfaces.IRelation;

public class Relation implements IRelation {
	
	INode start;
	INode end;
	int weight;
	
	public Relation(INode instart, INode inend, int inweight){
		start = instart;
		end = inend;
		weight = inweight;
	}
	
	@Override
	public INode getStartNode() {
		return start;
	}

	@Override
	public INode getEndNode() {
		return end;
	}

	@Override
	public int getWeight() {
		return weight;
	}

}
