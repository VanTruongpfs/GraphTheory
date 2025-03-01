package thuchanh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class Test {
	public static int[][] readFile(String file){
         int[][] arr = null;
		try{
			 List<String> lines = Files.readAllLines(Paths.get(file));
	         int n = Integer.valueOf(lines.get(0));
	         arr=new int[n][n];
           for (int i = 1; i <= n; i++) {
        	   String line = lines.get(i);
        	   int row =i-1;
        	   int col = 0;
        	  for (int j = 0; j < line.length(); j++) {
        		   if(line.charAt(j)==' ') {
        			   continue;
        		   }else {
        			   arr[row][col] = Character.getNumericValue(line.charAt(j));
        		   col++;
        		   }
        	  }
           }
        }catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
		return arr;
	}
	public static void display(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		display(readFile("src/thuchanh/data.txt"));
	}
}

