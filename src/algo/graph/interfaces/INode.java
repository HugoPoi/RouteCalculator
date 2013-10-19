package algo.graph.interfaces;

import java.util.List;

public interface INode {
	List<IRelation> getRelations();
	void addRelation(IRelation inrelation);
}
