

public class Node<Key extends Comparable<Key>> {
	Key					key;
	Node<Key>			parent, leftChild, rightChild;
	boolean				isRed;
	int					color;
	
	
	public Node(Key key) {
		this.key = key;
		leftChild = null;
		rightChild = null;
		color = 0;
		isRed = true;
	}
	
	
	
	public void setRed(boolean redFlag) {
		isRed = redFlag;
		if (redFlag)
			color = 0;
		else
			color = 1;
	}
	
	public boolean isLeftChild() {
		if (parent == null)
			return false;
		return parent.leftChild == this;
	}
	
	public boolean isRightChild() {
		if (parent == null)
			return false;
		return parent.rightChild == this;
	}
	
	public int compareTo(Node<Key> n){ 	//this < that  <0
 		return key.compareTo(n.key);  	//this > that  >0
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Node<?>))
			return false;
		
		Node<Key> n = (Node<Key>) o;
		return n.key.equals(this.key);
	}
  
	public boolean isLeaf() {
		if (this.leftChild == null && this.rightChild == null)
			return true;
		return false;
	}
	
	public boolean hasLeft() {
		return leftChild != null;
	}
	
	public boolean hasRight() {
		return rightChild != null;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Node<Key> getLeft() {
		return leftChild;
	}
	
	public Node<Key> getRight() {
		return rightChild;
	}
	
	public Node<Key> getParent() {
		return parent;
	}
	
	public int getColor() {
		return color;
	}
	
	public boolean isRed() {
		return isRed;
	}
	
	public void setLeft(Node<Key> n) {
		leftChild = n;
	}
	
	public void setRight(Node<Key> n) {
		rightChild = n;
	}
	
	public void setParent(Node<Key> n) {
		parent = n;
	}
}
