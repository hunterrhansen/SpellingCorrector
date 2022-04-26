package spell;

import java.io.IOException;

public class SpellCorrector implements ISpellCorrector {
  public SpellCorrector() {}

  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
    System.out.println("in useDictionary...");
  }

  @Override
  public String suggestSimilarWord(String inputWord) {
    System.out.println("in suggestSimilarWord...");
    return "hello";
  }

}
