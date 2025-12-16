package com.mygdx.ui;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Button {

    private Rectangle bounds;
    private String label;
    private NinePatch patch;
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();

    private float textX;
    private float textY;

    private Rectangle originalBounds;
    private float scale = 1f;
    private float targetScale = 1f;

    private static final float HOVER_SCALE = 1.1f;
    private static final float SCALE_SPEED = 8f;


    public Button(BitmapFont font, NinePatch patch, String label, Rectangle bounds) {
        this.bounds = bounds;
        this.originalBounds = new Rectangle(bounds);
        this.label = label;
        this.font = font;

        calcText();
        this.patch = patch;
    }

    private void calcText() {
        layout.setText(font, label);

        textX = bounds.x + (bounds.width  - layout.width)  / 2f;
        textY = bounds.y + (bounds.height + layout.height) / 2f;
    }

    public void growButton() {
        targetScale = HOVER_SCALE;
    }

    public void ungrowButton() {
        targetScale = 1f;
    }

    public void update(float delta) {
        scale = MathUtils.lerp(scale, targetScale, delta * SCALE_SPEED);

        float newWidth  = originalBounds.width  * scale;
        float newHeight = originalBounds.height * scale;

        bounds.width  = newWidth;
        bounds.height = newHeight;

        bounds.x = originalBounds.x + (originalBounds.width  - newWidth)  / 2f;
        bounds.y = originalBounds.y + (originalBounds.height - newHeight) / 2f;

        calcText();
    }

    public void draw(Batch batch) {
        patch.draw(batch, bounds.x, bounds.y, bounds.width, bounds.height);
        font.draw(batch, layout, textX, textY);
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    public String getLabel(){
        return label;
    }
}

