package com.mygdx.proceduralGeneration;

import java.util.Stack;

public class RoomConnectivityValidator {
    private int[][] grade;
    private boolean[][] visited;
    private int rows;
    private int cols;

    public RoomConnectivityValidator(int[][] grade) {
        this.grade = grade;
        this.rows = grade.length;
        this.cols = grade[0].length;
        this.visited = new boolean[rows][cols];
    }

    public boolean isAllRoomsConnected() {
        // Encontra o primeiro chão (0) ou área especial (2) para iniciar a busca
        int startX = -1, startY = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grade[i][j] == 0 || grade[i][j] == 2) {
                    startX = i;
                    startY = j;
                    break;
                }
            }
            if (startX != -1) break;
        }

        if (startX == -1) return true; // Não há áreas transitáveis

        // Realiza DFS
        dfs(startX, startY);

        // Verifica se todos os 0s e 2s foram visitados
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((grade[i][j] == 0 || grade[i][j] == 2) && !visited[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void dfs(int x, int y) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{x, y});
        visited[x][y] = true;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            for (int[] dir : directions) {
                int newX = current[0] + dir[0];
                int newY = current[1] + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols
                        && (grade[newX][newY] == 0 || grade[newX][newY] == 2)
                        && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    stack.push(new int[]{newX, newY});
                }
            }
        }
    }
}