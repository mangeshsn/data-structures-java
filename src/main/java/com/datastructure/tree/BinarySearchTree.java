package com.datastructure.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> {
	Node root = null;
	int nodeCount = 0;

	public enum TraversalOrder {
		PRE_ORDER, IN_ORDER, POST_ORDER
	}

	class Node {
		Node left, right;
		T data;

		Node(Node left, Node right, T data) {
			this.left = left;
			this.right = right;
			this.data = data;
		}
	}

	public int size() {
		return nodeCount;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean add(T data) {
		if (contains(data)) {
			return false;
		} else {
			root = add(root, data);
			nodeCount++;
			return true;
		}
	}

	private Node add(Node node, T data) {
		if (node == null) {
			node = new Node(null, null, data);
		} else if (data.compareTo(node.data) > 0) {
			node.right = add(node.right, data);
		} else if (data.compareTo(node.data) < 0) {
			node.left = add(node.left, data);
		}
		return node;
	}

	public boolean remove(T data) {
		if (contains(data)) {
			root = remove(root, data);
			nodeCount--;
			return true;
		} else {
			return false;
		}
	}

	private Node remove(Node node, T data) {
		if (node == null)
			return null;
		int compare = data.compareTo(node.data);
		if (compare > 0) {
			node.right = remove(node.right, data);

		} else if (compare < 0) {
			node.left = remove(node.left, data);
		} else {
			if (node.left == null) {
				Node rightChild = node.right;
				node.data = null;
				node = null;
				return rightChild;
			} else if (node.right == null) {
				Node leftChild = node.left;
				node.data = null;
				node = null;
				return leftChild;
			} else {
				Node tmp = findMin(node.right);
				node.data = tmp.data;
				node.right = remove(node.right, tmp.data);
			}
		}
		return node;
	}

	public Node findMin(Node node) {
		while (node.left != null)
			node = node.left;
		return node;
	}

	public Node findMax(Node node) {
		while (node.right != null)
			node = node.right;
		return node;
	}

	public boolean contains(T data) {
		return contains(root, data);
	}

	private int height(Node node) {
		if (node == null)
			return 0;
		return Math.max(height(node.left), height(node.right)) + 1;
	}

	public int height() {
		return height(root);
	}

	private boolean contains(Node node, T data) {

		if (node == null) {
			return false;
		}
		int compare = data.compareTo(node.data);
		if (compare > 0) {
			return contains(node.right, data);
		} else if (compare < 0) {
			return contains(node.left, data);
		} else {
			return true;
		}
	}

	public void printTree(Node node, String space) {
		if (node == null)
			return;
		space += space;
		printTree(node.left, space);
		System.out.println(space + node.data);
		printTree(node.right, space);

	}

	public void printTree() {
		printTree(root, " ");
	}

	public Iterator<T> traverse(TraversalOrder order) {
		switch (order) {
		case PRE_ORDER:
			return preOrderTraversal();

		default:
			return null;

		}
	}

	private Iterator<T> preOrderTraversal() {
		
		final Stack<Node> stack = new Stack<>();
		final int expCount = nodeCount;
		stack.push(root);
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				if(expCount!=nodeCount) {
					throw new ConcurrentModificationException();
				}
				return root!=null && !stack.isEmpty();
			}

			@Override
			public T next() {
				if(expCount!=nodeCount) {
					throw new ConcurrentModificationException();
				}
				Node node = stack.pop();
				if(node.right != null) {
					stack.push(node.right);
				}
				if(node.left!=null) {
					stack.push(node.left);
				}
				return node.data;
				
			}
			
		};

	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

		List<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i <= 1000; i++) {
			data.add(i);
		}
		Collections.shuffle(data);
		// data.forEach(System.out::println);
		for (Integer i : data) {
			bst.add(i);
		}
		//bst.printTree();
		bst.remove(4);
		//bst.printTree();
		System.out.println(bst.height());
	}
}
