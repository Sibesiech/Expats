package expat.model;

import expat.control.*;
import expat.model.board.ModelBoard;
import expat.model.board.ModelBoardFactory;
import expat.model.board.ModelHex;
import expat.model.buildings.ModelBuildingAction;

import java.util.LinkedList;

/**
 * is responsible for the game procedure
 * <p>
 * therefore it's a collection of:
 * - all the FXML-controlles
 * - the board
 * - all the players
 * <p>
 * this class is built intentionally like the player procedure
 * - dice and material distirbution
 * - trading
 * - building
 * - events
 * created on 22.03.2017
 *
 * @author vanonir
 */
public class ModelApp {
    private PaneBoardController boardController;
    private PanePlayerController playerController;
    private PaneActionController actionController;
    private PaneMatesController matesController;
    private ControllerMainStage mainController;
    private ModelBoard board;
    private int diceNumber;
    private LinkedList<ModelPlayer> players = new LinkedList<>();
    private ModelPlayer nowPlaying;
    private String currentStep;



    public ModelApp(ControllerMainStage mainController, PaneBoardController boardController, PaneMatesController matesController, PaneActionController actionController, PanePlayerController playerController) {
        this.boardController = boardController;
        this.actionController = actionController;
        this.matesController = matesController;
        this.playerController = playerController;
        this.mainController = mainController;
        ModelBoardFactory boardGenerator = new ModelBoardFactory(9, 7);
        this.board = boardGenerator.generateBoard();


    }

    /**
     * Generates a new Player with a ModelPlayerGenerator and add it to the player queue.
     */
    public void generatePlayer() {
        ModelPlayerGenerator playerGen = new ModelPlayerGenerator();
        ModelPlayer player = playerGen.newPlayer();
        players.add(player);
    }

    /**
     * handles the beginning of the game
     * <p>
     * - players choose the position of their first two settlements
     * - they get the materials from all flanking hexes
     */
    public void gameBegin() {
        generatePlayer();
        generatePlayer();
        nextPlayer();
        currentStep= "FirstBuildingStep";
    }

    public void buildingStep(){
        currentStep = "BuildingStep";
    }



    /**
     * is the first player-step that distributes materials
     * <p>
     * 1. dice
     * 2. material distribution
     */
    public void resourceStep() {
        ModelThrowDice throwDice = new ModelThrowDice();
        diceNumber = throwDice.getRandomDiceNumber();
        if (diceNumber != 7) {
            board.resourceOnDiceEvent(diceNumber);
        }


    }

    /**
     * is the second player-step that handles trading
     * <p>
     * 1. domestic trade (playertrade)
     * 2. sea trade (2:1, 3:1, 4:1)
     */
    public void tradeStep() {

    }

    /**
     * is the third player-step that handles special events
     * <p>
     * 1. rolled 7 (no resources, >7 cards drop, raider move, resource robbery)
     * 2. play development cards (knights, development, victory)
     */
    public void specialStep() {
        if (diceNumber == 7) {
            //TODO: do something. take one random material from a player.
        }
    }

    /**
     * Changes player, so next player can doo all steps.
     */
    public void nextPlayer() {
        board.abortBuildingAction();
        players.add(players.poll());
        nowPlaying = players.peek();
        panesInformationRefresh(); //TODO: remove into controller
    }

    /**
     * Displays current playerinformation in PanePlayerController.
     */
    private void panesInformationRefresh() { //TODO: remove into controller
        playerController.setPlayerInformation(nowPlaying.getPlayerName(), nowPlaying.getMaterialPoolString(), nowPlaying.getWinPointsString());

        String allPlayerStats = "";
        for (ModelPlayer element : players) {
            if (element != players.getFirst()) {
                allPlayerStats += (element.getPlayerName() + "\n");
                allPlayerStats += ("Victorypoints: " + element.getWinPointsString() + "\n\n");
            }
        }
        matesController.setMatesInformation(allPlayerStats);
    }

    /**
     * handles the end of the game
     */
    public void gameOver() {


    }


    public void firstBuildingAction(String type){
        board.firstBuildingAction(type,nowPlaying);
    }


    public void newBuildingAction(String type) {
        board.newBuildingAction(type, nowPlaying);
    }

    public void injectNewBuildingCoordsAndAddWinpoints(int[] coords, String type) {
        {

            if (board.finishBuildingAction(coords, type)) {
                if (type.equals("Building")) {
                    nowPlaying.changeVictoryPoints(1);
                }
                playerController.setPlayerInformation(nowPlaying.getPlayerName(), nowPlaying.getMaterialPoolString(), nowPlaying.getWinPointsString());

            }
        }
    }

    /**
     * @return ModelBoard
     */
    public ModelBoard getBoard() {
        return board;
    }

    /**
     * @return actual player.
     */
    public ModelPlayer getNowPlaying() {
        return nowPlaying;
    }


    public void moveRaider(int[] coords) {
        for (ModelHex[] hexline : board.getHexes()) {
            for (ModelHex hex : hexline) {
                if (hex.getCoords()[0] == coords[0] && hex.getCoords()[1] == coords[1]) {
                    board.getRaider().moveRaider(hex);
                }
            }

        }
        boardController.refreshBoardElements(board);
    }
    public String getCurrentStep() {
        return currentStep;
    }
}


