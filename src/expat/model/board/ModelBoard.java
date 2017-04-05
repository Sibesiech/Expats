package expat.model.board;

import expat.model.buildings.ModelBuildingAction;
import expat.model.ModelPlayer;
import expat.model.buildings.ModelBuilding;
import expat.model.ModelRaider;
import expat.model.buildings.ModelConnection;

import java.util.ArrayList;

/**
 * is responsible for the actual game board that includes hexes, buildings and a raider
 * <p>
 * created on 22.03.2017
 *
 * @author vanonir
 */
public class ModelBoard {
    private ModelHex[][] hexes;
    private ArrayList<ModelBuilding> buildings;
    private ArrayList<ModelConnection> connections;
    private ModelRaider raider;
    private ModelBuildingAction buildingAction;

    public ModelBoard(ModelHex[][] hexes, ArrayList<ModelBuilding> buildings, ArrayList<ModelConnection> connections, ModelRaider raider) {
        this.hexes = hexes;
        this.buildings = buildings;
        this.raider = raider;
        this.connections = connections;
    }

    /**
     * 1. checks all the hexes on board an compares them to the rolled dice number
     * 2. checks all the buildings flanking those specific hexes and checks whether the hex is raided or not
     * 3. calls the building class to distribute the diced materials
     *
     * @param diceNumber
     */
    public void resourceOnDiceEvent(int diceNumber) {
        for (ModelHex[] hexLine : hexes) {
            for (ModelHex hex : hexLine) {
                if (hex.getDiceNumber() == diceNumber) { //TODO: check if raided,
                    for (ModelBuilding building : buildings) {
                        if (building.isFlanking(hex) && !hex.checkIfRaided()) {
                            building.giveMaterialToOwner(hex.getMaterial());
                        }
                    }
                }
            }
        }
    }

    public void raiderOnHexEvent(){
        for (ModelBuilding building : buildings){
            if (building.isFlanking(raider.getRaiderHex())){
                System.out.println(building.getOwner());
            }
        }
    }

    public void firstBuildingAction(String type, ModelPlayer newOwner) {
        buildingAction = new ModelBuildingAction(newOwner,type,buildings,connections,true);
    }
    public void newBuildingAction(String type, ModelPlayer newOwner) {
        buildingAction = new ModelBuildingAction(newOwner, type, buildings, connections);

    }

    public boolean finishBuildingAction(int[] coords, String type) {


        if (buildingAction != null) {
            if (buildingAction.createBuilding(coords, type)) {
                buildingAction = null;
                return true;
            }
        }
        return false;
    }
    public void abortBuildingAction() {
        buildingAction = null;
    }

    public int countBuildingsOwned(ModelPlayer player){
        int counter = 0;
        for (ModelBuilding building:buildings) {
            if (building.getOwner()==player){
                counter+=1;
            }
        }
        return counter;
    }
    public int countConnectionsOwned(ModelPlayer player){
        int counter = 0;
        for (ModelConnection connection:connections) {
            if (connection.getOwner()==player){
                counter+=1;
            }
        }
        return counter;
    }


    public ModelHex[][] getHexes() {
        return hexes;
    }

    public ArrayList<ModelBuilding> getBuildings() {
        return buildings;
    }

    public ModelRaider getRaider() {
        return raider;
    }


    public ArrayList<ModelConnection> getConnections() {
        return connections;
    }



}
