package algo.graph.interfaces;

public interface IGraph {
	void addRoute(String n1,String n2, int weight);
	IRoute findRoute(String start, String target);
}
