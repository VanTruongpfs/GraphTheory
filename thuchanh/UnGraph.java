package thuchanh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
	@Override
	public void addEdge(int[][] matrix, int v1, int v2) {
		if(v1<0||v1>numVexs || v2<0||v2>numVexs || v1==v2) {
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
		            if (u == end) {
		            	break;
		            }
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
			//20
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
			//21
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
			//24
			@Override
			public List<Integer> findHamiltonianCycle(int start) {
		        Stack<Integer> stack = new Stack<>();
		        boolean[] visited = new boolean[numVexs];
		        List<Integer> path = new ArrayList<>();

		        stack.push(start);
		        visited[start] = true;
		        path.add(start);
		        while (!stack.isEmpty()) {
		            int node = stack.pop();
		            for (int neighbor = 0; neighbor < numVexs; neighbor++) {
		                if (adjMatrix[node][neighbor]==1 && !visited[neighbor]) {
		                    stack.push(neighbor);
		                    visited[neighbor] = true;
		                    path.add(neighbor);
		                    break;
		                }
		            }
		            // Nếu đi qua đủ numVertices và có cạnh quay về startNode -> Có chu trình Hamilton
		            if (path.size() == numVexs && adjMatrix[start][path.get(path.size()-1)]==1 ) {
		            	path.add(start);
		                return path;
		            }
		        }
		        return null; // Không tìm thấy chu trình Hamilton
		    }
			//25
			@Override
			public List<Integer> findHamiltonianPath(int start) {
				  Stack<Integer> stack = new Stack<>();
			        boolean[] visited = new boolean[numVexs];
			        List<Integer> path = new ArrayList<>();
			        stack.push(start);
			        visited[start] = true;
			        path.add(start);
			        while (!stack.isEmpty()) {
			            int node = stack.pop();
			            for (int neighbor = 0; neighbor < numVexs; neighbor++) {
			                if (adjMatrix[node][neighbor]==1 && !visited[neighbor]) {
			                    stack.push(neighbor);
			                    visited[neighbor] = true;
			                    path.add(neighbor);
			                    break;
			                }
			            }
			            // Nếu đi qua đủ numVertices và có cạnh quay về startNode -> Có chu trình Hamilton
			            if (path.size() == numVexs) {
			                return path;
			            }
			        }

			        return null; // Không tìm thấy chu trình Hamilton
			    }
		// cau 27
			@Override
			public int[][] bfsMST(int v) {
				int[][] tree = new int[numVexs][numVexs];
				if(checkConnection()) {
					Queue<Integer> queue = new LinkedList<Integer>();
					queue.add(v);
					track = new boolean[numVexs];
					track[v]=true;
					while(!queue.isEmpty()) {
						int u = queue.poll();
						for (int i = 0; i < numVexs; i++) {
							if (adjMatrix[u][i]!=0 && !track[i]) {
								if(!hasCircle(u, i, tree)) {
									tree[u][i]=tree[i][u]=1;
									System.out.println("Canh"+ u+"->"+ i);
									track[i]=true;
									queue.add(i);
								}
								else {
									System.out.println("Canh"+u+"->"+i+" tao cho trinh");
								}
							}
						}
					}
					System.out.println("Cay bao trum theo bfs");
					printMatrix(tree);
				}else {
					System.out.println("Đồ thị không liên thông");
				}
				return tree;
			}
			// cau 28
			@Override
			public int[][] dfsMST(int v) {
				int[][] tree = new int[numVexs][numVexs];
				if(checkConnection()) {
					Stack<Integer> stack = new Stack<Integer>();
					stack.push(v);
					track = new boolean[numVexs];
					track[v]=true;
					while(!stack.isEmpty()) {
						int u = stack.pop();
						for (int i = 0; i < numVexs; i++) {
							if (adjMatrix[u][i]!=0 && !track[i]) {
								if(!hasCircle(u, i, tree)) {
									tree[u][i]=tree[i][u]=1;
									System.out.println("Canh"+ u+"->"+ i);
									track[i]=true;
									stack.push(i);
								}
								else {
									System.out.println("Canh"+u+"->"+i+" tao cho trinh");
								}
							}
						}
					}
					System.out.println("Cay bao trum theo dfs");
					printMatrix(tree);
				}else {
					System.out.println("đồ thị không liên thông");
				}
			return tree;
			}
			// cau 29
			@Override
			public boolean hasCircle(int u, int v, int[][] a) {
				 boolean[] track = new boolean[numVexs]; // Mảng đánh dấu các đỉnh đã duyệt
				 int[] parent = new int[numVexs]; // Mảng lưu cha của từng đỉnh
				    // Khởi tạo
				    Arrays.fill(parent, -1); // Đặt lại mảng cha
				    Arrays.fill(track, false); // Đặt lại mảng theo dõi
				    Queue<Integer> queue = new LinkedList<>();
				    queue.add(u);
				    track[u]=true;
				    while(!queue.isEmpty()) {
				    	int dinhDangXet = queue.poll();
				    	for (int i = 0; i < numVexs; i++) {
							if(a[dinhDangXet][i]!=0) {
								if(!track[i]) {
									track[i]=true;
									parent[i]=dinhDangXet;
									queue.add(i);
								}else if(i!=parent[dinhDangXet]) {
									return true;
								}
							}
						}
				    }
				 // Không có chu trình, và có đường tới v
				 return track[v];	    
		}
			// cau 26
			public void recursiveMST(int v, int[][] matrix, boolean[] visited, int[][] tree) {
				visited[v] = true;
			    for (int i = 0; i < adjMatrix.length; i++) {
			        if (adjMatrix[v][i] != 0 && !visited[i]) {
			            tree[v][i] = tree[i][v] = 1; // Thêm cạnh vào cây bao trùm
			            System.out.println("Đã thêm cạnh " + v + " -> " + i);
			            recursiveMST(i, adjMatrix, visited, tree); // Gọi đệ quy cho đỉnh tiếp theo
			        }
			    }
			}
			@Override
			public void findMST(int start, int[][] adjMatrix) {
			    int numVexs = adjMatrix.length;
			    int[][] tree = new int[numVexs][numVexs];
			    boolean[] visited = new boolean[numVexs];
			    recursiveMST(start, adjMatrix, visited, tree);
			    System.out.println("Cây bao trùm tối thiểu theo DFS đệ quy:");
			    printMatrix(tree);
			}
			//
			@Override
			public int[][] kruskalMST() {
				int[][] tree = new int[numVexs][numVexs];
				List<Edge> list = new ArrayList<Edge>();
				track = new boolean[numVexs];
				for (int i = 0; i < adjMatrix.length; i++) {
					for (int j = 0; j < tree[i].length; j++) {
						if(adjMatrix[i][j]!=0 && !track[i]) {
							list.add(new Edge(i, j, adjMatrix[i][j]));
							System.out.println("đã thêm cạnh: " +i+"-" +j +":"+ adjMatrix[i][j]);
							adjMatrix[i][j]=adjMatrix[j][i]=0;
						}
					}
					track[i]=true;
				}
				Collections.sort(list);
				System.out.println("sắp xếp danh sách: ");
				System.out.println(list);
				int soCanhDaTham = 0;
				int duongDiNganNhat = 0;
				for (Edge edge : list) {
					if(!hasCircle(edge.srs, edge.dest, tree)) {
						tree[edge.srs][edge.dest]=tree[edge.dest][edge.srs] = edge.weight;
						duongDiNganNhat+=edge.weight;
					}
				}
				printMatrix(tree);
				System.out.println("cây bao trùm có trọng số ngắn nhất: "+ duongDiNganNhat);
				return null;
			}
		// câu 31
			@Override
			public int[][] primMST() {
				HashSet<Integer> v = new HashSet<Integer>();
				int[][] tree = new int[numVexs][numVexs];
				int w = 0;
				for (int i = 0; i < numVexs; i++) {
					v.add(i);
				}
				List<Integer> vt = new ArrayList<Integer>();
				v.remove(0);
				vt.add(0);
				while (!v.isEmpty()) {
					List<Edge> u = new ArrayList<Edge>();
					for (int s : vt) {
						for (int c : v) {
							if (adjMatrix[s][c] != 0) {
								u.add(new Edge(s, c, adjMatrix[s][c]));
							}
						}
					}
					if (!u.isEmpty()) {
						int min = Integer.MAX_VALUE;
						int x = -1;
						int y = -1;
						for (Edge e : u) {
							if (e.weight < min) {
								min = e.weight;
								x = e.srs;
								y = e.dest;
							}
						}
						tree[x][y] = tree[y][x] = min;
						w += min;
						vt.add(y);
						v.remove(y);
					}
				}
				printMatrix(tree);
				System.out.println("cây bao trùm có trọng số ngắn nhất: " + w);
				return tree;
			}
			@Override
			public int[][] dijkstra(int u) {
				track = new boolean[numVexs];
				int[] distance = new int [numVexs];
				int[] pathPreVex = new int[numVexs];
				for (int i = 0; i < pathPreVex.length; i++) {
					track[i]=false;
					distance[i]=Integer.MAX_VALUE;
					pathPreVex[i]=-1;
				}
				distance[u]=0;
				int demso =0;
				while(demso<numVexs-1) {
					int v = -1;
					int minWeight = Integer.MAX_VALUE;
					for (int i = 0; i < numVexs; i++) {
						if(distance[i]<minWeight&&!track[i]) {
							minWeight=distance[i];
							v=i;
						}
					}
					for (int i = 0; i < numVexs; i++) {
						if(adjMatrix[v][i]>0&&track[i]==false) {
							if(distance[i]>distance[v]+adjMatrix[v][i]) {
								distance[i]=distance[v]+adjMatrix[v][i];
								pathPreVex[i]=v;
							}
						}
					}
					track[v]=true;
					demso++;
				}
				for (int i = 0; i < numVexs; i++) {
					if(u==i) {
						continue;
					}
					System.out.println("duong di tu dinh "+u+ " toi dinh" +i);
					if(pathPreVex[i]==-1) {
						System.out.println("Khong tim duoc duong di");
						break;
					}
					int dest =i;
					Stack<Integer> st = new Stack<Integer>();
					st.push(dest);
					while(dest!=u){
						st.push(pathPreVex[dest]);
						dest= pathPreVex[dest];
					
					}
					System.out.print(st.pop());
					while(!st.isEmpty()) {
						System.out.print("--->"+st.pop());
						
					}
					System.out.println("\n distance = "+distance[i]);
				}
			
				return null;
			}
	public void bellmanfod() {
		int[] pathPreVext = new int[numVexs];
		int[] L = new int[numVexs];
		int max = Integer.MAX_VALUE;
		for (int i = 0; i < numVexs; i++) {
			pathPreVext[i] = -1;
			L[i] = max;
		}
		L[0] = 0;
		boolean stop = false; int k =0;
		while(!stop) {
			stop = true;
			for (int i = 0; i < numVexs; i++) {
				for (int j = 0; j < numVexs; j++) {
					if(adjMatrix[i][j]!=0 && L[i]!=max) {
						if(L[j]>L[i]+adjMatrix[i][j]) {
							pathPreVext[j]=i;
							stop=false;
							L[j] = L[i] + adjMatrix[i][j];
						}
					}
				}
			}
			k++;
			if(k>numVexs-1) {
				break;
			}
		}
//		 Kiểm tra chu trình âm
	    for (int i = 0; i < numVexs; i++) {
	        for (int j = 0; j < numVexs; j++) {
	            if (adjMatrix[i][j] != 0 && L[i] != max) {
	                if (L[j] > L[i] + adjMatrix[i][j]) {
	                    System.out.println("Đồ thị có chu trình âm.");
	                    return;
	                }
	            }
	        }
	    }

	    // In kết quả
	    System.out.println("Đường đi ngắn nhất từ đỉnh " + 0 + ":");
	    for (int i = 0; i < numVexs; i++) {
	        if (L[i] == max) {
	            System.out.println("Đỉnh " + i + ": không thể tới.");
	        } else {
	            System.out.print("đường đi từ " + 0 + " đến đỉnh " + i + ":  ");
	            printPath(pathPreVext, i);
	            System.out.println(i);
	        }
	    }
	}

	private void printPath(int[] pathPreVext, int i) {
	    if (pathPreVext[i] == -1) return;
	    printPath(pathPreVext, pathPreVext[i]);
	    System.out.print(pathPreVext[i] + " -> ");
	}
}

