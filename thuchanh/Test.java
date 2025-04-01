package thuchanh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Test {
//	public static int[][] readFile(String file){
//         int[][] arr = null;
//		try{
//			 List<String> lines = Files.readAllLines(Paths.get(file));
//	         int n = Integer.valueOf(lines.get(0));
//	         arr=new int[n][n];
//           for (int i = 1; i <= n; i++) {
//        	   String line = lines.get(i);
//        	   int row =i-1;
//        	   int col = 0;
//        	  for (int j = 0; j < line.length(); j++) {
//        		   if(line.charAt(j)==' ') {
//        			   continue;
//        		   }else {
//        			   arr[row][col] = Character.getNumericValue(line.charAt(j));
//        		   col++;
//        		   }
//        	  }
//           }
//        }catch (IOException e) {
//            System.err.println("Lỗi khi đọc file: " + e.getMessage());
//        }
//		return arr;
//	}
	public ArrayList<Integer> danhSachKe(int[][] arr, int v){
		HashMap<Integer, ArrayList<Integer>> danhSach = new HashMap<Integer, ArrayList<Integer>>();
		
		for (int i = 0; i < arr.length; i++) {
			ArrayList<Integer> canhKe = new ArrayList<Integer>();
			for (int j = 0; j < arr[i].length; j++) {
				if(arr[i][j]==1) {
					canhKe.add(j+1);
					danhSach.put(i+1, canhKe);
				}
				//kiem tra canh song song
				if(arr[i][j]==2) {
					canhKe.add(j+1);
					canhKe.add(j+1);
					danhSach.put(i+1, canhKe);
				}
			}
		}
		return danhSach.get(v);
	}
//	public static void display(int[][] arr) {
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr[i].length; j++) {
//				System.out.print(arr[i][j]+" ");
//			}
//			System.out.println();
//		}
//	}
//	public static void main(String[] args) {
//		display(readFile("src/thuchanh/data.txt"));
//		System.out.println(danhSachKe(readFile("src/thuchanh/data.txt")));
//	}
}

