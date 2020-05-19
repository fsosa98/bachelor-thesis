package hr.fer.ga;

import java.util.Random;

public class Strategy implements Comparable<Strategy> {

	private static final int N = 280;
	private Random rand;

	private int[] strategyTable = new int[280];
	private int numberOfHands;
	private int[] cards = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11 };

	private int fitnessScore;

	public Strategy(int numberOfHands) {
		this.numberOfHands = numberOfHands;
		rand = new Random();

		for (int i = 0; i < N; i++) {
			strategyTable[i] = rand.nextInt(2);
		}
		calculateScore();
	}

	public Strategy(int[] strategyTable, int numberOfHands) {
		this.numberOfHands = numberOfHands;
		rand = new Random();

		for (int i = 0; i < N; i++) {
			this.strategyTable[i] = strategyTable[i];
		}
	}

	public void calculateScore() {
		int x;
		int br = numberOfHands / 280;

		int dealer = 2;
		int player = 4;
		int Ace11Dealer = 0;
		int Ace11Player = 0;
		boolean playerHasAce = false;

		// hard
		for (int i = 0; i < 10; ++i) {
			if (i == 9) {
				Ace11Dealer = 1;
			}
			for (int j = 0; j < 18; ++j) {
				for (int k = 0; k < br; ++k) {
					x = playOneHand(strategyTable, dealer, player, Ace11Dealer, Ace11Player, playerHasAce);
					fitnessScore += x;
				}
				player++;
			}
			player = 4;
			dealer++;
		}

		Ace11Dealer = 0;
		Ace11Player = 1;
		playerHasAce = true;
		dealer = 2;
		player = 12;

		// soft
		for (int i = 0; i < 10; ++i) {
			if (i == 9) {
				Ace11Dealer = 1;
			}
			for (int j = 0; j < 10; ++j) {
				for (int k = 0; k < br; ++k) {
					x = playOneHand(strategyTable, dealer, player, Ace11Dealer, Ace11Player, playerHasAce);
					fitnessScore += x;
				}
				player++;
			}
			player = 12;
			dealer++;
		}
	}

	private int playOneHand(int strategyTable[], int dealer, int player, int Ace11Dealer, int Ace11Player,
			boolean playerHasAce) {
		int card;

		// player
		while (true) {
			if (player > 21) {
				return -1;
			}
			int hitOrStand;
			if (!playerHasAce) {
				hitOrStand = strategyTable[(player - 4) * 10 + dealer - 2];
			} else {
				hitOrStand = strategyTable[(player + 6) * 10 + dealer - 2];
			}
			if (hitOrStand == 0) {
				break;
			}
			card = cards[rand.nextInt(13)];
			player += card;
			if (card == 11) {
				Ace11Player++;
				playerHasAce = true;
			}

			if (player > 21) {
				if (Ace11Player > 0) {
					player -= 10;
					Ace11Player--;
					if (Ace11Player == 0)
						playerHasAce = false;
				} else {
					return -1;
				}
			}
		}
		if (player > 21) {
			return -1;
		}

		// dealer
		while (true) {
			card = cards[rand.nextInt(13)];
			dealer += card;
			if (card == 11) {
				Ace11Dealer++;
			}
			if (dealer > 21) {
				if (Ace11Dealer > 0) {
					dealer -= 10;
					Ace11Dealer--;
				} else {
					return 1;
				}
			}
			if (dealer >= 17) {
				break;
			}
		}
		if (dealer > player) {
			return -1;
		}
		if (dealer < player) {
			return 1;
		}

		return 0;
	}

	public int getFitnessScore() {
		return fitnessScore;
	}

	public int getNumberOfHands() {
		return numberOfHands;
	}

	public int[] getStrategyTable() {
		return strategyTable;
	}

	@Override
	public int compareTo(Strategy o) {
		return Integer.compare(o.getFitnessScore(), this.getFitnessScore());
	}

}
