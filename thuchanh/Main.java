package thuchanh;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Graph unGraph = new UnGraph();
		Graph directGraph = new DirectGraph();
		if(directGraph.loadGraph(directGraph.path)==true) {
			directGraph.printMatrix(directGraph.adjMatrix);
		}

//		System.out.println(unGraph.checkValid(unGraph.adjMatrix));
//		unGraph.addEdge(unGraph.adjMatrix, 0, 3);
//		System.out.println("them canh");
//		unGraph.printMatrix(unGraph.adjMatrix);
//		unGraph.removeEdge(unGraph.adjMatrix, 3, 2);
//		unGraph.removeEdge(unGraph.adjMatrix, 0, 3);
//		System.out.println("xoa canh");
//		unGraph.printMatrix(unGraph.adjMatrix);
//		System.out.println("tổng bậc của đỉnh: "+ ++v +" là "+ unGraph.deg(2));
//		System.out.println(unGraph.sumEdges());
//		System.out.println("Kiem tra do thi lien thong: "+unGraph.checkConnection());
//		System.out.println(unGraph.danhSachKe(unGraph.adjMatrix, v));
//		int v = 0;
//		unGraph.BFSGraph(v);
//		System.out.println("///////////////////////////////");
//		unGraph.DFSGraph(v);
//		System.out.println(unGraph.checkBipartiteGraph(1));
//		System.out.println(unGraph.isConnected(3));
//		System.out.println(unGraph.soThanhPhanLienThong());
//		System.out.println("đường đi:"+unGraph.findPath(0, 7));
//		System.out.println(unGraph.checkEuler());
//		System.out.println("Chu Trình Euler: " +unGraph.findEulerCycle(v));
//		unGraph.loadGraph(unGraph.path);
//		System.out.println("Đường đi Euler: " +unGraph.findPathEuler());
//		unGraph.loadGraph(unGraph.path);
//		System.out.println("Chu trình hamilton: "+unGraph.findHamiltonianCycle());
//		unGraph.loadGraph(unGraph.path);
//		System.out.println("Đường Đi: "+unGraph.findHamiltonianPath(0));
//		System.out.println();
//		System.out.println(directGraph.checkConnection());
//		directGraph.BFSGraph(0);
//		directGraph.DFSGraph(0);
		System.out.println(directGraph.isEuler());
		System.out.println(directGraph.isPathEuler());
		System.out.println(directGraph.checkEuler());
		System.out.println(directGraph.findEulerCycle(0));
		System.out.println(directGraph.findPathEuler());
		directGraph.loadGraph(directGraph.path);
		System.out.println(directGraph.findHamiltonianCycle());
		System.out.println(directGraph.findHamiltonianPath(0));
		
		
	
	}
}
