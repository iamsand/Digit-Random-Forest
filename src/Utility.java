import java.util.ArrayList;
import java.util.Random;

public class Utility {

	public static Random	r	= new Random();

	
	// returns the index of the maximum element of array d. If there are more than one, a random one is returned.
	public static int indexOfMax(int[] d) {
		int max = -1;
		ArrayList<Integer> max_index = new ArrayList<Integer>();
		for (int i = 0; i < d.length; i++) {
			if (d[i] >= max) {
				if (d[i] > max)
					max_index = new ArrayList<Integer>();
				max = d[i];
				max_index.add(i);
			}
		}
		return max_index.get(r.nextInt(max_index.size()));
	}
	
	// // returns the mode of al. The entries are assumed to be between 0 and 9 incl.
	// public static int getModeOf(ArrayList<Integer> al){
	// int[] d = new int[10];
	// for (int as : al){
	// d[as]++;
	// }
	// return indexOfMax(d);
	// }
}
