package student


fun main(args: Array<String>) {
    val tCollection = TweetCollection()

    tCollection.apply {
        ingestTweetsFromFile(Tests.SAMPLE_10_TWEET_CSV_FILE_PATH)
        importBasicSentimentWordsFromFile(Tests.BASIC_SENT_FILE_PATH)
        val invIndex = importInverseIndexFromFile(Tests.INV_INDEX_FILE_PATH)
        constructSharedWordGraph(invIndex)
        val s = tweetIdToTweetMap.toList().mapIndexed { index, (id, tweet) ->
            System.out.println("index: $index, tweetId: $id size: ${tweet.neighbourList.size}" )
            tweet.neighbourList.map {
                System.out.println(" -> index: $index, tweetId: $id neighbourId: $it")
                val s = tweetIdToTweetMap[it]?.predictedPolarity
                System.out.println(" -> index: $index, polarity: $s")
//                when (s) {
//                    Polarity.POS -> 1
//                    Polarity.NEG -> -1
//                    else -> 0
//                }
            }
        }
        s.forEach {
            it.forEach {
                System.out.println(it)
            }
        }
    }
}
