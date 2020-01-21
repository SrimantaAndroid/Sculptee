package com.sculptee.model.review

data class ProductCommand(val comment_author:String,val comment_date:String,
                          val comment_content:String, val rating:String) {
}