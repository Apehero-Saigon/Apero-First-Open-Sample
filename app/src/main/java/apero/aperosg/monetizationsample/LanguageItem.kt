package apero.aperosg.monetizationsample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.astronex.firstopen.model.Language
import com.astronex.firstopen.ui.component.foundation.CenterRow
import java.util.Locale

private val languageShape = CircleShape

private val baseModifier = Modifier
    .fillMaxWidth()
    .height(60.dp)
    .clip(languageShape)
    .border(1.dp, Color.Gray, languageShape)
    .background(Color.White)

private val baseSelectedModifier = Modifier
    .fillMaxWidth()
    .height(60.dp)
    .clip(languageShape)
    .border(2.dp, Color.Blue, languageShape)
    .background(Color.Blue.copy(0.1f))

@Composable
fun LanguageItem(
    modifier: Modifier = Modifier,
    language: Language,
    selected: Boolean,
    onClick: () -> Unit,
    showIcon: Boolean = true,
) {
    CenterRow(
        modifier = modifier
            .then(if (selected) baseSelectedModifier else baseModifier)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        itemSpacing = 16.dp,
        content = {
            if (showIcon) {
                Image(
                    painter = painterResource(language.imageId),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    contentScale = ContentScale.Fit,
                )
            }

            Text(
                text = Locale.forLanguageTag(language.code).displayName,
                color = Color.Black,
                modifier = Modifier.weight(1f),
            )

            Image(
                painter = painterResource(if (selected) com.astronex.firstopen.R.drawable.radio_selected else com.astronex.firstopen.R.drawable.radio_unselected),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }
    )
}