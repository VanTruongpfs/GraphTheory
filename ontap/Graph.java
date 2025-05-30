
package ontap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class Graph {

	protected int numVexs;
	protected int[][] adjMatrix;
	protected String path = "src/ontap/dijkstra.txt";
	protected boolean[] track = new boolean[numVexs];
	protected Queue<Integer> queue = new LinkedList<Integer>();
	protected Stack<Integer> stack = new Stack<Integer>();
	protected int[] parent = new int[numVexs];
	protected int[] pathH = new int[numVexs];
	protected boolean in, foundPath = false, foundCyclde = false;

	public Graph() {

	}

	// cau 1
	public boolean loadGraph(String path) throws IOException {
		File input = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String lineFirst = reader.readLine();
		this.numVexs = Integer.parseInt(lineFirst);
		System.out.println(numVexs);
		this.adjMatrix = new int[numVexs][numVexs];
		String line = "";
		int indexLine = 0;
		while ((line = reader.readLine()) != null) {
			String[] tokens = line.split(" ");
			for (int i = 0; i < numVexs; i++) {
				adjMatrix[indexLine][i] = Integer.parseInt(tokens[i]);
			}
			indexLine++;
		}
		reader.close();
		print(adjMatrix);
		return true;
	}

	// cau 2
	public void print(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(adjMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	// cau 4
	public boolean checkUnGraph() {
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				if (adjMatrix[i][j] != 0 && adjMatrix[j][i] == 0) {
					return false;
				}
			}
		}
		return true;
	}

//	cau 5 Viết phương thức thêm một cạnh vào đồ thị 
	public abstract void addEdge(int[][] matrix, int v1, int v2);

//	Câu 6: (1) Viết phương thức xóa một cạnh vào đồ thị 
	public abstract void removeEdge(int[][] matrix, int v1, int v2);

//	Câu 7: (1) Viết phương tính tổng bậc của mỗi đỉnh 
	public abstract int deg(int v);

//	Câu 8: (1) Viết phương tính tổng bậc của đồ thị 
	public abstract int sumDeg();

//	Câu 9: (1) Viết phương tính tổng số đỉnh của đồ thị 
	public abstract int numVertexs();

//	Câu 10: (1) Viết phương tính tổng số cạnh của đồ thị 
	public abstract int numEdges();

	public abstract boolean checkConnect();

	public void diTimCacDinhLienThong() {
		track = new boolean[numVexs];
		queue = new LinkedList<Integer>();
		int count = 1;
		HashMap<Integer, ArrayList<Integer>> thanhPhan = new HashMap<Integer, ArrayList<Integer>>();
		queue.add(0);
		track[0] = true;
		xetTinhLienThong(thanhPhan, count);
		System.out.println("Số thành phần liên thông là: " + thanhPhan.size());
		System.out.println(thanhPhan);
	}

	public void xetTinhLienThong(HashMap<Integer, ArrayList<Integer>> thanhPhan, int count) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (count == 1) {
			list.add(0);
		}
		while (!queue.isEmpty()) {
			int dinhXet = queue.poll();
			for (int i = 0; i < numVexs; i++) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					track[i] = true;
					queue.add(i);
					list.add(i);
				}
			}
			if (list.isEmpty()) {
				list.add(dinhXet);
			}
		}
		thanhPhan.put(count, list);
		count++;
		for (int i = 0; i < numVexs; i++) {
			if (!track[i]) {
				queue.add(i);
				track[i] = true;
				xetTinhLienThong(thanhPhan, count);
			}
		}
	}

//	Câu 13: (2) Viết phương thức dùng giải thuật BFS duyệt đồ thị G, 
	public void BFSGraph() {
		queue = new LinkedList<Integer>();
		track = new boolean[numVexs];
		ArrayList<Integer> bfs = new ArrayList<Integer>();
		queue.add(0);
		while (!queue.isEmpty()) {
			int dinhXet = queue.poll();
			if (!bfs.contains(dinhXet)) {
				bfs.add(dinhXet);
				track[dinhXet] = true;
			}
			for (int i = 0; i < numVexs; i++) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					queue.add(i);
				}
			}
			System.out.println(queue);
		}
		System.out.println("Duyệt theo chiều rộng: " + bfs);
	}

	public void BFSGraph(int startVex) {
		queue = new LinkedList<Integer>();
		track = new boolean[numVexs];
		ArrayList<Integer> bfs = new ArrayList<Integer>();
		queue.add(startVex);
		track[startVex] = true;
		while (!queue.isEmpty()) {
			int dinhXet = queue.poll();
			if (!bfs.contains(dinhXet)) {
				bfs.add(dinhXet);
				track[dinhXet] = true;
			}
			for (int i = 0; i < numVexs; i++) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					queue.add(i);
				}
			}
		}
		System.out.println("Duyệt theo chiều rộng bắt đầu từ đỉnh " + startVex + bfs);
	}

