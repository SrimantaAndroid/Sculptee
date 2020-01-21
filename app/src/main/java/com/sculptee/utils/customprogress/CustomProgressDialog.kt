package com.sculptee.utils.customprogress

import android.app.Activity
import android.app.Dialog

import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.ProgressBar
import android.view.Window.FEATURE_NO_TITLE
import com.sculptee.R


class CustomProgressDialog {

    var customProgress: CustomProgressDialog? = null
    private var mDialog: Dialog? = null

   public fun getInstance(): CustomProgressDialog {
        if (customProgress == null) {
            customProgress = CustomProgressDialog()
        }
        return customProgress as CustomProgressDialog
    }

    fun showProgress(context: Activity, message: String, cancelable: Boolean) {
        mDialog = Dialog(context,R.style.NewDialog) as Dialog
        mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog?.setContentView(R.layout.custom_progress_dialog_layout)
        //  mProgressBar.getIndeterminateDrawable().setColorFilter(context.getResources()
        // .getColor(R.color.material_blue_gray_500), PorterDuff.Mode.SRC_IN);
        val tv_loading = mDialog?.findViewById(R.id.tv_loading_txt) as TextView
        tv_loading.text = "" + message

       // mProgressBar.setIndeterminate(true)
        mDialog?.setCancelable(cancelable)
        mDialog?.setCanceledOnTouchOutside(cancelable)
        mDialog?.show()
    }

    fun hideProgress() {
        if (mDialog != null) {
            mDialog?.dismiss()
            mDialog = null
        }
    }

}