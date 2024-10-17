package com.example.examplemod.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Properties;

public class GUIBlock extends Block {
    public GUIBlock(){
        super(BlockBehaviour.Properties.of(Material.STONE));
    }

    // ブロックが右クリックされた際に呼び出されるメソッド
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        // サーバーサイドでは通常のブロックの動作を行う
        if (!pLevel.isClientSide){
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }

        // クライアントサイドでカスタムGUI（CustomScreen）を表示
        Minecraft.getInstance().setScreen(new CustomScreen());

        // スーパークラスのuseメソッドを呼び出して通常の処理を行う
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
