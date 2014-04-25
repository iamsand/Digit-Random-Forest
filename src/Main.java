import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Main {

	// Arguments
	public static int			max_height		= 20;
	public static int			forest_size		= 30;
	public static int			prt_intrvl		= 5000;
	// End Arguments

	// 20, 25 80%.

	// 42000 [id]
	public static int[]			id;
	// 42000 [index], [256 pixels as a vector]
	public static int[][]		data_train;
	public final static String	file_loc_train	= "train.csv";

	public static int[][]		data_test;
	public final static String	file_loc_test	= "test.csv";

	public static void main(String[] args) throws Exception {
		// Read data.
		long timeStart = System.currentTimeMillis();
		System.out.println("Reading data...");
		read_train_data();
		System.out.println("Reading complete...");
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");

		setup();

		// Build the Forest
		timeStart = System.currentTimeMillis();
		System.out.println("Building forest...");
		setup();
		Forest gump = new Forest(forest_size, max_height);
		System.out.println("Building complete...");
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");

		// i1 misclassified as i2
		int[][] error_tr = new int[10][10];

		// Test on training data.
		timeStart = System.currentTimeMillis();
		System.out.println("Testing on training data...");
		int correct = 0;
		for (int i = 0; i < 42000; i++) {
			if (i % prt_intrvl == 0)
				System.out.println("Testing tr " + i + " ...");
			int pred = gump.run(data_train[i]);
			if (id[i] == pred)
				correct++;
			else {
				error_tr[id[i]][pred]++;
			}
		}
		System.out.println(correct + " of 42000");
		System.out.println("Testing complete...");
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");

		System.out.print("  ");
		for (int i = 0; i < 10; i++) {
			System.out.print(pad(Integer.toString(i)));
		}
		System.out.println();

		for (int r = 0; r < 10; r++) {
			System.out.print(r + " ");
			for (int c = 0; c < 10; c++) {
				System.out.print(pad(Integer.toString(error_tr[r][c])));
			}
			System.out.println();
		}

		/*
		 * // Testing on test data. timeStart = System.currentTimeMillis(); System.out.println("Reading test data..."); read_test_data();
		 * System.out.println("Reading complete..."); System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");
		 * 
		 * timeStart = System.currentTimeMillis(); System.out.println("Testing on testing data..."); int[] pred = new int[28000]; for (int i = 0; i < 28000;
		 * i++) { if (i % prt_intrvl == 0) System.out.println("Training te " + i + " ..."); pred[i] = gump.run(data_test[i]); } write(pred);
		 * System.out.println("Testing complete ..."); System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");
		 */
	}

	public static String pad(String s) {
		if (s.length() >= 10)
			return s;
		return pad(s + " ");
	}

	// This methods reads the train.csv so that we can access it as an int[][].
	public static void read_train_data() throws Exception {
		id = new int[42000];
		data_train = new int[42000][28 * 28];
		BufferedReader br = new BufferedReader(new FileReader("res\\" + file_loc_train));
		String s = br.readLine();
		String[] splts = null;
		for (int i = 0; i < 42000; i++) {
			s = br.readLine();
			id[i] = s.charAt(0) - '0';
			splts = s.split(",");
			for (int j = 0; j < 28 * 28; j++)
				data_train[i][j] = Integer.parseInt(splts[j + 1]);
		}
		br.close();
	}

	public static void read_test_data() throws Exception {
		data_test = new int[28000][28 * 28];
		BufferedReader br = new BufferedReader(new FileReader("res\\" + file_loc_test));
		String s = br.readLine();
		String[] splts = null;
		for (int i = 0; i < 28000; i++) {
			s = br.readLine();
			splts = s.split(",");
			for (int j = 0; j < 28 * 28; j++)
				data_test[i][j] = Integer.parseInt(splts[j]);
		}
		br.close();
	}

	public static void write(int[] d) throws Exception {
		PrintWriter out = new PrintWriter("pred.txt", "UTF-8");
		out.println("ImageId,Label");
		for (int i = 0; i < d.length; i++)
			out.println((i + 1) + "," + d[i]);
		out.close();
	}

	public static void setup() {
		Tree.init();
		Tree.dataTrain = data_train;
		Tree.id = id;
	}
}
