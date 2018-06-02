package student;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;

public class Tests {

	// replace the following with your own directories + files
	/*
	String SAMPLE_10_TWEET_CSV_FILE_PATH = "/home/madras/teaching/18comp225/ass/data/training-10.csv";
	String BASIC_SENT_FILE_PATH = "/home/madras/teaching/18comp225/ass/data/basic-sent-words.txt";
	String INV_INDEX_FILE_PATH = "/home/madras/teaching/18comp225/ass/data/inv-index-50.txt";
	String FINEGRAINED_SENT_FILE_PATH = "/home/madras/teaching/18comp225/ass/data/finegrained-sent-words.txt";
	*/

	private static String path = "src/student/res/data/";
	static String SAMPLE_10_TWEET_CSV_FILE_PATH = path + "training-10.csv";
	static String SAMPLE_40_CSV_FILE_PATH = path + "training-40.csv";
	static String BASIC_SENT_FILE_PATH = path + "basic-sent-words.txt";
	static String INV_INDEX_FILE_PATH = path + "inv-index-40.txt";
	static String FINEGRAINED_SENT_FILE_PATH = path + "finegrained-sent-words.txt";

	// SAMPLE PASS-LEVEL TESTS
	
	@Test
	public void testNumTweets() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.numTweets(), Integer.valueOf(10));
	}
	
	@Test
	public void testTweetUser() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810369").getUser(), "_TheSpecialOne_");
	}

	@Test
	public void testTweetGoldPolarity() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810369").getGoldPolarity(), Polarity.NEG);
	}

	@Test
	public void testTweetPredictedPolarity() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810369").getPredictedPolarity(), Polarity.NONE);
	}

	@Test
	public void testTweetText() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810369").getText(), "@switchfoot http://twitpic.com/2y1zl - Awww, that's a bummer.  You shoulda got David Carr of Third Day to do it. ;D");
	}

	@Test
	public void testTweetWords() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		String w = d.getTweetByID("1467810369").getWords()[0];
		assertEquals(w, "awww");
	}

	@Test
	public void testTweetKeywordSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810672").getPredictedPolarity(), Polarity.NEG);
	}

	@Test
	public void testTweetKeywordSentimentPositive() {
		TweetCollection d = new TweetCollection();

		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467811184").getPredictedPolarity(), Polarity.POS);
	}

	@Test
	public void testTweetKeywordSentimentNone() {
		TweetCollection d = new TweetCollection();

		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467811372").getPredictedPolarity(), Polarity.NONE);
	}

	@Test
	public void testTweetKeywordSentimentNeutral() {
		TweetCollection d = new TweetCollection();

		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467811592").getPredictedPolarity(), Polarity.NEUT);
	}

	@Test
	public void testTweetKeywordCorrectSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810672").correctlyPredictedPolarity(), Boolean.TRUE);
	}

	@Test
	public void testTweetKeywordIncorrectSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467811184").correctlyPredictedPolarity(), Boolean.FALSE);
	}

	@Test
	public void testTweetKeywordAccuracy() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Double.valueOf(0.4), d.accuracy());
	}

	@Test
	public void testTweetKeywordCoverage() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.coverage(), Double.valueOf(0.5));
	}

	// SAMPLE CREDIT-LEVEL TESTS
	
	@Test
	public void testImportInverseIndex() {
		TweetCollection d = new TweetCollection();
		String[] IDs = {"1467811184", "1467811372"};
		Vector<String> v = new Vector<String>(Arrays.asList(IDs));
		Map<String, Vector<String>> invIndex = null;
		try {
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(v, invIndex.get("whole"));
	}

	@Test
	public void testImportInverseIndexSingleList() {
		TweetCollection d = new TweetCollection();
		String[] IDs = {"2193578982"};
		Vector<String> v = new Vector<String>(Arrays.asList(IDs));
		Map<String, Vector<String>> invIndex = null;
		try {
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(v, invIndex.get("lsd"));
	}

	@Test
	public void testGraphConstruction() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.constructSharedWordGraph(invIndex);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810672").numNeighbours(), Integer.valueOf(1));
	}

	@Test
	public void testGraphConstructionMultiple() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;

		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.constructSharedWordGraph(invIndex);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467810672").numNeighbours(), Integer.valueOf(1));
	}

	@Test
	public void testNumComponents() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Integer.valueOf(7), d.numConnectedComponents());
	}

	@Test
	public void testComponentLabelCountPos() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.componentSentLabelCount("1467811372", Polarity.POS), Integer.valueOf(1));
	}

	@Test
	public void testComponentLabelCountNeg() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.componentSentLabelCount("1467811372", Polarity.NEG), Integer.valueOf(0));
	}

	@Test
	public void testComponentLabelCountNeut() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.componentSentLabelCount("1467811372", Polarity.NEUT), Integer.valueOf(0));
	}

	@Test
	public void testComponentLabelCountNone() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.componentSentLabelCount("1467811372", Polarity.NONE), Integer.valueOf(1));
	}


	@Test
	public void testLabelPropagationOverComponent1() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
			d.propagateLabelAcrossComponent("1467811372", Polarity.NEUT, Boolean.FALSE);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467811372").getPredictedPolarity(), Polarity.NEUT);
	}

	@Test
	public void testLabelPropagationOverComponent2() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
			d.propagateLabelAcrossComponent("1467811372", Polarity.NEUT, Boolean.FALSE);

		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertFalse(true); // todo remove
		assertEquals(d.getTweetByID("1467811184").getPredictedPolarity(), Polarity.POS);
	}

	@Test
	public void testComponentMajorityLabel() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
			d.propagateMajorityLabelAcrossComponents(Boolean.FALSE);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467811372").getPredictedPolarity(), Polarity.POS);
	}
	/*
	*/

	// SAMPLE DISTINCTION-LEVEL TESTS
	
	@Test
	public void testTweetFinegrainedKeywordSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_10_TWEET_CSV_FILE_PATH);
			d.importFinegrainedSentimentWordsFromFile(FINEGRAINED_SENT_FILE_PATH);
			d.predictTweetSentimentFromFinegrainedWordlist(2, 1);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(d.getTweetByID("1467811594").getPredictedPolarity(), Polarity.NEG);
	}

	
}
