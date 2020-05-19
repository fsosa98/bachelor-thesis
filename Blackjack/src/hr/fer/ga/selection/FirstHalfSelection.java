package hr.fer.ga.selection;

import java.util.List;
import java.util.Random;
import hr.fer.ga.Strategy;

public class FirstHalfSelection implements Selection {

	private Random rand;

	public FirstHalfSelection() {
		rand = new Random();
	}

	@Override
	public int select(List<Strategy> population) {
		return rand.nextInt(population.size() / 2);
	}

}