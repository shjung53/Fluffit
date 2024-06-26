package com.kiwa.fluffit.collection

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.kiwa.fluffit.model.flupet.response.Collections

@SuppressLint("UnrememberedMutableState")
@Composable
internal fun CollectionFlupetCard(
    modifier: Modifier = Modifier,
    collectionList: List<Collections>,
    index: Int
) {
    val bitmap: MutableState<Bitmap?> = mutableStateOf(null)

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(collectionList.get(index).imageUrl[0])
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })

    Box(
        modifier = modifier.then(
            Modifier.aspectRatio(1f)
        )
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "도감 배경",
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            bitmap.value?.asImageBitmap()?.let {
                if (collectionList.get(index).metBefore) {
                    Image(
                        bitmap = it,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.6f)
                            .align(Alignment.Center)
                    )
                } else {
                    Image(
                        bitmap = it,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.6f)
                            .align(Alignment.Center),
                        colorFilter = ColorFilter.tint(Color.Black, BlendMode.SrcIn)
                    )
                }
            } ?: Image(
                painter = painterResource(id = R.drawable.rabbit_white),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.6f)
                    .align(Alignment.Center)
            )
        }
    }
}
