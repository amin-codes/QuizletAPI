# QuizletAPI
An API made to simplify tasks for retrieving information from quizlet sets. More to be added soon.

# Example Usage
```java

import package.where.api.is.located.QuizletAPI;

public class Example
{
  public static final String CLIENT_ID = "YOUR_CLIENT_ID";

  public static void main(String[] args)
  {
    String set_id = "234104177"; //Random quizlet set I found online. I am not the owner/creator of it.
    
    QuizletAPI api = new QuizletAPI(CLIENT_ID, set_id);
    
    //Now you can use all public methods within the QuizletAPI class.
    //For example:
    System.out.println(api.getTerms().get(0)); //Carbon
  }
  
}
```
