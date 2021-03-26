package com.ericktijerou.jetkanto.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ericktijerou.jetkanto.core.headerExpandedHeight
import com.ericktijerou.jetkanto.ui.component.KantoPlayer
import com.ericktijerou.jetkanto.ui.entity.RecordView
import com.ericktijerou.jetkanto.ui.theme.KantoTheme

@Composable
fun RecordList(modifier: Modifier = Modifier, list: List<RecordView>, state: LazyListState) {
    LazyColumn(modifier = modifier, state = state) {
        item {
            Box(Modifier.padding(top = headerExpandedHeight))
        }
        items(
            items = list,
            itemContent = { record ->
                RecordCard(record = record)
            }
        )
    }
}

@Composable
fun RecordCard(record: RecordView) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val context = LocalContext.current
            Text(text = "Foo", modifier = Modifier.padding(16.dp), color = KantoTheme.customColors.textPrimaryColor)
            KantoPlayer(context, record.videoUrl, false)
        }
    }
}