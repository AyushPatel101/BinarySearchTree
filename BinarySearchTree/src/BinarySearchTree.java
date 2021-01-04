
/* CS 314 STUDENTS: FILL IN THIS HEADER.

 *
 * Student information for assignment:
 *
 *  On my honor, Ayush Patel, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ap55837
 *  email address: patayush01@utexas.edu
 *  TA name: Tony
 *  Number of slip days I am using: 1
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Shell for a binary search tree class.
 * 
 * @author scottm
 * @param <E> The data type of the elements of this BinarySearchTree. Must
 *            implement Comparable or inherit from a class that implements
 *            Comparable.
 *
 */
public class BinarySearchTree<E extends Comparable<? super E>> {

	private BSTNode<E> root;
	private int size;

	/**
	 * Add the specified item to this Binary Search Tree if it is not already
	 * present. <br>
	 * pre: <tt>value</tt> != null<br>
	 * post: Add value to this tree if not already present. Return true if this
	 * tree changed as a result of this method call, false otherwise.
	 * 
	 * @param value the value to add to the tree
	 * @return false if an item equivalent to value is already present in the
	 *         tree, return true if value is added to the tree and size() = old
	 *         size() + 1
	 */
	public boolean add(E value) {
		if (value == null)
			throw new IllegalArgumentException("value cannot be null");
		// call helper (recursive) method that takes in a node
		return add(value, root);

	}

	// pre: none
	// post: returns true if node with value was added, false otherwise
	private boolean add(E value, BSTNode<E> node) {
		// edge case, if node we pass in is null, then it has to be root, so
		// initialize root with value as data
		if (node == null)
			root = new BSTNode<E>(value);
		else {
			int compare = value.compareTo(node.getData());
			// if value is less than data of node, need to check left child
			if (compare < 0) {
				// if left child is null, set node's left child to new node with
				// value as data
				if (node.getLeft() == null)
					node.setLeft(new BSTNode<E>(value));
				// else, go to left child (recursive step)
				else
					return add(value, node.getLeft());
			}
			// else, if value is greater than 0, check right child
			else if (compare > 0) {
				// if right child is null, set node's right child to new node
				// with value as data
				if (node.getRight() == null)
					node.setRight(new BSTNode<E>(value));
				// else, go to right child (recursive step)
				else
					return add(value, node.getRight());
			}
			// else, we found value already in tree, so return false
			else
				return false;
		}
		// if got here, then either node's left or right child has been
		// initialized, so add to size and return true
		size++;
		return true;
	}

	/**
	 * Remove a specified item from this Binary Search Tree if it is present.
	 * <br>
	 * pre: <tt>value</tt> != null<br>
	 * post: Remove value from the tree if present, return true if this tree
	 * changed as a result of this method call, false otherwise.
	 * 
	 * @param value the value to remove from the tree if present
	 * @return false if value was not present returns true if value was present
	 *         and size() = old size() - 1
	 */
	public boolean remove(E value) {
		if (value == null)
			throw new IllegalArgumentException("value cannot be null");
		int oldSize = size;
		// setting root helper methods return makes it so we don't have to check
		// if node is root every time (special case)
		root = remove(value, root);
		// if size changed, return true, else return false
		return oldSize != size;
	}

	// pre: none
	// post: returns node that will be new value of root. If this value doesn't
	// return root, then root was removed. Else, either nothing was removed or
	// node that was not root was removed
	private BSTNode<E> remove(E value, BSTNode<E> node) {
		// value is not present
		if (node == null) {
			return node;
		}
		int compare = value.compareTo(node.getData());
		// if less than, rebuilds tree with new left (recursive step)
		if (compare < 0)
			node.setLeft(remove(value, node.getLeft()));
		else if (compare > 0)
			// if greater than, rebuilds tree with new right (recursive step)
			node.setRight(remove(value, node.getRight()));
		else {
			// if got here, than element is in tree, so can decrement size
			size--;
			// case 1: if node with value has no children, can just set it to
			// null
			if (node.getLeft() == null && node.getRight() == null)
				node = null;
			// case 2: else if, its left child is null, then must have right
			// child at this point, so set node to its right child (removes
			// node)
			else if (node.getLeft() == null)
				node = node.getRight();
			// case 2.5: else if, its right child is null, then must have left
			// child at this point, so set node to its left child (removes node)
			else if (node.getRight() == null)
				node = node.getLeft();
			// case 3: node has two children
			else {
				// find max value of node on its left side, and set that to
				// node's data
				node.setData(max(node.getLeft()));
				// then "rebuild" tree with with new left children (recursive
				// step)
				node.setLeft(remove(node.getData(), node.getLeft()));
				// have to add to size here because will be off by one
				// (recursive step above will do size-- twice, when we only
				// removed one node, so need to compensate
				size++;
			}
		}
		// returning node to set root to such value, if root doesn't change this
		// will be root itself (accounts for special case where root is removed)
		return node;
	}

