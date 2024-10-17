package com.example.examplemod.block;

import com.example.examplemod.http.HttpApi;
import com.example.examplemod.http.JokeApiCallback;
import com.example.examplemod.http.JokeEntity;
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

public class WeatherBlock extends Block {
    public WeatherBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT));
    }


    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        HttpApi api = new HttpApi();

        // HTTPリクエストを実行
        api.getJokeRandom(new JokeApiCallback() {
            @Override
            public void onResponse(JokeEntity jokeEntity) {
                if (pPlacer != null) {
                    pPlacer.sendMessage(new TextComponent(jokeEntity.getSetup()), pPlacer.getUUID());
                }
            }

            @Override
            public void onFailure() {
                // レスポンスが失敗した場合の処理
                System.out.println("APIへのリクエスト中にエラーが発生しました。");
            }
        });
    }
}
