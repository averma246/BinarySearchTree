//========== BinarySearchTree ======================
// Ana Verma
//==================================================

public class BinarySearchTree{
  
  private Node tree;
  private int numElts = 0;
  
  //keeps track of how many times the remove operation has been performed -- used to alternate between replacing node with a left child or a right child
  private int rmvCounter = 0;
  
  
