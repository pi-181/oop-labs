package com.demkom58.lab14.graph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;

public class MovingShapeEntity implements Drawable, Updatable, Moving<MovingShapeEntity> {
    protected Shape source;
    protected Shape shape;
    protected Color color;

    protected float speed;
    protected float progress = 0;
    protected boolean done = false;

    protected boolean dead = false;

    protected Point2D target;
    protected float distance;
    protected Consumer<MovingShapeEntity> onDone;

    protected int z;
    protected int priority;

    public MovingShapeEntity(Shape shape, Color color, Point2D target,
                             Consumer<MovingShapeEntity> onDone, float speed) {
        this(shape, color, target, onDone, speed, 0, 0);
    }

    public MovingShapeEntity(Shape shape, Color color, Point2D target,
                             Consumer<MovingShapeEntity> onDone, float speed, int z, int priority) {
        this.shape = shape;
        this.source = shape;

        this.color = color;
        this.speed = speed;

        this.z = z;
        this.priority = priority;

        setTarget(target);
        setOnDone(onDone);
    }

    @Override
    public void update(Container container) {
        if (done)
            return;

        if (progress < 1) {
            progress += speed / distance;
            if (progress > 1)
                progress = 1;

            setProgress(progress);
            return;
        }

        done = true;
        onDone.accept(this);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setProgress(float progress) {
        final Rectangle2D bounds = source.getBounds2D();
        final double fromX = bounds.getX();
        final double fromY = bounds.getY();

        final double currentX = lerp(fromX, target.getX(), progress) - fromX;
        final double currentY = lerp(fromY, target.getY(), progress) - fromY;

        final AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(currentX, currentY);

        shape = affineTransform.createTransformedShape(source);
    }

    @Override
    public float getProgress() {
        return progress;
    }

    @Override
    public void setTarget(Point2D target) {
        this.target = target;
        this.progress = 0;
        this.source = shape;

        final Rectangle2D bounds = source.getBounds2D();
        this.distance = (float) Math.sqrt(
                Math.pow(bounds.getX() - target.getX(), 2) + Math.pow(bounds.getY() - target.getY(), 2)
        );

        this.done = false;
    }

    @Override
    public Point2D getTarget() {
        return target;
    }

    public Point2D getCenterPosition() {
        final Rectangle2D bounds = shape.getBounds2D();
        return new Point2D((float) bounds.getCenterX(), (float) bounds.getCenterY());
    }

    public Point2D getPosition() {
        final Rectangle2D bounds = shape.getBounds2D();
        return new Point2D((float) bounds.getX(), (float) bounds.getY());
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public Consumer<MovingShapeEntity> getOnDone() {
        return onDone;
    }

    @Override
    public void setOnDone(Consumer<MovingShapeEntity> onDone) {
        this.onDone = onDone;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fill(shape);
        graphics.draw(shape);
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void kill() {
        dead = true;
    }

    public static double lerp(double a, double b, double f) {
        return a + f * (b - a);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        this.source = shape;
    }
}