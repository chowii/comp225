package student;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static student.Polarity.*;
import static student.Tests.BASIC_SENT_FILE_PATH;
import static student.Tests.FINEGRAINED_SENT_FILE_PATH;


public class TweetCollection extends TreeMap<String, Tweet> {

    // TODO: add appropriate data types
    Map<String, Polarity> basicWordToPolarityMap;
    Map<String, FinegrainedSentiment> fineGrainedSentimentMap;
    Vector<Vector<String>> annotatedComponentVector;

    public TweetCollection() {
        // Constructor
        // TODO
        annotatedComponentVector = new Vector<>();
        fineGrainedSentimentMap = new HashMap<>();
        basicWordToPolarityMap = new HashMap<>();
        try {
            importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
            importFinegrainedSentimentWordsFromFile(FINEGRAINED_SENT_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * functions for accessing individual tweets
     */

    public Tweet getTweetByID(String ID) {
        // PRE: -
        // POST: Returns the Tweet object that with tweet ID
        return get(ID);
    }

    public Integer numTweets() {
        // PRE: -
        // POST: Returns the number of tweets in this collection
        return size();
    }

    /*
     * functions for accessing sentiment words
     */

    public Polarity getBasicSentimentWordPolarity(String w) {
        // PRE: w not null, basic sentiment words already read in from file
        // POST: Returns goldPolarity of w

        // TODO
        Polarity polarity;
        switch (w) {
            case "negative":
                polarity = NEG;
                break;
            case "positive":
                polarity = POS;
                break;
            case "neutral":
                polarity = NEUT;
                break;
            default:
                polarity = NONE;
        }
        return polarity;
    }

    public Polarity getFinegrainedSentimentWordPolarity(String w) {
        // PRE: w not null, finegrained sentiment words already read in from file
        // POST: Returns goldPolarity of w
        return fineGrainedSentimentMap.get(w).polarity;
    }

    public Strength getFinegrainedSentimentWordStrength(String w) {
        // PRE: w not null, finegrained sentiment words already read in from file
        // POST: Returns strength of w
        return fineGrainedSentimentMap.get(w).strength;
    }

    /*
     * functions for reading in tweets
     *
     */


    public void ingestTweetsFromFile(String fInName) throws IOException {
        // PRE: -
        // POST: Reads tweets from .csv file, stores in data structure

        // NOTES
        // Data source, file format description at http://help.sentiment140.com/for-students
        // Using apache csv reader: https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/

        try (
                Reader reader = Files.newBufferedReader(Paths.get(fInName));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                // Accessing Values by Column Index

                Tweet tw = new Tweet(csvRecord.get(0), // gold goldPolarity
                        csvRecord.get(1),                // ID
                        csvRecord.get(2),                // date
                        csvRecord.get(4),                // user
                        csvRecord.get(5));                // text

                // TODO: insert tweet tw into appropriate data type
                put(tw.getID(), tw);
            }
        }
    }

    public String[] getWords(String line) {
        /// PRE: -
        // POST: Returns tokenised text of tweet as array of strings

        if (line == null)
            return null;

        String[] words = null;

        String tmod = line;
        tmod = tmod.replaceAll("@.*?\\s", "");
        tmod = tmod.replaceAll("http.*?\\s", "");
        tmod = tmod.replaceAll("\\s+", " ");
        tmod = tmod.replaceAll("[\\W&&[^\\s]]+", "");
        tmod = tmod.toLowerCase();
        tmod = tmod.trim();
        words = tmod.split("\\s");

        return words;
    }

    /*
     * functions for sentiment words
     */

    public void importBasicSentimentWordsFromFile(String fInName) throws IOException {
        // PRE: -
        // POST: Read in and store basic sentiment words in appropriate data type

        // TODO
        FileReader fileReader = new FileReader(fInName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String readLine;

        while ((readLine = bufferedReader.readLine()) != null) {
            String[] wordAndIdArray = getWords(readLine);
            String word = wordAndIdArray[0];
            String id = wordAndIdArray[1];
            basicWordToPolarityMap.put(word, getBasicSentimentWordPolarity(id));
        }
    }

    public void importFinegrainedSentimentWordsFromFile(String fInName) throws IOException {
        // PRE: -
        // POST: Read in and store finegrained sentiment words in appropriate data type

        // TODO
        BufferedReader bufferedReader = getBufferedReaderFromFile(fInName);
        String readLine;

        while ((readLine = bufferedReader.readLine()) != null) {
            String[] sentimentParsed = readLine.replaceAll("\\w*=", "").split("\\s");
            FinegrainedSentiment finegrainedSentiment = new FinegrainedSentiment(
                    sentimentParsed[0],
                    sentimentParsed[1],
                    sentimentParsed[2],
                    sentimentParsed[3],
                    sentimentParsed[4],
                    sentimentParsed[5]);

            fineGrainedSentimentMap.put(finegrainedSentiment.word, finegrainedSentiment);
        }
        bufferedReader.close();
    }


    public Boolean isBasicSentWord(String w) {
        // PRE: Basic sentiment words have been read in and stored
        // POST: Returns true if w is a basic sentiment word, false otherwise
        return basicWordToPolarityMap.containsKey(w);
    }

    public Boolean isFinegrainedSentWord(String w) {
        // PRE: Finegrained sentiment words have been read in and stored
        // POST: Returns true if w is a finegrained sentiment word, false otherwise
        return fineGrainedSentimentMap.containsKey(w);
    }

    public void predictTweetSentimentFromBasicWordlist() {
        // PRE: Finegrained word sentiment already imported
        // POST: For all tweets in collection, tweet annotated with predicted sentiment
        //         based on count of sentiment words in sentWords

        // TODO

        forEach((id, tweet) -> {
            String[] tweetWordList = getWords(tweet.text);
            Integer count = null;
            Polarity p;
            for (String tweetWord : tweetWordList) {
                p = basicWordToPolarityMap.get(tweetWord);
                if (p != null) {
                    if (count == null) count = 0;
                    switch (p) {
                        case POS:
                            count++;
                            break;
                        case NEG:
                            count--;
                            break;
                    }
                }
            }

            tweet.setPredictedPolarity(count == null ? NONE : count > 0 ? POS : count == 0 ? NEUT : NEG);
        });
    }

    public void predictTweetSentimentFromFinegrainedWordlist(Integer strongWeight, Integer weakWeight) {
        // PRE: Finegrained word sentiment already imported
        // POST: For all tweets in v, tweet annotated with predicted sentiment
        //         based on count of sentiment words in sentWords

        // TODO
        forEach((id, tweet) -> {
            String[] wordList = tweet.getWords();
            Integer count = null;
            Polarity p;
            Strength s;
            for (String word : wordList) {
                FinegrainedSentiment f = fineGrainedSentimentMap.get(word);
                if (f != null) {
                    p = f.polarity;
                    s = f.strength;
                    if (count == null) count = 0;
                    switch (p) {
                        case POS:
                            if (s == Strength.STRONG) {
                                count += strongWeight;
                            } else {
                                count += weakWeight;
                            }
                            break;
                        case NEG:
                            if (s == Strength.STRONG) {
                                count -= strongWeight;
                            } else {
                                count -= weakWeight;
                            }
                            break;
                    }
                }
            }
            tweet.setPredictedPolarity(count == null ? NONE : count > 0 ? POS : count == 0 ? NEUT : NEG);
        });
    }

    /*
     * functions for inverse index
     *
     */

    public Map<String, Vector<String>> importInverseIndexFromFile(String fInName) throws IOException {
        // PRE: -
        // POST: Read in and returned contents of file as inverse index
        //         invIndex has words w as key, IDs of tweets that contain w as value

        // TODO
        BufferedReader bufferedReader = getBufferedReaderFromFile(fInName);
        Map<String, Vector<String>> inverseStringToIdListMap = new HashMap<>();
        String readLine;

        while ((readLine = bufferedReader.readLine()) != null) {
            String[] wordArray = readLine.split("[,\\s*]");
            Vector<String> idList = new Vector<>(Arrays.asList(wordArray).subList(1, wordArray.length));
            inverseStringToIdListMap.put(wordArray[0], idList);
        }
        return inverseStringToIdListMap;
    }

    public BufferedReader getBufferedReaderFromFile(String fInName) throws FileNotFoundException {
        FileReader fileReader = new FileReader(fInName);
        return new BufferedReader(fileReader);
    }


    /*
     * functions for graph construction
     */

    public void constructSharedWordGraph(Map<String, Vector<String>> invIndex) {
        // PRE: invIndex has words w as key, IDs of tweets that contain w as value
        // POST: Graph constructed, with tweets as vertices,
        //         and edges between them if they share a word

        for (String key : invIndex.keySet()) {
            Vector<String> idList = invIndex.get(key);
            idList.forEach((id) -> {
                Tweet t = get(id);
                if (t != null) {
                    for (String wordIdList : idList) {
                        if (!wordIdList.equals(id) && get(wordIdList) != null) {
                            t.addNeighbour(wordIdList);
                        }
                    }
                }
            });
        }
    }


    public Integer numConnectedComponents() {
        // PRE: -
        // POST: Returns the number of connected components

        // TODO
        return annotatedComponentVector.size();
    }


    public void annotateConnectedComponents() {
        // PRE: -
        // POST: Annotates graph so that it is partitioned into components

        // TODO

        for (String s : keySet()) {
            Vector<String> strings = new Vector<>();
            Tweet tweet = getTweetByID(s);
            if (!tweet.isVisited) {
                tweet.isVisited = true;
                dft(strings, tweet);
                annotatedComponentVector.add(strings);
            }
        }
    }

    public void dft(Vector<String> list, Tweet tweet) {
        tweet.isVisited = true;
        PriorityQueue<String> queue = new PriorityQueue<>();
        if (!list.contains(tweet.id))
            list.add(tweet.id);
        tweet.neighbourList.forEach(nId -> {
            if (!queue.contains(nId)) {
                queue.add(nId);
            }
        });
        while (queue.iterator().hasNext()) {
            Tweet tweetByID = getTweetByID(queue.poll());
            if (!tweetByID.isVisited)
                dft(list, tweetByID);
        }
    }


    public Integer componentSentLabelCount(String ID, Polarity p) {
        // PRE: Graph components are identified, ID is a valid tweet
        // POST: Returns count of labels corresponding to Polarity p in component containing ID

        // TODO
        Integer count = 0;
        for (Vector<String> v : annotatedComponentVector) {
            if (v.contains(ID)){
                for (String id : v) {
//                    if (id.equals(ID)) {
                        if (p == get(id).getPredictedPolarity()) {
                            count++;
//                        }
                    }
                }
            }
        }

        return count;
    }


    public void propagateLabelAcrossComponent(String ID, Polarity p, Boolean keepPred) {
        // PRE: ID is a tweet id in the graph
        // POST: Labels tweets in component with predicted polarity p
        //         (if keepPred == T, only tweets w pred polarity None; otherwise all tweets

        // TODO
        for (Vector<String> tId : annotatedComponentVector) {
            if (tId.contains(ID)) {
                for (String id : tId) {
                    Tweet tweet = get(id);
                    if (keepPred) {
                        if (tweet.predictedPolarity == NONE) {
                            tweet.predictedPolarity = p;
                        }
                    } else {
                        tweet.predictedPolarity = p;
                    }

                }
            }
        }
    }

    public void propagateMajorityLabelAcrossComponents(Boolean keepPred) {
        // PRE: Components are identified
        // POST: Tweets in each component are labelled with the majority sentiment for that component
        //       Majority label is defined as whichever of POS or NEG has the larger count;
        //         if POS and NEG are both zero, majority label is NONE
        //         otherwise, majority label is NEUT
        //       If keepPred is True, only tweets with predicted label None are labelled in this way
        //         otherwise, all tweets in the component are labelled in this way

        // TODO
        for (Vector<String> tId : annotatedComponentVector) {
            int posCount = 0, negCount = 0;
            for (String id : tId) {
                Polarity predictedPolarity = get(id).predictedPolarity;
                if (predictedPolarity == POS)
                    posCount++;
                else if (predictedPolarity == NEG)
                    negCount++;
            }
            if (posCount > negCount)
                propagateLabelAcrossComponent(tId.get(0), POS, keepPred);
            else if (negCount > posCount)
                propagateLabelAcrossComponent(tId.get(0), NEG, keepPred);
            else if (posCount == 0 && negCount == 0)
                propagateLabelAcrossComponent(tId.get(0), NONE, keepPred);
            else
                propagateLabelAcrossComponent(tId.get(0), NEUT, keepPred);

        }
    }



    /*
     * functions for evaluation
     */

    public Double accuracy() {
        // PRE: -
        // POST: Calculates and returns accuracy of labelling

        // TODO
        double numCorrect = 0, numPredicted = 0;
        for (String key : keySet()) {
            Tweet tweet = get(key);

            if (tweet.predictedPolarity != NONE) {
                numCorrect += tweet.predictedPolarity == tweet.goldPolarity ? 1 : 0;
                numPredicted++;
            }
        }
        return numCorrect / numPredicted;
    }

    public Double coverage() {
        // PRE: -
        // POST: Calculates and returns coverage of labelling

        // TODO
        int count = 0;
        for (String key : keySet()) {
            Tweet tweet = get(key);
            if (tweet.isTweetPredicted()) {
                count++;
            }
        }

        return (double) count / (double) size();
    }


    public static void main(String[] args) {
        TweetCollection tweetCollection = new TweetCollection();
        try {
            tweetCollection.ingestTweetsFromFile(Tests.SAMPLE_CSV_FILE_PATH);
            tweetCollection.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
            Map<String, Vector<String>> i = tweetCollection.importInverseIndexFromFile(Tests.INV_INDEX_FILE_PATH);
            tweetCollection.forEach((id, tweet) ->
                    tweet.dumpNeigbourList()
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void myTweetSentimentPredictor() {
        predictTweetSentimentFromFinegrainedWordlist(2, 1);
        System.out.println("myTweetSentimentPredictor: " + accuracy());
    }
}
