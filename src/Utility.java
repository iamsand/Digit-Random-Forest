import java.util.ArrayList;
import java.util.Random;

public class Utility {

	public static Random	r	= new Random();

	public static int indexOfMax(int[] d) {
		int max = -1;
		ArrayList<Integer> maxIndexs = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			if (d[i] >= max) {
				if (d[i] > max)
					maxIndexs = new ArrayList<Integer>();
				max = d[i];
				maxIndexs.add(i);
			}
		}
		return maxIndexs.get(r.nextInt(maxIndexs.size()));
	}
}
