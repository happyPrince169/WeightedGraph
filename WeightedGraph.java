package Graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class WeightedGraph {
	private static int[][] matrix;
	private static int[][] distance;
	private int vertices;
	private Edge[] edgeList;
	public static final int INFINITY = Integer.MAX_VALUE;
	LinkedList<Integer> linkedList = new LinkedList<>();

	public WeightedGraph(int v) {
		this.vertices = v;
		matrix = new int[v][v];
		distance = new int[v][v];
	}

	// Thêm cạnh vào đồ thị
	public int addEdge(int from, int to, int weight) {
		Edge edge = new Edge(from, to, weight);
		if (from >= vertices || to >= vertices) {
			System.out.println("The vertex does not exists");
			return -1;
		} else {
			if (edgeList == null) {
				edgeList = new Edge[1];
				edgeList[0] = edge;
			} else {
				Edge[] tmp = new Edge[edgeList.length + 1];
				for (int i = 0; i < edgeList.length; i++) {
					tmp[i] = edgeList[i];
					tmp[edgeList.length] = edge;
				}
				edgeList = new Edge[tmp.length];
				for (int i = 0; i < tmp.length; i++) {
					edgeList[i] = tmp[i];
				}
			}
			matrix[from][to] = edge.getWeight();
			return matrix[from][to];
		}
	}

	public int getEdge(int from, int to) {
		if (from >= vertices || to >= vertices) {
			System.out.println("The vertex does not exists");
			return -1;
		} else {
			return matrix[from][to];
		}
	}

	public void printGraph(int mat[][]) {
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

	// Thêm các cạnh của đồ thị, áp dụng cho đồ thị 4 đỉnh
	// là 4 ga tàu trong bài đã cho
	public void addGraph(WeightedGraph graph) {
		graph.addEdge(0, 0, 0);
		graph.addEdge(0, 1, 5);
		graph.addEdge(0, 2, 1);
		graph.addEdge(0, 3, 2);
		graph.addEdge(1, 0, 5);
		graph.addEdge(1, 1, 0);
		graph.addEdge(1, 2, 3);
		graph.addEdge(1, 3, 0);
		graph.addEdge(2, 0, 1);
		graph.addEdge(2, 1, 3);
		graph.addEdge(2, 2, 0);
		graph.addEdge(2, 3, 4);
		graph.addEdge(3, 0, 2);
		graph.addEdge(3, 1, 0);
		graph.addEdge(3, 2, 4);
		graph.addEdge(3, 3, 0);
	}

	// Tìm đường ngắn nhất bằng thuật toán Floyd Warshall
	public void findShortest(int from, int to) {
		if (from >= vertices || to >= vertices) {
			System.out.println("The vertex does not exists");
		} else {

			for (int i = 0; i < vertices; i++) {
				for (int j = 0; j < vertices; j++) {
					distance[i][j] = matrix[i][j];
				}
			}
			for (int inter = 0; inter < vertices; inter++) {
				for (int i = 0; i < vertices; i++) {
					for (int j = 0; j < vertices; j++) {
						if (distance[i][inter] + distance[inter][j] < distance[i][j]) {
							distance[i][j] = distance[i][inter] + distance[inter][j];
						}
					}
				}
			}
			
		}
	}
	public void findPath(int from, int to, int[][] distance) {
		for (int inter = from; inter < vertices; inter++) {
			if(inter!=from&&inter!=to) {
					if (distance[from][inter] + distance[inter][to] == distance[from][to]) {
							linkedList.add(inter);
						}
			}
		}
	}

	// In danh sách các nhà ga trung gian
	public void printList() {
		if (!linkedList.isEmpty()) {
			Iterator<Integer> it = linkedList.iterator();
			while (it.hasNext())
				System.out.println(addTrainStation(it.next()));
		}
	}

	// Nhập ga di và ga đến
	public int addVertex() {
		Scanner sc = new Scanner(System.in);
		int v;
		while (!sc.hasNextInt()) {
			System.out.println("Invalid input\\n Type the integer-type number:");
			return addVertex();
		}
		v = sc.nextInt();
		while (v > 3 || v < 0) {
			System.out.println("Vui lòng nhập theo danh sách nhà ga");
			return addVertex();
		}
		return v;
	}

	public void findTrainStation() {
		System.out.println("Danh sách nhà ga");
		System.out.println("0. Kim Mã");
		System.out.println("1. Phố cổ");
		System.out.println("2. Lê Duẩn");
		System.out.println("3. Láng");
	}

	public String addTrainStation(int v) {
		switch (v) {
		case 0:
			return "Kim Mã";
		case 1:
			return "Phố Cổ";
		case 2:
			return "Lê Duẩn";
		case 3:
			return "Láng";
		default:
			return null;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] a = {1,2};
		int b = a.length;
		System.out.println(b);		
		Scanner sc = new Scanner(System.in);
		int v = 4; // 4 đỉnh theo đề bài là 4 ga tàu
		WeightedGraph graph = new WeightedGraph(v);
		graph.addGraph(graph);
		graph.printGraph(matrix); //Ma trận kề biểu diễn đồ thị
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				if (i == j) {
					matrix[i][j] = 0;
					continue;
				}
				if (matrix[i][j] == 0) {
					matrix[i][j] = INFINITY;
				}
			}
		}
		

		System.out.println("Vui lòng chọn ga đi bằng nhập số từ 0 - 3");
		graph.findTrainStation();
		int from = graph.addVertex();
		System.out.println("Vui lòng chọn ga đến bằng nhập số từ 0 - 3");
		graph.findTrainStation();
		int to = graph.addVertex();
		
		
		if (to == from) {
			System.out.println("Ga đi trùng ga đến");
		} else {
			graph.findShortest(from, to);
			graph.findPath(from, to, distance);
			System.out.println("Khoảng cách ngắn nhất từ " + graph.addTrainStation(from) + " tới "
					+ graph.addTrainStation(to) + " là " + distance[from][to] + " km");
			System.out.println("Lộ trình theo thứ tự sau:");
			//System.out.println(graph.addTrainStation(from));
			graph.printList();
			//System.out.println(graph.addTrainStation(to));
			 graph.printGraph(distance); //Ma trận khoảng cách tính được từ Floy Warshall
		}
	}
}
