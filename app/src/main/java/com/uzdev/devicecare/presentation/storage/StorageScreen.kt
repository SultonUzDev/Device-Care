package com.uzdev.devicecare.presentation.storage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.uzdev.devicecare.presentation.components.ToolbarScreen
import com.uzdev.devicecare.data.storage.StorageManager
import com.uzdev.devicecare.theme.FirstTextColor
import com.uzdev.devicecare.theme.MainBackColor
import com.uzdev.devicecare.theme.SecondTextColor
import com.uzdev.devicecare.theme.ThirdColor

@SuppressLint("StaticFieldLeak")

@Composable
fun StorageScreen(navHostController: NavHostController) {


    val context = LocalContext.current
    val isGranted: LiveData<Boolean> = MutableLiveData(false)


    val storageManager = StorageManager(context = context)
    val storage = storageManager.getStorageInfo()
    Column(
        Modifier
            .fillMaxSize()
            .background(MainBackColor)
            .padding(
                start = 3.dp, end = 3.dp, top = 1.dp, bottom = 1.dp
            )
    ) {


        ToolbarScreen(navHostController = navHostController, title = "Storage")


        Row(modifier = Modifier.size(width = 200.dp, height = 35.dp)) {
            Button(onClick = {


            }) {
                Text(text = "Grant")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTextView(title = "Total storage:")
            DescriptionTextView(desc = storage.totalSpace)
        }
        HorizontalLine()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTextView(title = "Free storage:")
            DescriptionTextView(desc = storage.freeSpace)
        }
        HorizontalLine()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTextView(title = "Usable storage:")
            DescriptionTextView(desc = storage.usableSpace)
        }
        HorizontalLine()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTextView(title = "Folders count:")
            DescriptionTextView(desc = storage.foldersCount.toString())
        }
        HorizontalLine()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTextView(title = "Files count:")
            DescriptionTextView(desc = storage.filesCount.toString())
        }
        HorizontalLine()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTextView(title = "Total count of files and folder:")
            DescriptionTextView(desc = storage.totalCountOfFileAndDir.toString())
        }
        HorizontalLine()
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CheckFilePermissionButton(
    modifier: Modifier,
    hasPermission: Boolean,
    pagerState: PagerState,
    onClick: () -> Unit

) {

    Row(
        modifier = modifier.padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            visible = pagerState.currentPage == 1 && !hasPermission,
        ) {
            Button(onClick = onClick, shape = RoundedCornerShape(32.dp)) {
                Text(text = "Grant")
            }

        }

    }

}


@Composable
fun TitleTextView(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 8.dp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        color = FirstTextColor,
        letterSpacing = 1.sp,

        )

}

@Composable
fun DescriptionTextView(desc: String) {
    Text(
        text = desc,
        modifier = Modifier.padding(end = 8.dp),
        textAlign = TextAlign.End,
        color = SecondTextColor,
        letterSpacing = 1.sp,

        )
}

@Composable
fun HorizontalLine() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp)
            .height(1.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color.White, Color.Gray, ThirdColor
                    )
                )
            )
    )
}

@Composable
fun grantFilePermission(storageManager: StorageManager) {
    val context = LocalContext.current
    val fileRequestResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { grant ->


            })

    if (!storageManager.hasManageAllPermission()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
        } else {
            fileRequestResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    } else {
        fileRequestResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}