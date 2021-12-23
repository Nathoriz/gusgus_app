package mood.honeyprojects.gusgusapp.classes

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import mood.honeyprojects.gusgusapp.R
import java.util.*

class TimePickerFragment( val listener: (String) -> Unit ): DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hora = calendar.get( Calendar.HOUR_OF_DAY )
        val minuto = calendar.get( Calendar.MINUTE )

        val timePicker = TimePickerDialog( activity as Context, R.style.timePickerTheme, this, hora, minuto, true )
        return timePicker
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener( "$hourOfDay:$minute" )
    }
}