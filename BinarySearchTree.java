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
  //=========================================================================================
  
  
  
  //=================================== REMOVE ==============================================
  public String remove(String key){
    if(numElts == 0) return null;
    
    String removed = removeHelper(key, tree);
    
    if(removed != null){
      numElts--;
    }
    
    return removed;
  }
  //=========================================================================================
  
  
  
  //================================== PRINT ================================================
  
  public void inOrderTraverse(){
    //call helper method to allow for recursion
    printHelper(tree);
  }
  //=========================================================================================
  
  
  
  //================================== HELPER METHODS =======================================
  
  //-----addHelper---------------------------------------------------------------------------
  //helper for add -- allows for recursion through tree to find the place where the new 
  //element needs to be added
  //returns true if the element was successfully added -- false if the element was already
  //there and could not be added a second time
  //-----------------------------------------------------------------------------------------
  
  private boolean addHelper(String key, Node tree){
    //comparing the key and the value of the current node
    //if comparison is negative, key is smaller than the current node's value
    //if comparison is positive, key is larger than the current node's value
    int comparison = key.compareTo(tree.value);
    
    //if comparison is 0, key is already in tree
    if(comparison == 0) return false;
    
    //checking the left side of the node
    else if(comparison < 0){
      //if nothing exists to the left, the new node can be added there
      if(tree.left == null){
        tree.left = new Node(key);
        tree.left.parent = tree;
        return true;
      }
      
      //if some node exist to the left, all method again on that node
      else{
        return addHelper(key, tree.left);
      }
    }
      
  
  
  
////// NEED TO FINISH TRANFERING CODE FROM FILES ON COMPUTER, TO GITHUB
  
  
