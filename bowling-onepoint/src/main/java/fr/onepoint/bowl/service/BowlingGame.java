package fr.onepoint.bowl.service;
import static fr.onepoint.bowl.utils.Constants.quillesParTour;
import static fr.onepoint.bowl.utils.Constants.tourParJeuBownling;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.onepoint.bowl.model.Lancer;
import fr.onepoint.bowl.model.Tour;
import fr.onepoint.bowl.utils.Constants;

/**
 * Classe Service du jeu
 * @author Dimitri
 *
 */
public class BowlingGame implements IBowlingGame {
	private static Logger LOGGER = LogManager.getLogger(BowlingGame.class);
	public ArrayList<Tour> listeFrames = new ArrayList<Tour>();

    @Override
    public void roll (String quilles) {
        int quillesTombees = 0;
        if (Constants.CONST_X.equals(quilles)) {
        	quillesTombees = quillesParTour;
    	} else if  (Constants.CONST_SLASH.equals(quilles)){
        	quillesTombees = quillesParTour - getTourEnCours().getRoll1().getQuillesTombees();
    	} else if (quilles.matches("\\d+")) {
    		quillesTombees = Integer.valueOf(quilles);
    	}
        
        LOGGER.info("tour" + getNumeroTourEnCours() + ", quilles tombées " + quillesTombees);

        recalculScore(quillesTombees);

        getTourEnCours().setScoreTour(getTourEnCours().getScoreTour() + quillesTombees);
        if (premierLancer()) {
            getTourEnCours().setRoll1(new Lancer());
            getTourEnCours().getRoll1().setQuillesTombees(quillesTombees);
            getTourEnCours().getRoll1().setStrike(isStrike(quillesTombees));
            displayScore();
            if (isStrike(quillesTombees)) {
                jouerTourProchain();
            }
        } else {
            getTourEnCours().setRoll2(new Lancer());
            getTourEnCours().getRoll2().setQuillesTombees(quillesTombees);
            getTourEnCours().getRoll2().setSpare(isSpare(quillesTombees));
            displayScore();
            jouerTourProchain();
        }
    }

    /**
     * Recalcul score
     * Si spare avant donc  à rajouter le nombre des quilles du premier lancer
     * Si strike avant donc à rajouter le nombre des quilles des deux lancers
     * @param quillesTombees
     */
    private void recalculScore(int quillesTombees) {
        if (listeFrames.size() > 1) {
            if(getTourPrecedent().getRoll1().isStrike() ||
                    getTourPrecedent().getRoll2().isSpare() && premierLancer()) {
                getTourPrecedent().setScoreTour(getTourPrecedent().getScoreTour()+ quillesTombees);
            }
            
            if (listeFrames.size() >2 && getTourAvantPrecedent().getRoll1().isStrike() && getTourPrecedent().getRoll1().isStrike()) {
            	getTourAvantPrecedent().setScoreTour(getTourAvantPrecedent().getScoreTour()+ quillesTombees);

            }
        }
    }

    public Tour getTourEnCours() {
        return listeFrames.get(listeFrames.size() - 1);
    }

    private int getNumeroTourEnCours() {
        return listeFrames.size();
    }

    public Tour getTourPrecedent() {
        return listeFrames.get(listeFrames.size() - 2);
    }
    
    public Tour getTourAvantPrecedent() {
        return listeFrames.get(listeFrames.size() - 3);
    }
    
    private boolean premierLancer() {
        return getTourEnCours().getRoll1() == null;
    }

    private boolean isSpare(int quilles) {
        return getTourEnCours().getRoll1().getQuillesTombees() + quilles == quillesParTour;
    }

    private boolean isStrike(int quilles) {
        return quilles == quillesParTour;
    }

    /**
     * Jouer tour prochain
     */
    public void jouerTourProchain() {
        if (listeFrames.size() < tourParJeuBownling
        			|| ((listeFrames.size() == tourParJeuBownling || listeFrames.size() == tourParJeuBownling +1) 
        				&& (getTourPrecedent().getRoll1().isStrike() || getTourPrecedent().getRoll2().isSpare() ))) {
            listeFrames.add(new Tour());
        } else LOGGER.info("fin de jeu score total: " +  score());

    }

