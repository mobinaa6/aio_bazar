package com.example.aio_bazar.widget.textFiled

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aio_bazar.R
import com.example.aio_bazar.ui.theme.Shapes

@Composable
fun PasswordTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    isLastTextField: Boolean,
    onValueChanges: (String) -> Unit,
) {
    val passwordVisible = remember { mutableStateOf(false) }
    val focusManager= LocalFocusManager.current
    OutlinedTextField(
        label = { Text(text = hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(text = hint) },
        modifier = Modifier
            .fillMaxWidth(.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        trailingIcon = {
            val image =
                if (passwordVisible.value) painterResource(id = R.drawable.ic_invisible) else painterResource(
                    id = R.drawable.ic_visible
                )
            Icon(
                painter = image, contentDescription = null,
                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value })
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = if (isLastTextField) ImeAction.Done else ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Down)}),
        leadingIcon = { Icon(painter = painterResource(id = icon), contentDescription = null) },
    )
}
