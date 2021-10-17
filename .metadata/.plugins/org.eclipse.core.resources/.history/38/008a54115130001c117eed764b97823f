package fr.onepoint.bowl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.onepoint.bowl.service.BowlingGame;
import fr.onepoint.bowl.utils.Constants;
/**
 * Classe de test
 * @author Dimitri
 *
 */
public class BowlingGameTest {

    private static final String TOUS_CINQ_ET_SPARE = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5";
	private static final String ALL_NEUF_ET_ZERO = "9- 9- 9- 9- 9- 9- 9- 9- 9- 9-";
	private static final String ALL_STRIKES = "X X X X X X X X X X X X";
	private BowlingGame bowlingGame;

    @BeforeEach
    void setUp() throws Exception {
        this.bowlingGame = new BowlingGame();
    }

    @Test
    void testAllStrikes() throws Exception {
        bowlingGame.commencerJeu(ALL_STRIKES);
        assertEquals(300, bowlingGame.score());
    }

    @Test
    void testToutNeufEtZero() throws Exception {
        bowlingGame.commencerJeu(ALL_NEUF_ET_ZERO);
        assertEquals(90, bowlingGame.score());
    }

    @Test
    void testTousCinqEtSpare() throws Exception {
        bowlingGame.commencerJeu(TOUS_CINQ_ET_SPARE);
        assertEquals(150, bowlingGame.score());
    }

    @Test
    void testfausseSaisi() throws Exception {
    	Exception exc = assertThrows(Exception.class, () -> bowlingGame.commencerJeu("5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 88"));
        assertEquals(exc.getMessage(), Constants.ERREUR_JEU+ "nombre de quilles tombees en erreur : >10");
    }
    
    @Test
    void testfausseSaisi2() throws Exception {
    	Exception exc = assertThrows(Exception.class, () -> bowlingGame.commencerJeu("X X X X X X X X - X"));
        assertEquals(exc.getMessage(), Constants.ERREUR_JEU+ "strike format erreur");
    }
    
    @Test
    void testfausseSaisi3() throws Exception {
    	Exception exc = assertThrows(Exception.class, () -> bowlingGame.commencerJeu("X X /- X X X X X - X"));
        assertEquals(exc.getMessage(), Constants.ERREUR_JEU+ "tour format erreur: caractéres non valides");
    }
    
    @Test
    void testfausseSaisi4() throws Exception {
    	Exception exc = assertThrows(Exception.class, () -> bowlingGame.commencerJeu(""));
        assertEquals(exc.getMessage(), Constants.ERREUR_JEU+ "jeu vide");
    }
    
    @Test
    void testfausseSaisi5() throws Exception {
    	Exception exc = assertThrows(Exception.class, () -> bowlingGame.commencerJeu("5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 123 55"));
        assertEquals(exc.getMessage(), Constants.ERREUR_JEU+ "tour format erreur: taille non conforme");
    }
}