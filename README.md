# Instagram Feed Simulation

This project is a simplified Instagram-style feed system implemented in **Java**.  
It models users, posts, follow relationships, likes, and a personalized feed using **custom data structures** instead of the Java Collections Framework.

All core components are implemented from scratch:

- `MyHashMap` + `MyLinkedList` + `MyListNode` – custom hash map with separate chaining
- `MaxHeap` – max-heap used to rank posts in the feed
- `User` and `Post` – domain objects
- `Application` – main logic for commands
- `Main` – command-line entry point that reads an input file of commands and writes results to an output file

The goal is to demonstrate data structures, algorithm design, and object-oriented programming in Java.

---

## Features

### Users & Following
- `create_user <userID>`  
  Creates a new user with the given ID.

- `follow_user <followerID> <followedID>`  
  `followerID` starts following `followedID`.

- `unfollow_user <followerID> <followedID>`  
  `followerID` stops following `followedID`.

### Posts & Interactions
- `create_post <userID> <postID> <content>`  
  Creates a new post with the given `postID` and `content` posted by `userID`.  
  > Note: In this implementation, `content` is a single token (no spaces) because it is read as `temp[3]`.

- `see_post <userID> <postID>`  
  Marks the post as **seen** by the user.

- `see_all_posts_from_user <viewerID> <authorID>`  
  Marks **all posts** from `authorID` as seen by `viewerID`.

- `toggle_like <userID> <postID>`  
  Toggles a like on the post: if the user already liked the post, the like is removed; otherwise, the post is liked.

### Feed Generation & Scrolling
- `generate_feed <userID> <num>`  
  Builds a personalized feed for `userID` of up to `<num>` posts using:
  - follow relationships,
  - seen/liked posts,
  - and a `MaxHeap` to prioritize posts.

- `scroll_through_feed <userID> <num> <like1> <like2> ... <likeN>`  
  Simulates a user scrolling through the generated feed:
  - `<num>` is the number of posts to scroll through,
  - each `<likeX>` is `0` or `1` indicating whether the user likes that post while scrolling.

### Sorting Posts by Popularity
- `sort_posts <userID>`  
  Sorts all posts created by `userID` in descending order of like count using a `MaxHeap`, and prints them along with their like counts.

---

## Project Structure

```text
.
├── Application.java      # Core application logic and command handlers
├── Main.java             # Entry point (reads commands from file, writes output)
├── User.java             # User model (followed users, seen posts, liked posts, created posts)
├── Post.java             # Post model (ID, owner, content, like count, timestamps if any)
├── MyHashMap.java        # Custom hash map implementation (separate chaining)
├── MyLinkedList.java     # Linked list used for hash buckets
├── MyListNode.java       # Nodes used by MyLinkedList
├── MaxHeap.java          # Generic max-heap used for ranking post
```
## How to Build and Run

### 1. Clone the repository
- `git clone https://github.com//.git`
### 2. Compile the Java files
- `javac *.java`
### 3. Create an input file

Example (`input.txt`):
create_user alice
create_user bob
follow_user alice bob
create_post bob p1 photo1
see_post alice p1
toggle_like alice p1
generate_feed alice 5
sort_posts bob

### 4. Run the program

`Main` expects two command-line arguments: input file and output file.
- `java Main input.txt output.txt`

### 5. View results
- `cat output.txt`

---

## Implementation Details

### Custom HashMap
- Uses separate chaining with linked lists.
- Provides insert, get, and remove operations.
- Efficient for key–value storage without Java Collections.

### Custom Linked List
- Lightweight singly linked list implementation used for buckets.

### MaxHeap
Used to:
- rank posts in the feed,
- sort posts by like count.

### User Model
Each `User` tracks:
- followed users,
- seen posts,
- liked posts,
- created posts.

### Application Logic
`Application` maps commands to operations such as:
- createUser, followUser, unfollowUser  
- createPost, seePost, seeAllPostsFromUser  
- toggleLike, generateFeed, scrollThroughFeed, sortPosts  

---

## Extending the Project

Potential improvements include:
- Supporting multi-word post content  
- Adding timestamps for recency-based ranking  
- Saving data to files or databases  
- Creating a GUI or REST API on top of the logic  

---

If you encounter issues running the project, ensure:
- All `.java` files are compiled,
- `Main.java` contains the `public static void main` method,
- You pass **two arguments** to `java Main`: input and output paths.
