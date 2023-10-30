package widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun AppBar(

) {
    TopAppBar(
        title = {
            Text(
                text = "KenyanYt",
                color = Color.Gray)
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Face,
//                imageVector = Icons.Default.Menu,
                contentDescription = "Toggle drawer",
                tint = Color.Gray
            )
        }
    )
}