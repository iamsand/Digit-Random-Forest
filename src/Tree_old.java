import java.util.ArrayList;
import java.util.Random;

public class Tree {

	// 42000 [id]
	public static int[]					id;
	// 42000 [index], [256 pixels as a vector]
	public static int[][]				dataTrain;

	public Node							baseNode;
	public int[]						modes;				// This array corresponds to the bottom row of the tree. It gives us the classification.
	public int							maxHeight;
	public int							leafIndex;			// This is used for numbering all the leaves.

	public static Random				r	= new Random();
	public static ArrayList<Integer>	trainHelper;

	public Tree(int maxHeight) {
		baseNode = new Node(0);
		this.maxHeight = maxHeight;
		leafIndex = 0;
		build(baseNode);
		if (trainHelper == null) {
			trainHelper = new ArrayList<Integer>();
			for (int i = 0; i < 42000; i++)
				trainHelper.add(i);
		}
		modes = new int[leafIndex];
		train(trainHelper, baseNode);
	}

	public void build(Node p) {
		if (p.height >= maxHeight) {
			p.num = leafIndex;
			leafIndex++;
			return;
		}
		Node n1 = new Node(p.height + 1);
		Node n2 = new Node(p.height + 1);
		p.childL = n1;
		p.childR = n2;
		build(n1);
		build(n2);
	}

	// Pass this method an array of indicies.
	public static int getModeOf(ArrayList<Integer> al) {
		int[] d = new int[10];
		for (int i : al)
			d[id[i]]++;
		return Utility.indexOfMax(d);
	}

	public void train(ArrayList<Integer> al, Node n) {
		if (n.height == maxHeight) {
			modes[n.num] = getModeOf(al);
			return;
		}
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();
		for (int i = 0; i < al.size(); i++) {
			if (dataTrain[al.get(i)][n.pixelIndex] < n.pixelVal)
				left.add(al.get(i));
			else
				right.add(al.get(i));
		}
		train(left, n.childL);
		left = null; // Optimization?
		train(right, n.childR);
		right = null; // Optimization?
	}

	public int run(int[] i) {
		if (i.length != 28 * 28) {
			System.out.println("i length not 256");
			return -1;
		}
		return runHelp(i, baseNode);
	}

	public int runHelp(int[] i, Node n) {
		if (n.height == maxHeight)
			return modes[n.num];
		if (i[n.pixelIndex] < n.pixelVal)
			return runHelp(i, n.childL);
		return runHelp(i, n.childR);
	}

	class Node {
		int		height;
		int		num;

		int		pixelIndex;
		int		pixelVal;

		Node	childL;
		Node	childR;

		public Node(int height) {
			this.height = height;
			pixelIndex = r.nextInt(28 * 28);
			pixelVal = r.nextInt(256);
		}
	}
}
