package spell;

public class Trie implements ITrie {
  private Node root;
  private int wordCount;
  private int nodeCount;

  public Trie() {}

  @Override
  public void add(String word) {}

  @Override
  public INode find(String word) {
    return null;
  }

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
    // combine the following values:
    // 1. wordCount
    // 2. nodeCount
    // 3. the index of each of the root node's non-null children
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    // is obj == null?
    // is obj == this?
    // do this and obj have the same class?
    Trie dictionary = (Trie)obj;

    return equals_Helper(this.root, dictionary.root);
  }

  private boolean equals_Helper(Node n1, Node n2) {
    // compare n1 and n2 to see if they are the same
      // do n1 and n2 have the same count?
      // do they have non-null children in exactly the same indexes
    // recurse on the children and compare the child subtrees
    return true;
  }

  @Override
  public String toString() {
    StringBuilder currWord = new StringBuilder();
    StringBuilder output = new StringBuilder();

    toString_Helper(root, currWord, output);

    return output.toString();
  }

  private void toString_Helper(Node n, StringBuilder currWord, StringBuilder output) {

    if (n.getValue() > 0) {
      output.append(currWord.toString());
      output.append("\n");
    }

    for (int i = 0; i < n.getChildren().length; i++) {
      INode child = n.getChildren()[i];
      if (child != null) {
        char childLetter = (char)('a' + i);
        currWord.append(childLetter);
        toString_Helper((Node)child, currWord, output);

        currWord.deleteCharAt(currWord.length() - 1);
      }
    }
  }
}
