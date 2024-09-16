package com.sventripikal.thirtydaysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sventripikal.thirtydaysapp.data.MotivationalData
import com.sventripikal.thirtydaysapp.model.MotivationalIdea
import com.sventripikal.thirtydaysapp.ui.theme.ThirtyDaysAppTheme
import com.sventripikal.thirtydaysapp.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirtyDaysAppTheme {
                ThirtyDayApp()
            }
        }
    }
}


/**
 *  Main app composable
 */
@Composable
fun ThirtyDayApp() { }


/**
 *  Card composable applied to idea collection items
 *
 *  @param motivationalIdea data class model containing information
 *  @param modifier modifier values applied to composable
 */
@Composable
fun MotivationalItem(
    motivationalIdea: MotivationalIdea,
    modifier: Modifier = Modifier
) {
//  string value of collection item index   |   TO-DO: randomize list order
    val dayOfMonth = (MotivationalData.listOfIdeas.indexOf(motivationalIdea) + 1).toString()

    Card(
        elevation = CardDefaults.elevatedCardElevation(2.dp),   // add slight shadowing
        modifier = modifier
            .size(width = 500.dp, height = 400.dp)  // card dimensions
    ) {
        Column(
            modifier = Modifier.fillMaxSize()   // use entire card space
        ) {
            CardImage(motivationalIdea, Modifier.weight(1f))    // upper-half card space

            CardInformation(dayOfMonth, motivationalIdea, Modifier.weight(1f))  // lower-half card space
        }
    }
}


/**
 *  Image composable used to display idea image
 *
 *  @param motivationalIdea data class model containing information
 *  @param modifier modifier values applied to composable
 */
@Composable
fun CardImage(
    motivationalIdea: MotivationalIdea,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(motivationalIdea.image),  // idea image
        contentDescription = null,
        contentScale = ContentScale.FillBounds, // stretch image to fill entire space
        modifier = modifier
            .fillMaxSize()  // fill entire upper-half card space
            .clip(
                RoundedCornerShape(
                    bottomStart = 12.dp,    // round image lower-left corner
                    bottomEnd = 12.dp   // round image lower-right corner
                )
            )
    )
}


/**
 *  Card header and idea descriptors composable
 *
 *  @param dayOfMonth string value of collection item index
 *  @param motivationalIdea data class model containing information
 *  @param modifier modifier values applied to composable
 */
@Composable
private fun CardInformation(
    dayOfMonth: String,
    motivationalIdea: MotivationalIdea,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()  // fill entire lower-half card space
            .padding(20.dp) // spacing around column contents

    ) {
        CardHeadline(dayOfMonth, motivationalIdea)  // card headline composable

        Spacer(Modifier.height(10.dp))  // vertical spacing between composables

        Text(
            text = stringResource(motivationalIdea.description),    // idea description
            style = Typography.bodyLarge    // 16sp font size
        )

        Spacer(Modifier.weight(1f))     // fill all space between prior/next composables

        Text(
            text = stringResource(motivationalIdea.reference),  // image reference
            style = Typography.labelSmall,  // 11sp font size
            modifier = Modifier.align(Alignment.End)    // align text composable to right
        )
    }
}


/**
 *  Day of month and idea title composable
 *
 *  @param dayOfMonth string value of collection item index
 *  @param motivationalIdea data class model containing information
 */
@Composable
private fun CardHeadline(
    dayOfMonth: String,
    motivationalIdea: MotivationalIdea
) {
    Row {
        Text(
            text = "Day $dayOfMonth:",
            style = Typography.headlineMedium   // 20sp font size
        )

        Spacer(modifier = Modifier.width(10.dp))    // horizontal spacing between composables

        Text(
            text = stringResource(motivationalIdea.title),  // idea title
            style = Typography.headlineMedium,  // 20sp font size
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MotivationalItemLightPreview() {
    ThirtyDaysAppTheme(darkTheme = false) {
        MotivationalItem(MotivationalData.listOfIdeas.first())
    }
}

@Preview(showBackground = true)
@Composable
fun MotivationalItemDarkPreview() {
    ThirtyDaysAppTheme(darkTheme = true) {
        MotivationalItem(MotivationalData.listOfIdeas.first())
    }
}
