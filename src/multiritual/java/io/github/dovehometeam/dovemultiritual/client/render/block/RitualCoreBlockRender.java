package io.github.dovehometeam.dovemultiritual.client.render.block;

import com.mojang.blaze3d.MethodsReturnNonnullByDefault;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import io.github.dovehometeam.dovemultiritual.common.block.entity.RitualCoreBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RitualCoreBlockRender implements BlockEntityRenderer<RitualCoreBlockEntity> {
    public RitualCoreBlockRender(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(RitualCoreBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        RenderType triangleFanSolid = RenderType.create("triangle_fan_solid",
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.TRIANGLE_STRIP,
                256,
                false,
                false,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderType.POSITION_COLOR_SHADER)
                        .setTextureState(RenderStateShard.BLOCK_SHEET_MIPPED)
                        .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                        .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                        .setOverlayState(RenderStateShard.NO_OVERLAY)
                        .createCompositeState(false)
        );
        poseStack.mulPose(Axis.XP.rotationDegrees(be.rotateX));
        poseStack.mulPose(Axis.YP.rotationDegrees(be.rotateY));
        poseStack.mulPose(Axis.ZP.rotationDegrees(be.rotateZ));
        for (RitualCoreBlockEntity.Data datum : be.type.data) {
            VertexConsumer consumer = bufferSource.getBuffer(triangleFanSolid);
            renderStrip(be, consumer, poseStack, datum, packedLight, packedOverlay);
        }

        poseStack.popPose();


    }

    private static void renderStrip(RitualCoreBlockEntity be, VertexConsumer consumer, PoseStack poseStack, RitualCoreBlockEntity.Data type, int packedLight, int packedOverlay) {

        PoseStack.Pose lastPose = poseStack.last();
        Matrix4f pose = lastPose.pose();

        float innerRadius = type.radius() - type.thickness();
        float outerRadius = type.radius();

        float rotateX = be.dataRotateX.get(type);
        float rotateY = be.dataRotateY.get(type);
        float rotateZ = be.dataRotateZ.get(type);
        poseStack.mulPose(Axis.XP.rotationDegrees(rotateX));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotateY));
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotateZ));



        for (int i = 0; i <= type.segments(); i++) {
            float angle = (float) (2 * Math.PI * i / type.segments());
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);

            // 内圆顶点
            float innerX = type.relaX() + innerRadius * cos;
            float innerZ = type.relaZ() + innerRadius * sin;

            // 外圆顶点
            float outerX = type.relaX() + outerRadius * cos;
            float outerZ = type.relaZ()  + outerRadius * sin;

            // 计算UV坐标（可选）
            float u = 0.5f + 0.5f * cos;
            float v = 0.5f + 0.5f * sin;

            // 添加内圆和外圆顶点，形成三角形带
            consumer.addVertex(pose, innerX, type.relaY(), innerZ)
                    .setColor(type.argb())
                    .setUv(u, v)
                    .setOverlay(packedOverlay)
                    .setLight(packedLight)
                    .setNormal(0, 1, 0);

            consumer.addVertex(pose, outerX, type.relaY(), outerZ)
                    .setColor(type.argb())
                    .setUv(u, v)
                    .setOverlay(packedOverlay)
                    .setLight(packedLight)
                    .setNormal(0, 1, 0);
        }

        for (int i = type.segments(); i >= 0; i--) {
            float angle = (float) (2 * Math.PI * i / type.segments());
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);

            // 内圆顶点
            float innerX = type.relaX() + innerRadius * cos;
            float innerZ = type.relaZ() + innerRadius * sin;

            // 外圆顶点
            float outerX = type.relaX() + outerRadius * cos;
            float outerZ = type.relaZ() + outerRadius * sin;

            // 计算UV坐标（可选）
            float u = 0.5f + 0.5f * cos;
            float v = 0.5f + 0.5f * sin;

            // 添加内圆和外圆顶点，形成三角形带
            consumer.addVertex(pose, innerX, type.relaY(), innerZ)
                    .setColor(type.argb())
                    .setUv(u, v)
                    .setOverlay(packedOverlay)
                    .setLight(packedLight)
                    .setNormal(0, -1, 0);

            consumer.addVertex(pose, outerX, type.relaY(), outerZ)
                    .setColor(type.argb())
                    .setUv(u, v)
                    .setOverlay(packedOverlay)
                    .setLight(packedLight)
                    .setNormal(0, -1, 0);
        }

    }




}
