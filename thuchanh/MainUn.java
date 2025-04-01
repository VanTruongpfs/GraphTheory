package thuchanh;
import java.io.IOException;

public class MainUn {
	public static void main(String[] args) throws IOException {
		Graph unGraph = new UnGraph();
		
		if(unGraph.loadGraph(unGraph.path)==true) {
		}
		unGraph.printMatrix(unGraph.adjMatrix);
//		if(directGraph.loadGraph(directGraph.path)==true) {
//			
//		}
		System.out.println(unGraph.checkValid(unGraph.adjMatrix));
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
		int v = 0;
//		unGraph.BFSGraph(v);
//		System.out.println("///////////////////////////////");
//		unGraph.DFSGraph(v);
//		System.out.println(unGraph.checkBipartiteGraph(1));
//		System.out.println(unGraph.isConnected(3));
//		System.out.println(unGraph.soThanhPhanLienThong());
//		System.out.println("đường đi:"+unGraph.findPath(0, 7));
		System.out.println(unGraph.checkEuler());
		System.out.println("Chu Trình Euler: " +unGraph.findEulerCycle(v));
		unGraph.loadGraph(unGraph.path);
		System.out.println("Đường đi Euler: " +unGraph.findPathEuler());
		
		unGraph.loadGraph(unGraph.path);
		
		System.out.println("Chu trình hamilton: "+unGraph.findHamiltonianCycle(1));
		unGraph.printMatrix(unGraph.adjMatrix);
//		unGraph.loadGraph(unGraph.path);
		System.out.println("Đường Đi: "+unGraph.findHamiltonianPath(0));

		
		
	
	}
}
