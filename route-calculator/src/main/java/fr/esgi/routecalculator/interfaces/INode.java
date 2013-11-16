package fr.esgi.routecalculator.interfaces;

import java.util.List;

public interface INode {
	List<IRelation> getRelations();
	void addRelation(IRelation inrelation);
}
