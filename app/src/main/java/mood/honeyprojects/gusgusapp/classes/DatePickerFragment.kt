package mood.honeyprojects.gusgusapp.classes

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mood.honeyprojects.gusgusapp.R
import java.util.*

class DatePickerFragment( val listener: (anio: Int, mes: Int, dia: Int) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener( year, month, dayOfMonth )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val anio = calendar.get( Calendar.YEAR )
        val mes = calendar.get( Calendar.MONTH )
        val dia = calendar.get( Calendar.DAY_OF_MONTH )

        val datePicker = DatePickerDialog( activity as Context, R.style.datePickerTheme, this, anio, mes, dia )
        datePicker.datePicker.minDate = calendar.timeInMillis
        return datePicker
    }
}