package com.demkom58.lab14.graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class TestGraph extends JFrame {
    private JPanel rootPanel;

    private final URL url = TestGraph.class.getResource("/graal.png");
    private final JPanel panel = new JPanel() {
        public void paintComponent(Graphics g) {
            final BufferedImage img;
            try {
                img = ImageIO.read(url);
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }

            super.paintComponent(g);
            Graphics2D g2d =(Graphics2D) g;
            Image scaledImg = img.getScaledInstance(getWidth(), getHeight(),Image.SCALE_SMOOTH);
            g2d.drawImage(scaledImg, 0, 0, this);
        }
    };


    public TestGraph() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("TestGraph");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                testGraph();
            }
        });
    }

    public void testGraph() {
        System.out.println("This is testGraph");
//        final Container container = getContentPane();
//        final Graphics2D graphics = (Graphics2D) container.getGraphics();
//
//        final int width = container.getWidth();
//        final int height = container.getHeight();
//
//        graphics.setBackground(Color.YELLOW);
//        graphics.clearRect(0, 0, width, height);
//        graphics.setColor(Color.BLUE);
//        graphics.fillRect(0, 0, width, height / 2);
//
//        int size = height / 4;
//        int top = height / 16;
//        int left = width / 16;
//
//        graphics.setColor(Color.ORANGE);
//        graphics.fillOval(left, top, size, size);
//
//        graphics.setColor(Color.YELLOW.darker());
//        graphics.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,
//                BasicStroke.JOIN_MITER, 1, new float[]{ 10, 5 }, 0));
//
//        int x1, x2, y1, y2, r1 = size / 2, r2 = width * 2 / 3;
//        for (double fi = 0; fi < 2 * Math.PI; fi += Math.PI / 12) {
//            x1 = (int) (left + size / 2 + r1 * Math.cos(fi));
//            y1 = (int) (top + size / 2 + r1 * Math.sin(fi));
//            x2 = (int) (left + size / 2 + r2 * Math.cos(fi));
//            y2 = (int) (top + size / 2 + r2 * Math.sin(fi));
//            graphics.drawLine(x1, y1, x2, y2);
//        }
//
//        int penSize = size / 16;
//        graphics.setStroke(new BasicStroke(penSize));
//        graphics.setColor(Color.ORANGE.brighter());
//        graphics.drawOval(left, top, size, size);

//        testShape1();

//        testShape2();

//        testXOR();

//        movingRect();

//        setContentPane(panel);
    }

    private void testShape1() {
        Container container = getContentPane();
        Graphics2D graphics = (Graphics2D) container.getGraphics();

        final int width = container.getWidth();
        final int height = container.getHeight();
        graphics.clearRect(0, 0, width, height);

        GeneralPath gp = new GeneralPath();
        gp.moveTo(20, height - 20);
        gp.lineTo(40, height - 200);
        gp.lineTo(width - 20, height - 100);
        gp.closePath();
        gp.quadTo(40, height - 200, width - 20, height - 100);

        graphics.draw(gp);

    }

    private void testShape2() {
        Container container = getContentPane();
        Graphics2D graphics = (Graphics2D) container.getGraphics();

        final int width = container.getWidth();
        final int height = container.getHeight();
        graphics.clearRect(0, 0, width, height);

        GeneralPath gp = new GeneralPath();
        gp.moveTo(20, height - 20);

        gp.lineTo(20, height - 200);
        gp.lineTo(width - 20, height - 200);
        gp.lineTo(width - 20, height - 20);
        gp.closePath();

        gp.curveTo(20, height - 200, width - 20, height -200, width - 20, height - 20);
        graphics.draw(gp);
    }

    private Color altColor(Graphics2D g, Color c) {
        Color back = g.getBackground();
        int rgb = back.getRGB() ^ c.getRGB();
        return new Color(rgb);
    }

    private void testXOR() {
        Container c = getContentPane();
        Graphics2D g = (Graphics2D) c.getGraphics();
        int w = c.getWidth();
        int h = c.getHeight();
        g.clearRect(0, 0, w, h);
        int size = 50;
        int x = 0, y = (h - size) / 2, dx = 5;
        g.setXORMode(altColor(g, Color.RED));
        while (true) {
            g.fillOval(x, y, size, size);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            g.fillOval(x, y, size, size);
            x += dx;
            if (x > w - size || x <= 0 && dx < 0)
                dx = -dx;
        }
    }

    private Shape sh;
    public void movingRect() {
        Container c = getContentPane();
        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setColor(Color.GREEN);
        int w = c.getWidth();
        int h = c.getHeight();
        g.clearRect(0, 0, w, h);
        new Thread(() -> {
            // Створюємо прямокутник
            sh = new Rectangle2D.Float(0, h / 2f - h / 8f, w / 4f, h / 4f);
            while (sh.getBounds().getWidth() > 5) {
                Rectangle r = sh.getBounds();
                // Відображення
                EventQueue.invokeLater(() -> g.fill(sh));
                // Затримка у часі
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                // Відновлення фонового зображення панелі
                c.repaint(r.x, r.y, r.width, r.height);
                // Формування афінного перетворення
                AffineTransform at = new AffineTransform();
                at.translate(40, 0);
                at.scale(0.9, 0.95);
                at.rotate(0.08, r.x + r.width / 2f, r.y + r.height / 2f);
                // Перетворення зображення
                sh = at.createTransformedShape(sh);
            }
        }).start();
    }


}
