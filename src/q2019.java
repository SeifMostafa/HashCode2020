import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class q2019 {

	public static void main(String[] args) throws IOException {
		try {
			String[] inputs = { "a_example.in", "b_small.in", "c_medium.in", "d_quite_big.in", "e_also_big.in" };
			String answer =null;
			int finalNumTypes=0;
			int[]typeIndexes = null;
			
			for (String in : inputs) {
				File text = new File("/home/azizax/eclipse-workspace/HashCode2/src/" + in);
				Scanner scnr = new Scanner(text);
				int N, M;
				M = Integer.valueOf(scnr.next());
				N = Integer.valueOf(scnr.next());
				
				// System.out.println("Max: " + M + ", #types: "+ N);
				ArrayList<Integer>slices = new ArrayList<Integer>();
				for (int i=0;i<N;i++) {
					slices.add(Integer.valueOf(scnr.next()));
				//	boolean singleShot= calcWhileGo(slices,M);
					
				}
				
			 //System.out.println("slices: "+ slices);
//	        Simulator simulator = new Simulator(fileName);
//	        simulator.parseInput();
//	        simulator.simulate();
//	        simulator.printOutput();

				

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}
}
