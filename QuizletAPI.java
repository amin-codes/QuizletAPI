import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * The QuizletAPI was made for the intention of making
 * connecting to quizlet sets an easier task to accomplish.
 *
 * @author  Amin
 * @version 1.0
 * @since   2017-09-17
 */
public class QuizletAPI {
    private static String CLIENT_ID;
    private static String SET_ID;
    private ArrayList<String> cards;

    private ArrayList<String> terms, definitions, image, rank;

    public QuizletAPI(String clientID, String setID){
        CLIENT_ID = clientID;
        SET_ID = setID;
        String URL = "https://api.quizlet.com/2.0/sets/" + SET_ID + "/terms?client_id=" + CLIENT_ID;
        cards = new ArrayList<>();
        cards.addAll(Arrays.asList(getTextFromWebsite(URL).replace("[", "")
                .replace("]", "")
                .replace("{","").split("},")));
        terms = getItemFromCards(Choice.TERM);
        definitions = getItemFromCards(Choice.DEFINITION);
        image = getItemFromCards(Choice.IMAGE);
        rank = getItemFromCards(Choice.RANK);
    }
    /**
     * This method returns the Client ID being used
     * to access the set.
     * @return Returns the CLIENT_ID
     */
    public String getClientID() {
        return CLIENT_ID;
    }

    /**
     * This method returns the ID of the set.
     * @return Returns the SET_ID
     */
    public String getSetID() {
        return SET_ID;
    }

    private String getTextFromWebsite(String url){
        URL uri;
        try {
            uri = new URL(url);
            URLConnection ec = uri.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    ec.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder a = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                a.append(inputLine);

            in.close();
            return a.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<String> getItemFromCards(Choice choice){
        ArrayList<String> listOfChoice = new ArrayList<>();
        for (String card : cards) {
            String item = card.split(choice.getChoice())[1].split(",\"")[0].replace("\"","");
            listOfChoice.add(decode(item));
        }
        return listOfChoice;
    }
    /**
     * This method returns the terms within
     * the set.
     * @return The list of terms.
     */
    public ArrayList<String> getTerms() {
        return terms;
    }
    /**
     * This method returns the definitions
     * within the set.
     * @return The list of definitions.
     */
    public ArrayList<String> getDefinitions() {
        return definitions;
    }

    /**
     * This method returns definition of a term.
     * @param term The case-sensitive term within the set.
     * @return The definition of a term.
     */
    public String getDefinitionFromTerm(String term){
        return getDefinitions().get(getTerms().indexOf(term));
    }
    /**
     * This method returns the links of the
     * images being used within the set.
     * @return The list of images.
     */
    public ArrayList<String> getImages() {
        return image;
    }

    /**
     * This method returns the ranks within
     * the set.
     * @return The String ArrayList of the ranks.
     */
    public ArrayList<String> getRanks() {
        return rank;
    }

    /**
     * This method returns the index of a term
     * if the given non case-sensitive parameter
     * exists within the terms of the set.
     * @param checking The term that is being checked for.
     * @return -1 if the term doesn't exist, else the index of the term.
     */
    public int isTermInSet(String checking){
        for (String term : getTerms()) {
            if (term.equalsIgnoreCase(decode(checking))) {
                return getTerms().indexOf(term);
            }
        }
        return -1;
    }

    public enum Choice{
        ID("\"id\":"),
        TERM("\"term\":"),
        DEFINITION("\"definition\":"),
        IMAGE("\"image\":"),
        RANK("\"rank\":");
        String choice;
        Choice(String choice) {
            this.choice=choice;
        }

        private String getChoice(){
            return choice;
        }
    }

    private static String decode(final String in)
    {
        String working = in;
        int index;
        index = working.indexOf("\\u");
        while(index > -1)
        {
            int length = working.length();
            if(index > (length-6))break;
            int numStart = index + 2;
            int numFinish = numStart + 4;
            String substring = working.substring(numStart, numFinish);
            int number = Integer.parseInt(substring,16);
            String stringStart = working.substring(0, index);
            String stringEnd   = working.substring(numFinish);
            working = stringStart + ((char)number) + stringEnd;
            index = working.indexOf("\\u");
        }
        return working;
    }
}
