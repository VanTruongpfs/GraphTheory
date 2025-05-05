package thuchanh;

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
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

public abstract class Graph {
	protected int numVexs;
	protected int[][] adjMatrix;
	protected  String path="src/thuchanh/bellmanfod.txt";
	protected boolean[] track = new boolean[numVexs];
	ArrayList<Integer> dfs = new ArrayList<Integer>(); 
	ArrayList<Integer> bfs = new ArrayList<Integer>(); 
	Queue<Integer> queue = new LinkedList<Integer>();
	Stack<Integer> stack = new Stack<Integer>();
	protected int[] parent = new int[numVexs];
	public Graph(int numVexs, int[][] adjMatrix) {
		super();
		this.numVexs = numVexs;
		this.adjMatrix = adjMatrix;
	}
	public Graph() {
		
	}
	//cau 1
	public boolean loadGraph(String pathFile) throws IOException{
		File input = new File(pathFile);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String lineFirst = reader.readLine();
		this.numVexs=Integer.parseInt(lineFirst);
		this.adjMatrix=new int[numVexs][numVexs];
		String lines="";
		int indexLine=0;
		while((lines=reader.readLine())!=null) {
			String[] dt = lines.split(" ");
			for(int i =0;i<numVexs;i++) {
				this.adjMatrix[indexLine][i]=Integer.parseInt(dt[i]);
			}
			indexLine++;
		}
		reader.close();
		return true;
	}
	public static void printMatrix(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
	//cau 3
	public abstract boolean checkValid(int[][] array);
	//cau 5
	public abstract void addEdge(int[][] array, int v1, int v2);
	//cau 6
	public abstract void removeEdge(int[][] array, int v1, int v2);
	//cau 7
	public abstract int deg(int v);
	//cau 8
	public abstract int sumDeg();
	//cau 9
	public abstract int sumVertexs();
	//cau 10
	public abstract int sumEdges();
	// cau 11
	public abstract boolean checkConnection();
	
	public void reset() {
		for (int i = 0; i < track.length; i++) {
			track[i]=false;
		}
	}
	//trả về danh sách các đỉnh kề
	public ArrayList<Integer> danhSachKe(int[][] arr, int v){
		ArrayList<Integer> danhSachKe = new ArrayList<Integer>();
		for (int i = 0; i < numVexs; i++) {
			if(adjMatrix[v][i]!=0) {
				danhSachKe.add(i);
			}
		}
		return danhSachKe;
	}
	//cau 12
	private void dfs(int vex) {
        track[vex] = true;
        for (int i = 0; i < numVexs; i++) {
            if (adjMatrix[vex][i]==1 && !track[i]) {
                dfs(i);
            }
        }
    }
	private void bfs(int vex) {
		queue = new LinkedList<Integer>();
		queue.add(vex);
		track[vex] = true;
		while(!queue.isEmpty()) {
			int u = queue.poll();
			for (int x : danhSachKe(adjMatrix, vex)) {
				if(!track[x]) {
					queue.add(x);
					track[x]=true;
					parent[x]=vex;
				}
			}
		}
	}
		public int soThanhPhanLienThong() {
			int count = 0;
			Arrays.fill(track, false);
			for (int i = 0; i < numVexs; i++) {
				if(!track[i]) {
					count++;
					dfs(i);
				}
			}
			return count;
		}
	// cau 13
	public void BFSGraph(int v) {
		queue.add(v);
		while(!queue.isEmpty()) {
			int dinhDangXet = queue.poll();
			if(!bfs.contains(dinhDangXet)) {
				bfs.add(dinhDangXet);
				System.out.println("đang xét: " + dinhDangXet);
			}
			ArrayList<Integer> danhSachKe = danhSachKe(adjMatrix, dinhDangXet);
			System.out.println("danh sách đỉnh kề: "+danhSachKe);
			if(danhSachKe!=null) {
				for (int i = 0; i < danhSachKe.size(); i++) {
					if(!queue.contains(danhSachKe.get(i)) && !bfs.contains(danhSachKe.get(i))) {
						queue.add(danhSachKe.get(i));
					}
				}
			}
			System.out.println("queue" + queue);
			System.out.println("bfs" +bfs);
		}
	}
	// cau 14
		public void DFSGraph(int v) {
			track = new boolean[numVexs+1];
			stack = new Stack<Integer>();
			stack.add(v);
			track[v]=true;
			int count =0;
			while(!stack.isEmpty()) {
				int dinhDangXet = stack.pop();
				System.out.println("xet: "+dinhDangXet);
				if(!dfs.contains(dinhDangXet)) {
					dfs.add(dinhDangXet);
					
				}
				ArrayList<Integer> danhSachKe = danhSachKe(adjMatrix, dinhDangXet);
				System.out.println("danh sách đỉnh kề: "+danhSachKe);
					if(danhSachKe!=null) {
						for (int dinhKe : danhSachKe) {
							if(!stack.contains(dinhKe)&& !track[dinhKe]) {
								stack.push(dinhKe);
								track[dinhKe]=true;
							}
						}		
					}
				System.out.println("stack" + stack);
				System.out.println(dfs);
			}	
		}	
		// dfs đệ quy
			 public void DFSRecursive(int v) {
			        track = new boolean[numVexs];
			        System.out.print("DFS (Recursive): ");
			        DFSUtil(v);
			        System.out.println();
			    }
			    private void DFSUtil(int v) {
			    	track[v] = true;
			        System.out.print(v + " ");
			        for (int neighbor : danhSachKe(adjMatrix, v)) {
			            if (!track[neighbor]) {
			                DFSUtil(neighbor);
			            }
			        }
			    }
		
	
	// cau 15
		 public boolean isConnected(int v) {
		        if(v>=0&&v<numVexs) {
		        	int n = numVexs;
			        boolean[] visited = new boolean[n]; // Mảng đánh dấu đã thăm
			        Queue<Integer> queue = new LinkedList<>();
			        // Bắt đầu BFS từ đỉnh 0
			        queue.add(v);
			        visited[v] = true;
			        int count = 1; // Đếm số đỉnh đã thăm
			        while (!queue.isEmpty()) {
			            int dinhDangXet = queue.poll();
			            ArrayList<Integer> danhSachKe = danhSachKe(adjMatrix, dinhDangXet);
			            if (danhSachKe != null) {
			                for (int u : danhSachKe) {
			                    if (!visited[u]) {
			                        queue.add(u);
			                        visited[u] = true;
			                        count++;
			                    }
			                }
			            }
			        }
			        return count==n;
		        }else {
		        	return false;
		        }
		 }
	// cau 16
		 public abstract List<Integer> findPath(int start, int end);
			
		
	// cau 17
		public boolean checkBipartiteGraph(int start) {
			 int n = this.adjMatrix.length;
			 int[] color = new int[n]; 
			 Arrays.fill(color, 0); 
			 Queue<Integer> queue = new LinkedList<>();
			 queue.add(start);
			 color[start] = 1; 
			 while (!queue.isEmpty()) {
				 int dinhDangXet = queue.poll();
			     for (int i = 0; i < n; i++) {
			    	 if (this.adjMatrix[dinhDangXet][i] == 1) {
			    		 if (color[i] == 0) { 
			        	 color[i] = -color[dinhDangXet];
			             queue.add(i);
			    		 }else if (color[i] == color[dinhDangXet]) {
			    			 return false;
			    		 }
			         }
			     }
			 }
			return true;
		}
	//cau18
		public abstract String checkEuler();
		public abstract boolean isEuler();
		public abstract boolean isPathEuler();
	//cau 20
		public abstract ArrayList<Integer> findEulerCycle( int start);
	//câu 21
		public abstract ArrayList<Integer> findPathEuler();
	// câu 24
		public abstract List<Integer> findHamiltonianCycle(int start);
	//cau 25
		public abstract List<Integer> findHamiltonianPath(int start);
		//cau 26
		public abstract void findMST(int start, int[][] adjMatrix);
		//cau 27
		public abstract int[][] dfsMST(int v);
		//cau 28
		public abstract int[][] bfsMST(int v);
		//cau 29
		public abstract boolean hasCircle(int u, int v, int[][] a);
		// câu 30
		public abstract int[][] kruskalMST();
		//câu 31
		public abstract int[][] primMST();
		public abstract int[][] dijkstra(int v);
		public abstract void bellmanfod();
}
