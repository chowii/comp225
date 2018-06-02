package student;

import static student.Polarity.*;

public class FinegrainedSentiment {

    private FinegrainedSentiment() {
    }

    Strength strength;
    int length;
    String word;
    String partsOfSpeech;
    String stemmed;
    Polarity polarity;


    public FinegrainedSentiment(String strength, String length, String word, String partsOfSpeech, String stemmed, String polarity) {
        this.strength = getStrengthFromString(strength);
        this.length = Integer.valueOf(length);
        this.word = word;
        this.partsOfSpeech = partsOfSpeech;
        this.stemmed = stemmed;
        this.polarity = getPolarityFromString(polarity);
    }

    public Polarity getPolarityFromString(String polarity) {
        if (polarity == null) return null;
        Polarity res;
        switch (polarity) {
            case "positive":
                res = POS;
                break;
            case "negative":
                res = NEG;
                break;
            case "neutral":
                res = NEUT;
                break;
            default:
                res = NONE;
        }
        return res;
    }

    public Strength getStrengthFromString(String strength) {
        if (strength == null) return null;
        Strength res;
        switch (strength) {
            case "weaksubj":
                res = Strength.WEAK;
                break;
            case "strongsubj":
                res = Strength.STRONG;
                break;
            default:
                res = Strength.NONE;
        }
        return res;
    }

}
