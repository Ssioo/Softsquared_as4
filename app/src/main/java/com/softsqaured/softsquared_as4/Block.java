package com.softsqaured.softsquared_as4;

import android.graphics.RectF;

public class Block {
    public int x;
    public int y;
    public int width;
    public int height;
    public RectF rt;
    public int life;

    public Block(int x, int y, int width, int height, int life) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.life = life;

        rt = new RectF(x, y, x + width, y + height);
    }

    public boolean isbreakBlock() {
        if (life > 0) {
            return false;
        } else {
            return true;
        }
    }

}
