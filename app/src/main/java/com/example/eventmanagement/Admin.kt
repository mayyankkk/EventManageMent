package com.example.eventmanagement

data class Admin(val email:String?=null,
                 @field:JvmField
                 val isAdmin:Boolean=true,
                 val name:String?=null)
