package animeApp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by admin on 5/16/2016.
 */
public class textdisplayController implements Initializable {
    @FXML
    public TextFlow textarea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Controller.textDisplay.equals("about")) {
            Text text1 = new Text("\n   About Anime Watchmaster:");
            text1.setFont(Font.font("FontAwesome", FontWeight.BOLD, 15));

            Text text2 = new Text("\n       My nickname is Rimezero, I am a software engineer and also an anime fan. The main idea behind this project \n" +
                    "   started back in 2014 like a small exercise to practise programming in java fx but at the same time on developing something useful for me and my friends. \n" +
                    "       I used to be a semi-hardcore anime fan standing at the borders of 'otaku', I've watched a ton of different anime and used to watch at least 3 ongoing anime every new season\n" +
                    "   so I wanted a tool that would save me some precious time going over websites, searching for anime and keeping track of the ones I watch, along with downlaoding them. I tried to develop this \n" +
                    "   app to do just that, but slowly I started adding more and more stuff, updating once in a while to today's version. Now after getting used to this app I can't imagine going back if I plan \n" +
                    "   to watch anime.\n\n" +
                    "   I decided to share this with the community for free. Feel free to share this app with friends. Happy anime watching :)\n" +
                    "   \n" +
                    "   ");
            text2.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 14));

            textarea.getChildren().addAll(text1, text2);
        }else if(Controller.textDisplay.equals("button_instructions")){

            try {
                FileReader fr = new FileReader("button_instructions.txt");
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                String text = "";
                while(line!=null){
                    text += line +"\n";
                    line = br.readLine();
                }

                Text text1 = new Text(text);
                text1.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 14));
                textarea.getChildren().add(text1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
