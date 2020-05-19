package hr.fer.ga.crossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import hr.fer.ga.Strategy;

public class KPointCrossover implements Crossover {

	private Random rand;
	private int k;
	private double mutationRate;

	public KPointCrossover(int k, double mutationRate) {
		rand = new Random();
		this.k = k;
		this.mutationRate = mutationRate;
	}

	@Override
	public Strategy cross(Strategy strategy1, Strategy strategy2) {
		int N = 280;

		int[] strategyTable = new int[N];

		List<Integer> intersectionPoints = new ArrayList<Integer>();
		for (int i = 0; i < k; i++) {
			intersectionPoints.add(rand.nextInt(N));
		}
		Collections.sort(intersectionPoints);
		intersectionPoints.add(N+1);
		int current = 0;
		
		for (int i = 0; i < N; i++) {
			if (i > intersectionPoints.get(current)) {
				current++;
			}
			if (current % 2 == 0) {
				strategyTable[i] = strategy1.getStrategyTable()[i];
			} else {
				strategyTable[i] = strategy2.getStrategyTable()[i];
			}
			if (rand.nextDouble() < mutationRate) {
				strategyTable[i] = 1 - strategyTable[i];
			}
		}

		return new Strategy(strategyTable, strategy1.getNumberOfHands());
	}

}
