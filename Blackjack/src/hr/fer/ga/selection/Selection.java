package hr.fer.ga.selection;

import java.util.List;
import hr.fer.ga.Strategy;

public interface Selection {

	int select(List<Strategy> population);

}
