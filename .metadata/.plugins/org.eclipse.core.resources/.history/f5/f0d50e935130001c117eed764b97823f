package fr.onepoint.bowl;

import org.apache.commons.lang3.ArrayUtils;

import fr.onepoint.bowl.service.BowlingGame;

/**
 * 
 * @author Dimitri
 *
 */
public class Main {

	//@link BowlingGame
    private static BowlingGame game;

    public static void main(String[] args) throws Exception {
//    	String jeu = "X X X X X X X X X X --"; TEST OK
//    	String jeu = "X X X X X X X X X X X X"; ok
//    	String jeu = "9- 9- 9- 9- 9- 9- 9- 9- 9- 9-"; ok
//    	String jeu = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5" ok
//    	String jeu = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5 13"; ok
//    	String jeu = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 88"; //ok
    	String jeu ;
    	if (ArrayUtils.isEmpty(args)) {
        	jeu = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5"; 
    	} else {
    		jeu = args[0];
    	}
        game = new BowlingGame();
        game.commencerJeu(jeu);
    }
}
