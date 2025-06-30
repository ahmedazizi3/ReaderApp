package azizi.ahmed.reader.packages.utils


import android.icu.text.DateFormat
import com.google.firebase.Timestamp

fun formateTime(
    timestamp: Timestamp
): String {
    return DateFormat.getTimeInstance(DateFormat.SHORT).format(timestamp.toDate())
}
