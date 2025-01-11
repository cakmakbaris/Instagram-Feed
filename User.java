import java.util.ArrayList;

public class User {

    private final String ID; // Unique ID of the user

    public MyHashMap<String, User> followedUsers; // HashMap containing user's followed users

    public MyHashMap<String, Post> seenPosts; // HashMap containing posts seen by the user

    public MyHashMap<String, Post> likedPosts; // HashMap containing posts liked by the user

    public ArrayList<Post> createdPosts; // HashMap containing posts created by this user



    public User(String ID) {
        this.ID = ID;
        followedUsers = new MyHashMap<>();
        seenPosts = new MyHashMap<>();
        likedPosts = new MyHashMap<>();
        createdPosts = new ArrayList<>();
    }


    String getID(){
        return ID;
    }

    /**
     * User follows user2 if user2 is not already in user's followedUsers
     * @param user2 User to be followed
     * @return Response
     */
    public String follow(User user2){
        if (followedUsers.insert(user2.getID(), user2)) // Insertion successful
            return ID + " followed " + user2.getID() + ".";

        // Already following the user
        return "Some error occurred in follow_user.";
    }

    /**
     * User unfollows user2 if user2 is in user's followedUsers
     * @param user2 User to be unfollowed
     * @return Response
     */
    public String unfollow(User user2){
        if (followedUsers.remove(user2.getID())) // Deletion successful
            return ID + " unfollowed " + user2.getID() + ".";

        // User was not following user2
        return "Some error occurred in unfollow_user.";
    }

}
