package thuchanh;

public class Edge implements Comparable<Edge>{
	int srs, dest, weight;
	
	public Edge(int srs, int dest, int weight) {
		super();
		this.srs = srs;
		this.dest = dest;
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge o) {
		return this.weight - o.weight;
	}

	@Override
	public String toString() {
		return "cạnh: "+ this.srs +"-" +this.dest + ":"+this.weight +"\n" ;
	}
	

}
