package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {

  private ITrie dictionary;
  private Set<String> editOneDistanceWords;
  private Set<String> editTwoDistanceWords;
  private Set<String> suggestionWords;

  public SpellCorrector() {
    dictionary = new Trie();
    editOneDistanceWords = new HashSet<>();
    editTwoDistanceWords = new HashSet<>();
    suggestionWords = new TreeSet<>();
  }

  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
    File file = new File(dictionaryFileName);
    Scanner scanner = new Scanner(file);

    while (scanner.hasNext()) {
      dictionary.add(scanner.next());
    }

    scanner.close();
  }

  @Override
  public String suggestSimilarWord(String inputWord) {
    inputWord = inputWord.toLowerCase();

    if (dictionary.find(inputWord) != null) {
      return inputWord;
    }

    // edit distance 1
    TreeSet<String> suggestions = new TreeSet<>();
    int highestFrequency = 0;

    highestFrequency = deletionDistance(inputWord, highestFrequency, editOneDistanceWords, suggestions);
    highestFrequency = transpositionDistance(inputWord, highestFrequency, editOneDistanceWords, suggestions);
    highestFrequency = alterationDistance(inputWord, highestFrequency, editOneDistanceWords, suggestions);
    highestFrequency = insertionDistance(inputWord, highestFrequency, editOneDistanceWords, suggestions);

    // edit distance 2
    if (suggestions.isEmpty()) {
      for (String word : editOneDistanceWords) {
        highestFrequency = deletionDistance(word, highestFrequency, editTwoDistanceWords, suggestions);
        highestFrequency = transpositionDistance(word, highestFrequency, editTwoDistanceWords, suggestions);
        highestFrequency = alterationDistance(word, highestFrequency, editTwoDistanceWords, suggestions);
        highestFrequency = insertionDistance(word, highestFrequency, editTwoDistanceWords, suggestions);
      }
    }

    if (suggestions.isEmpty()) {
      return null;
    } else {
      return suggestions.first();
    }
  }

  private int deletionDistance(String word, int highestFreqency, Set<String> editedWords, Set<String> suggestions) {
    for (int i = 0; i < word.length(); i++) {
      StringBuilder tmp = new StringBuilder(word);
      String deletionEditWord = tmp.deleteCharAt(i).toString();

      editedWords.add(deletionEditWord);
      highestFreqency = calcHighestFreq(highestFreqency, deletionEditWord, suggestions);
    }

    return highestFreqency;
  }

  private int transpositionDistance(String word, int highestFrequency, Set<String> editedWords, Set<String> suggestions) {
    for (int i=0; i < word.length() - 1; i++) {
      StringBuilder tmp=new StringBuilder(word);
      StringBuilder transposedChars=new StringBuilder(word.substring(i, i + 2)).reverse();
      tmp.replace(i, i + 2, transposedChars.toString());
      String transposedWord=tmp.toString();

      editedWords.add(transposedWord);
      highestFrequency = calcHighestFreq(highestFrequency, transposedWord, suggestions);
    }
    return highestFrequency;
  }

  private int alterationDistance(String word, int highestFrequency, Set<String> editedWords, Set<String> suggestions) {
    for (int i = 0; i < word.length(); i++) {
      for (char letter = 'a'; letter <= 'z'; letter++) {
        if (letter != word.charAt(i)) {
          char[] tmp = word.toCharArray();
          tmp[i] = letter;
          String alteredWord = new String(tmp);

          editedWords.add(alteredWord);
          highestFrequency = calcHighestFreq(highestFrequency, alteredWord, suggestions);
        }

      }
    }
    return highestFrequency;
  }

  private int insertionDistance(String word,  int highestFrequency, Set<String> editedWords, Set<String> suggestions) {
    for (int i = 0; i <= word.length(); i++) {
      for (char letter = 'a'; letter <= 'z'; letter++) {
        String insertedWord = word.substring(0, i) + letter + word.substring(i);

        editedWords.add(insertedWord);
        highestFrequency = calcHighestFreq(highestFrequency, insertedWord, suggestions);
      }
    }

    return highestFrequency;
  }

  private int calcHighestFreq(int highestFrequency, String word, Set<String> suggestions) {
    INode found = dictionary.find(word);

    if (found != null) {
      if (found.getValue() > highestFrequency) {
        suggestions.clear();
        highestFrequency = found.getValue();
        suggestions.add(word);
      } else if (found.getValue() == highestFrequency) {
        suggestions.add(word);
      }
    }

    return highestFrequency;
  }

}
