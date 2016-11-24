package com.wuma.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwujun
 * on 2016/11/8 at 14:05
 * Given a 2D board and a list of words from the dictionary,
 * find all words in the board.
 * Each word must be constructed from letters of
 * sequentially adjacent cell, where "adjacent" cells are
 * those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once in a word.
 * For example,
 * Given words = ["oath","pea","eat","rain"] and board =
 * [
 * ['o','a','a','n'],
 * ['e','t','a','e'],
 * ['i','h','k','r'],
 * ['i','f','l','v']
 * ]
 * Return ["eat","oath"].
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 */
public class Ltcode212_WordSearchII {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<String>();
        int heigth = board.length;
        int width = board[0].length;
        int len = words.length;
        for (int k = 0; k < len; k++) {
            String word = words[k];
            int word_length = word.length();
            boolean isExist = false;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigth; j++) {
                    int level = 0;
                    boolean[][] isAccess = new boolean[heigth][width];
                    if (find(board, word, i, j, level, word_length, isAccess)) {
                        isExist = true;
                    }
                }
                if (isExist && !result.contains(word)) {
                    result.add(word);
                    break;
                }
            }
        }
        return result;
    }


    boolean find(char[][] board, String word, int x, int y, int level,
                 int word_length, boolean[][] isAccess) {
        int h = board.length - 1;
        int w = board[0].length - 1;
        if (x < 0 || y < 0 || x > w || y > h || isAccess[y][x]) {
            return false;
        }
        if (level + 1 >= word_length) {
            if (board[y][x] == word.charAt(level))
                return true;
            else {
                return false;
            }
        } else {
            isAccess[y][x] = true;
            if (board[y][x] == word.charAt(level)) {
                if (find(board, word, x - 1, y, level + 1, word_length, isAccess)) {
                    return true;
                } else if (find(board, word, x + 1, y, level + 1, word_length, isAccess)) {
                    return true;
                } else if (find(board, word, x, y - 1, level + 1, word_length, isAccess)) {
                    return true;
                } else if (find(board, word, x, y + 1, level + 1, word_length, isAccess)) {
                    return true;
                } else {
                    isAccess[y][x] = false;

                    return false;
                }

            } else {
                isAccess[y][x] = false;
                return false;
            }
        }
    }

    public void testfunc(boolean[][] test) {
        test[1][1] = true;
    }

    public static void main(String[] args) {
        char[][] borad = new char[][]{
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}};
        char[][] board = new char[][]{
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},};
        char[][] b = new char[][]{{}};
        char[][] bb = new char[][]{{'a'}, {'a'}};
        System.out.println("bb's length:" + bb.length);
        String[] words = new String[]{"oath", "pea", "eat", "rain"};
        String[] wo = new String[]{"aa"};
        Ltcode212_WordSearchII ws = new Ltcode212_WordSearchII();
        List<String> ll = ws.findWords(bb, wo);
        for (int i = 0; i < ll.size(); i++) {
            System.out.println(ll.get(i));
        }
        boolean[][] test = new boolean[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(test[i][j] ? 1 : 0);
            }
            System.out.println();
        }
        ws.testfunc(test);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(test[i][j] ? 1 : 0);
            }
            System.out.println();
        }
    }
}
