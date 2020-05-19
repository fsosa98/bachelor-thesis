package hr.fer.ga.selection;

import java.util.List;
import java.util.Random;
import hr.fer.ga.Strategy;

public class RankedSelection implements Selection {

	private int populationSize;
	private Random rand;

	public RankedSelection(int populationSize) {
		this.populationSize = populationSize;
		this.rand = new Random();
	}

	@Override
	public int select(List<Strategy> population) {
		int fitnessSum = populationSize * (populationSize - 1) / 2;
		int randomNumber = rand.nextInt(fitnessSum);
		int x = (int) (1 + Math.sqrt(1 - 4 * 1 * -2 * randomNumber)) / 2;

		return populationSize - x;
	}

}
