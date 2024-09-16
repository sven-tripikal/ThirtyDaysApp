package com.sventripikal.thirtydaysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.sventripikal.thirtydaysapp.data.MotivationalData
import com.sventripikal.thirtydaysapp.model.MotivationalIdea
import com.sventripikal.thirtydaysapp.ui.theme.ThirtyDaysAppTheme
import com.sventripikal.thirtydaysapp.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun ThirtyDayApp() {
    Surface {   // add color to background

        //  convert list to mutable list
        val ideasList = MotivationalData.listOfIdeas.toMutableList()

        //  reorder list
        ideasList.shuffle()

        LazyColumn( // organize items in a column
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.ten_dp)), // vertical spacing between items
            contentPadding = PaddingValues(dimensionResource(R.dimen.ten_dp)) // spacing around entire content column
        ) {
            items(ideasList) { idea ->

                val dayOfMonth = (ideasList.indexOf(idea) + 1)  // item index integer value

                MotivationalItem(dayOfMonth, idea)  // card composable
            }
        }
    }
}


/**
 *  Card composable applied to idea collection items
 *
 *  @param dayOfMonth collection item index value
 *  @param motivationalIdea data class model containing information
 *  @param modifier modifier values applied to composable
 */
@Composable
fun MotivationalItem(
    dayOfMonth: Int,
    motivationalIdea: MotivationalIdea,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.two_dp)),   // slight shadowing
        modifier = modifier
            .size(  // card dimensions
                width = dimensionResource(R.dimen.five_hundred_dp),
                height = dimensionResource(R.dimen.four_hundred_dp)
            )
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
                    bottomStart = dimensionResource(R.dimen.twelve_dp),    // round image lower-left corner
                    bottomEnd = dimensionResource(R.dimen.twelve_dp)   // round image lower-right corner
                )
            )
    )
}


/**
 *  Card header and idea descriptors composable
 *
 *  @param dayOfMonth collection item index value
 *  @param motivationalIdea data class model containing information
 *  @param modifier modifier values applied to composable
 */
@Composable
private fun CardInformation(
    dayOfMonth: Int,
    motivationalIdea: MotivationalIdea,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()  // fill entire lower-half card space
            .padding(dimensionResource(R.dimen.twenty_dp)) // spacing around column contents

    ) {
        CardHeadline(dayOfMonth, motivationalIdea)  // card headline composable

        Spacer(Modifier.height(dimensionResource(R.dimen.ten_dp)))  // vertical spacing between composables

        Text(
            text = stringResource(motivationalIdea.description),    // idea description
            style = Typography.bodyLarge,    // 16sp font size
            overflow = TextOverflow.Visible // display all text
        )

        Spacer(Modifier.weight(1f))     // fill all space between prior/next composables

        Text(
            text = stringResource(motivationalIdea.reference),  // image reference
            style = Typography.labelSmall,  // 11sp font size
            overflow = TextOverflow.Visible, // display all text
            modifier = Modifier.align(Alignment.End)    // align text composable to right
        )
    }
}


/**
 *  Day of month and idea title composable
 *
 *  @param dayOfMonth collection item index value
 *  @param motivationalIdea data class model containing information
 */
@Composable
private fun CardHeadline(
    dayOfMonth: Int,
    motivationalIdea: MotivationalIdea
) {
    Row {
        Text(
            text = stringResource(R.string.day_text, dayOfMonth),   // day of month text
            style = Typography.headlineMedium,   // 20sp font size
            overflow = TextOverflow.Visible // display all text
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.ten_dp))) // horizontal spacing between composables

        Text(
            text = stringResource(motivationalIdea.title),  // idea title
            style = Typography.headlineMedium,  // 20sp font size
            overflow = TextOverflow.Visible // display all text
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ThirtyDayAppPreview() {
    ThirtyDaysAppTheme(darkTheme = true) {
        ThirtyDayApp()
    }
}

@Preview(showBackground = true)
@Composable
fun MotivationalItemLightPreview() {
    ThirtyDaysAppTheme(darkTheme = false) {
        MotivationalItem(1, MotivationalData.listOfIdeas.first())
    }
}

@Preview(showBackground = true)
@Composable
fun MotivationalItemDarkPreview() {
    ThirtyDaysAppTheme(darkTheme = true) {
        MotivationalItem(1, MotivationalData.listOfIdeas.first())
    }
}