	/**
	 * Check to see if the specified element is in this Binary Search Tree. <br>
	 * pre: <tt>value</tt> != null<br>
	 * post: return true if value is present in tree, false otherwise
	 * 
	 * @param value the value to look for in the tree
	 * @return true if value is present in this tree, false otherwise
	 */
	public boolean isPresent(E value) {
		if (value == null)
			throw new IllegalArgumentException("value cannot be null");
		// calling recursive isPresent with node added to parameter
		return isPresent(value, root);
	}

	// pre: none
	// post: returns true if value is present, else otherwise
	private boolean isPresent(E value, BSTNode<E> node) {
		// base case: if get to a null node, then value not found so return
		// false
		if (node == null)
			return false;
		int compare = value.compareTo(node.getData());
		// if compare less than 0, traverse left children (recursive step)
		if (compare < 0)
			return isPresent(value, node.getLeft());
		// else if compare greater than 0, traverse right children (recursive
		// step)
		else if (compare > 0)
			return isPresent(value, node.getRight());
		// else, compare = 0, meaning we found value in BST, so return true
		else
			return true;
	}

	/**
	 * Return how many elements are in this Binary Search Tree. <br>
	 * pre: none<br>
	 * post: return the number of items in this tree
	 * 
	 * @return the number of items in this Binary Search Tree
	 */
	public int size() {
		return size;
	}

	/**
	 * return the height of this Binary Search Tree. <br>
	 * pre: none<br>
	 * post: return the height of this tree. If the tree is empty return -1,
	 * otherwise return the height of the tree
	 * 
	 * @return the height of this tree or -1 if the tree is empty
	 */
	public int height() {
		// calling recursive helper with node added to parameter
		return height(root);
	}

	// pre: none
	// post: returns height of tree
	private int height(BSTNode<E> node) {
		// if node is null, subtract one from height (preventing off by 1, as
		// BST of just one node has a height of 0)
		if (node == null)
			return -1;
		// else, add 1 to height and find max height off left and right of node
		// (recursive step) and return that
		return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
	}

	/**
	 * Return a list of all the elements in this Binary Search Tree. <br>
	 * pre: none<br>
	 * post: return a List object with all data from the tree in ascending
	 * order. If the tree is empty return an empty List
	 * 
	 * @return a List object with all data from the tree in sorted order if the
	 *         tree is empty return an empty List
	 */
	public List<E> getAll() {
		// calling recursive helper with list and node
		List<E> storage= new ArrayList<>();
		getAll(storage, root);
	    return storage;
	}

	// pre: none
	// post: returns a list of all elements in BST in ascending order
	private void getAll(List<E> temp, BSTNode<E> node) {
		if (node != null) {
			// recursive step, check all left children till hit a null, then
			// pops
			getAll(temp, node.getLeft());
			// add that node's data
			temp.add(node.getData());
			// recursive step, do the same recursion with right child (recursive
			// step)
			getAll(temp, node.getRight());

		}
	}

	/**
	 * return the maximum value in this binary search tree. <br>
	 * pre: <tt>size()</tt> > 0<br>
	 * post: return the largest value in this Binary Search Tree
	 * 
	 * @return the maximum value in this tree
	 */
	public E max() {
		if (size() == 0)
			throw new IllegalArgumentException("size cannot be 0");
		// calling recursive helper with node as parameter
		return max(root);
	}

	// pre: none
	// post: return max value in BST
	private E max(BSTNode<E> node) {
		// since finding max, should only check right children
		while (node.getRight() != null)
			node = node.getRight();
		// once got here, we hit a null right child, so return that node's data
		return node.getData();

	}

