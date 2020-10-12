package com.demkom58.lab14.graph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class MovingImageEntity extends ImageEntity implements Updatable, Moving<MovingImageEntity> {
    private int priority;

    protected Dimension startLocation;
    protected Point2D target;
    protected Consumer<MovingImageEntity> onDone;
    protected float speed;

    protected BufferedImage originalImage;
    protected float progress = 0;
    protected boolean done = false;
    protected float distance;
    protected float angle;

    public MovingImageEntity(String classpathImage, Dimension location, Dimension size, int z, int priority,
                             Point2D target, Consumer<MovingImageEntity> onDone, float speed) {
        super(classpathImage, location, size, z);
        this.originalImage = image;
        this.startLocation = new Dimension(location);
        this.priority = priority;
        this.speed = speed;

        setTarget(target);
        setOnDone(onDone);
    }

    public MovingImageEntity(BufferedImage image, Dimension location, Dimension size, int z, int priority,
                             Point2D target, Consumer<MovingImageEntity> onDone, float speed) {
        super(image, location, size, z);
        this.startLocation = new Dimension(location);
        this.priority = priority;
        this.speed = speed;

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
    public Consumer<MovingImageEntity> getOnDone() {
        return onDone;
    }

    @Override
    public void setOnDone(Consumer<MovingImageEntity> onDone) {
        this.onDone = onDone;
    }

    @Override
    public void setProgress(float progress) {
        final double currentX = lerp(startLocation.width, target.getX(), progress);
        final double currentY = lerp(startLocation.height, target.getY(), progress);

        location = new Dimension((int) currentX, (int) currentY);
    }

    @Override
    public float getProgress() {
        return progress;
    }

    @Override
    public void setTarget(Point2D target) {
        this.target = target;
        this.progress = 0;
        this.startLocation = location;

        this.distance = (float) Math.sqrt(
                Math.pow(startLocation.width - target.getX(), 2) + Math.pow(startLocation.height - target.getY(), 2)
        );

        this.done = false;

        System.out.println(calcAngle());
        setImage(originalImage);
    }

    @Override
    public Point2D getTarget() {
        return target;
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
    public boolean isDone() {
        return done;
    }

    public float getAngle() {
        return angle;
    }

    private float calcAngle() {
        final double raw = Math.atan2(target.getY() - startLocation.height, target.getX() - startLocation.width);
        return angle = (float) (raw + Math.toRadians(90));
    }

    @Override
    public void setImage(BufferedImage image) {
        this.originalImage = image;
        this.image = rotate(originalImage, angle);
    }

    private BufferedImage rotate(BufferedImage image, float rads) {
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));

        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);

        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2f, h / 2f);
        at.rotate(rads, 0, 0);
        at.translate(-image.getWidth() / 2f, -image.getHeight() / 2f);

        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image, rotatedImage);

        return rotatedImage;
    }

    public static double lerp(double a, double b, double f) {
        return a + f * (b - a);
    }

}
