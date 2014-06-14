package test;

public class StageSetting {
	private int goalGrade;
	private int initStep;
	private int[] categories;
	private int[][] mark = new int[9][9];

	StageSetting(int i) {
		switch (i) {
		case 0:
			goalGrade = 8000;
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
			goalGrade = 9000;
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
			goalGrade = 9000;
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
			goalGrade = 12000;
			initStep = 30;
			categories = new int[] { 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4,
					4, 5 };
			for (int k = 0; k < 9; k++) {
				for (int j = 0; j < 9; j++)
					mark[k][j] = 1;
			}
			break;
		}
	}

	public int getGoalGrade() {
		return goalGrade;
	}

	public int getInitStep() {
		return initStep;
	}

	public int[] getCategories() {
		return categories;
	}

	public int[][] getMark() {
		return mark;
	}
}