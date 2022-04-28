package spell;

import java.util.Locale;

public class Trie implements ITrie {
  private INode root;
  private int wordCount;
  private int nodeCount;

  public Trie() {
    root = new Node();
    wordCount = 0;
    nodeCount = 0;
  }

  @Override
  public void add(String word) {
    word = word.toLowerCase();
    addHelper(word, root, 0);
  }

  private void addHelper(String word, INode currNode, int wordIndex) {
    if (wordIndex < word.length()) {
      char letter = word.charAt(wordIndex);
      int index = letter - 'a';

      if (currNode.getChildren()[index] == null) {
        currNode.getChildren()[index] = new Node();
        this.nodeCount++;
      }
      wordIndex++;
      addHelper(word, currNode.getChildren()[index], wordIndex);
    } else {
      if (currNode.getValue() == 0) {
        this.wordCount++;
      }
      currNode.incrementValue();
    }
  }

  @Override
  public INode find(String word) {
    return null;
  }

  private INode findHelper(String word) { return new Node(); }

  @Override
  public int getWordCount() {
    return 0;
  }

  @Override
  public int getNodeCount() {
    return 0;
  }

  @Override
  public int hashCode() {
    int hashcode = wordCount * nodeCount;

    for (int i = 0; i < root.getChildren().length; i++) {
      hashcode *= i;
    }

    return hashcode;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != this.getClass()) {
      return false;
    }

    Trie dictionary = (Trie)obj;
    if (dictionary.getWordCount() != this.getWordCount() || dictionary.getNodeCount() != this.getNodeCount()) {
      return false;
    }

    return equalsHelper(this.root, dictionary.root);
  }

  private boolean equalsHelper(INode n1, INode n2) {
    if (n1.getValue() != n2.getValue()) {
      return false;
    }

    for (int i = 0; i < 26; i++) {
      if (n1.getChildren()[i] == null) {
        if (n2.getChildren()[i] != null) {
          return false;
        }
      } else {
        if ((n2.getChildren()[i] == null)) {
          return false;
        }
      }
    }

    for (int i = 0; i < 26; i++) {
      INode child1 = n1.getChildren()[i];
      INode child2 = n2.getChildren()[i];
      if (child1 != null) {
        boolean equal = equalsHelper(child1, child2);
        if (!equal) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public String toString() {
    StringBuilder currWord = new StringBuilder();
    StringBuilder output = new StringBuilder();

    toString_Helper(root, currWord, output);

    return output.toString();
  }

  private void toString_Helper(INode n, StringBuilder currWord, StringBuilder output) {

    if (n.getValue() > 0) {
      output.append(currWord.toString());
      output.append("\n");
    }

    for (int i = 0; i < n.getChildren().length; i++) {
      INode child = n.getChildren()[i];
      if (child != null) {
        char childLetter = (char)('a' + i);
        currWord.append(childLetter);
        toString_Helper(child, currWord, output);

        currWord.deleteCharAt(currWord.length() - 1);
      }
    }
  }
}
