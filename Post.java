public class Post implements Comparable<Post>{

    private final String postID; // Unique ID of the post
    private final String authorID; // Author of the post

    private String content; // Content of the post(not used)

    private int likeCount; // Total like count of the post

    public Post(String postID, String userID, String content) {
        this.postID = postID;
        authorID = userID;
        this.content = content;
        likeCount = 0; // Initially 0

    }

    public String getPostID(){
        return postID;
    }

    public String getAuthorID(){
        return authorID;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void incrementLikeCount(){
        likeCount++;
    }

    public void decrementLikeCount(){
        likeCount--;
    }

    /**
     * @param o the Post to be compared.
     * @return If like counts are equal lexicographically bigger one is bigger()
     */
    @Override
    public int compareTo(Post o) {
        if (o == null)
            return 1;
        if (likeCount > o.likeCount)
            return 1;
        else if (likeCount < o.likeCount)
            return -1;
        else{
            return Integer.compare(postID.compareTo(o.getPostID()), 0);
        }
    }
}
