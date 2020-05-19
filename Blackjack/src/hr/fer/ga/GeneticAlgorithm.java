package hr.fer.ga;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.selection.Selection;

public class GeneticAlgorithm {

	private static final int N = 280;
	private int populationSize;
	private int numberOfGenerations;
	private Selection selection;
	private Crossover crossover;
	private double elitism;
	private List<Strategy> population;

	public int optimalStrategyTable[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1,
			1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
			0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0 };

	public GeneticAlgorithm(int populationSize, int numberOfHands, int numberOfGenerations, double elitism,
			double mutationRate, Selection selection, Crossover crossover) {

		population = new ArrayList<Strategy>();

		this.populationSize = populationSize;
		this.numberOfGenerations = numberOfGenerations;
		this.elitism = elitism;
		this.selection = selection;
		this.crossover = crossover;

		for (int i = 0; i < populationSize; i++) {
			population.add(new Strategy(numberOfHands));
		}
		Collections.sort(population);
	}

	public void start() {

		ExecutorService EXEC = Executors.newCachedThreadPool();

		for (int generation = 0; generation < numberOfGenerations; generation++) {
			List<Strategy> newPopulation = new ArrayList<>();
			int i, j;
			for (i = 0; i < ((int) elitism * populationSize); i++) {
				newPopulation.add(population.get(i));
			}
			j = i;
			for (; i < populationSize; i++) {
				int parent1 = selection.select(population);
				int parent2 = selection.select(population);
				newPopulation.add(crossover.cross(population.get(parent1), population.get(parent2)));
			}
			population = newPopulation;

			List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
			for (; j < populationSize; j++) {
				Strategy strategy = population.get(j);
				Callable<Void> c = new Callable<>() {
					@Override
					public Void call() throws Exception {
						strategy.calculateScore();
						return null;
					}
				};
				tasks.add(c);
			}
			try {
				@SuppressWarnings("unused")
				List<Future<Void>> results = EXEC.invokeAll(tasks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Collections.sort(population);
			// System.out.println("GENERACIJA " + generation + ". iznosi " +
			// population.get(0).getFitnessScore());
			System.out.println(population.get(0).getFitnessScore());
		}
		EXEC.shutdown();
	}

	public void printTable() {
		Strategy strategy = population.get(0);

		int zadnji = 0;

		for (int i = 0; i < N; ++i) {
			if (i == 180) {
				System.out.println();
			}
			if (strategy.getStrategyTable()[i] == 0) {
				System.out.print("S ");
			} else {
				System.out.print("H ");
			}
			if (i % 10 == 9) {
				System.out.print("\t");
				for (int w = zadnji; w <= i; w++) {
					if (optimalStrategyTable[w] == 0) {
						System.out.print("S ");
					} else {
						System.out.print("H ");
					}
				}
				System.out.println();
				zadnji = i + 1;
			}
		}
	}

	public Strategy getResult() {
		return population.get(0);
	}

}
