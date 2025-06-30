package azizi.ahmed.reader.packages.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp

fun formateDate(
    timestamp: Timestamp
): String {
    return DateFormat.getDateInstance().format(timestamp.toDate())
}