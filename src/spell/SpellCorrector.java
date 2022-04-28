package spell;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
  private ITrie dictionary;


  public SpellCorrector() {
    dictionary = new Trie();
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
    return inputWord;
  }

}
