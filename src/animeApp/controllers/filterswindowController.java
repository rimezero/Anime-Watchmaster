package animeApp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by Peitch on 08/09/15.
 */
public class filterswindowController {

    @FXML
    private HBox hbox;
    @FXML
    private HBox hbox2;
    ObservableList<Node> vboxes;
    @FXML
    private void addFiltersToList(){
        AllAnimeController.filterslist=new ArrayList<>();
        //vboxes=hbox.getChildren();
        hbox.getChildren();
        hbox2.getChildren();
        vboxes=FXCollections.observableArrayList(Stream.concat(hbox.getChildren().stream(), hbox2.getChildren().stream())
                .collect(Collectors.toList()));
        for(Node nd : vboxes){
            VBox v = (VBox)nd;
            ObservableList<Node> checks = v.getChildren();
            for(Node node : checks){
                CheckBox ckbox = (CheckBox)node;
                if(ckbox.isSelected()) {
                    AllAnimeController.filterslist.add(ckbox.getText());
                    System.out.println(ckbox.getText());
                }
            }
        }
        AllAnimeController.windownew.close();
    }
}
