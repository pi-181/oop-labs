package com.demkom58.lab14.graph;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageContainerEntity extends ImageEntity {
    protected final Color textColor;
    protected int count;

    public ImageContainerEntity(String classpathImage, Dimension location, Dimension size, int z, Color textColor) {
        super(classpathImage, location, size, z);
        this.textColor = textColor;
    }

    public ImageContainerEntity(BufferedImage image, Dimension location, Dimension size, int z, Color textColor) {
        super(image, location, size, z);
        this.textColor = textColor;
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
        graphics.setColor(textColor);

        final Dimension size = super.size;
        final Dimension location = super.location;
        graphics.drawString(String.valueOf(count), location.width + (size.width / 2) - 5, location.height + (size.height / 2) + 5);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int count) {
        this.count += count;
    }
}
