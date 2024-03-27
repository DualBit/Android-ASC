package com.android.asc.amity.util

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.graphics.Shader
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Android custom pixel shader, [AGSL](<https://developer.android.com/develop/ui/views/graphics/agsl/using-agsl>)
 * (Android Graphics Shader Language)
 */
@RequiresApi(33)
fun Modifier.backgroundShader(shaderSrc: String) = this.drawWithCache {
    val shader = RuntimeShader(shaderSrc)
    val brush = ShaderBrush(shader)
    onDrawBehind {
        drawRect(brush)
    }
}

/**
 * The [customBlur] modifier extension takes a blur parameter, which specifies the intensity of the blur effect.
 * It is used to apply a graphicsLayer to the Composable, which, in turn, applies a blur effect using
 * RenderEffect.createBlurEffect. The graphicsLayer is used to apply rendering effects to the Composable.
 */
@RequiresApi(31)
fun Modifier.customBlur(blur: Float) = this.then(
    graphicsLayer {
        if (blur > 0f)
            renderEffect = RenderEffect
                .createBlurEffect(
                    blur,
                    blur,
                    Shader.TileMode.DECAL,
                )
                .asComposeRenderEffect()
    }
)