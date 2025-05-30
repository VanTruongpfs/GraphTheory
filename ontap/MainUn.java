package ontap;

import java.io.IOException;

public class MainUn {
	public static void main(String[] args) throws IOException {
		Graph unGraph = new UnGraph();
		unGraph.loadGraph(unGraph.path);
		
		System.out.println("cau 4: " + unGraph.checkUnGraph());
//		System.out.println("them canh");
//		unGraph.addEdge(unGraph.adjMatrix, 0, 3);
//		unGraph.print(unGraph.adjMatrix);
//		System.out.println("xoa canh");
//		unGraph.removeEdge(unGraph.adjMatrix, 0, 3);
//		unGraph.print(unGraph.adjMatrix);
//		System.out.println(unGraph.deg(3));
//		System.out.println(unGraph.sumDeg());
//		System.out.println(unGraph.numEdges());
//		System.out.println(unGraph.checkConnect());
//		unGraph.diTimCacDinhLienThong();
//		unGraph.BFSGraph();
//		unGraph.BFSGraph(5);
//		unGraph.DFSGraph();
//		System.out.println();
//		unGraph.findPathTwoVexs(5,7);
//		System.out.println();
//		System.out.println("Kiem tra do thi co luong phan khong");
//		System.out.println(unGraph.checkBipartiteGraph());
//		System.out.println(unGraph.hasEulerCycle(unGraph.adjMatrix));
//		unGraph.findEulerCycle(0);
//		unGraph.findEulerPath();
//		unGraph.algHamiltion(0);
//		System.out.println(unGraph.SpanningTreeByDFS(0));
//		System.out.println(unGraph.TreeDFS(0));
//		System.out.println(unGraph.TreeBFS(0));
//		System.out.println(unGraph.kruskalMST());
//		System.out.println(unGraph.primMST(0));
//		unGraph.algoDisktra(unGraph.adjMatrix, 0,8);
//		unGraph.bellmanfod();
//		unGraph.floyd();
		unGraph.floyd(0, 7);
	}
}
