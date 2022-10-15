import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    int n = 0;
    // String[] fruit = new String[10];
    ArrayList<String> fruit = new ArrayList<String>();
    

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                } else if(parameters[0].equals("s")){
                    fruit.add(parameters[1]);
                    n+=1;
                    return String.format("%s was add", fruit.get(n-1));
                }
            } else if(url.getPath().contains("/search")){
                String[] parameters = url.getQuery().split("=");
                ArrayList<String> returnlist = new ArrayList<String>();
                String text="";

                if (parameters[0].equals("s")){
                    for (int i = 0; i < fruit.size(); i++){
                        if(fruit.get(i).contains(parameters[1])){
                            returnlist.add(fruit.get(i));
                        }
                    }
                    for (int i = 0; i < returnlist.size(); i++){
                        text += returnlist.get(i);
                        text += " ";
                    }
                    return text;
                }
            }
            
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
