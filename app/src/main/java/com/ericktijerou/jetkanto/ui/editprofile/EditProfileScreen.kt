package com.ericktijerou.jetkanto.ui.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.ui.component.Loader
import com.ericktijerou.jetkanto.ui.component.TextField
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.Teal500
import com.ericktijerou.jetkanto.ui.util.ViewState
import com.ericktijerou.jetkanto.ui.util.hiltViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun EditProfileScreen(onBackPressed: () -> Unit) {
    val viewModel: EditProfileViewModel by hiltViewModel()
    val session = viewModel.session.collectAsState(initial = null).value
    val updateState = viewModel.updateSessionState.collectAsState(initial = null)
    if (session == null) {
        Loader()
    } else {
        var name by remember { mutableStateOf(TextFieldValue(session.name)) }
        var username by remember { mutableStateOf(TextFieldValue(session.username)) }
        var bio by remember { mutableStateOf(TextFieldValue(session.bio)) }
        Scaffold(
            topBar = {
                EditProfileTopBar(
                    backgroundColor = KantoTheme.colors.background,
                    onBackPressed = onBackPressed,
                    onSave = {
                        val newSession = session.apply {
                            this.name = name.text
                            this.username = username.text
                            this.bio = bio.text
                        }
                        viewModel.updateSession(newSession)
                    }
                )
            }
        ) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilImage(
                    data = session.avatar,
                    contentDescription = stringResource(R.string.label_avatar),
                    fadeIn = true,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(CircleShape)
                        .size(72.dp)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp)
                        .height(24.dp)
                        .clickable { }
                        .background(color = KantoTheme.colors.primary, shape = CircleShape)
                ) {
                    Text(
                        text = stringResource(R.string.label_change_photo),
                        style = KantoTheme.typography.body2.copy(
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                TextField(
                    textFieldValue = name,
                    label = stringResource(R.string.label_name),
                    onTextChanged = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(vertical = 4.dp)
                )
                TextField(
                    textFieldValue = username,
                    label = stringResource(R.string.label_username),
                    onTextChanged = { username = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(vertical = 4.dp)
                )
                TextField(
                    textFieldValue = bio,
                    label = stringResource(R.string.label_bio),
                    onTextChanged = { bio = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(vertical = 4.dp)
                )
            }
        }
        when (updateState.value) {
            is ViewState.Loading -> Loader()
            is ViewState.Success -> onBackPressed()
        }

    }
}

@Composable
fun EditProfileTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    onBackPressed: () -> Unit,
    onSave: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.label_edit_profile),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = KantoTheme.customColors.textPrimaryColor,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.label_cancel),
                    tint = KantoTheme.customColors.textSecondaryColor,
                )
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.label_cancel),
                    tint = Teal500
                )
            }
        },
        backgroundColor = backgroundColor,
        modifier = modifier,
        elevation = 0.dp
    )
}
