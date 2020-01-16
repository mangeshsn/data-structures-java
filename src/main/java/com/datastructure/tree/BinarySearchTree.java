package com.datastructure.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
	Node root = null;
	int nodeCount = 0;

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

	public boolean contains(T data) {
		return contains(root, data);
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
		space+=space;
		printTree(node.left, space);
		System.out.println(space + node.data);
		printTree(node.right, space);
		
	}

	public void printTree() {
		printTree(root," ");
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

		List<Integer> data = new ArrayList<Integer>();
		for(int i = 0; i <= 20; i++) {
			data.add(i);
		}
		Collections.shuffle(data);
		//data.forEach(System.out::println);
		for(Integer i : data) {
			bst.add(i);
		}
		bst.printTree();
	}
}
