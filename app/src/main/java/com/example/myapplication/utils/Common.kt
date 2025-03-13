package com.example.myapplication.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.example.myapplication.R

class Common {
    companion object {
        fun getWidthPercent(context: Context, percent: Int): Int {
            val width = context.resources.displayMetrics.widthPixels ?: 0
            return (width * percent) / 100
        }
        fun getHeightPercent(context: Context, percent: Int): Int {
            val height = context.resources.displayMetrics.heightPixels ?: 0
            return (height * percent) / 100
        }
        fun TextView.isEllipsized(ellipsize: (isEllipsized : Boolean) -> Unit){
            val lineCount = layout.lineCount
            if (lineCount >0) {
                val ellipseCount = layout.getEllipsisCount(lineCount - 1)
                ellipsize.invoke(ellipseCount > 0)
            }
        }

        fun descriptionDialog(context: Context, title: String?, subTitle: String?, description: String?) {
            val dialog = Dialog(context, R.style.Theme_MyApplication)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.description_dialog)

            dialog.findViewById<TextView>(R.id.tvTitle).text = title
            dialog.findViewById<TextView>(R.id.tvSubtitle).text = subTitle
            dialog.findViewById<TextView>(R.id.tvDescription).text = description
            dialog.findViewById<TextView>(R.id.btnClose).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}