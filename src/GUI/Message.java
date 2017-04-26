package GUI;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.Label;
import java.awt.ScrollPane;
import java.util.*;


/**
 * Created by havardbjornoy on 03/04/2017.
 */
public class Message { // CREATES A SINGLE MESSAGE FOR THE CHATBOT

    String message;
    Boolean isBot;

    public Message (Boolean isBot, String speech) {
        this.isBot = isBot;
        message = speech;
    }
}
