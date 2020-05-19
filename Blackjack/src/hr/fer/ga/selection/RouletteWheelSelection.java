package hr.fer.ga.selection;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import hr.fer.ga.Strategy;

public class RouletteWheelSelection implements Selection {

	private Random rand;

	public RouletteWheelSelection() {
		rand = new Random();
	}

	@Override
	public int select(List<Strategy> population) {
		double[] selectionList = new double[population.size()];

		int fitnessSum = 0;
		for (int i = 0; i < population.size(); i++) {
			fitnessSum += population.get(i).getFitnessScore();
		}
		int cumulativeSum = 0;
		for (int i = 0; i < population.size(); i++) {
			cumulativeSum += population.get(i).getFitnessScore();
			selectionList[i] = (double) cumulativeSum / fitnessSum;
		}

		double random_double = rand.nextDouble();

		return -Arrays.binarySearch(selectionList, random_double) - 1;
	}

}
