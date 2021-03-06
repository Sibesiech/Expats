package expat.control;


import expat.model.ModelApp;
import expat.model.ModelPlayer;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.LinkedList;

/**
 * the right hand pane that works as a display for other player progress (victory points)
 * <p>
 * created on 29.03.2017
 *
 * @author gallatib
 */
public class PaneMatesController {

    private ModelApp app;
    @FXML public TextArea matesVictoryPointsTextArea;


    /**
     * Takes a reference of ModelApp so this controller can call it.
     *
     * @param app
     */
    public void init(ModelApp app){
        this.app = app;
    }

    /**
     * Sets Textareas with informations about enemy players acquired from the app.
     */
    public void setMatesInformation(){
        LinkedList<ModelPlayer> players = app.getPlayers();
        String allPlayerStats = "";
        for (ModelPlayer element : players) {
            if (element != players.getFirst()) {
                allPlayerStats += (element.getPlayerName() + "\n");
                allPlayerStats += ("Victorypoints: " + element.getWinPointsString() + "\n");
                allPlayerStats += ("Number of Cards: " + element.getMaterial().getSumOfAllMaterials() + "\n\n");
            }
        }
        matesVictoryPointsTextArea.setText(allPlayerStats);
    }
}
