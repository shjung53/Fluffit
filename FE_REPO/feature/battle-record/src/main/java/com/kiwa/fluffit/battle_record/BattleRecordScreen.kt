package com.kiwa.fluffit.battle_record

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun BattleRecordScreen() {
    Box(){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.battlerecordbackground),
            contentDescription = "배경화면",
            contentScale = ContentScale.FillBounds
        )
    }
}