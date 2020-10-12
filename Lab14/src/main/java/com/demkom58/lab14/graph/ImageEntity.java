package com.demkom58.lab14.graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageEntity implements Drawable {
    protected boolean dead = false;
    protected int z = 0;

    protected BufferedImage image;
    protected Dimension location;
    protected Dimension size;

    public ImageEntity(String classpathImage, Dimension location, Dimension size, int z) {
        try {
            this.z = z;
            this.image = ImageIO.read(getClass().getResourceAsStream(classpathImage));
            this.location = location;
            this.size = size;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageEntity(BufferedImage image, Dimension location, Dimension size, int z) {
        this.z = z;
        this.image = image;
        this.location = location;
        this.size = size;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(image, location.width, location.height, size.width, size.height, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Dimension getLocation() {
        return location;
    }

    public Point2D getPosition() {
        return new Point2D((float) location.width, (float) location.height);
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
        this.dead = true;
    }
}
