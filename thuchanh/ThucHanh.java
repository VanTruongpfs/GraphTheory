package thuchanh;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ThucHanh {
	//cau 1
	public static int[][] loadGraph(String file){
		int[][] arr = null;
		try {
			 List<String> lines = Files.readAllLines(Paths.get(file));
			 int n = Integer.parseInt(lines.get(0));
			 arr = new int[n][n];
			 for (int i = 1; i < lines.size(); i++) {
				 int row = i-1;
				 int col = 0;
				 String temp = lines.get(i);
				for (int j = 0; j < temp.length(); j++) {
					if(temp.charAt(j)!=' ') {
						arr[row][col] = Character.getNumericValue(temp.charAt(j));
						col++;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arr;
	}
	//cau 2
	public static void printMatrix(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		printMatrix(loadGraph("src/thuchanh/test.txt"));
		
	}
}
