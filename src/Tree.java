import java.util.ArrayList;
import java.util.Random;

public class Tree {

	// 42000 [id]
	public static int[]					id;
	// 42000 [index], [256 pixels as a vector]
	public static int[][]				dataTrain;

	public int[]						tree_thresh;
	public int[]						tree_pixel;
	public int							tree_sz;		// This is the number of nodes in the tree - 1, since we are counting from 0.

	public int[]						modes;			// This array corresponds to the bottom row of the tree. It gives us the classification.
	public int							maxHeight;

	public static Random				r;
	public static ArrayList<Integer>	trainHelper;

	public static void init() {
		r = new Random();
		trainHelper = new ArrayList<Integer>();
		for (int i = 0; i < 42000; i++)
			trainHelper.add(i);
	}

	public Tree(int maxHeight) {
		this.maxHeight = maxHeight;
		build_strct();
		train();
	}

	public void build_strct() {
		tree_sz = (1 << maxHeight + 1) - 2;
		tree_thresh = new int[tree_sz];
		tree_pixel = new int[tree_sz];
		for (int i = 0; i < tree_pixel.length; i++)
			tree_pixel[i] = r.nextInt(28 * 28);
		for (int i = 0; i < tree_thresh.length; i++)
			tree_thresh[i] = r.nextInt(256);
	}

	public int left(int n) {
		return 2 * n + 1;
	}

	public int right(int n) {
		return 2 * n + 2;
	}

	// returns the [0,9] classification based on an arraylist of inds of training data.
	public int mode_from_inds(ArrayList<Integer> al) {
		int[] d = new int[10];
		for (int i = 0; i < al.size(); i++)
			d[id[al.get(i)]]++;
		return Utility.indexOfMax(d);
	}

	// assumes the index is a leaf node.
	public int ind_to_mode_ind(int index) {
		// int tmp = modes.length - (tree_sz - index) - 1;
		// System.out.println("map " + index + " to " + tmp);
		return modes.length - (tree_sz - index) - 1;
	}

	public void train() {
		modes = new int[1 << maxHeight];
		train_help(trainHelper, 0);
	}

	public void train_help(ArrayList<Integer> al, int index) {
		if (left(index) > tree_sz) {
			modes[ind_to_mode_ind(index)] = mode_from_inds(al);
			return;
		}
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();
		for (int i = 0; i < al.size(); i++) {
			if (dataTrain[al.get(i)][tree_pixel[index]] < tree_thresh[index])
				left.add(al.get(i));
			else
				right.add(al.get(i));
		}
		train_help(left, left(index));
		left = null;
		train_help(right, right(index));
		right = null;
	}

	public int run(int[] i) {
		if (i.length != 28 * 28) {
			System.out.println("i length not 256");
			return -1;
		}
		return runHelp(i, 0);
	}

	public int runHelp(int[] i, int index) {
		if (left(index) > tree_sz)
			return modes[ind_to_mode_ind(index)];
		if (i[tree_pixel[index]] < tree_thresh[index])
			return runHelp(i, left(index));
		return runHelp(i, right(index));
	}
}