	/**
	 * return the minimum value in this binary search tree. <br>
	 * pre: <tt>size()</tt> > 0<br>
	 * post: return the smallest value in this Binary Search Tree
	 * 
	 * @return the minimum value in this tree
	 */
	public E min() {
		if (size() == 0)
			throw new IllegalArgumentException("size cannot be 0");
		// calling recursive helper with node as parameter
		return min(root);
	}

	// pre: none
	// post: returns min value in tree
	private E min(BSTNode<E> node) {
		// since finding min, should only check left children
		while (node.getLeft() != null)
			node = node.getLeft();
		// once got here, we hit a null left child, so return that node's data
		return node.getData();
	}

	/**
	 * An add method that implements the add algorithm iteratively instead of
	 * recursively. <br>
	 * pre: data != null <br>
	 * post: if data is not present add it to the tree, otherwise do nothing.
	 * 
	 * @param data the item to be added to this tree
	 * @return true if data was not present before this call to add, false
	 *         otherwise.
	 */
	public boolean iterativeAdd(E data) {
		// precon
		if (data == null)
			throw new IllegalArgumentException("data cannot be null");
		int oldSize = size;
		// special case, root is a null, so set root and return
		if (root == null) {
			root = new BSTNode<E>(data);
			size++;
		} else {
			// else, start traversing from root until hit a null
			BSTNode<E> node = root;
			// while no element is removed
			while (oldSize == size) {
				int compare = data.compareTo(node.getData());
				// if data is less than node's data
				if (compare < 0) {
					// if that node has no left child, can set left child to new
					// node with data
					if (node.getLeft() == null) {
						node.setLeft(new BSTNode<E>(data));
						size++;
					}
					// else, it has left child, so continue traversing from that
					// left child
					node = node.getLeft();
					// if data is less than node's data
				} else if (compare > 0) {
					// if that node has no right child, can set right child to
					// new node with data
					if (node.getRight() == null) {
						node.setRight(new BSTNode<E>(data));
						size++;
					}
					// else, it has right child, so continue traversing from
					// that right child
					node = node.getRight();
				}
				// if got here, than data is already in BST, so return false
				else
					return false;
			}
		}
		// if gets here, then check if size changed (which it should have).
		// Returns true if it did, false otherwise
		return oldSize != size;
	}

	/**
	 * Return the "kth" element in this Binary Search Tree. If kth = 0 the
	 * smallest value (minimum) is returned. If kth = 1 the second smallest
	 * value is returned, and so forth. <br>
	 * pre: 0 <= kth < size()
	 * 
	 * @param kth indicates the rank of the element to get
	 * @return the kth value in this Binary Search Tree
	 */
	public E get(int kth) {
		if (kth < 0 || kth >= size)
			throw new IllegalArgumentException("kth is out of bounds");
		// using kthStorage to store kth to utilize pass by reference, get
		// modifies kth and remembers new value of kth even when recursion pops
		// because its stored in array
		int[] kthStorage = new int[1];
		kthStorage[0] = kth;
		return get(kthStorage, root);
	}

	// pre: none
	// post: returns kth value in BST
	private E get(int[] kth, BSTNode<E> node) {
		if (node != null) {
			// recursive step, check all left children and store value
			E data = get(kth, node.getLeft());
			// if getting left child didn't get a null, that means found kth
			// value, so return data
			if (data != null)
				return data;

			// if got to a kth element, return that element
			if (kth[0] == 0)
				return node.getData();
			// decrement kth because found value, but not kth value
			kth[0]--;
			// recursive step, check all right children. can return here because
			// if statement above will get appropriate value
			return get(kth, node.getRight());

		}
		// method will never actually return this (final pop), but needed for
		// recursion termination (returns null)
		return null;

	}

	/**
	 * Return a List with all values in this Binary Search Tree that are less
	 * than the parameter <tt>value</tt>. <tt>value</tt> != null<br>
	 * 
	 * @param value the cutoff value
	 * @return a List with all values in this tree that are less than the
	 *         parameter value. If there are no values in this tree less than
	 *         value return an empty list. The elements of the list are in
	 *         ascending order.
	 */
	public List<E> getAllLessThan(E value) {
		if (value == null)
			throw new IllegalArgumentException("value cannot be null");
		// calling recursive helper with ArrayList to store values and node
		List<E> storage= new ArrayList<>();
		getAllLessThan(storage, value, root);
		return storage;
	}

