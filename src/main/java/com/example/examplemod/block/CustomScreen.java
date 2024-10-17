package com.example.examplemod.block;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.http.HttpApi;
import com.example.examplemod.http.JokeApiCallback;
import com.example.examplemod.http.JokeEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.TextTable;
import net.minecraftforge.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomScreen extends Screen {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ExampleMod.MODID, "textures/gui/custom_gui.png");
    private EditBox textField;
    private TextComponent setupLabel;
    private TextComponent punchlineLabel;
    private Button button;

    public CustomScreen() {
        super(new TextComponent("Custom GUI"));
    }

    // GUIの初期化処理
    @Override
    protected void init() {
        // テキストフィールドを作成し、位置とサイズを設定
        textField = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 50, 200, 20, new TextComponent("Enter text"));
        setupLabel = new TextComponent("setup");
        punchlineLabel = new TextComponent("punchline");

        // ボタンを作成し、位置、サイズ、ラベル、クリック時の動作を設定
        button = new Button(this.width / 2 - 50, this.height / 2, 100, 20, new TextComponent("Submit"), (onPress) -> {
            String inputText = textField.getValue();  // テキストフィールドの値を取得
            Player player = Minecraft.getInstance().player;  // プレイヤーを取得
            // プレイヤーにメッセージを送信
            player.sendMessage(new TranslatableComponent("YOUR INPUT WAS: " + inputText), player.getUUID());

            HttpApi httpApi = new HttpApi();

            httpApi.getJoke(Integer.parseInt(inputText), new JokeApiCallback(){
                @Override
                public void onResponse(JokeEntity jokeEntity) {
                    if (player != null) {
                        LOGGER.info("Joke: " + jokeEntity.getSetup() + " " + jokeEntity.getPunchline());
                        setupLabel = new TextComponent(jokeEntity.getSetup());
                        punchlineLabel = new TextComponent(jokeEntity.getPunchline());
                    }
                }
                @Override
                public void onFailure() {
                    System.out.println("APIへのリクエスト中にエラーが発生しました。");
                }
            });
        });

        // テキストフィールドとボタンをGUIに追加
        this.addRenderableWidget(textField);
        this.addRenderableWidget(button);
        this.setInitialFocus(textField);  // テキストフィールドにフォーカスを設定
    }

    // GUIの描画処理
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        // 背景を描画
        this.renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);  // 背景テクスチャを設定
        int x = (this.width - 256) / 2;  // 背景のX位置を計算
        int y = (this.height - 166) / 2; // 背景のY位置を計算
        this.blit(pPoseStack, x, y, 0, 0, 256, 166);  // 背景画像を描画

        this.font.draw(pPoseStack, setupLabel, this.width / 2 - 100, this.height / 2 - 70, 0x080808);  // タイトルを描画（X座標、Y座標、色
        this.font.draw(pPoseStack, punchlineLabel, this.width / 2 - 100, this.height / 2 - 10, 0x080808);  // タイトルを描画（X座標、Y座標、色

        // 他のコンポーネントを描画
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        textField.render(pPoseStack, pMouseX, pMouseY, pPartialTick);  // テキストフィールドの描画
        button.render(pPoseStack, pMouseX, pMouseY, pPartialTick);      // ボタンの描画
    }

    // ゲームを止めるかどうかを指定（falseにするとゲームはとまらない）
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // tickごとに呼び出されるメソッド
    @Override
    public void tick() {
        textField.tick();  // テキストフィールドを更新
    }
}
