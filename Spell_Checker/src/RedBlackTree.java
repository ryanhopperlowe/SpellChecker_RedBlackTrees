

public class RedBlackTree<Key extends Comparable<Key>> {
	private Node<Key>				root;
	
	public boolean isLeaf(Node<Key> n){
		if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
		if (n.equals(root)) return false;
		if (n.leftChild == null && n.rightChild == null){
			return true;
		}
		return false;
	}
	
	public void visit(Node n){
		System.out.println(n.key);
	}
	
	public void printTree(){  //preorder: visit, go left, go right
		Node<Key> currentNode = root;	
		printTree(currentNode);
	}
	
	public void printTree(Node<Key> node){
		System.out.println(node.key);
		if (node.hasLeft())
			printTree(node.leftChild);
		if (node.hasRight())
			printTree(node.rightChild);
	}
	
	// place a new node in the RB tree with data the parameter and color it red. 
	public void addNode(Key data) {  	//this < that  <0.  this > that  >0		
		Node<Key> node = new Node<>(data);
		
		//if there's no root node, set root to the node
		if (root == null) {
			node.setRed(false);
			root = node;
			return;
		}		
		
		//start from root
		Node<Key> currentNode = root;
		while (currentNode != null) {
			int comparison = node.compareTo(currentNode); //compare new node to currentNode
			Node<Key> nextNode = null;
			
			//if new node is greater
			//then check the current node's right child
			if (comparison > 0) {
				nextNode = currentNode.rightChild;
				
				//if the current node has no right child, 
				//then set it to the new node
				if (nextNode == null) {
					currentNode.rightChild = node;
					node.parent = currentNode;
				}
			}
			//if it's smaller
			//then check the left child
			else if (comparison < 0) {
				nextNode = currentNode.leftChild;
				
				//if there is no left child
				//set it to the new node
				if (nextNode == null) {
					currentNode.leftChild = node;
					node.parent = currentNode;
				}
			}
			
			//next node will be null if the node is added or found
			//breaking the loop
			currentNode = nextNode;
		}
		
		this.fixTree(node);
	}

	public void insert(Key data){
		addNode(data);
	}
	
	public Node<Key> lookup(Key k) {
		if (root == null)
			return null;
		
		//start at root
		Node<Key> currentNode = root;
		Node<Key> node = new Node<>(k);
		
		//while the node is not found
		while (currentNode != null) {
			int comparison = node.compareTo(currentNode); //compare current to new node
			
//			System.out.println(currentNode.getKey()); //print steps taken
			
			if (comparison > 0)
				currentNode = currentNode.rightChild; //check right child
			else if (comparison < 0)
				currentNode = currentNode.leftChild; //check left child
			else 
				break; //node is found
		}
		return currentNode; //currentNode will be null when there are no more nodes to check
	}
 	
	
	public Node<Key> getSibling(Node<Key> n) {  
		if (n == root)
			return null;
		
		if (n.parent.hasLeft() && n == n.parent.leftChild)
			return n.parent.rightChild;
		else if (n.parent.hasRight())
			return n.parent.leftChild;
		return null;
	}
	
	public Node<Key> getAunt(Node<Key> n){
		return this.getSibling(n.parent);
	}
	
	public Node<Key> getGrandparent(Node<Key> n) {
		if (n == root || n.parent == root)
			return null;
		return n.parent.parent;
	}
	
	public void rotateLeft(Node<Key> x){
		Node<Key> y = x.rightChild;
		x.rightChild = y.leftChild;
		if (y.leftChild != null)
			y.leftChild.parent = x;
		y.parent = x.parent;
		if (x == root)
			root = y;
		else if (this.isLeftChild(x.parent, x))
			x.parent.leftChild = y;
		else
			x.parent.rightChild = y;
		y.leftChild = x;
		x.parent = y;
	}
	
	public void rotateRight(Node<Key> x){
		Node<Key> y = x.leftChild;
		x.leftChild = y.rightChild;
		if (y.rightChild != null)
			y.rightChild.parent = x;
		y.parent = x.parent;
		if (x == root)
			root = y;
		else if (this.isLeftChild(x.parent, x))
			x.parent.leftChild = y;
		else
			x.parent.rightChild = y;
		y.rightChild = x;
		x.parent = y;
	}
	
	public void fixTree(Node<Key> current) {
		if (current == root) { //set root to black
			current.setRed(false);
			return;
		}

		if (!current.parent.isRed) //if parent is black, the tree is fine
			return;
		
		Node<Key> aunt = getAunt(current);
		
		if (aunt != null && aunt.isRed) { //if aunt is red
			current.parent.setRed(false);
			aunt.setRed(false);
			getGrandparent(current).setRed(true);
			current = getGrandparent(current); //move to grandparent
		}
		else if (current.parent.isLeftChild()) { //if parent is leftchild
			if (current.isRightChild()) { //if current is right child
				current = current.parent;
				this.rotateLeft(current);
			} 
			else if (current.isLeftChild()) { //if current is left child
				current.parent.setRed(false);
				getGrandparent(current).setRed(true);
				this.rotateRight(getGrandparent(current));
			}
		}
		else if (current.parent.isRightChild()) { //if parent is right child
			if (current.isLeftChild()) { // if current is left child
				current = current.parent;
				this.rotateRight(current);
			}
			else if (current.isRightChild()) { //if current is rightchild
				current.parent.setRed(false);
				getGrandparent(current).setRed(true);
				this.rotateLeft(getGrandparent(current));
			}
		}
		//recursive call
		this.fixTree(current);
	}
	
	public boolean isEmpty(Node<Key> n){
		if (n.key == null){
			return true;
		}
		return false;
	}
	 
	public boolean isLeftChild(Node<Key> parent, Node<Key> child)
	{
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor v) {
	   	RedBlackTree.preOrderVisit(root, v);
	}	 
	 
	private static void preOrderVisit(Node n, Visitor v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}
}
