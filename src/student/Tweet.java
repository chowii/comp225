package student;

import java.util.*;
import java.util.stream.Collectors;

import static student.Polarity.*;

enum Polarity {
    POS, NEG, NEUT, NONE;
}

enum Strength {
    STRONG, WEAK, NONE;
}

public class Tweet {

    // TODO: Add appropriate data types
    String polarityString, id, date, user, text;
    Polarity goldPolarity, predictedPolarity, annotatedPolarity;
    Vector<String> neighbourList = new Vector<>();
    boolean isVisited;


    public Tweet(String p, String i, String d, String u, String t) {
        polarityString = p;
        goldPolarity = getPolarityFromString(polarityString);
        id = i;
        date = d;
        user = u;
        text = t;
        predictedPolarity = Polarity.NONE;
        isVisited = false;
    }

    public Polarity getAnnotatedPolarity() {
        return annotatedPolarity;
    }

    public void setAnnotatedPolarity(Polarity annotatedPolarity) {
        this.annotatedPolarity = annotatedPolarity;
    }

    public Polarity getPolarityFromString(String polarityString) {
        Polarity polarity;
        switch (polarityString) {
            case "0":
                polarity = NEG;
                break;
            case "2":
                polarity = NEUT;
                break;
            case "4":
                polarity = POS;
                break;
            default:
                polarity = NONE;
        }
        return polarity;
    }

    public void addNeighbour(String ID) {
        // PRE: -
        // POST: Adds a neighbour to the current tweet as part of graph structure

        if (neighbourList.indexOf(ID) < 0)
            neighbourList.add(ID);
    }

    public Integer numNeighbours() {
        // PRE: -
        // POST: Returns the number of neighbours of this tweet

        // TODO
        return neighbourList.size();
    }

    public void deleteAllNeighbours() {
        // PRE: -
        // POST: Deletes all neighbours of this tweet

        // TODO
        neighbourList.clear();
    }

    public Vector<String> getNeighbourTweetIDs() {
        // PRE: -
        // POST: Returns IDs of neighbouring tweets as vector of strings

        // TODO

        return neighbourList;
    }

    public Boolean isNeighbour(String ID) {
        // PRE: -
        // POST: Returns true if ID is neighbour of the current tweet, false otherwise

        // TODO
        return neighbourList.stream().filter(neighbourId -> neighbourId.equalsIgnoreCase(ID)).collect(Collectors.toList()).size() > 1;
    }

    public Boolean correctlyPredictedPolarity() {
        // PRE: -
        // POST: Returns true if predicted goldPolarity matches gold, false otherwise
        return predictedPolarity == goldPolarity;
    }

    public Polarity getGoldPolarity() {
        // PRE: -
        // POST: Returns the gold goldPolarity of the tweet
        return getPolarityFromString(this.polarityString);
    }

    public Polarity getPredictedPolarity() {
        // PRE: -
        // POST: Returns the predicted goldPolarity of the tweet
        return predictedPolarity;
    }

    public void setPredictedPolarity(Polarity p) {
        // PRE: -
        // POST: Sets the predicted goldPolarity of the tweet
        predictedPolarity = p;
    }

    public String getID() {
        // PRE: -
        // POST: Returns ID of tweet
        return id;
    }

    public String getDate() {
        // PRE: -
        // POST: Returns date of tweet
        return date;
    }

    public String getUser() {
        // PRE: -
        // POST: Returns identity of tweeter
        return user;
    }

    public String getText() {
        // PRE: -
        // POST: Returns text of tweet as a single string
        return text;
    }

    public boolean isTweetPredicted() {
        return predictedPolarity != NONE;
    }

    public String[] getWords() {
        // PRE: -
        // POST: Returns tokenised text of tweet as array of strings

        if (this.getText() == null)
            return null;

        String[] words = null;

        String tmod = this.getText();
        tmod = tmod.replaceAll("@.*?\\s", "");
        tmod = tmod.replaceAll("http.*?\\s", "");
        tmod = tmod.replaceAll("\\s+", " ");
        tmod = tmod.replaceAll("[\\W&&[^\\s]]+", "");
        tmod = tmod.toLowerCase();
        tmod = tmod.trim();
        words = tmod.split("\\s");

        return words;

    }

    String neighbourListToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= neighbourList.size(); i++) {
            sb.append("tweetId: ").append(id).append(" -> ").append("neighbourId: ");
            if (neighbourList.size() < 0)
                sb.append("no neighbour");
            else
                sb.append(neighbourList.get(i));
            sb.append('\n');
        }
        return sb.toString();
    }

    void dumpNeigbourList() {
        System.out.println(neighbourListToString());

    }

}