    /**
     * 
     * @param jeu
     * @throws Exception
     */
    public void commencerJeu(String jeu) throws Exception {
    	jouerTourProchain() ;

    	if (StringUtils.isEmpty(jeu)) {
        	throw new Exception(Constants.ERREUR_JEU + "jeu vide");
    	}
    	String[] tours = jeu.trim().split(StringUtils.SPACE);
    	LOGGER.info("tours: " +  jeu);
        controleTourValide(tours);

        for (int i = 0; i < tours.length; i++) {
        	String[] digits2 =tours[i].split("(?<=.)");
        	for (String quille : digits2) {
				roll(quille);
			}
		}
    }

    
    /**
     * 
     * @param tours
     * @throws Exception
     */
	private void controleTourValide(String[] tours) throws Exception {
		if (tours.length < tourParJeuBownling ||
        		(tours.length == 11 && !tours[9].endsWith(Constants.CONST_X)) ||
        		(tours.length == 12 && (!tours[9].endsWith(Constants.CONST_X)||  !tours[10].endsWith(Constants.CONST_X)))){
			LOGGER.error(Constants.ERREUR_JEU  + "strike format erreur");
			throw new Exception(Constants.ERREUR_JEU + "strike format erreur");
        }
        
        for (int i = 0; i < tours.length; i++) {
        	String[] digits2 =tours[i].split("(?<=.)");	
        	try  {
        		Integer.parseInt(tours[i]);
        		int total = 0;
        		for (int j = 0; j < digits2.length; j++) {
        			total += Integer.valueOf(digits2[j]);
    				if (total > 10) {
    					LOGGER.error(Constants.ERREUR_JEU + "nombre de quilles tombees en erreur : >10");
                    	throw new Exception(Constants.ERREUR_JEU + "nombre de quilles tombees en erreur : >10");
    				}
    			} 
        	} catch(NumberFormatException exc) {
        		if (!(tours[i].contains(Constants.CONST_TIRE) || (tours[i].endsWith(Constants.CONST_SLASH) || i== 9) || tours[i].startsWith(Constants.CONST_X) ||tours[i].endsWith(Constants.CONST_X)) || tours[i].startsWith(Constants.CONST_SLASH)) {
					LOGGER.error(Constants.ERREUR_JEU + "tour format erreur: caractéres non valides");
        			throw new Exception(Constants.ERREUR_JEU + "tour format erreur: caractéres non valides");
        		}
        	}
        	if (digits2.length == 1 && !digits2[0].equals(Constants.CONST_X)) {
				LOGGER.error(Constants.ERREUR_JEU + "strike format erreur");
            	throw new Exception(Constants.ERREUR_JEU + "strike format erreur");
        	} 
        	if(!digits2[0].equals(Constants.CONST_X) && digits2.length>2 && i<9) {
				LOGGER.error(Constants.ERREUR_JEU + "tour format erreur: taille non conforme");
            	throw new Exception(Constants.ERREUR_JEU + "tour format erreur: taille non conforme");
        	}
        	if(tours[i].contains(Constants.CONST_TIRE) &&  i >= 9 && (digits2.length>3 || digits2.length<2)) {
				LOGGER.error(Constants.ERREUR_JEU + "tour format erreur: taille non conforme");
        		throw new Exception(Constants.ERREUR_JEU + "tour format erreur: taille non conforme");
        	}
		}
	}

    private void displayScore() {
        LOGGER.info("Total score - " + score());
    }

	@Override
	public Integer score() {
    	Integer totalGameScore = 0;

        for (int i=0; i <= listeFrames.size() - 1; i++) {
        	if (i < tourParJeuBownling) {
        		totalGameScore += listeFrames.get(i).getScoreTour();
        	}
        }
        return totalGameScore;
    }
}
