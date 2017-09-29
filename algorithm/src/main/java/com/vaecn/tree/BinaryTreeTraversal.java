package com.vaecn.tree;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by sifan on 2017/9/29.
 */
public class BinaryTreeTraversal {

    public static Node createBintree(int[] array) {
        if (array == null || array.length == 0) return null;
        Node node = new Node(array[0]);
        for (int i = 1; i < array.length; i++) {
            insert(node, array[i]);
        }
        return node;
    }

    public static void insert(Node root, int data) {
        if (root == null) {
            return;
        }

        if (data < root.getData()) {
            if (root.getLeft() == null) {
                root.setLeft(new Node(data));
            } else {
                insert(root.getLeft(), data);
            }
        } else {
            if (root.getRight() == null) {
                root.setRight(new Node(data));
            } else {
                insert(root.getRight(), data);
            }
        }
    }

    public static void preOrder(Node root) {
        if (root == null) return;
        System.out.print(root.getData() + " ");
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    public static void midOrder(Node root) {
        if (root == null) return;
        midOrder(root.getLeft());
        System.out.print(root.getData() + " ");
        midOrder(root.getRight());
    }

    public static void afterOrder(Node root) {
        if (root == null) return;
        afterOrder(root.getLeft());
        afterOrder(root.getRight());
        System.out.print(root.getData() + " ");
    }

    @Data
    @AllArgsConstructor
    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{12, 76, 35, 22, 16, 48, 90, 46, 9, 40};
        Node node = createBintree(array);
        System.out.println(node);

        System.out.println("先序遍历： ");
        preOrder(node);
        System.out.println();

        System.out.println("中序遍历： ");
        midOrder(node);
        System.out.println();

        System.out.println("后序遍历： ");
        afterOrder(node);
        System.out.println();
    }
}
