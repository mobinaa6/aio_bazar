package com.example.aio_bazar.widget.textFiled

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aio_bazar.ui.theme.Blue
import com.example.aio_bazar.ui.theme.Shapes

@Composable
fun MainTextField(
    edtValue: String,
    icon: Int?,
    hint: String,
    isEmail: Boolean=false,
    onValueChanges: (String) -> Unit,
) {
    OutlinedTextField(
        value = edtValue,
        label = { Text(text = hint) },
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(text = hint) },
        modifier = Modifier
            .fillMaxWidth(.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        leadingIcon = { if (icon!=null)Icon(painter = painterResource(id = icon), contentDescription = null)  },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Text
        ),

        )
}