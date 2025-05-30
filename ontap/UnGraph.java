package ontap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class UnGraph extends Graph {
//	cau 5 Viết phương thức thêm một cạnh vào đồ thị 
	public void addEdge(int[][] matrix, int v1, int v2) {
		if (v1 >= 0 && v1 < numVexs || v2 >= 0 && v2 <= numVexs) {
			if (adjMatrix[v1][v2] != 0) {
				matrix[v1][v2] = matrix[v2][v1] = matrix[v1][v2] + 1;
			} else {
				matrix[v1][v2] = matrix[v2][v1] = 1;
			}
		} else {
			System.out.println("canh khong hop le");
		}
	}

//	Câu 6: (1) Viết phương thức xóa một cạnh vào đồ thị 
	public void removeEdge(int[][] matrix, int v1, int v2) {
		if (v1 >= 0 && v2 < numVexs || v1 < numVexs && v2 >= 0) {
			matrix[v1][v2] = matrix[v2][v1] = 0;
		} else {
			System.out.println("canh khong hop le");
		}
	}

//	Câu 7: (1) Viết phương tính tổng bậc của mỗi đỉnh 
	public int deg(int v) {
		int result = 0;
		for (int i = 0; i < numVexs; i++) {
			if (adjMatrix[v][i] != 0) {
				if (i == v) {
					result += adjMatrix[v][i] * 2;
				} else {
					result += adjMatrix[v][i];
				}
			}
		}
		return result;
	}

//	Câu 8: (1) Viết phương tính tổng bậc của đồ thị 
	public int sumDeg() {
		int result = 0;
		for (int i = 0; i < numVexs; i++) {
			result += deg(i);
		}
		return result;
	}

//	Câu 9: (1) Viết phương tính tổng số đỉnh của đồ thị 
	public int numVertexs() {
		return numVexs;
	}

//	Câu 10: (1) Viết phương tính tổng số cạnh của đồ thị 
	public int numEdges() {
		return sumDeg() / 2;
	}

	@Override
	public boolean checkConnect() {
		track = new boolean[numVexs];
		queue = new LinkedList<Integer>();
		queue.add(0);
		track[0] = true;
		while (!queue.isEmpty()) {
			int dinhXet = queue.poll();
			for (int i = 0; i < numVexs; i++) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					track[i] = true;
					queue.add(i);
				}
			}
		}
		for (boolean i : track) {
			if (!i) {
				return i;
			}
		}
		return true;
	}

	@Override
	public boolean hasEulerCycle(int[][] matrix) {
		if (checkConnect()) {
			for (int i = 0; i < matrix.length; i++) {
				if (deg(i) % 2 != 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean hasEulerPath(int[][] matrix) {
		int dinhLe = 0;
		int dinhChan = 0;
		if (checkConnect()) {
			for (int i = 0; i < numVexs; i++) {
				if (deg(i) % 2 != 0) {
					dinhLe++;
				} else {
					dinhChan++;
				}
			}
		}
		return numVexs - dinhChan == 2;
	}

	@Override
	public void findEulerPath() {
		if (!checkConnect()) {
			System.out.println("do thi khong lien thong");
			return;
		} else {
			if (hasEulerPath(adjMatrix)) {
				ArrayList<Integer> euler = new ArrayList<Integer>();
				int[][] temp = new int[numVexs][numVexs];
				for (int i = 0; i < numVexs; i++) {
					for (int j = 0; j < numVexs; j++) {
						temp[i][j] = adjMatrix[i][j];
					}
				}
				stack = new Stack<Integer>();
				for (int i = 0; i < temp.length; i++) {
					if (deg(i) % 2 != 0) {
						stack.add(i);
						break;
					}
				}
				while (!stack.isEmpty()) {
					int dinhXet = stack.peek();
					int count = 0;
					for (int i = 0; i < numVexs; i++) {
						if (temp[dinhXet][i] != 0) {
							stack.push(i);
							temp[dinhXet][i] = temp[i][dinhXet] = 0;
							count++;
							break;
						}
					}
					if (count == 0) {
						euler.add(stack.pop());
						count = 0;
					}
				}
				for (int i = euler.size() - 1; i > 0; i--) {
					System.out.print(euler.get(i) + 1 + " ");
				}
			} else {
				System.out.println("do thi khong co duong di");
			}
		}
	}

	@Override
	public void findEulerCycle(int v) {
		if (!hasEulerCycle(adjMatrix)) {
			System.out.println("Đồ thị không có chu trình euler");
			return;
		} else {
			ArrayList<Integer> euler = new ArrayList<Integer>();
			int[][] temp = new int[numVexs][numVexs];
			for (int i = 0; i < numVexs; i++) {
				for (int j = 0; j < numVexs; j++) {
					temp[i][j] = adjMatrix[i][j];
				}
			}
			stack = new Stack<Integer>();
			stack.push(v);
			while (!stack.isEmpty()) {
				int dinhXet = stack.peek();
				int count = 0;
				for (int i = 0; i < numVexs; i++) {
					if (temp[dinhXet][i] != 0) {
						stack.push(i);
						temp[dinhXet][i] = temp[i][dinhXet] = 0;
						count++;
						break;
					}
				}
				if (count == 0) {
					euler.add(stack.pop());
					count = 0;
				}
			}
			for (int i = euler.size() - 1; i >= 0; i--) {
				System.out.print(euler.get(i) + 1 + " ");
			}
		}
	}

	public void initHamilton() {
		track = new boolean[numVexs];
		pathH = new int[numVexs];
		foundCyclde = false;
		foundPath = false;
		in = false;
		Arrays.fill(pathH, -1);
	}

	public void algHamiltion(int v) {
		if (!checkConnect()) {
			System.out.println("Do thi khong lien thong");
			return;
		}
		initHamilton();
		pathH[0] = v;
		track[v] = true;
		expend(1);
		if (foundCyclde == false) {
			System.out.println("đồ thị không có chu trình hamitol");
		}

	}

	private void expend(int i) {
		if (foundCyclde == true || foundPath == true)
			return;
		for (int j = 0; j < numVexs; j++) {
			if (adjMatrix[pathH[i - 1]][j] != 0 && !track[j]) {
				pathH[i] = j;
				System.out.println(Arrays.toString(pathH));
				if (i < numVexs - 1) {
					track[j] = true;
					expend(i + 1);
					System.out.println(j);
					track[j] = false;
				} else {
					in = true;
					if (adjMatrix[pathH[i]][pathH[0]] != 0 && in == true) {
						foundCyclde = true;
						System.out.println("chu trình hamintol");
						print(pathH);
						System.out.println(pathH[0]);
						System.out.println("đường đi hamilton");
						print(pathH);
						in = false;
						return;
					} else {
						if (in == true) {
							foundPath = true;
							print(pathH);
							return;
						}

					}
				}
			}

		}
	}

	public void print(int[] pathH) {
		for (int i = 0; i < pathH.length; i++) {
			System.out.print(pathH[i] + " ");

		}
	}

// cay bao trum de quy
	@Override
	public int[][] SpanningTreeByDFS(int v) {
		if (!checkConnect()) {
			System.out.println("do thi khong lien thong");
			return null;
		}
		int[][] tree = new int[numVexs][numVexs];
		track = new boolean[numVexs];
		parent = new int[numVexs];
		Arrays.fill(parent, -1);
		dfs(tree, v);
		print(tree);
		return null;
	}

	public void dfs(int[][] tree, int v) {
		track[v] = true;
		for (int i = 0; i < numVexs; i++) {
			if (adjMatrix[v][i] != 0 && !track[i]) {
				parent[i] = v;
				tree[v][i] = tree[i][v] = adjMatrix[v][i];
				int s = v + 1;
				int t = i + 1;
				System.out.println("Canh: " + s + "->" + t);
				dfs(tree, i);
			}
		}
	}

	@Override
	public int[][] TreeDFS(int v) {
		int[][] tree = new int[numVexs][numVexs];
		track = new boolean[numVexs];
		stack = new Stack<Integer>();
		stack.push(v);
		while (!stack.isEmpty()) {
			int dinhXet = stack.peek();
			track[dinhXet] = true;
			boolean hasNext = false;
			for (int i = 0; i < numVexs; i++) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					tree[dinhXet][i] = tree[i][dinhXet] = adjMatrix[dinhXet][i];
					hasNext = true;
					int s = dinhXet + 1;
					int t = i + 1;
					stack.push(i);
					System.out.println("Canh: " + s + "->" + t);
					break;
				}
			}
			if (!hasNext) {
				stack.pop();
			}
		}
		return null;
	}

	@Override
	public int[][] TreeBFS(int v) {
		int[][] tree = new int[numVexs][numVexs];
		track = new boolean[numVexs];
		queue = new LinkedList<Integer>();
		queue.add(v);
		while (!queue.isEmpty()) {
			int dinhXet = queue.poll();
			for (int i = 0; i < numVexs; i++) {
				if (adjMatrix[dinhXet][i] != 0 && !track[i]) {
					tree[dinhXet][i] = tree[i][dinhXet] = adjMatrix[dinhXet][i];
					track[i] = true;
					queue.add(i);
					int s = dinhXet + 1;
					int t = i + 1;
					System.out.println("Canh: " + s + " -> " + t);
				}
			}
		}
		print(tree);
		return null;
	}

	@Override
	public boolean checkCycle(int v1, int v2, int[][] matrix) {
		 parent = new int[numVexs]; // Mảng lưu cha của từng đỉnh
		track = new boolean[numVexs];
		// Khởi tạo
		Arrays.fill(parent, -1); // Đặt lại mảng cha
		Queue<Integer> queue = new LinkedList<>();
		queue.add(v1);
		track[v1] = true;
		while (!queue.isEmpty()) {
			int dinhDangXet = queue.poll();
			for (int i = 0; i < numVexs; i++) {
				if (matrix[dinhDangXet][i] != 0) {
					if (!track[i]) {
						track[i] = true;
						parent[i] = dinhDangXet;
						queue.add(i);
					} else if (i != parent[dinhDangXet]) {
						return true;
					}
				}
			}
		}
		// Không có chu trình, và có đường tới v
		return track[v2];
	}

	public ArrayList<Integer> danhSachKe(int v) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < numVexs; i++) {
			if (adjMatrix[v][i] != 0) {
				result.add(i);
			}
		}
		return result;
	}

	@Override
	public int[][] kruskalMST() {
		if (!checkConnect()) {
			return null;
		}
		Stack<Edge> stack = new Stack<Edge>();
		int[][] tree = new int[numVexs][numVexs];
		int trongSo = 0;
		track = new boolean[numVexs];
		List<Edge> list = new ArrayList<>();
		for (int i = 0; i < tree.length; i++) {
			for (int j = 0; j < tree.length; j++) {
				if (adjMatrix[i][j] != 0 && !track[j]) {
					list.add(new Edge(i, j, adjMatrix[i][j]));
					System.out.println("thêm cạnh: " + i + "-" + j);
				}
			}
			track[i] = true;
		}
		Collections.sort(list);
		System.out.println(list);
		stack.add(list.get(0));
		for (Edge edge : list) {
			if (!checkCycle(edge.srs, edge.dest, tree)) {
				tree[edge.srs][edge.dest] = tree[edge.dest][edge.srs] = 1;
				System.out.println("thêm cạnh " + edge.srs + " " + edge.dest + " có trọng số: " + edge.weight);
				trongSo += edge.weight;
			} else {
				System.out.println("loại cạnh " + edge.srs + " " + edge.dest + " vì tạo chu trình");
			}
		}
		System.out.println("cây bao trùm có trọng số nhỏ nhất: " + trongSo);
		return tree;
	}

	// câu 31
	@Override
	public int[][] primMST(int start) {
		stack = new Stack<Integer>();
		int[][] tree = new int[numVexs][numVexs];
		int trongSo = 0;
		track = new boolean[numVexs];
		List<Edge> danhSachCanh = new ArrayList<>();
		PriorityQueue<Edge> luu = new PriorityQueue<>();
		int i = 0;
		int count = 0;
		stack.push(start);
		while (i < numVexs - 1) {
			int dinhXet = stack.peek();
			if (count == 0) {
				for (Integer v : danhSachKe(dinhXet)) {
					if (!track[v]) {
						luu.add(new Edge(dinhXet, v, adjMatrix[dinhXet][v]));
					}
				}
				track[dinhXet] = true;
			}
			Edge min = luu.poll();
			if (!checkCycle(min.srs, min.dest, tree)) {
				danhSachCanh.add(min);
				tree[min.srs][min.dest] = tree[min.dest][min.srs] = 1;
				stack.push(min.dest);
				luu.remove(min);
				i++;
				count = 0;
				trongSo += min.weight;
				System.out.println("Đã thêm cạnh: " + min.srs + "-" + min.dest);
			} else {
				System.out.println("Bỏ cạnh: " + min.srs + "-" + min.dest);
				count++;
			}
		}
		System.out.println("trọng số nhỏ nhất là: " + trongSo);
		return tree;
	}

	@Override
	public void algoDisktra(int[][] matrix, int verStart, int des) {
		track = new boolean[numVexs];
		int[] distance = new int[numVexs];
		int[] pathPreVex = new int[numVexs];
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(pathPreVex, -1);
		distance[verStart] = 0; //khởi tạo
		int demso = 0;
		while (demso < numVexs - 1) {
			int v = -1;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < numVexs; i++) {
				if(min>distance[i] && !track[i]) {
					min=distance[i];
					v = i;
				}
			}
			track[v]=true;
			System.out.println(v);
			for (int i = 0; i < numVexs; i++) {
				if(adjMatrix[v][i]!=0) {
					if(distance[i]>distance[v]+adjMatrix[v][i]) {
						distance[i]=distance[v]+adjMatrix[v][i];
						pathPreVex[i] =v;
					}
				}
			}
			demso++;
		}
		System.out.println("Khoảng cách từ: "+verStart +" toi dinh "+ des);
		List<Integer> l = new ArrayList<Integer>();
		for (int i = des; i!=-1; i=pathPreVex[i]) {
			l.add(i);
		}
		Collections.reverse(l);
		for (Integer i : l) {
			if(i==des) {
				System.out.print(i);
			}else {
				System.out.print(i+"-->"+" ");
			}
		}
		System.out.println( " có độ dài là: "+distance[des]);

		}
