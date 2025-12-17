package com.mygdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.core.Assets;
import com.mygdx.core.Consts;
import com.mygdx.utils.MeuInputProcessor;

import java.util.Random;

public class Menu implements Screen {

    private final ScreenNavigator navigator;
    private MeuInputProcessor meuInput;
    private Assets assets;
    private SpriteBatch batch;
    private BitmapFont font, labelFont;

    private NinePatch menuBackground;

    private final float bgWidth  = Gdx.graphics.getWidth() * 0.3f;
    private final float bgHeight = Gdx.graphics.getHeight() * 0.6f;
    private final float x = (Gdx.graphics.getWidth()  - bgWidth)  / 2f;
    private final float y = (Gdx.graphics.getHeight() - bgHeight) / 2f;
    private final float buttonWidth  = bgWidth * 0.3f;
    private final float buttonHeight = bgHeight * 0.15f;
    private final float margin = bgWidth * 0.05f;

    private Button[] buttons = new Button[2];
    private TextBox[] textBoxes = new TextBox[4];
    private Text[] labels = new Text[4];

    private TextBox focusedTextBox;

    public Menu(ScreenNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void show() {
        assets = new Assets();
        batch = new SpriteBatch();
        meuInput = new MeuInputProcessor();
        Gdx.input.setInputProcessor(meuInput);
        font = new BitmapFont();
        labelFont = new BitmapFont();
        labelFont.getData().setScale(1.5f);

        labelFont.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );

        labelFont.setColor(Color.BLACK);
        font.setColor(Color.BLACK);

        createButtons();
        createTextBox();
        createLabels();
        menuBackground = assets.getAtlas("Menu").createPatch("Button");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        Vector2 mouseLoc = meuInput.getMouseLoc();
        Vector2 click = meuInput.hasClicked();

        buttonProcess(delta, mouseLoc, click);
        textBoxProcess(delta, click);

        batch.begin();
        draw(batch);
        batch.end();
    }

    private void onButtonClicked(Button button) {
        switch (button.getLabel()){
            case "Iniciar":
                Long seed = textBoxes[0].getSeedOrNull();
                if (seed != null) {
                    Consts.SEED = seed.intValue();
                } else {
                    Consts.SEED = -1;
                }

                seed = textBoxes[1].getSeedOrNull();
                if (seed != null) {
                    Consts.QUANT_OBSTACLES = seed.intValue();
                } else {
                    Consts.QUANT_OBSTACLES = 40;
                }

                seed = textBoxes[2].getSeedOrNull();
                if (seed != null) {
                    Consts.QUANT_TRAPS = seed.intValue();
                } else {
                    Consts.QUANT_TRAPS = 40;
                }

                navigator.goToGame();
                break;

            case "Sair":
                navigator.exit();
                break;
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }

    private void buttonProcess(float delta, Vector2 mouseLoc, Vector2 click){
        for(Button button : buttons) {
            if(button.contains(mouseLoc.x, mouseLoc.y)){
                button.growButton();
            }
            else {
                button.ungrowButton();
            }

            if (click != null && button.contains(click.x, click.y)) {
                onButtonClicked(button);
            }


            button.update(delta);
        }
    }

    private void textBoxProcess(float delta, Vector2 click) {

        if (click != null) {
            focusedTextBox = null;

            for (TextBox box : textBoxes) {
                if (box.contains(click.x, click.y)) {
                    focusedTextBox = box;
                    box.setFocused(true);
                } else {
                    box.setFocused(false);
                }
            }
        }

        if (focusedTextBox != null) {
            char typed = meuInput.getTypedChar();
            if (typed != 0) {
                focusedTextBox.type(typed);
            }
            if (meuInput.isKeyPressed(com.badlogic.gdx.Input.Keys.BACKSPACE)) {
                focusedTextBox.backspace();
            }
        }

        for (TextBox box : textBoxes) {
            box.update(delta);
        }
    }

    private void createButtons() {
        buttons[0] = new Button(font,
                assets.getAtlas("Menu").createPatch("Button"),
                "Iniciar",
                new Rectangle(x + margin, y + margin, buttonWidth, buttonHeight));
        buttons[1] = new Button(font,
                assets.getAtlas("Menu").createPatch("Button"),
                "Sair",
                new Rectangle(x + bgWidth - buttonWidth - margin, y + margin,buttonWidth, buttonHeight));
    }

    private void createTextBox() {
        textBoxes[0] = new TextBox(
                font,
                assets.getAtlas("Menu").createPatch("Button"),
                new Rectangle(
                        x + bgWidth - buttonWidth - margin * 5,
                        y + bgHeight - buttonHeight - margin * 2,
                        bgWidth - margin * 10,
                        buttonHeight
                )
        );
        textBoxes[0].setText(new Random().nextInt());
        textBoxes[1] = new TextBox(
                font,
                assets.getAtlas("Menu").createPatch("Button"),
                new Rectangle(
                        x + bgWidth - buttonWidth - margin * 5,
                        y + bgHeight - buttonHeight - margin * 5,
                        bgWidth - margin * 10,
                        buttonHeight
                )
        );
        textBoxes[1].setText(40);
        textBoxes[2] = new TextBox(
                font,
                assets.getAtlas("Menu").createPatch("Button"),
                new Rectangle(
                        x + bgWidth - buttonWidth - margin * 5,
                        y + bgHeight - buttonHeight - margin * 8,
                        bgWidth - margin * 10,
                        buttonHeight
                )
        );
        textBoxes[2].setText(40);
        textBoxes[3] = new TextBox(
                font,
                assets.getAtlas("Menu").createPatch("Button"),
                new Rectangle(
                        x + bgWidth - buttonWidth - margin * 5,
                        y + bgHeight - buttonHeight - margin * 11,
                        bgWidth - margin * 10,
                        buttonHeight
                )
        );
        textBoxes[3].setText(1);
    }

    private void createLabels(){
       labels[0] = new Text("Seed", labelFont,
               x + margin,
               y + bgHeight - margin * 3.5f
       );
        labels[1] = new Text("Número de obstáculos", labelFont,
                x + margin,
                y + bgHeight - margin * 6.5f
        );
        labels[2] = new Text("Número de Armadilhas", labelFont,
                x + margin,
                y + bgHeight - margin * 9.5f
        );
        labels[3] = new Text("Numero de fantasmas", labelFont,
                x + margin,
                y + bgHeight - margin * 12.5f
        );
    }

    public void draw(SpriteBatch batch) {
        menuBackground.draw(batch, x, y, bgWidth, bgHeight);

        for (Button button : buttons) {
            button.draw(batch);
        }

        for (TextBox box : textBoxes) {
            box.draw(batch);
        }

        for (Text text : labels) {
            text.draw(batch);
        }
    }

}
