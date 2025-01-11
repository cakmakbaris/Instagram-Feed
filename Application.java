import java.util.ArrayList;

/**
 * Out application which consists of users and posts and some methods described below used by users
 */
public class Application {
    MyHashMap<String, User> users = new MyHashMap<>(); // HashMap of users
    MyHashMap<String, Post> posts = new MyHashMap<>(); // HashMap of posts

    /**
     * Given a userID, creates a User object with ID : userID and adds it to users HashMap.
     * @param userID Unique ID of the user
     * @return Response
     */
    public String createUser(String userID){
        User user = new User(userID);
        if (users.insert(userID, user)) // If insertion is successful
            return "Created user with Id " + userID + ".";
        // If insertion is not successful, that means user already exists
        return "Some error occurred in create_user.";
    }

    /**
     * Given user1ID and user2ID, add user2 to user1's followed users
     * @param user1ID ID of the user that follows
     * @param user2ID ID of the user that is being followed by user1
     * @return Response
     */
    public String followUser(String user1ID, String user2ID){
        User user1 = users.getValue(user1ID);
        User user2 = users.getValue(user2ID);
        if (user1 == null || user2 == null) { // One of users does not exists
            return "Some error occurred in follow_user.";
        }
        if (user1ID.equals(user2ID)) // A user cannot follow itself
            return "Some error occurred in follow_user.";
        return user1.follow(user2); // Execute the follow action
    }

    /**
     *Given user1ID and user2ID, remove user2 from user1's unfollowed users
     * @param user1ID String user that unfollows
     * @param user2ID String user that is unfollowed by the other user
     * @return corresponding response of the actions
     */
    public String unfollowUser(String user1ID, String user2ID){
        User user1 = users.getValue(user1ID);
        User user2 = users.getValue(user2ID);
        if (user1 == null || user2 == null) // One of users does not exist
            return "Some error occurred in unfollow_user.";
        if (user1ID.equals(user2ID))
            return "Some error occurred in unfollow_user.";
        return user1.unfollow(user2); // Execute the unfollow action
    }

    /**
     * Creates the post with postID.
     * @param userID ID of the user who creates the post
     * @param postID ID of the post to be created
     * @param content Content of the post
     * @return Response
     */
    public String createPost(String userID, String postID, String content){
        User user = users.getValue(userID);
        Post post = posts.getValue(postID);
        if (user == null) // User does not exists
            return "Some error occurred in create_post.";
        if (post != null) // Post with postID already exists
            return "Some error occurred in create_post.";
        post = new Post(postID, userID, content);
        posts.insert(postID, post); // Insert this post to the HashMap with Key, Value - postID, post
        user.createdPosts.add(post); // Also insert the post to users createdPosts
        return userID + " created a post with Id " + postID + ".";
    }

    /**
     * Adds the post to user's seenPosts
     * @param userID ID of the user who sees the post
     * @param postID ID of the post to be seen
     * @return Response
     */
    public String seePost(String userID, String postID){
        User user = users.getValue(userID);
        Post post = posts.getValue(postID);

        if (user == null || post == null) // User or post does not exist
            return "Some error occurred in see_post.";
        user.seenPosts.insert(postID, post);
        return userID + " saw " + postID + ".";
    }

    /**
     * Adds every post of user2 to user1's seenPosts
     * @param user1ID ID of the user who sees the posts
     * @param user2ID ID of the user whose posts are being seen
     * @return Response
     */
    public String seeAllPostsFromUser(String user1ID, String user2ID){
        User user1 = users.getValue(user1ID);
        User user2 = users.getValue(user2ID);

        if (user1 == null || user2 == null) // One of users does not exist
            return "Some error occurred in see_all_posts_from_user.";
        // Insert every post of user2 to user1's seenPosts (Even if there are posts seen by user1 before, It won't be a problem)
        for (Post post : user2.createdPosts){
            user1.seenPosts.insert(post.getPostID(), post);
        }
        return user1ID + " saw all posts of " + user2ID + ".";
    }

    /**
     * User with ID userID clicks the like button of post with ID postID
     * @param userID ID of the user who clicks the like button
     * @param postID ID of the post whose like button is clicked
     * @return corresponding response of the action
     */
    public String toggleLike(String userID, String postID){
        User user = users.getValue(userID);
        Post post = posts.getValue(postID);

        if (user == null || post == null) // User or post does not exist
            return "Some error occurred in toggle_like.";

        if (user.likedPosts.getValue(postID) == null){ // If post is not currently liked for user, like it
            user.likedPosts.insert(postID, post);
            user.seenPosts.insert(postID, post); // User also sees the post while liking
            post.incrementLikeCount(); // likeCount++
            return userID + " liked " + postID + ".";
        }else{ // Post is currently liked for user so, unlike it
            user.likedPosts.remove(postID);
            post.decrementLikeCount(); // likeCount--
            return userID + " unliked " + postID + ".";
        }
    }

