package com.ericktijerou.jetkanto.ui.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.core.headerExpandedHeight
import com.ericktijerou.jetkanto.ui.component.KantoPlayer
import com.ericktijerou.jetkanto.ui.component.KantoProgressIndicator
import com.ericktijerou.jetkanto.ui.entity.RecordView
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.Teal500
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun RecordList(modifier: Modifier = Modifier, list: List<RecordView>) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Box(Modifier.padding(top = headerExpandedHeight))
        list.forEach {
            RecordCard(record = it)
        }
    }
}

@Composable
fun RecordCard(record: RecordView) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = KantoTheme.customColors.videoCardColor,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 0.dp
    ) {
        Column {
            RecordHeader(
                record = record,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
            PlayerWithControls(
                record = record,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            RecordFooter(
                likes = record.likeCount.toString(),
                modifier = Modifier.padding(start = 8.dp),
                onLikeClick = {},
                isLiked = true
            )
        }
    }
}

@Composable
fun PlayerWithControls(record: RecordView, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        KantoProgressIndicator(
            progress = 0.5f,
            color = KantoTheme.customColors.textPrimaryColor,
            modifier = Modifier.fillMaxWidth(),
            strokeWidth = 2.dp
        )
        Box(modifier = modifier) {
            KantoPlayer(context, record.videoUrl, false)
            Icon(
                imageVector = Icons.Outlined.Videocam,
                contentDescription = stringResource(R.string.label_video_icon),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            )
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = stringResource(R.string.label_video_icon),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp)
            )
            Text(
                text = record.songName,
                color = Color.White,
                style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Black),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun RecordHeader(record: RecordView, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImage(
            data = record.ownerAvatar,
            contentDescription = stringResource(R.string.label_avatar),
            fadeIn = true,
            modifier = Modifier
                .clip(CircleShape)
                .border(5.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                .size(36.dp)
        )
        Text(
            text = record.ownerName,
            modifier = Modifier.padding(start = 12.dp),
            color = KantoTheme.customColors.textPrimaryColor,
            style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = stringResource(R.string.label_sang),
            modifier = Modifier.padding(start = 4.dp),
            color = KantoTheme.customColors.textPrimaryColor,
            style = KantoTheme.typography.body1
        )
        Text(
            text = record.songName,
            modifier = Modifier.padding(start = 4.dp),
            color = KantoTheme.customColors.textPrimaryColor,
            style = KantoTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun RecordFooter(likes: String, modifier: Modifier, onLikeClick: () -> Unit, isLiked: Boolean) {
    IconButton(onClick = onLikeClick, modifier = modifier) {
        val color = if (isLiked) Teal500 else KantoTheme.customColors.textPrimaryColor
        val icon = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Text(
                text = likes,
                color = color,
                style = KantoTheme.typography.body1,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}