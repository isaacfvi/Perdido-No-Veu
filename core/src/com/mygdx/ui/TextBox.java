package com.mygdx.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;

public class TextBox {

    private Rectangle bounds;
    private StringBuilder text = new StringBuilder();

    private boolean focused = false;
    private boolean showCursor = true;
    private float blinkTimer = 0f;

    private int maxLength = 10;

    private BitmapFont font;
    private NinePatch background;
    private GlyphLayout layout = new GlyphLayout();

    public TextBox(BitmapFont font, NinePatch background, Rectangle bounds) {
        this.font = font;
        this.background = background;
        this.bounds = bounds;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
        blinkTimer = 0;
        showCursor = true;
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    public void type(char c) {
        if (!focused) return;
        if (text.length() >= maxLength) return;
        if (Character.isISOControl(c)) return;

        text.append(c);
    }

    public void backspace() {
        if (!focused) return;
        if (text.length() == 0) return;

        text.deleteCharAt(text.length() - 1);
    }

    public void update(float delta) {
        if (!focused) return;

        blinkTimer += delta;
        if (blinkTimer > 0.5f) {
            blinkTimer = 0;
            showCursor = !showCursor;
        }
    }

    public void draw(Batch batch) {
        background.draw(batch, bounds.x, bounds.y, bounds.width, bounds.height);

        layout.setText(font, text.toString());
        float textX = bounds.x + bounds.x * 0.05f;
        float textY = bounds.y + bounds.height / 2f + layout.height / 2f;

        font.draw(batch, layout, textX, textY);

        if (focused && showCursor) {
            float cursorX = textX + layout.width + 2;
            font.draw(batch, "|", cursorX, textY);
        }
    }

    // ---------- VALOR ----------
    public Long getSeedOrNull() {
        if (text.isEmpty()) return null;
        return Long.parseLong(text.toString());
    }
}
