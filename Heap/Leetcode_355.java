package Heap;

import java.util.*;

public class Leetcode_355
{
}
class Twitter {
    private int timestamp;
    private Map<Integer, List<int[]>> tweets;      // userId -> list of [timestamp, tweetId]
    private Map<Integer, Set<Integer>> following;   // userId -> set of userIds they follow

    public Twitter() {
        timestamp = 0;
        tweets = new HashMap<>();
        following = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new int[]{timestamp++, tweetId});
    }

    public List<Integer> getNewsFeed(int userId) {
        // max heap ordered by timestamp (descending): [timestamp, tweetId, userId, index]
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);

        Set<Integer> people = new HashSet<>();
        people.add(userId); // you always see your own tweets
        if (following.containsKey(userId)) {
            people.addAll(following.get(userId));
        }

        // seed the heap with each person's most recent tweet
        for (int person : people) {
            if (tweets.containsKey(person)) {
                List<int[]> userTweets = tweets.get(person);
                int lastIndex = userTweets.size() - 1;
                int[] mostRecent = userTweets.get(lastIndex);
                maxHeap.offer(new int[]{mostRecent[0], mostRecent[1], person, lastIndex});
            }
        }

        List<Integer> result = new ArrayList<>();

        while (!maxHeap.isEmpty() && result.size() < 10) {
            int[] curr = maxHeap.poll();
            result.add(curr[1]); // tweetId

            int person = curr[2];
            int idx = curr[3] - 1; // check for an older tweet from the same person

            if (idx >= 0) {
                int[] older = tweets.get(person).get(idx);
                maxHeap.offer(new int[]{older[0], older[1], person, idx});
            }
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) return; // you don't need to "follow" yourself
        following.putIfAbsent(followerId, new HashSet<>());
        following.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId)) {
            following.get(followerId).remove(followeeId);
        }
    }
    /*
postTweet: O(1)
follow/unfollow: O(1)

getNewsFeed: O(F log F) — where F = number of people followed, since the heap never holds more than F+1 entries at once, and we do up to 10 poll/offer cycles
*/
}
