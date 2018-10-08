//============ BinarySearchTree =================================================================================================//
//Ana Verma
//==============================================================================================================================//

public class BinarySearchTree{

    private Node tree;
    private int numElts = 0;

    //keeps track of how many times the remove operation has been performed -- used to alternate between
    //replacing node with a left child or a right child
    private int rmvCounter = 0;



    //================================= LOOK-UP =======================================================================//
    //lookup method -- if the key exists in the key, the node that findElement returns
    //will not be null and this method will return true
    //if it is null, this method will return false
    //--------------------------------------------------------------------------------------------------------------
    public boolean lookup(String key){
	Node element = findElement(key, tree);

	if(element == null) return false;
	else return true;
    }
    //===============================================================================================================//



    //================================= ADD =======================================================================//
    public void add(String key){
	boolean keyAdded = false;

	//if adding the root
	if(numElts == 0){
	    tree = new Node(key);
	    numElts++;
	    return;
	}

	//adding to a !empty tree
	else{
	    //passing in the key and the tree so can traverse through it to find where it should go (if anywhere at all)
	    //if the helper method returns true, then the new key was added successfully
	    //if it returns false, then the key already exists in tree and numElts should not be incremented 
	    keyAdded = addHelper(key,tree);
	}

	if(keyAdded){
	    numElts++;
	}

	return;
    }
    //===============================================================================================================//




    //================================= REMOVE =======================================================================//
    public String remove(String key){
	if(numElts == 0) return null;

	String removed = removeHelper(key, tree);

	if(removed != null){
	    numElts--;
	}

	return removed;
    }
    //===============================================================================================================//


    //================================= PRINT =======================================================================//

    public void inOrderTraverse(){
	//call helper method to allow for recursion
	printHelper(tree);
    }
    //==============================================================================================================//





    //================================= HELPER METHODS ==================================================================//

    //---- addHelper ------------------------------------------------------------------------------------------
    //helper for add -- allows for recursion through tree to find the place where the new 
    //element needs to be added
    //returns true if the element was successfuly added -- false if the element was already there 
    //and could not be added a second time
    //---------------------------------------------------------------------------------------------------------
	
    private boolean addHelper(String key, Node tree){

	//comparing the key and the value of the current node
	//if comparison is negative, key is smaller than the current node's value
	//if comparison is positive, key is larger than the current node's value
	int comparison = key.compareTo(tree.value);
		
	//if comparison is zero, key is equal to the current node's value -- nothing should be added since the key already exists in tree
	//return false since add "failed"
	if(comparison == 0) return false;

	//checking the left side of the node 
	else if(comparison < 0){
	    //if nothing exists to the left, the new node can be added there 			
	    if(tree.left == null){
		tree.left = new Node(key);
		tree.left.parent = tree;
		return true;
	    }

	    //if some node exists to the left, call method again on that left node
	    else{
		return addHelper(key, tree.left);
	    }
	}

	//checking the right side of the node
	else if(comparison > 0){
	    //if nothing exists to the right, the new node can be added there
	    if(tree.right == null){
		tree.right = new Node(key);
		tree.right.parent = tree;
		return true;
	    }

	    //if some node already exists to the right, call method on that right node
	    else{
		return addHelper(key, tree.right);
	    }
	}

	return false;
    }

    //-- removeHelper ----------------------------------------------------------------------------------------------------
    //helper method for remove -- does the bulk of the work 
    //checks whether the element to be removed is in the tree
    //returns null if element not found, if found it replaces that node with a different node 
    //and returns the key to show that the remove was successful
    //---------------------------------------------------------------------------------------------------------

