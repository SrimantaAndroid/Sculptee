package com.sculptee.network

class NetworkUrlConstant {
    companion object{
        val CONSUMER_KEY:String="ck_f36a691ba7ab086bda16115b5901390a7dc04758"
        val CONSUMER_SECRET:String="cs_32fea993cccb0e6a39a05a82629b493ecc22a389"
       // val BASE_URL:String="http://13.234.121.130/wp-json/"
          val BASE_URL:String="http://sculptee.in/wp-json/"
        const val EVENTS:String="wc/v3/products/categories"
        const val CATEGORY_DETAILS:String="wc/v3/products/?status=publish"
        const val PRODUCT_DETAILS:String="wc/v3/products/{id}"
        //const val LOGIN="jwt-auth/v1/token"
        //"sculpteetheme/v1/customer-logging"
        const val  LOGIN="sculpteetheme/v1/customer-login"
        const val SIGNUP="sculpteetheme/v1/customer-signup"
        const val ADDTO_CART="cocart/v1/add-item"
        const val GET_CART_LIST="cocart/v1/get-cart"
        const val CREATE_ORDER="wc/v3/orders"
        const val CUSTOMER_ORDER_LIST="wc/v2/orders?"
        const val POSTCODE_LOCATION_CHECK="sculpteetheme/v1/shipping_postcode_exists/{postcode}"
        const val HASHURL="https://debajyotibasak.000webhostapp.com/PayUMoneyHash.php"
        const val ADD_FAVOURITE="sculpteetheme/v1/wishlists/add-item"
        const val FOVOURITELIST="wc/v3/products/?"
        const val REMOVEFROMLIST="sculpteetheme/v1/wishlists/remove-item"
        const val GETSHIPPINGVAL="sculpteetheme/v1/product-charges"
        const val UPDATEORDER="wc/v3/orders/{id}"
        const val  DETE_USER_TOKEN="sculpteetheme/v1/user-token-remove"
        const val COUPAN="wc/v3/coupons?"
        const val USER_COUPONS="sculpteetheme/v1/user-coupons/{id}"
        const val FLAVOURAPI="wc/v3/products/attributes/5/terms"
        const val UPDATEPASSWORD="wc/v3/customers/{id}"
        const val REVIEWRATING="wc/v3/products/reviews"
        const val SAVEADDESSS="sculpteetheme/v1/add-new-shipping-address"
        const val UPDATEADDRESS="sculpteetheme/v1/update-shipping-address"
        const val GETUSERADDRESS="sculpteetheme/v1/get-shipping-address/{userid}"
        const val DELETEADDESS="sculpteetheme/v1/delete-shipping-address/{userid}/{address_index}"

    }
}