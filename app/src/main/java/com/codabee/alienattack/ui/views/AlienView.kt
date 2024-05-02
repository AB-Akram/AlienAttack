package com.codabee.alienattack.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codabee.alienattack.`view-model`.AlienViewModel

@Composable
fun AlienView(vm: AlienViewModel = viewModel()) {
    val alienState by vm.state.collectAsState()

    //Sizes
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    val third = screenWidth / 3
    val inDP = third.dp

    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Alien Attack !",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Black
        )
        Row(modifier = Modifier.height(inDP)) {
            Spacer(modifier = Modifier.width(((screenWidth - third) * alienState.position).dp))
            Image(
                painter = painterResource(id = alienState.image),
                contentDescription = null,
                modifier = Modifier
                    .width(inDP)
                    .height(inDP)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { vm.onStart() },
                enabled = !alienState.isActive
            ) { Text(text = "Start") }
            Button(
                onClick = { vm.onCancel() },
                enabled = alienState.isActive
            ) { Text(text = "Cancel") }
            Button(
                onClick = { vm.onReset() },
                enabled = (alienState.isActive && alienState.distance == 0)
            ) { Text(text = "Reset") }
        }
    }
}
