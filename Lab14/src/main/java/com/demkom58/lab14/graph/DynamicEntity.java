package com.demkom58.lab14.graph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;

public class DynamicEntity implements Drawable, Updatable {
    protected Shape source;
    protected Shape shape;
    protected Color color;

    protected float speed;
    protected float progress = 0;
    protected boolean done = false;

    protected boolean dead = false;

    protected Point2D target;
    protected float distance;
    protected Consumer<DynamicEntity> onDone;

    public DynamicEntity(Shape shape,
                          Color color,
                          Point2D target,
                          Consumer<DynamicEntity> onDone,
                          float speed) {
        this.shape = shape;
        this.source = shape;

        this.color = color;
        this.speed = speed;

        setTarget(target);
        setOnDone(onDone);
    }

    @Override
    public void update(Container container) {
        if (done)
            return;

        if (progress < 1) {
            progress += speed/distance;
            if (progress > 1)
                progress = 1;

            setProgress(progress);
            return;
        }

        done = true;
        onDone.accept(this);
    }

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

    public Point2D getCenterPosition() {
        final Rectangle2D bounds = shape.getBounds2D();
        return new Point2D((float) bounds.getCenterX(), (float) bounds.getCenterY());
    }

    public Point2D getPosition() {
        final Rectangle2D bounds = shape.getBounds2D();
        return new Point2D((float) bounds.getX(), (float) bounds.getY());
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setOnDone(Consumer<DynamicEntity> onDone) {
        this.onDone = onDone;
    }

    public Shape getShape() {
        return shape;
    }

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