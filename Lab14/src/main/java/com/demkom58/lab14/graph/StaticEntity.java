package com.demkom58.lab14.graph;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class StaticEntity implements Drawable {
    protected final Shape shape;
    protected final Color color;
    protected String text;
    protected Color textColor = Color.BLACK;
    protected boolean dead = false;

    protected int z;

    public StaticEntity(Shape shape, Color color) {
        this(shape, color, 0);
    }

    public StaticEntity(Shape shape, Color color, int z) {
        this.shape = shape;
        this.color = color;
        this.z = z;
    }

    public StaticEntity(Shape shape, Color color, String text, Color textColor) {
        this(shape, color, text, textColor, 0);
    }

    public StaticEntity(Shape shape, Color color, String text, Color textColor, int z) {
        this.shape = shape;
        this.color = color;
        this.text = text;
        this.textColor = textColor;
        this.z = z;
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fill(shape);
        graphics.draw(shape);

        if (text != null) {
            final Rectangle bounds = shape.getBounds();
            graphics.setColor(textColor);
            graphics.drawString(text, bounds.x, bounds.y);
        }
    }

    @Override
    public int getZ() {
        return z;
    }

    public Point2D getCenterPosition() {
        final Rectangle2D bounds = shape.getBounds2D();
        return new Point2D((float) bounds.getCenterX(), (float) bounds.getCenterY());
    }

    public Point2D getPosition() {
        final Rectangle2D bounds = shape.getBounds2D();
        return new Point2D((float) bounds.getX(), (float) bounds.getY());
    }

    public Shape getShape() {
        return shape;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void kill() {
        dead = true;
    }
}