package com.sculptee.utils.customviewview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class ProductShowCaseWebView : WebView {


    private fun init(context: Context) {

        /*Setting the basic settings for the webview*/

        settings.javaScriptEnabled = true

        settings.setAppCacheEnabled(true)

        settings.saveFormData = true

        settings.javaScriptEnabled = true

       // addJavascriptInterface(JavaScriptInterface(), "jsinterface")
        scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY


    }

    internal inner class JavaScriptInterface//                System.out.println("intent");

    constructor(context: Context){

    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }


    override fun loadDataWithBaseURL(
        baseUrl: String?,
        data: String,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?
    ) {
        super.loadDataWithBaseURL(
            baseUrl, "<style>"
                    /*Setting the style attribute to fit the image in the screen*/

                    + "img"
                    + "{"
                    + "display: inline;height: auto;min-width: 100%;max-width: 100%; margin: 10px 0px;"
                    + "}"
                    + "li"
                    + "{"
                    + "margin: 10px 0px;"
                    + "}"

                    + "iframe"
                    + "{"
                    + "display:block;min-width: 100%;max-width: 100%; margin: 10px 0px;"
                    + "}" + "h3" + "{" +
                    "  font-family: ProxyBold" +
                    "}"


                    + "a{ color: #b6b6b6; }"
                    + "body,body *{ word-wrap: break-word; max-width: 100%;}"

                    + "</style>" +

                    /*Intializing the scripts(jquery.min.js & j360.js) from the assetsfolder*/

                    "<script src=\"file:///android_asset/js/jquery.min.js\"></script>\n" +
                    "    <script type=\"text/javascript\" src=\"file:///android_asset/js/j360.js\"></script>\n" +
                    "</head>\n" +

                    "<body>\n" +
                    "\n" +

                    /*Setting the jquery j360 for tag product which is assigned in the div tag*/

                    "<script type=\"text/javascript\" style=\"height: auto;min-width: 100%;max-width: 100%; margin: 10px 0px; overflow: hidden;\">\n" +
                    "            jQuery(document).ready(function() {\n" +
                    "                jQuery('#product').j360();\n" +
                    "            });\n" +
                    "\n" +
                    "</script>\n" +
                    "<center>\n" +
                    "    <div id=\"product\" style=\"height: auto;min-width: 100%;max-width: 100%; margin: 10px 0px; overflow: hidden;\">" + data/*data is  the Images Begin Assigned*/ + "</div>\n" +
                    "</center>\n" +
                    "\n" +
                    "</body>", mimeType, encoding, historyUrl
        )
    }


}