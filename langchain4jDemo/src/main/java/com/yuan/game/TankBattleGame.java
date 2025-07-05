package com.yuan.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class TankBattleGame extends JPanel implements ActionListener, KeyListener {
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int TANK_SIZE = 30;
    private static final int BULLET_SIZE = 5;
    private static final int TANK_SPEED = 3;
    private static final int BULLET_SPEED = 5;
    
    private PlayerTank player;
    private ArrayList<EnemyTank> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Wall> walls;
    private Timer gameTimer;
    private boolean[] keys;
    private Random random;
    private int score;
    private boolean gameRunning;
    
    public TankBattleGame() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        
        keys = new boolean[256];
        random = new Random();
        score = 0;
        gameRunning = true;
        
        initializeGame();
        
        gameTimer = new Timer(16, this); // ~60 FPS
        gameTimer.start();
    }
    
    private void initializeGame() {
        // 初始化玩家坦克
        player = new PlayerTank(GAME_WIDTH / 2, GAME_HEIGHT - 50);
        
        // 初始化敌方坦克
        enemies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            enemies.add(new EnemyTank(50 + i * 200, 50));
        }
        
        // 初始化子弹列表
        bullets = new ArrayList<>();
        
        // 初始化墙壁
        walls = new ArrayList<>();
        createWalls();
    }
    
    private void createWalls() {
        // 创建一些墙壁作为障碍物
        walls.add(new Wall(200, 200, 100, 20));
        walls.add(new Wall(500, 300, 20, 100));
        walls.add(new Wall(300, 450, 150, 20));
        walls.add(new Wall(100, 350, 80, 20));
        walls.add(new Wall(600, 150, 20, 120));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!gameRunning) {
            drawGameOver(g);
            return;
        }
        
        // 绘制墙壁
        g.setColor(Color.GRAY);
        for (Wall wall : walls) {
            g.fillRect(wall.x, wall.y, wall.width, wall.height);
        }
        
        // 绘制玩家坦克
        player.draw(g);
        
        // 绘制敌方坦克
        for (EnemyTank enemy : enemies) {
            enemy.draw(g);
        }
        
        // 绘制子弹
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        
        // 绘制分数
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("分数: " + score, 10, 25);
        g.drawString("敌方坦克: " + enemies.size(), 10, 45);
        
        // 绘制控制说明
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("WASD移动, 空格键射击", 10, GAME_HEIGHT - 10);
    }
    
    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String gameOverText = "游戏结束";
        String scoreText = "最终分数: " + score;
        
        int x1 = (GAME_WIDTH - fm.stringWidth(gameOverText)) / 2;
        int y1 = GAME_HEIGHT / 2 - 50;
        g.drawString(gameOverText, x1, y1);
        
        g.setFont(new Font("Arial", Font.BOLD, 24));
        fm = g.getFontMetrics();
        int x2 = (GAME_WIDTH - fm.stringWidth(scoreText)) / 2;
        int y2 = GAME_HEIGHT / 2 + 20;
        g.drawString(scoreText, x2, y2);
        
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        fm = g.getFontMetrics();
        String restartText = "按 R 键重新开始";
        int x3 = (GAME_WIDTH - fm.stringWidth(restartText)) / 2;
        int y3 = GAME_HEIGHT / 2 + 60;
        g.drawString(restartText, x3, y3);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameRunning) return;
        
        // 处理玩家输入
        handleInput();
        
        // 更新游戏对象
        updateBullets();
        updateEnemies();
        
        // 检查碰撞
        checkCollisions();
        
        // 检查胜利条件
        if (enemies.isEmpty()) {
            spawnNewEnemies();
        }
        
        repaint();
    }
    
    private void handleInput() {
        if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
            player.moveUp();
        }
        if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
            player.moveDown();
        }
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
            player.moveLeft();
        }
        if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
            player.moveRight();
        }
        
        // 边界检查
        if (player.x < 0) player.x = 0;
        if (player.x > GAME_WIDTH - TANK_SIZE) player.x = GAME_WIDTH - TANK_SIZE;
        if (player.y < 0) player.y = 0;
        if (player.y > GAME_HEIGHT - TANK_SIZE) player.y = GAME_HEIGHT - TANK_SIZE;
    }
    
    private void updateBullets() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();
            
            // 移除越界的子弹
            if (bullet.x < 0 || bullet.x > GAME_WIDTH || 
                bullet.y < 0 || bullet.y > GAME_HEIGHT) {
                bulletIterator.remove();
            }
        }
    }
    
    private void updateEnemies() {
        for (EnemyTank enemy : enemies) {
            enemy.update();
            
            // 敌方坦克随机射击
            if (random.nextInt(100) < 2) { // 2% 概率射击
                bullets.add(new Bullet(enemy.x + TANK_SIZE/2, enemy.y + TANK_SIZE, 
                                     0, BULLET_SPEED, false));
            }
        }
    }
    
    private void checkCollisions() {
        // 检查子弹与坦克的碰撞
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            
            // 检查子弹与墙壁碰撞
            boolean hitWall = false;
            for (Wall wall : walls) {
                if (bullet.intersects(wall)) {
                    bulletIterator.remove();
                    hitWall = true;
                    break;
                }
            }
            if (hitWall) continue;
            
            if (bullet.isPlayerBullet) {
                // 玩家子弹击中敌方坦克
                Iterator<EnemyTank> enemyIterator = enemies.iterator();
                while (enemyIterator.hasNext()) {
                    EnemyTank enemy = enemyIterator.next();
                    if (bullet.intersects(enemy)) {
                        enemyIterator.remove();
                        bulletIterator.remove();
                        score += 100;
                        break;
                    }
                }
            } else {
                // 敌方子弹击中玩家
                if (bullet.intersects(player)) {
                    gameRunning = false;
                    break;
                }
            }
        }
    }
    
    private void spawnNewEnemies() {
        // 生成新的敌方坦克
        for (int i = 0; i < 3; i++) {
            enemies.add(new EnemyTank(50 + i * 200, 50));
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keys.length) {
            keys[keyCode] = true;
        }
        
        if (keyCode == KeyEvent.VK_SPACE) {
            // 玩家射击
            bullets.add(new Bullet(player.x + TANK_SIZE/2, player.y, 
                                 0, -BULLET_SPEED, true));
        }
        
        if (keyCode == KeyEvent.VK_R && !gameRunning) {
            // 重新开始游戏
            gameRunning = true;
            score = 0;
            initializeGame();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keys.length) {
            keys[keyCode] = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    // 内部类定义
    class PlayerTank {
        int x, y;
        
        public PlayerTank(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public void moveUp() { y -= TANK_SPEED; }
        public void moveDown() { y += TANK_SPEED; }
        public void moveLeft() { x -= TANK_SPEED; }
        public void moveRight() { x += TANK_SPEED; }
        
        public void draw(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, TANK_SIZE, TANK_SIZE);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x + 5, y - 5, 5, 10); // 炮管
            g.fillRect(x + 15, y - 5, 5, 10);
        }
        
        public boolean intersects(Bullet bullet) {
            return bullet.x >= x && bullet.x <= x + TANK_SIZE &&
                   bullet.y >= y && bullet.y <= y + TANK_SIZE;
        }
    }
    
    class EnemyTank {
        int x, y;
        int direction;
        int moveCounter;
        
        public EnemyTank(int x, int y) {
            this.x = x;
            this.y = y;
            this.direction = random.nextInt(4);
            this.moveCounter = 0;
        }
        
        public void update() {
            moveCounter++;
            
            // 每60帧改变一次方向
            if (moveCounter % 60 == 0) {
                direction = random.nextInt(4);
            }
            
            // 移动
            switch (direction) {
                case 0: y -= TANK_SPEED; break; // 上
                case 1: y += TANK_SPEED; break; // 下
                case 2: x -= TANK_SPEED; break; // 左
                case 3: x += TANK_SPEED; break; // 右
            }
            
            // 边界检查
            if (x < 0 || x > GAME_WIDTH - TANK_SIZE) {
                direction = (direction == 2) ? 3 : 2;
                x = Math.max(0, Math.min(GAME_WIDTH - TANK_SIZE, x));
            }
            if (y < 0 || y > GAME_HEIGHT - TANK_SIZE) {
                direction = (direction == 0) ? 1 : 0;
                y = Math.max(0, Math.min(GAME_HEIGHT - TANK_SIZE, y));
            }
        }
        
        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, TANK_SIZE, TANK_SIZE);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x + 10, y + 25, 10, 8); // 炮管
        }
        
        public boolean intersects(Bullet bullet) {
            return bullet.x >= x && bullet.x <= x + TANK_SIZE &&
                   bullet.y >= y && bullet.y <= y + TANK_SIZE;
        }
    }
    
    class Bullet {
        int x, y;
        int dx, dy;
        boolean isPlayerBullet;
        
        public Bullet(int x, int y, int dx, int dy, boolean isPlayerBullet) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.isPlayerBullet = isPlayerBullet;
        }
        
        public void update() {
            x += dx;
            y += dy;
        }
        
        public void draw(Graphics g) {
            g.setColor(isPlayerBullet ? Color.YELLOW : Color.ORANGE);
            g.fillOval(x, y, BULLET_SIZE, BULLET_SIZE);
        }
        
        public boolean intersects(PlayerTank tank) {
            return x >= tank.x && x <= tank.x + TANK_SIZE &&
                   y >= tank.y && y <= tank.y + TANK_SIZE;
        }
        
        public boolean intersects(EnemyTank tank) {
            return x >= tank.x && x <= tank.x + TANK_SIZE &&
                   y >= tank.y && y <= tank.y + TANK_SIZE;
        }
        
        public boolean intersects(Wall wall) {
            return x >= wall.x && x <= wall.x + wall.width &&
                   y >= wall.y && y <= wall.y + wall.height;
        }
    }
    
    class Wall {
        int x, y, width, height;
        
        public Wall(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("坦克大战");
        TankBattleGame game = new TankBattleGame();
        
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}