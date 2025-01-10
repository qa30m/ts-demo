package com.example.tsdemoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsdemoapp.R
import com.example.tsdemoapp.ui.theme.blackFont
import com.example.tsdemoapp.ui.theme.redColor

@Composable
fun ActionRow(
    title: String,
    onActionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = blackFont,
            fontWeight = FontWeight.Normal,
        )
        Row(
            modifier = Modifier
                .clickable { onActionClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Add Now",
                fontSize = 16.sp,
                color = redColor,
                fontWeight = FontWeight.Normal,
            )
            Icon(
                painter = painterResource(R.drawable.arrow_1),
                contentDescription = "",
                tint = redColor,
                modifier = Modifier.size(32.dp)
                    .padding(start = 8.dp, end = 16.dp)
            )
        }
    }
}