package com.demkom58.lab14.graph;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TestGraph extends JFrame {
    private final Random random = new Random();
    private final EntityManager entityManager = new EntityManager();
    private JPanel rootPanel;

    private ImageContainerEntity finalPlanet;
    private ImageContainerEntity bridgePlanet;
    private ImageEntity asteroidEntity;
    private ImageEntity stationEntity;
    private ImageEntity earthEntity;

    public TestGraph() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setTitle("TestGraph");

        EventQueue.invokeLater(this::init);
    }

    public void init() {
        final int width = rootPanel.getWidth();
        final int height = rootPanel.getHeight();

        final Graphics graphics = rootPanel.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.clearRect(0, 0, width, height);

        finalPlanet = new ImageContainerEntity("/planet.png",
                new Dimension(width - 100, height - 100), new Dimension(70, 50), 1, Color.BLACK);
        entityManager.add(finalPlanet);

        bridgePlanet = new ImageContainerEntity("/planet.png", new Dimension(width - 100, 100), new Dimension(70, 50), 1, Color.BLACK);
        entityManager.add(bridgePlanet);

        asteroidEntity = new ImageEntity("/asteroid.png", new Dimension(width / 2 - 50, height / 2 - 100), new Dimension(50, 50), 1);
        entityManager.add(asteroidEntity);

        stationEntity = new ImageEntity("/station.png", new Dimension(width / 2 - 50, height / 2 + 100), new Dimension(50, 50), 1);
        entityManager.add(stationEntity);

        earthEntity = new ImageEntity("/earth.png",
                new Dimension(100, (int) (height / 2f - 50)), new Dimension(50, 50), 1);
        entityManager.add(earthEntity);

        Thread handlerThread = new Thread(this::handle);
        handlerThread.setDaemon(true);
        handlerThread.start();

        Thread spawnerThread = new Thread(this::spawn);
        spawnerThread.setDaemon(true);
        spawnerThread.start();
    }

    private void handle() {
        while (true) {
            entityManager.shrink();
            entityManager.getUpdatables().forEach(u -> u.update(rootPanel));
            rootPanel.repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setBackground(new Color(28, 26, 57));
        g2.clearRect(0, 0, getWidth(), getHeight());
        entityManager.getDrawables().forEach(d -> d.draw(g2));
    }

    private void spawn() {
        final Point2D earthPos = earthEntity.getPosition();
        final Point2D stationPos = stationEntity.getPosition();
        final Point2D asteroidPos = asteroidEntity.getPosition();
        final Point2D finalPlanetPos = finalPlanet.getPosition();
        final Point2D bridgePlanetPos = bridgePlanet.getPosition();

        while (true) {
            try {
                Thread.sleep(500 + random.nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            entityManager.add(createEarthShip(earthPos, stationPos, finalPlanetPos, bridgePlanetPos));

            if (bridgePlanet.getCount() > 0 && random.nextBoolean()) {
                bridgePlanet.addCount(-1);

                final Dimension start = new Dimension((int) bridgePlanetPos.getX(), (int) bridgePlanetPos.getY());
                entityManager.add(new MovingImageEntity("/rocket.png", start,
                        new Dimension(20, 20), 0, 0, asteroidPos, a -> {
                    if (a.target.equals(finalPlanetPos)) {
                        a.kill();
                        finalPlanet.addCount(1);
                        return;
                    }

                    a.setTarget(finalPlanetPos);
                }, 2.0f));
            }
        }
    }

    public MovingImageEntity createEarthShip(Point2D from, Point2D handler, Point2D storage, Point2D wasteStorage) {
        return new MovingImageEntity("/rocket.png", from.dimension(),
                new Dimension(20, 20), 0, 0, handler, a -> {
            if (a.target.equals(storage)) {
                a.kill();
                finalPlanet.addCount(1);
                return;
            }

            a.setTarget(storage);
            if (random.nextFloat() > 0.8f)
                entityManager.add(createStationShip(a.getPosition(), wasteStorage));
        }, 2f);
    }

    public MovingImageEntity createStationShip(Point2D position, Point2D target) {
        return new MovingImageEntity("/rocket.png", position.dimension(),
                new Dimension(20, 20), 0, 0, target, a -> {
            a.kill();
            bridgePlanet.addCount(1);
        }, 2f);
    }


    private void createUIComponents() {
        rootPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                draw((Graphics2D) g);
            }
        };
    }
}