//	Câu 14: Viết phương thức dùng giải thuật DFS duyệt đồ thị G, 
	public void DFSGraph() {
		stack = new Stack<Integer>();
		track = new boolean[numVexs];
		ArrayList<Integer> dfs = new ArrayList<Integer>();
		stack.push(0);
		track[0] = true;
		while (!stack.isEmpty()) {
			int dinhXet = stack.pop();
			if (!dfs.contains(dinhXet)) {
				dfs.add(dinhXet);
				track[dinhXet] = true;
			}
			for (int i = numVexs - 1; i >= 0; i--) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					stack.push(i);
				}
			}
			System.out.println(stack);
		}
		for (int i = 0; i < dfs.size(); i++) {
			System.out.print(dfs.get(i) + 1 + " ");
		}
	}

	public void DFSGraph(int startVex) {
		stack = new Stack<Integer>();
		track = new boolean[numVexs];
		ArrayList<Integer> dfs = new ArrayList<Integer>();
		stack.push(startVex);
		while (!stack.isEmpty()) {
			int dinhXet = stack.pop();
			if (!dfs.contains(dinhXet)) {
				dfs.add(dinhXet);
				track[dinhXet] = true;
			}
			for (int i = numVexs - 1; i >= 0; i--) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					stack.push(i);
				}
			}
			System.out.println(stack);
		}
		for (int i = 0; i < dfs.size(); i++) {
			System.out.print(dfs.get(i) + 1 + " ");
		}
	}

//	Câu 16: (2) Viết phương thức tìm đường đi giữa 2 đỉnh từ s tới t bằng cách sử dụng thuật toán 
//	duyêt theo chiều rộng hoặc duyệt theo chiều sâu? 
	public void findPathTwoVexs(int s, int t) {
		parent = new int[numVexs];
		Arrays.fill(parent, -1);
		queue = new LinkedList<Integer>();
		track = new boolean[numVexs];
		queue.add(s);
		track[s] = true;
		while (!queue.isEmpty()) {
			int dinhXet = queue.poll();
			if (dinhXet == t) {
				break;
			}
			for (int i = 0; i < numVexs; i++) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					parent[i] = dinhXet;
					track[i] = true;
					queue.add(i);
				}
			}
		}
		System.out.println("ok");
		ArrayList<Integer> path = new ArrayList<Integer>();
		for (int i = t; i != -1; i = parent[i]) {
			path.add(i);
		}

		Collections.reverse(path);
		for (Integer integer : path) {
			if (integer != -1) {
				System.out.print(integer + "->");
			} else {
				System.out.println("Không tìm thấy đường đi");
			}
		}
	}

//	Câu 17: (2) Viết phương thức kiểm tra đồ thị có lưỡng phân hay không bằng cách sử dụng 
//	thuật toán duyêt theo chiều rộng hoặc duyệt theo chiều sâu để tô màu cho 2 đỉnh có tạo cạnh 
//	với nhau? 
	public boolean checkBipartiteGraph() {
		int[] color = new int[numVexs];
		Arrays.fill(color, -1);
		for (int start = 0; start < numVexs; start++) {
			if (color[start] == -1) {
				queue = new LinkedList<>();
				queue.add(start);
				color[start] = 0;
				while (!queue.isEmpty()) {
					int dinhXet = queue.poll();
					for (int i = 0; i < numVexs; i++) {
						if (adjMatrix[dinhXet][i] != 0) {
							if (color[i] == -1) {
								color[i] = 1 - color[dinhXet];
								queue.add(i);
							} else if (color[i] == color[dinhXet]) {
								return false;
							}
						}

					}

				}
			}
		}
		return true;
	}

//	Câu 18: (3) Viết phương thức kiểm tra đồ thị G có chu trình Euler hay không? 
	public abstract boolean hasEulerCycle(int[][] matrix);

// câu 19
	public abstract boolean hasEulerPath(int[][] matrix);

// Câu 20: (3) Viết phương thức tìm chu trình Euler của đồ thị G? 
	public abstract void findEulerCycle(int v);

//	 Câu 21: (3) Viết phương thức tìm đường đi Euler của đồ thị G? 
	public abstract void findEulerPath();

	public abstract void algHamiltion(int v);

//	 Viết phương thức duyệt cây bao trùm bằng thuật toán duyệt theo chiều sâu DFS đệ
//	 quy?
	public abstract int[][] SpanningTreeByDFS(int v);

	public abstract int[][] TreeDFS(int v);

	public abstract int[][] TreeBFS(int v);

	public abstract boolean checkCycle(int v1, int v2, int[][] matrix);

	public abstract int[][] kruskalMST();

	public abstract int[][] primMST(int v);

	public abstract void algoDisktra(int[][] matrix, int verStart, int des);
	public abstract void bellmanfod();
	public abstract void floyd(int v, int u);
	public abstract void floyd();
	public abstract void warshall() ;
}
