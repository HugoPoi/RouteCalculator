package algo.graph.interfaces;

import java.util.List;
import java.util.Stack;

public interface IRoute extends Comparable<IRoute>{
	public List<IRelation> getNextPossibleNodeMove(INode target);
	public int getTotalWeight();
	public Stack<INode> export();
}
