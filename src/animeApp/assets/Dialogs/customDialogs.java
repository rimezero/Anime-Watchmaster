package animeApp.assets.Dialogs;



import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import animeApp.databaseUtils.StartUpLocation;

public class customDialogs {

    public static boolean displayConfirmDialog(String title, String message, String question){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(question);
        
        alertOnCurrentScreen(alert);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK)
            return true;
        else
            return false;
    }
    public static String displaySiteChoiceDialog(){
        List<String> choices = new ArrayList<>();
        choices.add("Animefreak");
        choices.add("Animeultima");
        choices.add("MyAnimeList");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Animeultima", choices);
        dialog.setTitle("Site choice");
        dialog.setHeaderText("Which site do you prefer?");
        dialog.setContentText("Choose a site:");
        
        dialogOnCurrentScreen(dialog, 200, 100);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }else
            return " ";
    }

    public static String displaySiteChoiceDialogDbType(){
        List<String> choices = new ArrayList<>();
        choices.add("SQLite");
        choices.add("MySQL");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("SQLite", choices);
        dialog.setTitle("Local database type");
        dialog.setHeaderText("Which type will you use?");
        dialog.setContentText("Choose a database:");
        
        dialogOnCurrentScreen(dialog, 200, 100);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }else
            return " ";
    }

    public static String displayImageChoiceDialog(){
        List<String> choices = new ArrayList<>();
        choices.add("Show images");
        choices.add("Do not show images");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Options:", choices);
        dialog.setTitle("Image Display");
        dialog.setHeaderText("Should the program display images on anime lists?");
        dialog.setContentText("Displaying images will use more memory (you can change this anytime)");
        
        dialogOnCurrentScreen(dialog, 200, 100);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }else
            return " ";
    }
    public static void displayErrorDialog(String title,String mainmessage, String secondarymessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(mainmessage);
        alert.setContentText(secondarymessage);
        
        alertOnCurrentScreen(alert);

        alert.showAndWait();
    }
    public static void displayInformationDialog(String title, String mainmessage, String secondarymessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(mainmessage);
        alert.setContentText(secondarymessage);
        
        alertOnCurrentScreen(alert);
        

        alert.showAndWait();
    }
    
    /**
     * 
     * @param initialValue Initial value displayed in the text field
     * @return returns "Cancel" if dialog is cancelled
     */
    public static String displayTextInputDialog(String initialValue, String title, String headerText, String contentText) {
    	String result = "Cancel";
    	TextInputDialog dialog = new TextInputDialog(initialValue);
    	dialog.setTitle(title);
    	dialog.setHeaderText(headerText);
    	dialog.setContentText(contentText);
    	
    	dialogOnCurrentScreen(dialog, 200, 100);
    	
    	Optional<String> tmpresult = dialog.showAndWait();
    	
    	if (tmpresult.isPresent()){
    	    result = tmpresult.get();
    	}
    	
    	return result;
    }
    
    /**
     * 
     * @param title
     * @param headerText 
     * @param contentText usually set to ex.message()
     * @param ex The exception to be printed
     */
    public static void displayExceptionDialog(String title, String headerText, String contentText, Exception ex) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle(title);
    	alert.setHeaderText(headerText);
    	alert.setContentText(contentText);

    	// Create expandable Exception.
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	String exceptionText = sw.toString();

    	Label label = new Label("The exception stacktrace was:");

    	TextArea textArea = new TextArea(exceptionText);
    	textArea.setEditable(false);
    	textArea.setWrapText(true);

    	textArea.setMaxWidth(Double.MAX_VALUE);
    	textArea.setMaxHeight(Double.MAX_VALUE);
    	GridPane.setVgrow(textArea, Priority.ALWAYS);
    	GridPane.setHgrow(textArea, Priority.ALWAYS);

    	GridPane expContent = new GridPane();
    	expContent.setMaxWidth(Double.MAX_VALUE);
    	expContent.add(label, 0, 0);
    	expContent.add(textArea, 0, 1);

    	// Set expandable Exception into the dialog pane.
    	alert.getDialogPane().setExpandableContent(expContent);

    	alert.showAndWait();
    }
    
    public static void alertOnCurrentScreen(Alert alert) {
    	//set on active screen
        StartUpLocation startupLoc = new StartUpLocation(200, 100);
        double xPos = startupLoc.getXPos();
        double yPos = startupLoc.getYPos();
        // Set Only if X and Y are not zero and were computed correctly
        if (xPos != 0 && yPos != 0) {
        	alert.setX(xPos);
        	alert.setY(yPos);
        } 
    }
    
    public static void dialogOnCurrentScreen(Dialog<String> dialog, int width, int height) {
    	//set on active screen
        StartUpLocation startupLoc = new StartUpLocation(width, height);
        double xPos = startupLoc.getXPos();
        double yPos = startupLoc.getYPos();
        // Set Only if X and Y are not zero and were computed correctly
        if (xPos != 0 && yPos != 0) {
        	dialog.setX(xPos);
        	dialog.setY(yPos);
        } 
    }
    /*
    public static String[] displayLoginDialog(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Database login");
        dialog.setHeaderText("Enter your database information");


        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> username.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String usr = username.getText(),pass=password.getText();
                if(databaseControl.testConnection(usr,pass))
                    return new Pair<>(usr, pass);
                else
                    return new Pair<>("failedlogin","failedlogin");
            }else {
                return new Pair<>("canceledlogin","canceledlogin");
            }
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();


        String[] resultarray = new String[2];
        result.ifPresent(usernamePassword -> {
            resultarray[0] = usernamePassword.getKey();
            resultarray[1] = usernamePassword.getValue();
        });
        return resultarray;
    }*/
}
