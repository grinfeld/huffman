package com.mikerusoft.huffman;

import java.util.*;
import java.util.stream.Collectors;

public class Result {

    // huffman decoding - you receive input of mapping between character and its code based on frequency
    // (which is interesting only when you implement full algorithm) and already encoded text
    // Now we should decode this text, based on mapping received as first parameter

    private static final String NEWLINE = "[newline]";

    public static String decode(List<String> codes, String encoded) {
        Node root = buildDataStructure(codes);
        return decode(encoded, root);
    }

    private static List<Pair<String, String>> fillCodes(List<String> codes) {
        return Optional.ofNullable(codes).orElseGet(ArrayList::new)
                .stream().map(Result::splitCodes).map(Result::fixedNewLine).collect(Collectors.toList());
    }

    private static Pair<String, String> fixedNewLine(Pair<String, String> pair) {
        String character = NEWLINE.equals(pair.getRight()) ? "\n" : pair.getRight();
        return new Pair<>(pair.getLeft(), character);
    }

    private static Pair<String, String> splitCodes(String codePair) {
        String[] pair = codePair.split("\\s");
        if (pair.length < 2) {
            throw new IllegalArgumentException("Should be as pair of char and bits divided by space, but " + codePair);
        }
        return new Pair<>(pair[1].trim(), pair[0].trim());
    }

    private static Node buildDataStructure(List<String> codes) {
        Node root = new Node();

        List<Pair<String, String>> pairs = fillCodes(codes);

        Node currentNode = root;
        for (Pair<String, String> current : pairs) {
            buildSpecificNode(currentNode, current.getLeft().toCharArray(), current.getRight());
            currentNode = root;
        }

        return root;
    }

    private static void buildSpecificNode(Node currentNode, char[] bits, String character) {
        for (char bit : bits) {
            currentNode = currentNode.setNode(bit);
        }
        currentNode.setValue(character);
    }

    private static String decode(String encoded, Node root) {
        char[] bits = encoded.toCharArray();
        StringBuilder sb = new StringBuilder();
        Node currentNode = root;
        for (char bit : bits) {
            Node foundNode = currentNode.getNode(bit);
            if (foundNode == null) {
                sb.insert(sb.length() - 1, bit);
            } else if (foundNode.getValue() != null) {
                sb.append(foundNode.getValue());
                currentNode = root;
            } else {
                currentNode = foundNode;
            }
        }
        return sb.toString();
    }

    public static class Node {
        private Node left;
        private Node right;
        private String value;

        public Node() {}

        public Node setLeft(Node node) {
            this.left = node;
            return this.left;
        }

        public Node setRight(Node node) {
            this.right = node;
            return this.right;
        }

        public Node setNode(char bit) {
            if ('1' == bit)
                return right != null ? right : setRight(new Node());
            else if ('0' == bit)
                return left != null ? left : setLeft(new Node());

            throw new RuntimeException("Invalid bit" + bit);
        }

        public Node getNode(char bit) {
            if ('1' == bit)
                return this.right;
            else if ('0' == bit)
                return this.left;

            return null;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Pair<L, R> {
        private L left;
        private R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }
    }
}
