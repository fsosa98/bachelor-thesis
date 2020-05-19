package hr.fer.ga.crossover;

import java.util.Random;
import hr.fer.ga.Strategy;

public class UniformCrossover implements Crossover {

	private Random rand;
	private double mutationRate;

	public UniformCrossover(double mutationRate) {
		rand = new Random();
		this.mutationRate = mutationRate;
	}

	@Override
	public Strategy cross(Strategy strategy1, Strategy strategy2) {
		int N = 280;

		int[] strategyTable = new int[N];
		int f1 = strategy1.getFitnessScore();
		int f2 = strategy2.getFitnessScore();
		double koef;

		if (f1 >= 0 && f2 >= 0) {
			if (f1 == 0 && f2 == 0) {
				koef = 0.5;
			} else {
				koef = ((double) f2) / (f1 + f2);
			}
		} else if (f1 >= 0 && f2 < 0) {
			koef = 0.2;
		} else if (f2 >= 0 && f1 < 0) {
			koef = 0.8;
		} else {
			koef = (-(double) f1) / (-f1 - f2);
		}

		for (int i = 0; i < N; i++) {
			double f = rand.nextDouble();
			if (f > koef) {
				strategyTable[i] = strategy1.getStrategyTable()[i];
			} else {
				strategyTable[i] = strategy2.getStrategyTable()[i];
			}

			if (f < mutationRate) {
				strategyTable[i] = 1 - strategyTable[i];
			}
		}

		return new Strategy(strategyTable, strategy1.getNumberOfHands());
	}

}
