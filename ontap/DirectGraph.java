package ontap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class DirectGraph extends Graph {
	private int start, end;
	@Override
	public void addEdge(int[][] matrix, int v1, int v2) {
		if (v1 >= 0 && v1 < numVexs || v2 >= 0 && v2 <= numVexs) {
			if (matrix[v1][v2] != 0) {
				matrix[v1][v2] = matrix[v1][v2] + 1;
				;
			} else {
				matrix[v1][v2] = 1;
			}
		} else {
			System.out.println("canh khong hop le");
		}
	}

	@Override
	public void removeEdge(int[][] matrix, int v1, int v2) {
		if (v1 >= 0 && v1 < numVexs || v2 >= 0 && v2 <= numVexs) {
			if (matrix[v1][v2] != 0) {
				matrix[v1][v2] = matrix[v1][v2] - 1;
				;
			} else {
				matrix[v1][v2] = 0;
			}
		} else {
			System.out.println("canh khong hop le");
		}

	}

	@Override
	public int deg(int v) {
		int result = 0;
		for (int i = 0; i < numVexs; i++) {
			if (adjMatrix[v][i] != 0) {
				result += adjMatrix[v][i];
			}
		}
		for (int i = 0; i < numVexs; i++) {
			if (adjMatrix[i][v] != 0) {
				result += adjMatrix[i][v];
			}
		}
		return result;
	}

	@Override
	public int sumDeg() {
		int result = 0;
		for (int i = 0; i < numVexs; i++) {
			result += deg(i);
		}
		return result;
	}

	@Override
	public int numVertexs() {
		return numVexs;
	}

	@Override
	public int numEdges() {
		int result = 0;
		for (int i = 0; i < numVexs; i++) {
			if (adjMatrix[i][i] != 0) {
				result += adjMatrix[i][i];
			}
			for (int j = 0; j < numVexs; j++) {
				if (adjMatrix[i][j] != 0) {
					result += adjMatrix[i][j];
				}
			}
		}

		return result;
	}

	@Override
	public boolean checkConnect() {
		int[][] matrix = new int[numVexs][numVexs];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if(adjMatrix[i][j]!=0) {
					matrix[i][j]=matrix[j][i];
				}
			}
		}
		
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
		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return false;
		}
		for (int i = 0; i < numVexs; i++) {
			int in = 0;
			int out = 0;
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != 0) {
					out+=matrix[i][j];
				}
				if (matrix[j][i] != 0) {
					in+=matrix[i][j];
				}
			}
			if (in != out) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean hasEulerPath(int[][] matrix) {
		int n = matrix.length;
		int startNodes = 0, endNodes = 0;

		if (!checkConnect()) {
			System.out.println("Đồ thị không liên thông");
			return false;
		}

		for (int i = 0; i < n; i++) {
			int in = 0, out = 0;
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] != 0)
					out++;
				if (matrix[j][i] != 0)
					in++;
			}

			if (out - in == 1) {
				startNodes++;
				start =i;
			} else if (in - out == 1) {
				endNodes++;
				end=i;
			} else if (in != out) {
				return false;
			}
		}
		if ((startNodes == 1 && endNodes == 1) || (startNodes == 0 && endNodes == 0)) {
			return true;
		}
		return false;
	}

	@Override
	public void findEulerPath() {
		if(hasEulerPath(adjMatrix)) {
			ArrayList<Integer> euler = new ArrayList<Integer>();
			int[][] temp = new int[numVexs][numVexs];
			for (int i = 0; i < numVexs; i++) {
			    for (int j = 0; j < numVexs; j++) {
			        temp[i][j] = adjMatrix[i][j];
			    }
			}
			
			stack = new Stack<Integer>();
			stack.push(start);
			while (!stack.isEmpty()) {
				int dinhXet = stack.peek();
				int count = 0;
				for (int i = 0; i < numVexs; i++) {
					if (temp[dinhXet][i] != 0) {
						stack.push(i);
						temp[dinhXet][i]--;
						count++;
						break;
					}
				}
				if (count == 0) {
					euler.add(stack.pop());
				}
			}
			for (int i = euler.size() - 1; i>=0; i--) {
				System.out.print(euler.get(i) + 1 + " ");
			}
		}else {
			System.out.println("đồ thị không có đường đi");
		}
	
}

	@Override
	public void findEulerCycle(int v)  {
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
						temp[dinhXet][i]--;
						count++;
						break;
					}
				}
				if (count == 0) {
					euler.add(stack.pop());
				}
			}
			for (int i = euler.size() - 1; i >= 0; i--) {
				System.out.print(euler.get(i) + 1 + " ");
			}
		}
	}

	@Override
	public void algHamiltion(int v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[][] SpanningTreeByDFS(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] TreeDFS(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] TreeBFS(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkCycle( int v1, int v2,int[][] matrix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[][] kruskalMST() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] primMST(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void algoDisktra(int[][] matrix, int verStart, int des) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bellmanfod() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void floyd(int v, int u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void floyd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warshall() {
		// TODO Auto-generated method stub
		
	}

	
}
