package fr.onepoint.bowl.model;
/**
 * Classe model pour chaque tour du jeu
 * @author Dimitri
 *
 */
public class Tour {

    public Lancer roll1, roll2 = null;
    public Integer scoreTour = 0;

    public Tour() {}

	public Lancer getRoll1() {
		return roll1;
	}

	public void setRoll1(Lancer roll1) {
		this.roll1 = roll1;
	}

	public Lancer getRoll2() {
		return roll2;
	}

	public void setRoll2(Lancer roll2) {
		this.roll2 = roll2;
	}

	public Integer getScoreTour() {
		return scoreTour;
	}

	public void setScoreTour(Integer scoreTour) {
		this.scoreTour = scoreTour;
	}
}
		