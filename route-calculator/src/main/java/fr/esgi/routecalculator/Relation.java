package fr.esgi.routecalculator;

import fr.esgi.routecalculator.interfaces.INode;
import fr.esgi.routecalculator.interfaces.IRelation;

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
