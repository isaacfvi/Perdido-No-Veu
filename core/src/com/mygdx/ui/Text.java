package com.mygdx.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

public class Text {

    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();
    private Vector2 position;

    public Text(String label, BitmapFont font, float x, float y) {
        this.font = font;
        position = new Vector2(x, y);

        layout.setText(font, label);
    }

    public void draw(Batch batch){
        font.draw(batch, layout, position.x, position.y);
    }
}
