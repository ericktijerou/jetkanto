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
package com.ericktijerou.jetkanto.core

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.io.FileOutputStream

fun Int?.orZero() = this ?: ZERO
fun Long?.orZero() = this ?: ZERO.toLong()
fun Any?.isNull(): Boolean = this == null
fun <T> MutableStateFlow<T>.set(block: T.() -> T) {
    this.value = this.value.block()
}

fun Uri.getRealPath(context: Context): String? {
    val cursor = context.contentResolver.query(this, null, null, null, null)
    return cursor?.let {
        it.moveToNext()
        val path = it.getString(it.getColumnIndex("_data"))
        it.close()
        path
    }
}

fun Context.saveBitmap(bitmap: Bitmap): String {
    val dir = File(
        getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "kanto"
    )
    val file = File(
        dir.absolutePath,
        System.currentTimeMillis().toString().plus(".jpg")
    )
    if (!dir.exists()) dir.mkdir()
    if (!file.exists()) file.createNewFile()
    try {
        val out = FileOutputStream(file, false)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return file.absolutePath
}
