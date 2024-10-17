package com.example.examplemod.block;

import com.example.examplemod.http.HttpApi;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class WeatherBlock extends Block {
    public WeatherBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT));
    }


    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        // 非同期処理を開始
        CompletableFuture.supplyAsync(() -> {
            HttpApi api = new HttpApi();
            api.baseUrl = "https://official-joke-api.appspot.com/jokes/random";

            // HTTPリクエストを実行
            return api.getData("");
        }).thenApplyAsync(jokeString -> {
            // JSON文字列をJSONObjectとしてパース
            JSONObject jsonObject = new JSONObject(jokeString);

            // 値を取得
            String type = jsonObject.getString("type");
            String setup = jsonObject.getString("setup");
            String punchline = jsonObject.getString("punchline");
            int id = jsonObject.getInt("id");

            return setup; // 取得したsetupを返す
        }).thenAccept(setup -> {
            // pPlacerがnullでない場合にメッセージを送信
            if (pPlacer != null) {
                pPlacer.sendMessage(new TextComponent(setup), pPlacer.getUUID());
            }
        }).exceptionally(ex -> {
            // エラーハンドリング
            ex.printStackTrace();
            return null;
        });
    }
}
