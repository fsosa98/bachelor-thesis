package hr.fer.ga;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.crossover.KPointCrossover;
import hr.fer.ga.crossover.UniformCrossover;
import hr.fer.ga.selection.RankedSelection;
import hr.fer.ga.selection.RouletteWheelSelection;
import hr.fer.ga.selection.Selection;
import hr.fer.ga.selection.TournamentSelection;

public class Console extends JFrame {

	private static final long serialVersionUID = 1L;

	public Console() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 800);
		setLocationRelativeTo(null);
		setTitle("Blackjack");
		initGUI();
	}

	private void initGUI() {

		Container cp = getContentPane();
		setLayout(new BorderLayout());

		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel realRightPanel = new JPanel();
		JButton button = new JButton("Pokreni");
		JLabel result = new JLabel("Točnost: ");

		leftPanel.setLayout(new GridLayout(21, 1));
		realRightPanel.setLayout(new BorderLayout());

		JLabel populationSizeLabel1 = new JLabel("Veličina populacije");
		JLabel populationSizeLabel2 = new JLabel("350");
		JSlider populationSizeSlider = new JSlider(0, 500, 350);

		populationSizeSlider.setPaintTrack(true);
		populationSizeSlider.setPaintTicks(true);
		populationSizeSlider.setPaintLabels(true);

		populationSizeSlider.setMajorTickSpacing(250);
		populationSizeSlider.setMinorTickSpacing(50);

		populationSizeSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				populationSizeLabel2.setText("" + populationSizeSlider.getValue());
			}
		});

		leftPanel.add(populationSizeLabel1);
		leftPanel.add(populationSizeSlider);
		leftPanel.add(populationSizeLabel2);

		JLabel numberOfHandsLabel1 = new JLabel("Broj odigranih ruku");
		JLabel numberOfHandsLabel2 = new JLabel("28000");
		JSlider numberOfHandsSlider = new JSlider(0, 50000, 28000);

		numberOfHandsSlider.setPaintTrack(true);
		numberOfHandsSlider.setPaintTicks(true);
		numberOfHandsSlider.setPaintLabels(true);

		numberOfHandsSlider.setMajorTickSpacing(25000);
		numberOfHandsSlider.setMinorTickSpacing(10000);

		numberOfHandsSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				numberOfHandsLabel2.setText("" + numberOfHandsSlider.getValue());
			}
		});

		leftPanel.add(numberOfHandsLabel1);
		leftPanel.add(numberOfHandsSlider);
		leftPanel.add(numberOfHandsLabel2);

		JLabel numberOfGenerationsLabel1 = new JLabel("Broj generacija");
		JLabel numberOfGenerationsLabel2 = new JLabel("150");
		JSlider numberOfGenerationsSlider = new JSlider(0, 300, 150);

		numberOfGenerationsSlider.setPaintTrack(true);
		numberOfGenerationsSlider.setPaintTicks(true);
		numberOfGenerationsSlider.setPaintLabels(true);

		numberOfGenerationsSlider.setMajorTickSpacing(150);
		numberOfGenerationsSlider.setMinorTickSpacing(50);

		numberOfGenerationsSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				numberOfGenerationsLabel2.setText("" + numberOfGenerationsSlider.getValue());
			}
		});

		leftPanel.add(numberOfGenerationsLabel1);
		leftPanel.add(numberOfGenerationsSlider);
		leftPanel.add(numberOfGenerationsLabel2);

		JLabel elitismLabel1 = new JLabel("Postotak elitizma");
		JLabel elitismLabel2 = new JLabel("10");
		JSlider elitismSlider = new JSlider(0, 100, 10);

		elitismSlider.setPaintTrack(true);
		elitismSlider.setPaintTicks(true);
		elitismSlider.setPaintLabels(true);

		elitismSlider.setMajorTickSpacing(50);
		elitismSlider.setMinorTickSpacing(10);

		elitismSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				elitismLabel2.setText("" + elitismSlider.getValue());
			}
		});

		leftPanel.add(elitismLabel1);
		leftPanel.add(elitismSlider);
		leftPanel.add(elitismLabel2);

		JLabel mutationLabel1 = new JLabel("Postotak mutacija");
		JLabel mutationLabel2 = new JLabel("0");
		JSlider mutationSlider = new JSlider(0, 100, 0);

		mutationSlider.setPaintTrack(true);
		mutationSlider.setPaintTicks(true);
		mutationSlider.setPaintLabels(true);

		mutationSlider.setMajorTickSpacing(50);
		mutationSlider.setMinorTickSpacing(10);

		mutationSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mutationLabel2.setText("" + mutationSlider.getValue());
			}
		});

		leftPanel.add(mutationLabel1);
		leftPanel.add(mutationSlider);
		leftPanel.add(mutationLabel2);

		// SELECTION
		JLabel selectionLabel = new JLabel("Postupak selekcije:");
		leftPanel.add(selectionLabel);
		JRadioButton[] selectionButtons = new JRadioButton[3];

		selectionButtons[0] = new JRadioButton("Rang");
		selectionButtons[0].setSelected(true);
		selectionButtons[1] = new JRadioButton("Jednostavna");
		selectionButtons[2] = new JRadioButton("Turnirska");

		ButtonGroup groupSelection = new ButtonGroup();
		JPanel selectionPanel = new JPanel();
		for (int i = 0; i < selectionButtons.length; i++) {
			groupSelection.add(selectionButtons[i]);
			selectionPanel.add(selectionButtons[i]);
		}

		leftPanel.add(selectionPanel);

		// CROSSOVER
		JLabel crossoverLabel = new JLabel("Postupak križanja:");
		leftPanel.add(crossoverLabel);
		JRadioButton[] crossoverButtons = new JRadioButton[2];

		crossoverButtons[0] = new JRadioButton("Uniformno");
		crossoverButtons[0].setSelected(true);
		crossoverButtons[1] = new JRadioButton("Točka prekida");

		ButtonGroup groupCrossover = new ButtonGroup();
		JPanel crossoverPanel = new JPanel();
		for (int i = 0; i < crossoverButtons.length; i++) {
			groupCrossover.add(crossoverButtons[i]);
			crossoverPanel.add(crossoverButtons[i]);
		}

		leftPanel.add(crossoverPanel);

		// TABLE
		rightPanel.setLayout(new GridLayout(2, 1));
		JPanel hardPanel = new JPanel();
		hardPanel.setLayout(new GridLayout(19, 11));
		JPanel softPanel = new JPanel();
		softPanel.setLayout(new GridLayout(11, 11));

		rightPanel.add(hardPanel);
		rightPanel.add(softPanel);
		realRightPanel.add(rightPanel, BorderLayout.CENTER);
		realRightPanel.add(result, BorderLayout.SOUTH);

		JLabel[][] hardSumLabels = new JLabel[19][11];
		JLabel[][] softSumLabels = new JLabel[11][11];

		// hard
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 11; j++) {
				hardSumLabels[i][j] = new JLabel("", JLabel.CENTER);
				if (i == 0 && j == 0) {
				} else if (i == 0) {
					if ((2 + j - 1) == 11) {
						hardSumLabels[i][j].setText("AS ");
					} else {
						hardSumLabels[i][j].setText((2 + j - 1) + " ");
					}
				} else if (j == 0) {
					if ((4 + i - 1) >= 10) {
						hardSumLabels[i][j].setText((4 + i - 1) + "  ");
					} else {
						hardSumLabels[i][j].setText(" " + (4 + i - 1) + " ");
					}
				}
				hardPanel.add(hardSumLabels[i][j]);
			}
		}

		// soft
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				softSumLabels[i][j] = new JLabel("", JLabel.CENTER);
				if (i == 0 && j == 0) {
				} else if (i == 0) {
					if ((2 + j - 1) == 11) {
						softSumLabels[i][j].setText("AS ");
					} else {
						softSumLabels[i][j].setText((2 + j - 1) + " ");
					}
				} else if (j == 0) {
					if ((4 + i - 1) >= 10) {
						softSumLabels[i][j].setText((12 + i - 1) + "  ");
					} else {
						softSumLabels[i][j].setText((12 + i - 1) + " ");
					}
				}
				softPanel.add(softSumLabels[i][j]);
			}
		}

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(populationSizeSlider.getValue());
				System.out.println(numberOfHandsSlider.getValue());
				System.out.println(numberOfGenerationsSlider.getValue());
				System.out.println(elitismSlider.getValue());
				System.out.println(mutationSlider.getValue());

				Selection selectedSelection = new RankedSelection(populationSizeSlider.getValue());
				for (int i = 0; i < selectionButtons.length; i++) {
					if (selectionButtons[i].isSelected()) {
						if (selectionButtons[i].getText().equals("Rang")) {
							break;
						}
						if (selectionButtons[i].getText().equals("Jednostavna")) {
							selectedSelection = new RouletteWheelSelection();
							break;
						}
						if (selectionButtons[i].getText().equals("Turnirska")) {
							selectedSelection = new TournamentSelection(3);
							break;
						}
					}
				}

				Crossover selectedCrossover = new UniformCrossover(mutationSlider.getValue() / 100.);
				for (int i = 0; i < crossoverButtons.length; i++) {
					if (crossoverButtons[i].isSelected()) {
						if (crossoverButtons[i].getText().equals("Uniformno")) {
							break;
						}
						if (crossoverButtons[i].getText().equals("Točka prekida")) {
							selectedCrossover = new KPointCrossover(2, mutationSlider.getValue() / 100.);
							break;
						}
					}
				}

				GeneticAlgorithm ga = new GeneticAlgorithm(populationSizeSlider.getValue(),
						numberOfHandsSlider.getValue(), numberOfGenerationsSlider.getValue(),
						elitismSlider.getValue() / 100., mutationSlider.getValue() / 100., selectedSelection,
						selectedCrossover);
				ga.start();

				Strategy strategy = ga.getResult();

				int br = 0;
				// hard
				for (int i = 0; i < 18; i++) {
					for (int j = 0; j < 10; j++) {
						if (strategy.getStrategyTable()[i * 10 + j] == 0) {
							hardSumLabels[i + 1][j + 1].setText("S");
							hardSumLabels[i + 1][j + 1].setBackground(Color.YELLOW);
							hardSumLabels[i + 1][j + 1].setOpaque(true);
							if (strategy.getStrategyTable()[i * 10 + j] != ga.optimalStrategyTable[i * 10 + j]) {
								hardSumLabels[i + 1][j + 1].setForeground(Color.RED);
								hardSumLabels[i + 1][j + 1].setBorder(border);
							}
						} else {
							hardSumLabels[i + 1][j + 1].setText("H");
							hardSumLabels[i + 1][j + 1].setBackground(Color.RED);
							hardSumLabels[i + 1][j + 1].setOpaque(true);
							if (strategy.getStrategyTable()[i * 10 + j] != ga.optimalStrategyTable[i * 10 + j]) {
								hardSumLabels[i + 1][j + 1].setForeground(Color.YELLOW);
								hardSumLabels[i + 1][j + 1].setBorder(border);
							}
						}

						if (strategy.getStrategyTable()[i * 10 + j] == ga.optimalStrategyTable[i * 10 + j]) {
							br++;
							hardSumLabels[i + 1][j + 1].setForeground(Color.BLACK);
							hardSumLabels[i + 1][j + 1].setBorder(null);
						}

						hardSumLabels[i + 1][j + 1].repaint();
					}
				}

				// soft
				for (int i = 18; i < 28; i++) {
					for (int j = 0; j < 10; j++) {
						if (strategy.getStrategyTable()[i * 10 + j] == 0) {
							softSumLabels[i + 1 - 18][j + 1].setText("S");
							softSumLabels[i + 1 - 18][j + 1].setBackground(Color.YELLOW);
							softSumLabels[i + 1 - 18][j + 1].setOpaque(true);
							if (strategy.getStrategyTable()[i * 10 + j] != ga.optimalStrategyTable[i * 10 + j]) {
								softSumLabels[i + 1 - 18][j + 1].setForeground(Color.RED);
								softSumLabels[i + 1 - 18][j + 1].setBorder(border);
							}
						} else {
							softSumLabels[i + 1 - 18][j + 1].setText("H");
							softSumLabels[i + 1 - 18][j + 1].setBackground(Color.RED);
							softSumLabels[i + 1 - 18][j + 1].setOpaque(true);
							if (strategy.getStrategyTable()[i * 10 + j] != ga.optimalStrategyTable[i * 10 + j]) {
								softSumLabels[i + 1 - 18][j + 1].setForeground(Color.YELLOW);
								softSumLabels[i + 1 - 18][j + 1].setBorder(border);
							}
						}

						if (strategy.getStrategyTable()[i * 10 + j] == ga.optimalStrategyTable[i * 10 + j]) {
							br++;
							softSumLabels[i + 1 - 18][j + 1].setForeground(Color.BLACK);
							softSumLabels[i + 1 - 18][j + 1].setBorder(null);
						}

						softSumLabels[i + 1 - 18][j + 1].repaint();
					}
				}
				DecimalFormat df = new DecimalFormat("##.####");
				result.setText("Točnost: (" + br + "/280) = " + df.format(br * 100 / 280.) + "%");

			}
		});

		JButton buttonReset = new JButton("Reset");
		buttonReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				populationSizeSlider.setValue(350);
				numberOfHandsSlider.setValue(28000);
				numberOfGenerationsSlider.setValue(150);
				elitismSlider.setValue(10);
				mutationSlider.setValue(0);
			}
		});
		leftPanel.add(buttonReset);

		cp.add(leftPanel, BorderLayout.WEST);
		cp.add(realRightPanel, BorderLayout.CENTER);
		cp.add(button, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Console().setVisible(true);
		});
	}

}
