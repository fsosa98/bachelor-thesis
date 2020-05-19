package hr.fer.ga.crossover;

import hr.fer.ga.Strategy;

public interface Crossover {

	Strategy cross(Strategy strategy1, Strategy strategy2);

}
