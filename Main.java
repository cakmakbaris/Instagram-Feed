import java.io.*;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws IOException {
        Application app = new Application();
        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        BufferedWriter outWriter; // To output in a file we will use BufferedWriter class
        try{
            outWriter =new BufferedWriter(new FileWriter(outputFile));
        }catch (IOException e2){
            e2.printStackTrace();
            return;
        }

        Scanner reader; // Scanner for the input file
        try{
            reader = new Scanner(inputFile);
        }catch (FileNotFoundException e){
            System.out.println("Cannot find input file");
            return;
        }


        String line;
        String[] temp;
        while (reader.hasNextLine()){
            line = reader.nextLine();
            temp = line.split(" ");
            String command = temp[0];

            if (command.equals("create_user")){
                String userID = temp[1];
                outWriter.write(app.createUser(userID));
                outWriter.newLine();
            }
            else if (command.equals("follow_user")){
                String user1ID = temp[1];
                String user2ID = temp[2];
                outWriter.write(app.followUser(user1ID, user2ID));
                outWriter.newLine();
            }
            else if (command.equals("unfollow_user")){
                String user1ID = temp[1];
                String user2ID = temp[2];
                outWriter.write(app.unfollowUser(user1ID, user2ID));
                outWriter.newLine();
            }
            else if (command.equals("create_post")){
                String userID = temp[1];
                String postID = temp[2];
                String content = temp[3];
                outWriter.write(app.createPost(userID, postID, content));
                outWriter.newLine();
            }
            else if (command.equals("see_post")){
                String userID = temp[1];
                String postID = temp[2];
                outWriter.write(app.seePost(userID, postID));
                outWriter.newLine();
            }
            else if (command.equals("see_all_posts_from_user")){
                String user1ID = temp[1];
                String user2ID = temp[2];
                outWriter.write(app.seeAllPostsFromUser(user1ID, user2ID));
                outWriter.newLine();
            }
            else if (command.equals("toggle_like")){
                String userID = temp[1];
                String postID = temp[2];
                outWriter.write(app.toggleLike(userID, postID));
                outWriter.newLine();
            }
            else if (command.equals("generate_feed")){
                String userID = temp[1];
                int num = Integer.parseInt(temp[2]);
                outWriter.write(app.generateFeed(userID, num));
                outWriter.newLine();
            }
            else if (command.equals("scroll_through_feed")){
                String userID = temp[1];
                int num = Integer.parseInt(temp[2]);
                int[] likes = new int[temp.length-3];
                for (int i = 3; i < temp.length; i++)
                    likes[i-3] = Integer.parseInt(temp[i]);
                outWriter.write(app.scrollThroughFeed(userID, num, likes));
                outWriter.newLine();
            }
            else if (command.equals("sort_posts")){
                String userID = temp[1];
                outWriter.write(app.sortPosts(userID));
                outWriter.newLine();
            }
        }
        reader.close();
        outWriter.close();
    }
}