    /**
     * Generates num posts from the feed of the user. If user seen a post before, don't include it in the feed
     * @param userID ID of the user that we want to generate feed from
     * @param num Number of posts to obtain from user's feed
     * @return Response
     */
    public String generateFeed(String userID, int num){
        User user = users.getValue(userID);
        if (user == null) // user does not exist
            return "Some error occurred in generate_feed.";
        ArrayList<User> followedUsers = user.followedUsers.getValues(); // Every user that the current user follows
        ArrayList<Post> postsFromFollowedUsers = new ArrayList<>(); // Every post from followed users
        for (User followed : followedUsers){ // Iterate through followed users
            ArrayList<Post> currentUsersPosts = followed.createdPosts;
            for (Post post : currentUsersPosts){ // Iterate through the followed user's posts
                if (user.seenPosts.getValue(post.getPostID()) == null) // If post is not seen before, add it to ArrayList
                    postsFromFollowedUsers.add(post);
            }
        }
        Post[] arr = postsFromFollowedUsers.toArray(new Post[0]); // Convert the ArrayList to array
        MaxHeap<Post> feed = new MaxHeap<>(arr); // Generate a max heap from the array

        StringBuilder response = new StringBuilder("Feed for " + userID + ":");
        if (feed.getSize() >= num){ // If there are sufficient posts in feed
            // Delete from heap and append the data to response num times
            // In this way we get first num posts of the feed
            for (int i = 0; i < num; i++){
                Post post = feed.deleteMax();
                response.append("\nPost ID: ").append(post.getPostID()).append(", Author: ").append(post.getAuthorID()).append(", Likes: ").append(post.getLikeCount());
            }
        }else{ // If there are less posts in feed than num
            int initialHeapSize = feed.getSize();
            for (int i = 0; i < initialHeapSize; i++){
                Post post = feed.deleteMax();
                response.append("\nPost ID: ").append(post.getPostID()).append(", Author: ").append(post.getAuthorID()).append(", Likes: ").append(post.getLikeCount());
            }
            response.append("\nNo more posts available for ").append(userID).append(".");
        }


        return response.toString();

    }

    /**
     * User with userID scrolls through its feed and see num posts and clicks like for some of them
     * @param userID ID of the user that we want to scroll through feed
     * @param num Number of posts to scroll through
     * @param likes Array of 1 or 0. 1->click like, 0->don't click like
     * @return Response
     */
    public String scrollThroughFeed(String userID, int num, int[] likes){
        User user = users.getValue(userID);
        if (user == null) // User does not exist
            return "Some error occurred in scroll_through_feed.";
        ArrayList<User> followedUsers = user.followedUsers.getValues(); // Every user that the current user follows
        ArrayList<Post> postsFromFollowedUsers = new ArrayList<>(); // Every post from followed users
        for (User followed : followedUsers){ // Iterate through followed users
            ArrayList<Post> currentUsersPosts = followed.createdPosts;
            for (Post post : currentUsersPosts){ // Iterate through the followed user's posts
                if (user.seenPosts.getValue(post.getPostID()) == null) // If post is not seen before, add it to ArrayList
                    postsFromFollowedUsers.add(post);
            }
        }
        Post[] arr = postsFromFollowedUsers.toArray(new Post[0]); // Convert the ArrayList to array
        MaxHeap<Post> feed = new MaxHeap<>(arr); // Generate a max heap from array


        StringBuilder response = new StringBuilder(userID + " is scrolling through feed:");
        if (feed.getSize() >= num){ // If there are sufficient posts in feed
            for (int i = 0; i < num; i++){
                Post post = feed.deleteMax();
                if (likes[i] == 0){ // Don't click like just see the post
                    user.seenPosts.insert(post.getPostID(), post);
                    response.append("\n").append(userID).append(" saw ").append(post.getPostID()).append(" while scrolling.");
                }else if (likes[i] == 1){ // Click the like button
                    user.seenPosts.insert(post.getPostID(), post);
                    user.likedPosts.insert(post.getPostID(), post);
                    post.incrementLikeCount();
                    response.append("\n").append(userID).append(" saw ").append(post.getPostID()).append(" while scrolling and clicked the like button.");
                }
            }

        }else{
            int initialHeapSize = feed.getSize();
            for (int i = 0; i < initialHeapSize; i++){
                Post post = feed.deleteMax();
                if (likes[i] == 0){ // Don't click just see the post
                    user.seenPosts.insert(post.getPostID(), post);
                    response.append("\n").append(userID).append(" saw ").append(post.getPostID()).append(" while scrolling.");
                }else if (likes[i] == 1){ // Click the like button
                    user.seenPosts.insert(post.getPostID(), post);
                    user.likedPosts.insert(post.getPostID(), post);
                    post.incrementLikeCount();
                    response.append("\n").append(userID).append(" saw ").append(post.getPostID()).append(" while scrolling and clicked the like button.");
                }
            }
            response.append("\nNo more posts in feed.");
        }

        return response.toString();

    }

    /**
     * Use heapsort algorithm to sort posts and return it
     * @param userID user that we want to sort its posts
     * @return corresponding response of the action
     */
    public String sortPosts(String userID){
        User user = users.getValue(userID);
        StringBuilder response = new StringBuilder();
        if (user == null) // User does not exist
            return "Some error occurred in sort_posts.";
        if (user.createdPosts.isEmpty()) // There is no post this user created
            return "No posts from " + userID + ".";
        response.append("Sorting ").append(userID).append("'s posts:");
        Post[] arrForm = user.createdPosts.toArray(new Post[0]); // Convert ArrayList to array
        MaxHeap<Post> heapPosts = new MaxHeap<>(arrForm); // Generate heap from array
        while (heapPosts.getSize() > 0){
            // Delete from heap and add to response
            Post post = heapPosts.deleteMax();
            response.append("\n").append(post.getPostID()).append(", Likes: ").append(post.getLikeCount());
        }
        return response.toString();
    }
}
