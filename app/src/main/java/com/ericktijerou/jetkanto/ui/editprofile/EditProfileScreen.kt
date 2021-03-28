/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetkanto.ui.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ericktijerou.jetkanto.R
import com.ericktijerou.jetkanto.core.getRealPath
import com.ericktijerou.jetkanto.core.saveBitmap
import com.ericktijerou.jetkanto.ui.component.Loader
import com.ericktijerou.jetkanto.ui.component.RequestPermission
import com.ericktijerou.jetkanto.ui.component.TextField
import com.ericktijerou.jetkanto.ui.entity.UserView
import com.ericktijerou.jetkanto.ui.theme.KantoTheme
import com.ericktijerou.jetkanto.ui.theme.Teal500
import com.ericktijerou.jetkanto.ui.util.TextValidator
import com.ericktijerou.jetkanto.ui.util.ViewState
import com.ericktijerou.jetkanto.ui.util.hiltViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import java.io.File

@Composable
fun EditProfileScreen(onBackPressed: () -> Unit) {
    val viewModel: EditProfileViewModel by hiltViewModel()
    val session = viewModel.session.collectAsState(initial = null).value
    val updateState = viewModel.updateSessionState.collectAsState(initial = null)
    val showChooseImageDialog = remember { mutableStateOf(false) }
    if (session == null) {
        Loader()
    } else {
        val localImagePath = remember { mutableStateOf(session.localAvatarPath) }
        EditProfileBody(
            session = session,
            onBackPressed = onBackPressed,
            localImagePath = localImagePath.value,
            onSave = { newSession ->
                viewModel.updateSession(newSession)
            },
            onChangePhoto = {
                showChooseImageDialog.value = true
            }
        )
        ChooseImageDialog(
            onDismissRequest = { showChooseImageDialog.value = false },
            visible = showChooseImageDialog.value,
            onImageResult = {
                localImagePath.value = it
            }
        )
        when (updateState.value) {
            is ViewState.Loading -> Loader()
            is ViewState.Success -> onBackPressed()
        }
    }
}

@Composable
fun EditProfileBody(
    session: UserView,
    onBackPressed: () -> Unit,
    localImagePath: String,
    onSave: (UserView) -> Unit,
    onChangePhoto: () -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue(session.name)) }
    var username by remember { mutableStateOf(TextFieldValue(session.username)) }
    var bio by remember { mutableStateOf(TextFieldValue(session.bio)) }
    val isValidName = TextValidator.isValidName(name.text)
    val isValidUserName = TextValidator.isValidUsername(username.text)
    val isValidBio = TextValidator.isValidBio(bio.text)
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
                        this.localAvatarPath = localImagePath
                    }
                    onSave(newSession)
                },
                saveEnabled = isValidName && isValidUserName && isValidBio
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageData = if (localImagePath.isNotEmpty()) {
                File(localImagePath)
            } else {
                session.avatar
            }
            CoilImage(
                data = imageData,
                contentDescription = stringResource(R.string.label_avatar),
                fadeIn = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(CircleShape)
                    .size(72.dp),
                contentScale = ContentScale.Crop
            )
            TextButton(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = 28.dp
                    )
                    .height(28.dp),
                onClick = onChangePhoto,
                shape = CircleShape,
                colors = textButtonColors(
                    backgroundColor = KantoTheme.customColors.cardColor,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = stringResource(R.string.label_change_photo),
                    style = KantoTheme.typography.body2.copy(
                        color = KantoTheme.customColors.textSecondaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                )
            }
            TextField(
                textFieldValue = name,
                label = stringResource(R.string.label_name),
                onTextChanged = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(vertical = 4.dp),
                isErrorValue = !isValidName
            )
            TextField(
                textFieldValue = username,
                label = stringResource(R.string.label_username),
                onTextChanged = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(vertical = 4.dp),
                isErrorValue = !isValidUserName
            )
            TextField(
                textFieldValue = bio,
                label = stringResource(R.string.label_bio),
                onTextChanged = { bio = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(vertical = 4.dp),
                isErrorValue = !isValidBio
            )
        }
    }
}

@Composable
fun EditProfileTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    onBackPressed: () -> Unit,
    onSave: () -> Unit,
    saveEnabled: Boolean
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
            if (saveEnabled) {
                IconButton(onClick = onSave) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(R.string.label_cancel),
                        tint = Teal500
                    )
                }
            }
        },
        backgroundColor = backgroundColor,
        modifier = modifier,
        elevation = 0.dp
    )
}

@Composable
fun ChooseImageDialog(
    onDismissRequest: () -> Unit,
    visible: Boolean,
    onImageResult: (String) -> Unit
) {
    val context = LocalContext.current
    val shouldRequestPermission = remember { mutableStateOf(false) }
    val requestCode = remember { mutableStateOf<RequestCode>(RequestCode.Camera) }
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imagePath = when (requestCode.value) {
                    RequestCode.Gallery -> {
                        result.data?.data?.getRealPath(context)
                    }
                    RequestCode.Camera -> {
                        val bitmap = result.data?.extras?.get("data") as? Bitmap
                        bitmap?.let { context.saveBitmap(it) }
                    }
                }
                imagePath?.let {
                    onImageResult(it)
                }
            }
        }
    if (visible) {
        Dialog(onDismissRequest = onDismissRequest) {
            Column(
                modifier = Modifier
                    .background(
                        color = KantoTheme.colors.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_select_image),
                    style = KantoTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
                    color = KantoTheme.customColors.textPrimaryColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TextButton(
                    onClick = {
                        requestCode.value = RequestCode.Camera
                        startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = stringResource(R.string.label_take_photo),
                        style = KantoTheme.typography.body1,
                        color = KantoTheme.customColors.textPrimaryColor
                    )
                }
                TextButton(
                    onClick = {
                        shouldRequestPermission.value = true
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = stringResource(R.string.label_choose_from_library),
                        style = KantoTheme.typography.body1,
                        color = KantoTheme.customColors.textPrimaryColor
                    )
                }
            }
        }
    }
    if (shouldRequestPermission.value) {
        RequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) { permissionGranted ->
            if (permissionGranted) {
                requestCode.value = RequestCode.Gallery
                startForResult.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.label_require_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
            shouldRequestPermission.value = false
        }
    }
}

sealed class RequestCode {
    object Camera : RequestCode()
    object Gallery : RequestCode()
}
