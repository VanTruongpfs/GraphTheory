package thuchanh;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
				if(adjMatrix[v][i]==1) {
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

}
