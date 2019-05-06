package com.hexadec.megalexa.model
import org.json.JSONObject

class User constructor(private val id: String ,private val name: String) : parserJson{

    fun getName():String{
        return name
    }
    fun getId():String{
        return id
    }

    override fun toJSON() : JSONObject{
        val userJSON : JSONObject = JSONObject()
        userJSON.put("Id User", id)
        userJSON.put("name User", name)
        return userJSON
    }
}