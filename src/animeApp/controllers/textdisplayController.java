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

            Text text2 = new Text("\n       My nickname is Peitch, I am a computer science student in Alexander Technological Educational Institute in Greece. The main idea behind this project \n" +
                    "   started about a year ago like an exercise to practise programming in java fx but at the same time on developing something useful for me and my friends. \n" +
                    "       I am a semi-hardcore anime fan standing at the borders of 'otaku', I've watched a ton of different anime and I always watch at least 3 ongoing anime every new season\n" +
                    "   so i wanted a tool that would save me some precious time going over websites, searching for anime and keeping track of the ones i watch. I tried to develop this \n" +
                    "   program to do just that, but slowly I started adding more and more stuff, updating once in a while to today's version. Now i use this program nearly every day and \n" +
                    "   it saves me a lot of time and effort by doing so.\n" +
                    "       Since it seems very useful to me and my friends i decided to share it for free with everyone in the community and i will propably develop an android application \n" +
                    "   like this one sometime soon. I finished my third year of university by now and the android application is propably going to be at a different level since my skills are \n" +
                    "   now a lot greater than back when i started making this.\n" +
                    "       Note that in case something changes to the server address this application uses to draw data or i change my own database server that you will need a new version of the\n" +
                    "   program for it to keep functioning normally.\n" +
                    "   \n" +
                    "       If anyone would like to contact me please do at valadispit3@hotmail.com although i might take a while to respond since i dont check my mail regularly. I hope you like\n" +
                    "   my program. Share it with your frinds, share it with the community and KEEP WATCHING ANIME!!! :-)");
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