    private String removeHelper(String key, Node tree){

	//find the element using the key if it is even in the tree
	Node element = findElement(key, tree);

	//if the element is null (meaning it was not found) -- it cannot be removed from the tree
	//since it is not even in the tree -- return null
	if(element == null) return null;

	//initializing the node that will ultimately replace the node we are removing here 
	Node replacement = null;



	//======================================================================================================================
	//REMOVING A NODE WITH NO CHILDREN 
	//======================================================================================================================

	//if removing a leaf (node with only null children)
	if(element.left == null && element.right == null){
	    //check and see if the element is actually the root (it's parent is null) and to double check -- see if the number of elements 
	    //is 1 -- that way we know for sure that the only element in the tree is the root
	    //if it is the root, set the tree of this object to null since all elements have been removed 
	    if(element.parent == null && numElts == 1){
		this.tree = null;
	    }

	    //otherwise if the leaf is NOT the root 
	    else{
		//if the element is the left child of its parent, set the parent's left child to null
		if(element.parent.left == element){
		    element.parent.left = null;
		}

		//if the element is the right child of its parent, set the parent's right child to null
		else if(element.parent.right == element){
		    element.parent.right = null;
		}
	    }
	}

	//=====================================================================================================================
	//REMOVING A NODE WITH ONLY ONE CHILD 
	//=====================================================================================================================

	//if removing a node with only a left child (right child is null) -- replacement is the left child
	if((element.left != null && element.right == null) || (element.left == null && element.right != null)){
	    if(element.left != null && element.right == null){
		replacement = element.left;
	    }
	    //if removing a node with only a right child (left child is null) -- replacement is the right child
	    else if(element.left == null && element.right != null){
		replacement  = element.right;
	    }

	    //check and see if the element is actually the root -- if it is, then the tree of this object is replaced by the replacement
	    if(element.parent == null){
		this.tree = replacement;
		replacement.parent = null;
	    }

	    //otherwise if the node is NOT the root
	    else{
		//if the element is the left child of the parent, then set the replacement as the left child of the parent
		if(element.parent.left == element){
		    element.parent.left = replacement;
		}

		//if the element is the right child of the parent, then set the replacement as the right child of the parent 
		else if(element.parent.right == element){
		    element.parent.right = replacement;
		}

		//set the parent of the replacement to be the parent of the element
		replacement.parent = element.parent;
	    }
	}
		
	//=====================================================================================================================
	//REMOVING AN INTERNAL NODE WITH TWO NON_NULL CHILDREN
	//=====================================================================================================================

	//if element to be removed has no null children 
	if(element.left != null && element.right != null){

	    //to keep the tree from getting too unbalanced, the rmvCounter variable is used to alternate between finding the 
	    //left most node in right tree, and the right-most node in the left tree -- these nodes become the replacment nodes

	    //if rmvCounter is even, use the right-most left node as the replacement
	    if(rmvCounter%2 == 0){
		replacement = findMax(tree.left);
	    }

	    //if rmvCounter is odd, use the left-most right node as the replacement
	    else{
		replacement = findMin(tree.right);
	    }

	    //If replacement is not null, set replacement's parent's corresponding child to be null
	    if(replacement != null){
		if(replacement.parent.left == replacement){
		    replacement.parent.left = null;
		}
		else if(replacement.parent.right == replacement){
		    replacement.parent.right = null;
		}
	    }


	    //if the element is the root of the tree, then the tree of this tree will now point to the replacement
	    if(element.parent == null){
		this.tree = replacement;
		replacement.parent = null;
	    }


	    //if the element is NOT the root of the tree, then the elements parent will now point to the replacement
	    else{
		//if the element is the left child of the parent
		if(element.parent.left == element){
		    element.parent.left = replacement;
		}

		//if the element is the right child of the parent
		else if(element.parent.right == element){
		    element.parent.right = replacement;
		}

				
		//update the parent pointer of the replacement
		replacement.parent = element.parent;
	    }

	    //replace the replacement's left and right children as the left and right children of the element
			
	    replacement.left = element.left;
	    replacement.right = element.right;

	    //if the replacement's children are not null, their parent pointer must point to the replacement
	    if(replacement.left != null){
		replacement.left.parent = replacement;
	    }
	    if(replacement.right != null){
		replacement.right.parent = replacement;
	    }

	}

	//return the key of the element that was just removed 
	return element.value;
    }


    //----------------------------------------------------------------------------------------------------------------------------
    //HELPER METHOD USED IN REMOVE -- finds which node needs to serve as the replacement when there is a choice (when 
    //the node you are removing has more than one child)

    //--- findMax ---------------------------------------------------------------------------------------
    //find the node with the largest key in a given tree
    //---------------------------------------------------------------------------------------------------
    private Node findMax (Node tree){
	if(tree.right == null){ 
	    return tree;
	}
	return findMax(tree.right);
    }

    //--- findMin ---------------------------------------------------------------------------------------
    //find the node with the smallest key in a given tree
    //---------------------------------------------------------------------------------------------------
    private Node findMin(Node tree){
	if(tree.left == null){
	    return tree;
	}
	return findMin(tree);

    }
    //----------------------------------------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------------------------------------
    //HELPER METHOD USED IN BOTH REMOVE AND LOOK-UP
    //----------------------------------------------------------------------------------------------------------------------------

    //----- findElement ------------------------------------------------------------------------------------------
    //helper method which finds and returns an element from the tree if it is present -- if the element does not exist, it returns null
    //------------------------------------------------------------------------------------------------------------

    private Node findElement(String key, Node t){
	//value to be used to compare the key and the value of the current node
	int comparison = key.compareTo(t.value);

	//if the value of the current node equals the key, then the node has been found 
	if(comparison == 0){
	    return t;
	}

	//if the key is smaller, then the left tree must be traversed if it is not null 
	//if the left tree is null then that means the element cannot be found in the tree
	else if(comparison < 0){
	    if(t.left != null){
		return findElement(key, t.left);
	    }
	}	

	//if the key is larger, then the right tree must be traversed if it is not null
	//if the right tree is null then that means the element cannot be found in the tree
	else if(comparison > 0){
	    if(t.right != null){
		return findElement(key, t.right);
	    }
	}

	return null;

    }

    //-- printHelper ----------------------------------------------------------------------------------------------------
    //helper method for inOrderTraverse
    //recursively goes through the tree and prints everything out in order 
    //---------------------------------------------------------------------------------------------------------
    private void printHelper(Node tree){
	//if the tree is null, simply return
	if(tree == null) return;

	//print everything to the left of the node
	if(tree.left != null){
	    printHelper(tree.left);
	}

	//print the current node
	System.out.println(tree.value);


	//print everything to the right of the node
	if(tree.right != null){
	    printHelper(tree.right);
	}
    }


    //================================= END OF BinarySearchTree ======================================================================//


}