//in tu s toi tât ca canh
//	for (int i = 0; i < numVexs; i++) {
//		if(i==verStart) {
//			continue;
//		}
//		
//		if (pathPreVex[i] == -1) {
//			System.out.println("Khong tim duoc duong di");
//			break;
//		}
//		System.out.println("duong di tu dinh " + verStart + " toi dinh " + i);
//		int dest = i;
//		Stack<Integer> st = new Stack<Integer>();
//		st.push(dest);
//		while (dest != verStart) {
//			st.push(pathPreVex[dest]);
//			dest = pathPreVex[dest];
//
//		}
//		System.out.print(st.pop());
//		while (!st.isEmpty()) {
//			System.out.print("--->" + st.pop());
//
//		}
//		System.out.println("\n distance = " + distance[i]);
//	}

	@Override
	public void bellmanfod() {
		int[] pathPreVext = new int[numVexs];
		int[] distance = new int[numVexs];
		int max = Integer.MAX_VALUE;
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(pathPreVext, -1);
		distance[0] = 0;
		boolean stop = false;
		int k = 0;
		while (!stop) {
			stop = true;
			for (int i = 0; i < numVexs; i++) {
				for (int j = 0; j < numVexs; j++) {
					if(adjMatrix[i][j]!=0 && distance[i]!=max) {
						if(distance[j]>distance[i]+adjMatrix[i][j]) {
							distance[j]=distance[i]+adjMatrix[i][j];
							pathPreVext[j]=i;
							stop=false;
						}
					}
				}
			}
			k++;
			if (k > numVexs - 1) {
				break;
			}
		}
//		 Kiểm tra chu trình âm
		for (int i = 0; i < numVexs; i++) {
			for (int j = 0; j < numVexs; j++) {
				if (adjMatrix[i][j] != 0 && distance[i] != max) {
					if (distance[j] > distance[i] + adjMatrix[i][j]) {
						System.out.println("Đồ thị có chu trình âm.");
						return;
					}
				}
			}
		}

		// In kết quả
		System.out.println("Đường đi ngắn nhất từ đỉnh " + 0 + ":");
		for (int i = 0; i < numVexs; i++) {
			if (distance[i] == max) {
				System.out.println("Đỉnh " + i + ": không thể tới.");
			} else {
				System.out.print("đường đi từ " + 0 + " đến đỉnh " + i + ":  ");
				printPath(pathPreVext, i);
				System.out.println(i);
			}
		}
	}

	private void printPath(int[] pathPreVext, int i) {
		if (pathPreVext[i] == -1)
			return;
		printPath(pathPreVext, pathPreVext[i]);
		System.out.print(pathPreVext[i] + " -> ");
	}

	@Override
	public void floyd(int v, int u) {
		int[][] w = new int[numVexs][numVexs];
		int[][] path = new int[numVexs][numVexs];
		int max = Integer.MAX_VALUE;

		// Khởi tạo ma trận khoảng cách và đường đi
		for (int i = 0; i < numVexs; i++) {
			for (int j = 0; j < numVexs; j++) {
				if (i == j) {
					w[i][j] = 0;
					path[i][j] = -1;
				} else if (adjMatrix[i][j] != 0) {
					w[i][j] = adjMatrix[i][j];
					path[i][j] = i;
				} else {
					w[i][j] = max;
					path[i][j] = -1;
				}
			}
		}

		// Thuật toán Floyd
		for (int k = 0; k < numVexs; k++) {
			for (int i = 0; i < numVexs; i++) {
				for (int j = 0; j < numVexs; j++) {
					if (w[i][k] != max && w[k][j] != max && w[i][j] > w[i][k] + w[k][j]) {
						w[i][j] = w[i][k] + w[k][j];
						path[i][j] = path[k][j];
					}
				}
			}
		}

		// In ma trận khoảng cách
		System.out.println("Ma trận khoảng cách ngắn nhất:");
		for (int i = 0; i < numVexs; i++) {
			for (int j = 0; j < numVexs; j++) {
				if (w[i][j] == max) {
					System.out.print("max ");
				} else {
					System.out.print(w[i][j] + "   ");
				}
			}
			System.out.println();
		}

		// In đường đi ngắn nhất
		System.out.print("Đường đi ngắn nhất từ " + v + " đến " + u + ": ");
		if (w[v][u] == max) {
			System.out.println("Không có đường đi");
		} else {
			printPath(path, v, u);
			System.out.println(" => Tổng chi phí: " + w[v][u]);
		}
	}

	// Truy vết và in đường đi từ i đến j
	public void printPath(int[][] path, int i, int j) {
		List<Integer> stack = new ArrayList<>();
		while (j != i) {
			stack.add(j);
			j = path[i][j];
			if (j == -1) {
				System.out.print("Không có đường đi");
				return;
			}
		}
		stack.add(i);
		Collections.reverse(stack);
		for (int k = 0; k < stack.size(); k++) {
			System.out.print(stack.get(k));
			if (k != stack.size() - 1)
				System.out.print(" -> ");
		}
	}
	@Override
	public void floyd() {
		int[][] w = new int[numVexs][numVexs];
		int[][] path = new int[numVexs][numVexs];
		int max = Integer.MAX_VALUE;
		
		// Khởi tạo ma trận khoảng cách và đường đi
		for (int i = 0; i < numVexs; i++) {
			for (int j = 0; j < numVexs; j++) {
				if (i == j) {
					w[i][j] = 0;
					path[i][j] = -1;
				} else if (adjMatrix[i][j] != 0) {
					w[i][j] = adjMatrix[i][j];
					path[i][j] = i;
				} else {
					w[i][j] = max;
					path[i][j] = -1;
				}
			}
		}
		
		// Thuật toán Floyd
		for (int k = 0; k < numVexs; k++) {
			for (int i = 0; i < numVexs; i++) {
				for (int j = 0; j < numVexs; j++) {
					if (w[i][k] != max && w[k][j] != max && w[i][j] > w[i][k] + w[k][j]) {
						w[i][j] = w[i][k] + w[k][j];
						path[i][j] = path[k][j];
					}
				}
			}
		}
		
		// In ma trận khoảng cách
		System.out.println("Ma trận khoảng cách ngắn nhất:");
		for (int i = 0; i < numVexs; i++) {
			for (int j = 0; j < numVexs; j++) {
				if (w[i][j] == max) {
					System.out.print("max ");
				} else {
					System.out.print(w[i][j] + "   ");
				}
			}
			System.out.println();
		}
		for (int i = 0; i < numVexs; i++) {
			for (int j = 0; j < numVexs; j++) {
				if (w[i][j] == max) {
					System.out.println("Không có đường đi");
				} else {
					printPath(path, i, j);
					System.out.println(" => Tổng trọng số: " + w[i][j]);
				}
			}
		}
	}
	@Override
	public void warshall() {
		int[][] temp = new int[numVexs][numVexs];

		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				temp[i][j] = adjMatrix[i][j];
			}
		}
		for (int k = 0; k < temp.length; k++) {
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j < temp.length; j++) {
					if (temp[i][k] != 0 && temp[k][j] != 0) {
						if (temp[i][j] == 0) {
							temp[i][j] = 1;
						}
					}
				}
			}
		}

		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				System.out.print(temp[i][j] + "\t");
			}
			System.out.println();
		}

	}

	
}
