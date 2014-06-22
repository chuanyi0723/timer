package test;

public class StageSetting {
	private int goal;
	private int initStep;
	private int[] categories;
	private int[][] mark;

	public StageSetting(int i) {
		switch (i) {
		case 0:
			goal = 8000;
			initStep = 10;
			categories = new int[] { 0, 1, 2 };
			mark = new int[][] { { 0, 1, 1, 0, 0, 0, 1, 1, 0 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 0, 0, 1, 1, 1, 1, 1, 0, 0 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 0, 1, 1, 0, 0, 0, 1, 1, 0 } };
			break;
		case 1:
			goal = 9000;
			initStep = 15;
			categories = new int[] { 0, 0, 0, 1, 1, 1, 2, 2, 2, 3 };
			mark = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

			break;
		case 2:
			goal = 9000;
			initStep = 30;
			categories = new int[] { 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4 };
			mark = new int[][] { { 1, 1, 0, 1, 1, 1, 0, 1, 1 },
					{ 1, 1, 0, 1, 1, 1, 0, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 0, 1, 1, 1, 0, 1, 1 },
					{ 1, 1, 0, 1, 1, 1, 0, 1, 1 }, };

			break;
		case 3:
			goal = 12000;
			initStep = 30;
			categories = new int[] { 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4,
					4, 5 };
			mark = new int[9][9];
			for (int k = 0; k < 9; k++) {
				for (int j = 0; j < 9; j++)
					mark[k][j] = 1;
			}
			break;
		}
	}

	public int[] getCategories() {
		return categories;
	}

	public int getGoal() {
		return goal;
	}

	public int getInitStep() {
		return initStep;
	}

	public int[][] getMark() {
		return mark;
	}
}
