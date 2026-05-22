package com.example.redscate

import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.LAYOUT_INFLATER_SERVICE

class Cancelar(private val activity: Activity) {
    private val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val popupView = inflater.inflate(R.layout.activity_template_cancelar, null)   // actividad del pop up
    private val blurBackground = activity.findViewById<View>(R.id.blur_background_cancelar)  // backgraund

    private val buttonSi = popupView.findViewById<TextView>(R.id.button_si)
    private val buttonNo = popupView.findViewById<TextView>(R.id.button_no)




    fun showPopupWindowCancelar(view: View) {
        blurBackground.visibility = View.VISIBLE                                //vuelve visible el backgraund
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            isFocusable = true
            setOnDismissListener { blurBackground.visibility = View.GONE }  //vuelve invisible el backgraund y cierra el pop up
        }
            buttonNo.setOnClickListener {
                popupWindow.dismiss()

            }
        buttonSi.setOnClickListener {
            val intent = Intent(activity, Radar::class.java)
            activity.startActivity(intent)
                popupWindow.dismiss()
        }


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }


}