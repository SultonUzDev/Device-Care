package com.uzdev.devicecare.data.storage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
import com.uzdev.devicecare.utils.getSpaceAsGb
import java.io.File

class StorageManager(private val context: Context) {
    private val mainDir = File(Environment.getExternalStorageDirectory().absolutePath)

    fun hasManageAllPermission(): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        true
    }

    @SuppressLint("UsableSpace")
    fun getStorageInfo(): Storage {
        val filesCount = getFilesCount()
        val foldersCount = getFolderCount()
        return Storage(
            totalSpace = mainDir.totalSpace.getSpaceAsGb(),
            usableSpace = mainDir.usableSpace.getSpaceAsGb(),
            freeSpace = mainDir.freeSpace.getSpaceAsGb(),
            filesCount = filesCount,
            foldersCount = foldersCount,
            totalCountOfFileAndDir = foldersCount + filesCount
        )
    }


    private fun getFolderCount(): Int {
        var count = 0;
        mainDir.walkTopDown().forEach {
            if (it.isDirectory) {
                count++
            }
        }

        return count
    }

    private fun getFilesCount(): Int {
        var count = 0;
        mainDir.walkTopDown().forEach {
            if (it.isFile) {
                count++
            }
        }

        return count
    }
}


data class Storage(
    val totalSpace: String,
    val usableSpace: String,
    val freeSpace: String,
    val totalCountOfFileAndDir: Int,
    val filesCount: Int,
    val foldersCount: Int,
)