package animeApp.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


/**
 * Created by Peitch on 08/09/15.
 */
public class filterswindowController {

    @FXML
    private HBox hbox;
    ObservableList<Node> vboxes;
    @FXML
    private void addFiltersToList(){
        AllAnimeController.filterslist=new ArrayList<>();
        vboxes=hbox.getChildren();
        for(Node nd : vboxes){
            VBox v = (VBox)nd;
            ObservableList<Node> checks = v.getChildren();
            for(Node node : checks){
                CheckBox ckbox = (CheckBox)node;
                if(ckbox.isSelected())
                    AllAnimeController.filterslist.add(ckbox.getText());
            }
        }
        AllAnimeController.windownew.close();
    }
}
