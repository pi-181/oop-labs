package com.demkom58.lab14.graph;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class TestGraph extends JFrame {
    private final Random random = new Random();
    private final EntityManager entityManager = new EntityManager();
    private JPanel rootPanel;

    private Container container;
    private Graphics2D graphics;

    private ContainerEntity resultContainer;
    private ContainerEntity wasteContainer;
    private StaticEntity wasteHandler;
    private StaticEntity inputHandler;
    private StaticEntity spawner;

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
        container = getContentPane();
        graphics = (Graphics2D) container.getGraphics();

        final int width = container.getWidth();
        final int height = container.getHeight();

        graphics.setColor(Color.BLACK);
        graphics.clearRect(0, 0, width, height);

        resultContainer = new ContainerEntity(new Rectangle2D.Float(width - 100, height - 100, 50, 50), Color.BLACK);
        resultContainer.setTextColor(Color.WHITE);
        entityManager.add(resultContainer);

        wasteContainer = new ContainerEntity(new Rectangle2D.Float(width - 100, 100, 50, 50), Color.BLACK);
        wasteContainer.setTextColor(Color.WHITE);
        entityManager.add(wasteContainer);

        wasteHandler = new StaticEntity(new Rectangle2D.Float(width / 2f - 50, height / 2f - 100, 50, 50), Color.BLACK);
        wasteHandler.setText("Waste Handler");
        entityManager.add(wasteHandler);

        inputHandler = new StaticEntity(new Rectangle2D.Float(width / 2f - 50, height / 2f + 100, 50, 50), Color.BLACK);
        inputHandler.setText("Wood Handler");
        entityManager.add(inputHandler);

        spawner = new StaticEntity(new Rectangle2D.Float(100, height / 2f - 50, 50, 50), Color.BLUE);
        entityManager.add(spawner);

        Thread handlerThread = new Thread(this::handle);
        handlerThread.setDaemon(true);
        handlerThread.start();

        Thread spawnerThread = new Thread(this::spawn);
        spawnerThread.setDaemon(true);
        spawnerThread.start();
    }

    private void handle() {
        while (true) {
            graphics.clearRect(0, 0, getWidth(), getHeight());
            entityManager.shrink();

            entityManager.getUpdatables().forEach(u -> u.update(container));
            entityManager.getDrawables().forEach(d -> d.draw(graphics));

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void spawn() {
        final Point2D spawnPos = spawner.getPosition();
        final Point2D handlerPos = inputHandler.getPosition();
        final Point2D wasteHandlerPos = wasteHandler.getPosition();
        final Point2D resPos = resultContainer.getPosition();
        final Point2D wastePos = wasteContainer.getPosition();

        while (true) {
            try {
                Thread.sleep(500 + random.nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            entityManager.add(createMaterial(spawnPos, handlerPos, resPos, wastePos));
            System.out.println("Spawned!");

            if (wasteContainer.getCount() > 0 && random.nextBoolean()) {
                wasteContainer.addCount(-1);

                final RoundRectangle2D.Float start =
                        new RoundRectangle2D.Float(wastePos.getX(), wastePos.getY(), 20, 20, 10, 10);

                entityManager.add(new DynamicEntity(start, Color.RED, wasteHandlerPos, a -> {
                    if (a.target.equals(resPos)) {
                        a.kill();
                        resultContainer.addCount(1);
                        return;
                    }

                    a.setShape(createMaterialShape(a.getPosition()));
                    a.setTarget(resPos);
                }, 2.0f));
            }
        }
    }

    public DynamicEntity createMaterial(Point2D from, Point2D handler, Point2D storage, Point2D wasteStorage) {
        final Shape spawned = createMaterialShape(from);
        return new DynamicEntity(spawned, Color.RED, handler, a -> {
            if (a.target.equals(storage)) {
                a.kill();
                resultContainer.addCount(1);
                return;
            }

            a.setTarget(storage);
            if (random.nextFloat() > 0.8f)
                entityManager.add(createWaste(a.getPosition(), wasteStorage));
        }, 2f);
    }

    public DynamicEntity createWaste(Point2D position, Point2D target) {
        final Shape spawned = new RoundRectangle2D.Float(position.getX(), position.getY(), 20, 20, 10, 10);
        return new DynamicEntity(spawned, Color.RED, target, a -> {
            a.kill();
            wasteContainer.addCount(1);
        }, 2f);
    }

    private Shape createMaterialShape(Point2D pos) {
        return random.nextBoolean()
                ? new Ellipse2D.Float(pos.getX(), pos.getY(), 20, 30)
                : new Rectangle2D.Float(pos.getX(), pos.getY(), 20, 30);
    }

}
