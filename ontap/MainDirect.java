package ontap;

import java.io.IOException;

public class MainDirect {
	public static void main(String[] args) throws IOException {
		DirectGraph direct = new DirectGraph();
		direct.loadGraph(direct.path);
//		System.out.println(direct.checkUnGraph());
//		direct.addEdge(direct.adjMatrix, 0, 2);
//		direct.print(direct.adjMatrix);
//		direct.removeEdge(direct.adjMatrix, 0, 2);
//		direct.print(direct.adjMatrix);
//		System.out.println(direct.deg(0));
//		System.out.println(direct.sumDeg());
//		System.out.println(direct.numEdges());
		System.out.println(direct.checkConnect());
//		direct.diTimCacDinhLienThong();
		direct.findEulerCycle(0);
		System.out.println("");
		direct.findEulerPath();
}
}
