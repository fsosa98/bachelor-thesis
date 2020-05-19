package hr.fer.ga.selection;

import java.util.List;
import java.util.Random;
import hr.fer.ga.Strategy;

public class TournamentSelection implements Selection {

	private Random rand;
	private int k;

	public TournamentSelection(int k) {
		this.rand = new Random();
		this.k = k;
	}

	@Override
	public int select(List<Strategy> population) {
		int selected = population.size() - 1;

		for (int i = 0; i < k; i++) {
			int x = rand.nextInt(population.size());
			if (selected > x)
				selected = x;
		}
		return selected;
	}

}
