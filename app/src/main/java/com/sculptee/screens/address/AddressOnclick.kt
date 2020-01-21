package com.sculptee.screens.address

import android.view.View

class AddressOnclick : View.OnClickListener{

        var addressActivity: AddressActivity?=null
        var addressViewBind: AddressViewBind?=null


        constructor(addressActivity: AddressActivity, addressViewBind: AddressViewBind){
            this.addressActivity=addressActivity
            this.addressViewBind=addressViewBind

        }

        override fun onClick(p0: View?) {

    }
}