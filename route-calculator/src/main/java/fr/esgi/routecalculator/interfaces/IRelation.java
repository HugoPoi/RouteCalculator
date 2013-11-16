package fr.esgi.routecalculator.interfaces;

public interface IRelation {
	INode getStartNode();
	INode getEndNode();
	int getWeight();
}
