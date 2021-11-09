/**
 * A program to carry on conversations with a human user. This version:
 * <ul>
 * <li>Uses advanced search for keywords</li>
 * <li>Will transform statements as well as react to keywords</li>
 * </ul>
 * 
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4 {
  /**
   * Get a default greeting
   * 
   * @return a greeting
   */
  public String getGreeting() {
    return "Helloooooooooooooo, I am a spooooky bot... type something if you dare";
  }

  /**
   * Gives a response to a user statement
   * 
   * @param statement the user statement
   * @return a response based on the rules given
   */
  public String getResponse(String statement) {
    String response = "";
    if (statement.length() == 0) {
      response = "Huh, whooooooooo was that?";
    }

    else if (findKeyword(statement, "no") >= 0) {
      response = "Why so negative?";
    } else if (findKeyword(statement, "mother") >= 0 || findKeyword(statement, "father") >= 0
        || findKeyword(statement, "sister") >= 0 || findKeyword(statement, "brother") >= 0) {
      response = "Oooooooo, more people to spoooooooooooook. Tell me more about your family.";
    }

    // Responses which require transformations
    else if (findKeyword(statement, "I want to", 0) >= 0) {
      response = transformIWantToStatement(statement);
    }

    else if (findKeyword(statement, "halloween") >= 0) {
      response = "Halloooooooooween is my favorite holiday.";
    }

    else if (findKeyword(statement, "trick") >= 0) {
      response = "I looooooooove dooooooing tricks on people!";
    } else if (findKeyword(statement, "boo") >= 0) {
      response = "That was nooooooot a goooooood boo. You're suppooooooosed tooooooo say it like, 'BOOOOOOOOOOOOOOOOOO!'";
    } else if (findKeyword(statement, "scare") >= 0) {
      response = "I am very scary. BOOOOOOOOO!";
    }

    else if (findKeyword(statement, "about") >= 0) {
      response = "I am spoooooooky bot, beware what you ask, you may be haunted!";
    }

    else if (findKeyword(statement, "help") >= 0) {
      response = "Helloooo! I am spoooooooky bot.  You can use keywords like halloween or anything spoooky for extra conversation with me";
    }

    else if (findKeyword(statement, "Food") >= 0) {
      response = "I looooooove food.  My favorite is pumpkins and the souls of the innocent";
    }

    else if (findKeyword(statement, "Food") >= 0) {
      response = "I looooooove food.  My favorite is pumpkins and the souls of the innocent";
    } else if (findKeyword(statement, "Urmil") >= 0) {
      response = "Urmil? Oooooooooh, I tooooook his sooooooul and ate it. It was very delicious. Pretty gooooooood food, but I think Amrith's soul would be tastier.";
    }

    else

    {
      // Look for a two word (you <something> me)
      // pattern
      int psn = findKeyword(statement, "you", 0);

      if (psn >= 0 && findKeyword(statement, "me", psn) >= 0) {
        response = transformYouMeStatement(statement);
      } else {
        response = getRandomResponse();
      }
    }
    return response;
  }

  /**
   * Take a statement with "I want to <something>." and transform it into "What
   * would it mean to <something>?"
   * 
   * @param statement the user statement, assumed to contain "I want to"
   * @return the transformed statement
   */
  private String transformIWantToStatement(String statement) {
    // Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals(".")) {
      statement = statement.substring(0, statement.length() - 1);
    }
    int psn = findKeyword(statement, "I want to", 0);
    String restOfStatement = statement.substring(psn + 9).trim();
    return "What would it mean tooooooooo " + restOfStatement + "?";
  }

  /**
   * Take a statement with "you <something> me" and transform it into "What makes
   * you think that I <something> you?"
   * 
   * @param statement the user statement, assumed to contain "you" followed by
   *                  "me"
   * @return the transformed statement
   */
  private String transformYouMeStatement(String statement) {
    // Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement.length() - 1);
    if (lastChar.equals(".")) {
      statement = statement.substring(0, statement.length() - 1);
    }

    int psnOfYou = findKeyword(statement, "you", 0);
    int psnOfMe = findKeyword(statement, "me", psnOfYou + 3);

    String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
    int rand = (int) (Math.random() * 2);
    if (rand == 0) {
      return "Yes, I dooooooooooo " + restOfStatement + " you.";
    }

    return "What makes yoooooooooooooooooou think that I " + restOfStatement + " you?";
  }

  /**
   * Search for one word in phrase. The search is not case sensitive. This method
   * will check that the given goal is not a substring of a longer string (so, for
   * example, "I know" does not contain "no").
   * 
   * @param statement the string to search
   * @param goal      the string to search for
   * @param startPos  the character of the string to begin the search at
   * @return the index of the first occurrence of goal in statement or -1 if it's
   *         not found
   */
  private int findKeyword(String statement, String goal, int startPos) {
    String phrase = statement.trim();
    // The only change to incorporate the startPos is in the line below
    int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);

    // Refinement--make sure the goal isn't part of a word
    while (psn >= 0) {
      // Find the string of length 1 before and after the word
      String before = " ", after = " ";
      if (psn > 0) {
        before = phrase.substring(psn - 1, psn).toLowerCase();
      }
      if (psn + goal.length() < phrase.length()) {
        after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
      }

      // If before and after aren't letters, we've found the word
      if (((before.compareTo("a") < 0) || (before.compareTo("z") > 0)) // before is not a letter
          && ((after.compareTo("a") < 0) || (after.compareTo("z") > 0))) {
        return psn;
      }

      // The last position didn't work, so let's find the next, if there is one.
      psn = phrase.indexOf(goal.toLowerCase(), psn + 1);

    }

    return -1;
  }

  /**
   * Search for one word in phrase. The search is not case sensitive. This method
   * will check that the given goal is not a substring of a longer string (so, for
   * example, "I know" does not contain "no"). The search begins at the beginning
   * of the string.
   * 
   * @param statement the string to search
   * @param goal      the string to search for
   * @return the index of the first occurrence of goal in statement or -1 if it's
   *         not found
   */
  private int findKeyword(String statement, String goal) {
    return findKeyword(statement, goal, 0);
  }

  /**
   * Pick a default response to use if nothing else fits.
   * 
   * @return a non-committal string
   */
  private String getRandomResponse() {
    final int NUMBER_OF_RESPONSES = 4;
    double r = Math.random();
    int whichResponse = (int) (r * NUMBER_OF_RESPONSES);
    String response = "";

    if (whichResponse == 0) {
      response = "Very spooooooooky, tell me more.";
    } else if (whichResponse == 1) {
      response = "Hmmm. Not very spooooooooky.";
    } else if (whichResponse == 2) {
      response = "Do you really think soooooooo?";
    } else if (whichResponse == 3) {
      response = "You dooooooooon't say?";
    }

    return response;
  }

}