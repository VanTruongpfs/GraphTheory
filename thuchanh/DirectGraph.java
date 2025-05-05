package thuchanh;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DirectGraph extends Graph{

	@Override
	public boolean checkValid(int[][] array) {
		return false;
	}

	@Override
	public void addEdge(int[][] array, int v1, int v2) {
		if(v1<0||v1>numVexs||v2<0||v2>numVexs|| v1==v2) {
			System.out.println("Sai");
		}else {
			array[v1][v2]=1;
		}
		
	}

	@Override
	public void removeEdge(int[][] array, int v1, int v2) {
		if(v1<0||v1>numVexs||v2<0||v2>numVexs|| v1==v2) {
			System.out.println("Sai");
		}else {
			 array[v1][v2]=0;
		}
		
	}

	@Override
	public int deg(int v) {
		int count=0;
		if(v>=0 && v<numVexs) {
			for (int i = 0; i < numVexs; i++) {
				if(adjMatrix[v][i]!=0) {
				 count++;
				}
			}
		}else {
			return -1;
		}
		return count;
	}

	@Override
	public int sumDeg() {
		int result =0;
		for (int i = 0; i <numVexs; i++) {
			result+=deg(i);
		}
		return result;
	}

	@Override
	public int sumVertexs() {
		return numVexs;
	}

	@Override
	public int sumEdges() {
		return sumDeg();
	}

	@Override
	public boolean checkConnection() {
		int[][] matrix = new int[numVexs][numVexs];
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				if(adjMatrix[i][j]==1) {
					matrix[i][j]=matrix[j][i]=1;
				}
			}
		}
		
		track = new boolean[numVexs];
		int count =0;
		track[0] = true;
		count++;
		for (int i = 0; i < matrix.length; i++) {
			if(track[i]) {
				for (int j = 0; j < matrix[i].length; j++) {
					if(matrix[i][j]!=0 && track[j]==false) {
						track[j]=true;
						count++;
						if(count==numVexs) {
							return true;
						}
					}
				}
				System.out.println();
			}
		}
		return false;
	}

	@Override
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

	@Override
	public boolean isEuler() {
		for (int i = 0; i < adjMatrix.length; i++) {
			int inDeg =0;
			int outDeg = 0;
			for (int j = 0; j < adjMatrix[i].length; j++) {
				if(adjMatrix[i][j]!=0) {
					outDeg++;
				}
				if(adjMatrix[j][i]!=0) {
					inDeg++;
				}
			}
			if(inDeg!=outDeg) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isPathEuler() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int inMoreOutAndOutMoreIn = 0;
		for (int i = 0; i < adjMatrix.length; i++) {
			int inDeg =0;
			int outDeg = 0;
			for (int j = 0; j < adjMatrix[i].length; j++) {
				if(adjMatrix[i][j]!=0) {
					outDeg++;
				}
				if(adjMatrix[j][i]!=0) {
					inDeg++;
				}
			}
			if(outDeg-inDeg==1 || inDeg-outDeg==1) {
				inMoreOutAndOutMoreIn++;
			}
			if(outDeg-inDeg==0) {
				list.add(1);
			}
		}
		if(inMoreOutAndOutMoreIn!=2 || inMoreOutAndOutMoreIn!=0) {
			return false;
		}
		return list.size()+inMoreOutAndOutMoreIn==numVexs;
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
				           adjMatrix[current][next] = 0;
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
	//cau 21
	@Override
	public ArrayList<Integer> findPathEuler() {
	    Stack<Integer> stack = new Stack<>();
	    ArrayList<Integer> eulerPath = new ArrayList<>();

	    int start = findStartNode(); // Tìm đỉnh bắt đầu đường đi Euler

	    if (start == -1) {
	        System.out.println("Đồ thị không có đường đi Euler.");
	        return eulerPath;
	    }

	    stack.push(start);

	    while (!stack.isEmpty()) {
	        int current = stack.peek();
	        boolean hasNext = false;

	        for (int next = 0; next < numVexs; next++) {
	            if (adjMatrix[current][next] > 0) { // Nếu có cạnh từ current -> next
	                stack.push(next);
	                adjMatrix[current][next]--; // Xóa cạnh (đồ thị có hướng)
	                hasNext = true;
	                break;
	            }
	        }

	        if (!hasNext) {
	            eulerPath.add(stack.pop()); // Khi không còn cạnh, thêm vào đường đi
	        }
	    }

	    Collections.reverse(eulerPath); // Đảo ngược để có thứ tự đúng
	    return eulerPath;
	}

	// 🔍 Hàm tìm đỉnh bắt đầu đường đi Euler
	private int findStartNode() {
	    int start = -1, end = -1;

	    for (int i = 0; i < numVexs; i++) {
	        int outDegree = 0, inDegree = 0;

	        for (int j = 0; j < numVexs; j++) {
	            outDegree += adjMatrix[i][j];
	            inDegree += adjMatrix[j][i];
	        }

	        if (outDegree - inDegree == 1) {
	            if (start == -1) {
	                start = i; // Đỉnh có bậc ra nhiều hơn bậc vào → Đỉnh bắt đầu
	            } else {
	                return -1; // Nếu có hơn 1 đỉnh như vậy, không tồn tại đường đi Euler
	            }
	        } else if (inDegree - outDegree == 1) {
	            if (end == -1) {
	                end = i; // Đỉnh có bậc vào nhiều hơn bậc ra
	            } else {
	                return -1; // Nếu có hơn 1 đỉnh như vậy, không tồn tại đường đi Euler
	            }
	        }
	    }

	    return (start != -1) ? start : 0; // Nếu không có đỉnh bắt đầu riêng biệt, chọn bất kỳ đỉnh có cạnh
	}
	//cau 16
	@Override
	public ArrayList<Integer> findPath(int start, int end) {
	    Stack<Integer> stack = new Stack<>();
	    ArrayList<Integer> path = new ArrayList<>();
	    boolean[] visited = new boolean[numVexs];
	    
	    stack.push(start);

	    while (!stack.isEmpty()) {
	        int current = stack.pop();

	        if (!visited[current]) {
	            path.add(current);
	            visited[current] = true;
	        }

	        if (current == end) {
	            return path; // Tìm thấy đường đi
	        }

	        for (int next = 0; next < numVexs; next++) {
	            if (adjMatrix[current][next] > 0 && !visited[next]) {
	                stack.push(next);
	            }
	        }
	    }

	    return new ArrayList<>(); // Không tìm thấy đường đi
	}
	// câu 24
			public List<Integer> findHamiltonianCycle(int start) {
			    Stack<Integer> stack = new Stack<>();
			    boolean[] visited = new boolean[numVexs];
			    List<Integer> path = new ArrayList<>();
			    stack.push(0);
			    visited[0] = true;
			    path.add(0);
			    while (!stack.isEmpty()) {
			        int current = stack.peek();
			        // Nếu đã đi qua tất cả các đỉnh, kiểm tra xem có thể quay về 0 không
			        if (path.size() == numVexs) { 
			            if (adjMatrix[current][0] == 1) { 
			                path.add(0);
			                return path;
			            }
			        }

			        boolean foundNext = false;
			        for (int next = 0; next < numVexs; next++) {
			            if (!visited[next] && adjMatrix[current][next] == 1) {
			                stack.push(next);
			                visited[next] = true;
			                path.add(next);
			                foundNext = true;
			                break;
			            }
			        }

			        // Nếu không tìm được đỉnh tiếp theo, quay lui
			        if (!foundNext) {
			            stack.pop();
			            if (!stack.isEmpty()) {
			                int last = path.remove(path.size() - 1);
			                visited[last] = false; // Sửa lỗi quan trọng ở đây
			            }
			        }
			    }

			    return null; // Không tìm thấy chu trình Hamilton
			}
		//cau 25
			public List<Integer> findHamiltonianPath(int start) {
		        Stack<Integer> stack = new Stack<>();
		        boolean[] visited = new boolean[numVexs];
		        List<Integer> path = new ArrayList<>();

		        stack.push(start);
		        visited[start] = true;
		        path.add(start);

		        while (!stack.isEmpty()) {
		            int current = stack.peek();
		            // Nếu đã đi qua tất cả các đỉnh
		            if (path.size() == numVexs) {
		                return path;
		            }
		            boolean foundNext = false;
		            for (int next = 0; next < numVexs; next++) {
		                if (!visited[next] && adjMatrix[current][next] == 1) {
		                    stack.push(next);
		                    visited[next] = true;
		                    path.add(next);
		                    foundNext = true;
		                    break;
		                }
		            }

		            // Nếu không tìm được đường đi tiếp, quay lui
		            if (!foundNext) {
		                stack.pop();
		                if (!stack.isEmpty()) {
		                    visited[current] = false;
		                    path.remove(path.size() - 1);
		                }
		            }
		        }
		        return null; // Không tìm thấy đường đi Hamilton
		    }
			//cau 26
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
			
			//cau 27
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
			//cau 28
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
			@Override
			public boolean hasCircle(int v, int u, int[][] a) {
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
			@Override
			public int[][] kruskalMST() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int[][] primMST() {
				// TODO Auto-generated method stub
				return null;
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

			@Override
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
				// Kiểm tra chu trình âm
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
			            System.out.print("Đỉnh " + i + ": khoảng cách = " + L[i] + ", đường đi: ");
			            printPath(pathPreVext, i);
			            System.out.println(i);
			        }
			    }
			}

			private void printPath(int[] pathPreVext, int j) {
			    if (pathPreVext[j] == -1) return;
			    printPath(pathPreVext, pathPreVext[j]);
			    System.out.print(pathPreVext[j] + " -> ");
			}
		
}
