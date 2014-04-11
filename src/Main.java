import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

	// Arguments
	public static int			maxHeight		= 15;
	public static int			forestSize		= 200;
	// End Arguments
	
	// 12,1000 ~ 65%
	// 13,1000 ~ 70%
	// 15,200  ~ 75%
	

	// 42000 [id]
	public static int[]			id;
	// 42000 [index], [256 pixels as a vector]
	public static int[][]		dataTrain;
	public final static String	fileLocTrain	= "train.csv";

	public static int[][]		dataTest;
	public final static String	fileLocTest		= "test.csv";

	public static void main(String[] args) throws Exception {
		long timeStart = System.currentTimeMillis();
		System.out.println("Reading data...");
		readTrainingData();
		System.out.println("Reading complete...");
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");

		timeStart = System.currentTimeMillis();
		System.out.println("Building forest...");
		setup();
		Forest gump = new Forest(forestSize, maxHeight);
		System.out.println("Building complete...");
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");

		timeStart = System.currentTimeMillis();
		System.out.println("Testing on training data...");
		int correct = 0;
		for (int i = 0; i < 42000; i++) {
			if (i%1000 == 0)
				System.out.println("Training " + i + " ...");
			if (id[i] == gump.run(dataTrain[i]))
				correct++;
			// System.out.println("Train Index " + i + " " + id[i]);
			// System.out.println("Prediction: " + gump.run(dataTrain[i]));
		}
		System.out.println(correct + " of 42000");
		System.out.println("Testing complete...");
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timeStart) + " ms");
	}

	// This methods reads the train.csv so that we can access it as an int[][].
	public static void readTrainingData() throws Exception {
		id = new int[42000];
		dataTrain = new int[42000][28 * 28];
		BufferedReader br = new BufferedReader(new FileReader("res\\" + fileLocTrain));
		String s = br.readLine();
		String[] splts = null;
		for (int i = 0; i < 42000; i++) {
			s = br.readLine();
			id[i] = s.charAt(0) - '0';
			splts = s.substring(2).split(",");
			for (int j = 0; j < 28 * 28; j++)
				dataTrain[i][j] = Integer.parseInt(splts[j]);
		}
		br.close();
	}

	public static void setup() {
		Tree.dataTrain = dataTrain;
		Tree.id = id;
	}
}
