package thuchanh;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class UnGraph extends Graph{

	public UnGraph() {
		super();
	}
	@Override
	public boolean checkValid(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if(array[i][j]!=array[j][i]) {
					return false;
				}
			}
		}
		return true;
	}
	public void addEdge(int[][] matrix, int v1, int v2) {
		if(v1<0||v1>numVexs||v2<0||v2>numVexs|| v1==v2) {
			System.out.println("Sai");
		}else {
			matrix[v1][v2] = matrix[v2][v1]=1;
		}
	}
	@Override
	public void removeEdge(int[][] matrix, int v1, int v2) {
		if(v1<0||v1>numVexs||v2<0||v2>numVexs) {
			System.out.println("Sai");
		}else {
			matrix[v1][v2] = matrix[v2][v1]=0;
		}
		
	}
	@Override
	public int deg(int v) {
		int sum = 0;
		if(v>=0 && v<this.numVexs) {
			for (int i = 0; i < this.numVexs; i++) {
				sum+=this.adjMatrix[v][i];
			}
		}
		return sum;
	}
	@Override
	public int sumVertexs() {
		return this.numVexs;
	}
	@Override
	public int sumEdges() {
		int sumLevelOfAllVertexs = 0;
		for (int i = 0; i < this.adjMatrix.length; i++) {
			if(this.adjMatrix[i][i]==1) {
				sumLevelOfAllVertexs+=1;
			}
			for (int j = 0; j < this.adjMatrix[i].length; j++) {
				sumLevelOfAllVertexs+=this.adjMatrix[i][j];
			}
		}
		int result = sumLevelOfAllVertexs/2;
		return result;
	}
	@Override
	public int sumDeg() {
		int result = 0;
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				result+=adjMatrix[i][j];
			}
		}
		return result;
	}
	public boolean checkConnection() {
		int count = 0;
		track = new boolean[this.numVexs];
		track[0]=true;
		count++;
		for (int i = 0; i < adjMatrix.length; i++) {
			if(track[i]) {
				for (int j = 0; j < adjMatrix[i].length; j++) {
					if(adjMatrix[i][j]!=0 && track[j]==false) {
						track[j]=true;
						count++;
						if(count==numVexs) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	// cau 16
	 public List<Integer> findPath(int start, int end) {
		 List<Integer> path = new ArrayList<>();
	        if(start>=0 && end <= numVexs) {
	        	queue = new LinkedList<>();
		        track = new boolean[numVexs]; 
		        int[] prev = new int[numVexs]; // Lưu đỉnh cha
		        Arrays.fill(prev, -1); // Mặc định là -1 (chưa có đường đi)
		        queue.add(start);
		        track[start] = true;
		        // BFS tìm kiếm đường đi
		        while (!queue.isEmpty()) {
		            int u = queue.poll();
		            if (u == end) break;
		            if(danhSachKe(adjMatrix, u)!=null) {
		            	for (int v : danhSachKe(adjMatrix, u)) {
			                if (!track[v]) {
			                    queue.add(v);
			                    track[v] = true;
			                    prev[v] = u; // Ghi lại đỉnh cha
			                }
			            }
		            }
		        }

		        for (int at = end; at != -1; at = prev[at]) {
		            path.add(at);
		        }
		        Collections.reverse(path);
		        if (path.get(0) != start) return new ArrayList<>();
		      
	        }
	        return path;
	    }
	// cau 18
		public String checkEuler() {
			String result="";
			if(isEuler()) {
				result+="Đồ thị có chu trình euler và đường đi euler";
			}else {
				if(isPathEuler()) {
					result+="Đồ thị có đường đi Euler và không có chu trình Euler";
				}else {
					result="Đồ thị không có chu trình và không có đường đi Euler";
				}
			}
			return result;
		}
		//cau 19
			@Override
			public boolean isEuler() {
				int count=0;
				if(checkConnection()) {
					for (int i = 0; i < numVexs; i++) {
						if(deg(i)%2==0) {
							count++;
						}
					}
					return count==numVexs;
				}else {
					return false;
				}
			}
			// cau 20
			@Override
			public boolean isPathEuler() {
				int even = 0;
				int odd=0;
				if(checkConnection()) {
					if(isEuler()) {
						return true;
					}else {
						for (int i = 0; i < numVexs; i++) {
							if(deg(i)%2==0) {
								even++;
							}else {
								odd++;
							}
						}
						if(even==numVexs-2|| even==numVexs) {
							return true;
						}
					}
				}
				return false;
			}
			@Override
			public ArrayList<Integer> findEulerCycle( int start) {
				Stack<Integer> stack = new Stack<>();
			    ArrayList<Integer> eulerCycle = new ArrayList<>();
			    
				   if (!isEuler()) {
				        System.out.println("Đồ thị không có chu trình Euler.");
				    }else {
					    stack.push(start);
					    while (!stack.isEmpty()) {
					        int current = stack.peek();
					        boolean hasNext = false;
					        for (int next = 0; next < numVexs; next++) {
					            if (adjMatrix[current][next] != 0) {
						           stack.push(next);
						           adjMatrix[current][next] = adjMatrix[next][current] = 0;
						           hasNext = true;
						           break;
					            }
					        }
					        if (!hasNext) {
					            eulerCycle.add(stack.pop());
					        }
					    }
				    }
				   Collections.reverse(eulerCycle);
				    
				  return eulerCycle;
			}
			@Override
			public ArrayList<Integer> findPathEuler() {
				Stack<Integer> stack = new Stack<Integer>();
				 ArrayList<Integer> pathEuler = new ArrayList<Integer> ();
				if(!isPathEuler()) {
					System.out.println("Đồ thị không có đường đi euler");
				}else {
					int odd = 0;
					for (int i = 0; i < numVexs; i++) {
						if(deg(i)%2!=0) {
							odd=i;
							break;
						}
					}
					stack.push(odd);
					while (!stack.isEmpty()) {
						int current = stack.peek();
						boolean hasNext = false;
				        for (int next = 0; next < numVexs; next++) {
				            if (adjMatrix[current][next] != 0) {
					           stack.push(next);
					           adjMatrix[current][next] = adjMatrix[next][current] = 0;
					           hasNext = true;
					           break;
				            }
				        }
				        if (!hasNext) {
				            pathEuler.add(stack.pop());
				        }
					}
					Collections.reverse(pathEuler);
				}
				return pathEuler;
			}
	
	
}
