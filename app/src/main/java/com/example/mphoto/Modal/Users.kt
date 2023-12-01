package com.example.mphoto.Modal

data class Users(var name:String?, var email:String?, var password: String?, var img:String?){

    constructor(img: String):this(null,null,null,img)
    constructor(name: String?,email: String?,password: String?):this(name,email, password, null)
    constructor():this(null,null,null,null)

}
