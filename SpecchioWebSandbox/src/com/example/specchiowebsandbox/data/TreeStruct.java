package com.example.specchiowebsandbox.data;

import java.util.ArrayList;
import java.util.List;

public class TreeStruct<T> {
	
	private Node<T> root;
	
	public TreeStruct(T rootData){
		root = new Node<T>();
		root.data = rootData;
		root.children = new ArrayList<Node<T>>();
	}
	
	public void add(T nodeData, Node parent, int hierarchy_lvl){
		Node<T> node = new Node<T>();
		node.data = nodeData;
		node.parent = parent;
		node.hierarchy_level=hierarchy_lvl;
		
		
	}
	
	public Node<T> getRoot(){
		return root;
	}
	
	public static class Node<T>{
		private T data;
		private Node<T> parent;
		private List<Node<T>> children;
		private int hierarchy_level;
		
		public void addChildren(Node<T> node){
			children.add(node);
		}
		
		
	}

}
