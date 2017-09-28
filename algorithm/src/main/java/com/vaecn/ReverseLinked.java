package com.vaecn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by pansifan on 17/9/28.
 */
public class ReverseLinked {

    public static Node recursionReverse(Node node) {
        if (node == null || node.getNext() == null) {
            return node;
        }

        Node curNode = node;
        Node reverse = recursionReverse(curNode.getNext());   //先反转后续节点
        curNode.getNext().setNext(curNode);               // 当前节点指针指向前一节点
        curNode.setNext(null);                         // 前一节点指针为空
        return reverse;
    }

    public static Node loopReverse(Node node) {
        if (node == null || node.getNext() == null) {
            return node;
        }
        Node preNode = null;
        Node curNode = node;
        Node temp;
        while (curNode != null) {
            temp = curNode.getNext();
            curNode.setNext(preNode);
            preNode = curNode;
            curNode = temp;
        }
        return preNode;
    }

    @AllArgsConstructor
    @Data
    public static class Node {
        private int data;
        private Node next;
    }

    public static void main(String[] args) {
        Node node4 = new Node(4, null);
        Node node3 = new Node(3, node4);
        Node node2 = new Node(2, node3);
        Node node1 = new Node(1, node2);
        Node node = new Node(0, node1);
        printNode(node);
        node = ReverseLinked.recursionReverse(node);
        printNode(node);
        node = ReverseLinked.loopReverse(node);
        printNode(node);
    }

    public static void printNode(@NonNull Node node) {
        StringBuilder sb = new StringBuilder();
        while (node.getNext() != null) {
            sb.append(node.getData()).append(" -> ");
            node = node.getNext();
        }
        sb.append(node.getData());
        System.out.println(sb.toString());
    }
}
