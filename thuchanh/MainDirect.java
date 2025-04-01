package thuchanh;

import java.io.IOException;

public class MainDirect {
	public static void main(String[] args) throws IOException {
		Graph directGraph = new DirectGraph();
		directGraph.loadGraph(directGraph.path);
		System.out.println("direct");
		System.out.println(directGraph.checkConnection());
		directGraph.BFSGraph(0);
		directGraph.DFSGraph(0);
		System.out.println(directGraph.isEuler());
		System.out.println(directGraph.isPathEuler());
		System.out.println(directGraph.checkEuler());
		System.out.println(directGraph.findEulerCycle(0));
		System.out.println(directGraph.findPathEuler());
		
		System.out.println(directGraph.findHamiltonianCycle(0));
		System.out.println(directGraph.findHamiltonianPath(0));
	}
}
