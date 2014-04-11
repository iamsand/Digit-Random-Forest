import java.util.ArrayList;
import java.util.Random;

public class Forest {

	ArrayList<Tree>			trees;

	public static Random	r	= new Random();

	public Forest(int pop, int maxHeight) {
		trees = new ArrayList<Tree>();
		while (trees.size() < pop) {
			System.out.println("Building tree " + trees.size() + " ...");
			trees.add(new Tree(maxHeight));
		}
	}

	public int run(int[] i) {
		int[] d = new int[10];
		for (int j = 0; j < trees.size(); j++) {
			int predic = trees.get(j).run(i);
			d[predic]++;
		}
		return Utility.indexOfMax(d);
	}
}