	// pre: none
	// post: returns list of all elements less than value
	private void getAllLessThan(List<E> temp, E value,
			BSTNode<E> node) {
		if (node != null) {
			// recursive step, check all left children till hit a null, then
			// pops
			getAllLessThan(temp, value, node.getLeft());
			// if ever get to a node >= parameter node, return temp (got all
			// values < node)
			if (node.getData().compareTo(value) < 0) {	
			temp.add(node.getData());
			// recursive step, check all right children till hit a null, then
			// pops
			getAllLessThan(temp, value, node.getRight());
			}
		}
	}

	/**
	 * Return a List with all values in this Binary Search Tree that are greater
	 * than the parameter <tt>value</tt>. <tt>value</tt> != null<br>
	 * 
	 * @param value the cutoff value
	 * @return a List with all values in this tree that are greater than the
	 *         parameter value. If there are no values in this tree greater than
	 *         value return an empty list. The elements of the list are in
	 *         ascending order.
	 */
	public List<E> getAllGreaterThan(E value) {
		if (value == null)
			throw new IllegalArgumentException("value cannot be null");
		// calling recursive helper with ArrayList to store values and node
		List<E> storage= new ArrayList<>();
		getAllGreaterThan(storage, value, root);
		return storage;
	}

	// pre: none
	// post: returns list of all elements strictly greater than value
	private void getAllGreaterThan(List<E> temp, E value,
			BSTNode<E> node) {
		if (node != null) {
			// recursive step, check left children as long as they are greater
			// than value
			if (node.getData().compareTo(value) > 0) {
				getAllGreaterThan(temp, value, node.getLeft());
				// since greater, add it to list
				temp.add(node.getData());
			}
			// recursive step, check all right children (In order traversal)
			getAllGreaterThan(temp, value, node.getRight());
		}
	}

	/**
	 * Find the number of nodes in this tree at the specified depth. <br>
	 * pre: none
	 * 
	 * @param d The target depth.
	 * @return The number of nodes in this tree at a depth equal to the
	 *         parameter d.
	 */
	public int numNodesAtDepth(int d) {
		// calling recursive helper with node added to parameter
		return numNodesAtDepth(d, root);
	}

	// pre: none
	// post: returns number of nodes at depth d
	private int numNodesAtDepth(int d, BSTNode<E> node) {
		// if node is null, don't add any to depth counter (return 0)
		if (node == null)
			return 0;
		// if at depth 0 and node does not equal null (then it exists), add 1 to
		// depth counter (return 1)
		if (d == 0)
			return 1;
		// recursive step: adds to depth counter every time get to d and node is
		// not null
		return numNodesAtDepth(d - 1, node.getLeft())
				+ numNodesAtDepth(d - 1, node.getRight());

	}

	/**
	 * Prints a vertical representation of this tree. The tree has been rotated
	 * counter clockwise 90 degrees. The root is on the left. Each node is
	 * printed out on its own row. A node's children will not necessarily be at
	 * the rows directly above and below a row. They will be indented three
	 * spaces from the parent. Nodes indented the same amount are at the same
	 * depth. <br>
	 * pre: none
	 */
	public void printTree() {
		printTree(root, "");
	}

	private void printTree(BSTNode<E> n, String spaces) {
		if (n != null) {
			printTree(n.getRight(), spaces + "  ");
			System.out.println(spaces + n.getData());
			printTree(n.getLeft(), spaces + "  ");
		}
	}

	private static class BSTNode<E extends Comparable<? super E>> {
		private E data;
		private BSTNode<E> left;
		private BSTNode<E> right;

		public BSTNode() {
			this(null);
		}

		public BSTNode(E initValue) {
			this(null, initValue, null);
		}

		public BSTNode(BSTNode<E> initLeft, E initValue, BSTNode<E> initRight) {
			data = initValue;
			left = initLeft;
			right = initRight;
		}

		public E getData() {
			return data;
		}

		public BSTNode<E> getLeft() {
			return left;
		}

		public BSTNode<E> getRight() {
			return right;
		}

		public void setData(E theNewValue) {
			data = theNewValue;
		}

		public void setLeft(BSTNode<E> theNewLeft) {
			left = theNewLeft;
		}

		public void setRight(BSTNode<E> theNewRight) {
			right = theNewRight;
		}
	}
}