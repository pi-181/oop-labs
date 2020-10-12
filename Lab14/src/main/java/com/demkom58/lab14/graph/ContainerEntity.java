package com.demkom58.lab14.graph;

import java.awt.*;

public class ContainerEntity extends ShapeEntity {
    private int count;

    public ContainerEntity(Shape shape, Color color) {
        super(shape, color, 0);
        setCount(0);
    }

    public ContainerEntity(Shape shape, Color color, int z) {
        super(shape, color, z);
        setCount(0);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fill(shape);
        graphics.draw(shape);

        if (text != null) {
            final Rectangle bounds = shape.getBounds();
            graphics.setColor(textColor);
            graphics.drawString(text, bounds.x + (bounds.width / 2) - 5, bounds.y + (bounds.height / 2) + 5);
        }

    }

    public void setCount(int count) {
        this.count = count;
        setText(String.valueOf(count));
    }

    public void addCount(int count) {
        setCount(getCount() + count);
    }

    public int getCount() {
        return count;
    }
}