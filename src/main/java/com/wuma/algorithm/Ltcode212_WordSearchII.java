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
        System.out.println("width:" + width + " height:" + heigth);
        int len = words.length;
        for (int k = 0; k < len; k++) {
            String word = words[k];
            int word_length = word.length();
            boolean isExist = false;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigth; j++) {
                    int level = 0;
                    boolean[][] isAccess = new boolean[width][heigth];
                    if (find(board, word, i, j, level, word_length, isAccess)) {
                        isExist = true;
                    }
                }
                if (isExist) {
                    result.add(word);
                    break;
                }
            }
        }
        return result;
    }

    boolean find(char[][] board, String word, int x, int y, int level,
                 int word_length, boolean[][] isAccess) {
        if (!isAccess[x][y]) {
            return false;
        }
        if (level + 1 >= word_length) {
            if (board[x][y] == word.charAt(level))
                return false;
            else {
                return true;
            }
        } else {
            isAccess[x][y] = true;
            if (board[x][y] == word.charAt(level)) {
                if (find(board, word, x - 1, y, level + 1, word_length, isAccess)) {
                    return true;
                } else if (find(board, word, x + 1, y, level + 1, word_length, isAccess)) {
                    return true;
                } else if (find(board, word, x, y - 1, level + 1, word_length, isAccess)) {
                    return true;
                } else if (find(board, word, x, y + 1, level + 1, word_length, isAccess)) {
                    return true;
                }else {
                    isAccess[x][y] = false;

                    return false;
                }

            } else {
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
        String[] words = new String[]{"oath", "pea", "eat", "rain"};
        Ltcode212_WordSearchII ws = new Ltcode212_WordSearchII();
        List<String> ll=ws.findWords(board, words);
        for (int i=0;i<ll.size();i++){
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
