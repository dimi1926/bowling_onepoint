package fr.onepoint.bowl.service;

/**
 * Interface du Service BowlingGame
 * @author Dimitri
 */
public interface IBowlingGame {
	
    /**
     * @param quillesTombees
     */
    void roll(String quillesTombees);

    /**
     * @return score
     */
    Integer score();
}