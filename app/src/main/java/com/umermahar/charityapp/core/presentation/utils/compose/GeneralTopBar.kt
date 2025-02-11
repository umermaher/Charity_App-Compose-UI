package com.umermahar.charityapp.core.presentation.utils.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.umermahar.charityapp.R

@Composable
fun GeneralTopBar(
    modifier: Modifier = Modifier.fillMaxWidth() // measured w.r.t design in XD
        .padding(
            end = 21.dp,
            start = 14.dp,
            top = 27.dp,
            bottom = 26.dp
        ),
    onBackButtonClick: () -> Unit,
    title: String,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    actionIcons: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackButtonClick
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_back),
                tint = contentColor,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            modifier = Modifier
                .weight(1f),
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = contentColor
            ),
        )

        actionIcons()
    }
}