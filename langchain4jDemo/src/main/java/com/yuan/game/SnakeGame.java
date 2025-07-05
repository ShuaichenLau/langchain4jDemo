package com.yuan.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (BOARD_WIDTH * BOARD_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 80;

    private ArrayList<Point> snake;
    private Point food;
    private char direction = 'R'; // R-右, L-左, U-上, D-下
    private boolean running = false;
    private Timer timer;
    private Random random;
    private int score = 0;
    private int highScore = 0;

    public SnakeGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame();
    }

    public void startGame() {
        snake = new ArrayList<>();
        // 初始化蛇的身体（3个单位长）
        snake.add(new Point(UNIT_SIZE * 2, 0));
        snake.add(new Point(UNIT_SIZE, 0));
        snake.add(new Point(0, 0));

        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // 绘制网格线（可选）
            drawGrid(g);

            // 绘制食物
            drawFood(g);

            // 绘制蛇
            drawSnake(g);

            // 绘制分数
            drawScore(g);

        } else {
            gameOver(g);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        // 绘制垂直线
        for (int i = 0; i < BOARD_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, BOARD_HEIGHT);
        }
        // 绘制水平线
        for (int i = 0; i < BOARD_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(0, i * UNIT_SIZE, BOARD_WIDTH, i * UNIT_SIZE);
        }
    }

    private void drawFood(Graphics g) {
        // 绘制食物为红色圆形
        g.setColor(Color.RED);
        g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

        // 添加食物的光晕效果
        g.setColor(new Color(255, 100, 100, 50));
        g.fillOval(food.x - 5, food.y - 5, UNIT_SIZE + 10, UNIT_SIZE + 10);
    }

    private void drawSnake(Graphics g) {
        for (int i = 0; i < snake.size(); i++) {
            Point segment = snake.get(i);

            if (i == 0) {
                // 蛇头 - 绿色
                g.setColor(Color.GREEN);
                g.fillRect(segment.x, segment.y, UNIT_SIZE, UNIT_SIZE);

                // 蛇头边框
                g.setColor(Color.DARK_GRAY);
                g.drawRect(segment.x, segment.y, UNIT_SIZE, UNIT_SIZE);

                // 蛇眼睛
                g.setColor(Color.WHITE);
                if (direction == 'R') {
                    g.fillOval(segment.x + 12, segment.y + 3, 4, 4);
                    g.fillOval(segment.x + 12, segment.y + 13, 4, 4);
                } else if (direction == 'L') {
                    g.fillOval(segment.x + 4, segment.y + 3, 4, 4);
                    g.fillOval(segment.x + 4, segment.y + 13, 4, 4);
                } else if (direction == 'U') {
                    g.fillOval(segment.x + 3, segment.y + 4, 4, 4);
                    g.fillOval(segment.x + 13, segment.y + 4, 4, 4);
                } else if (direction == 'D') {
                    g.fillOval(segment.x + 3, segment.y + 12, 4, 4);
                    g.fillOval(segment.x + 13, segment.y + 12, 4, 4);
                }
            } else {
                // 蛇身 - 渐变绿色
                int alpha = Math.max(50, 255 - (i * 10));
                g.setColor(new Color(0, alpha, 0));
                g.fillRect(segment.x, segment.y, UNIT_SIZE, UNIT_SIZE);

                // 蛇身边框
                g.setColor(Color.DARK_GRAY);
                g.drawRect(segment.x, segment.y, UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    private void drawScore(Graphics g) {
        // 当前分数
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("分数: " + score, 10, g.getFont().getSize());

        // 最高分
        g.drawString("最高分: " + highScore, 10, g.getFont().getSize() * 2 + 5);

        // 游戏说明
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("使用方向键控制蛇的移动", 10, BOARD_HEIGHT - 30);
        g.drawString("吃红色食物获得分数", 10, BOARD_HEIGHT - 15);
    }

    public void newFood() {
        int x = random.nextInt(BOARD_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int y = random.nextInt(BOARD_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        food = new Point(x, y);

        // 确保食物不会出现在蛇身上
        while (snake.contains(food)) {
            x = random.nextInt(BOARD_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            y = random.nextInt(BOARD_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            food = new Point(x, y);
        }
    }

    public void move() {
        Point newHead = new Point(snake.get(0));

        switch (direction) {
            case 'U':
                newHead.y -= UNIT_SIZE;
                break;
            case 'D':
                newHead.y += UNIT_SIZE;
                break;
            case 'L':
                newHead.x -= UNIT_SIZE;
                break;
            case 'R':
                newHead.x += UNIT_SIZE;
                break;
        }

        snake.add(0, newHead);

        // 检查是否吃到食物
        if (newHead.equals(food)) {
            score++;
            newFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    public void checkFood() {
        // 这个方法在move()中已经处理了
    }

    public void checkCollisions() {
        Point head = snake.get(0);

        // 检查蛇头是否撞到身体
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                running = false;
            }
        }

        // 检查蛇头是否撞到边界
        if (head.x < 0 || head.x >= BOARD_WIDTH || head.y < 0 || head.y >= BOARD_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
            if (score > highScore) {
                highScore = score;
            }
        }
    }

    public void gameOver(Graphics g) {
        // 游戏结束分数
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("分数: " + score, (BOARD_WIDTH - metrics1.stringWidth("分数: " + score)) / 2,
                g.getFont().getSize());

        // 游戏结束文本
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("游戏结束", (BOARD_WIDTH - metrics2.stringWidth("游戏结束")) / 2,
                BOARD_HEIGHT / 2);

        // 重新开始提示
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("按空格键重新开始", (BOARD_WIDTH - metrics3.stringWidth("按空格键重新开始")) / 2,
                BOARD_HEIGHT / 2 + 60);

        // 最高分显示
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics4 = getFontMetrics(g.getFont());
        g.drawString("最高分: " + highScore, (BOARD_WIDTH - metrics4.stringWidth("最高分: " + highScore)) / 2,
                BOARD_HEIGHT / 2 + 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!running) {
                    score = 0;
                    startGame();
                }
                break;
            case KeyEvent.VK_P:
                // 暂停/恢复游戏
                if (running) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("贪食蛇游戏");
        SnakeGame game = new SnakeGame();

        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // 显示游戏说明
        JOptionPane.showMessageDialog(frame,
                "欢迎来到贪食蛇游戏！\n\n" +
                        "🎮 使用方向键控制蛇的移动\n" +
                        "🍎 吃红色食物获得分数\n" +
                        "⚠️ 不要撞到墙壁或自己的身体\n" +
                        "🔄 游戏结束后按空格键重新开始\n" +
                        "⏸️ 按P键暂停/恢复游戏\n\n" +
                        "祝你游戏愉快！",
                "游戏说明",
                JOptionPane.INFORMATION_MESSAGE);
    }
}