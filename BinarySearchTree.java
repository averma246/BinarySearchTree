//========== BinarySearchTree =============================================================
// Ana Verma
//=========================================================================================

public class BinarySearchTree{
  
  private Node tree;
  private int numElts = 0;
  
  //keeps track of how many times the remove operation has been performed -- 
  //used to alternate between replacing node with a left child or a right child
  private int rmvCounter = 0;
  
  
  //======== LOOK UP =======================================================================
  //lookup method -- if the key exists in the tree, the node that findElement returns will 
  //not be null and this method will return true
  //if it is null, then this method will return false
  //----------------------------------------------------------------------------------------
  public boolean lookup(String key){
    Node element = findElement(key, tree);
    
    if(element == null) return false;
    else return true;
  }
  //=========================================================================================
  
  
  //================================== ADD ==================================================
  public void add(String key){
    boolean keyAdded = false;
    
    //if adding the root
    if(numElts == 0){
      tree = new Node(key);
      numElts++;
      return;
    }
    
    //adding to a non-empty tree
    else{
      //passing in the key and the tree so can traverse through it to find where it should go 
      //(if anywhere at all)
      //if the helper method returns true, then the new key was added successfully
      //if it returns false, then the key already exists in the tree and numElts should 
      //not be incremented
      keyAdded = addHelper(key,tree);
    }
    
    if(keyAdded){
      numElts++;
    }
    
    return;
  }
