package com.hexadec.megalexa.util

import android.util.Log
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.JsonMarshaller
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.hexadec.megalexa.model.User
import com.hexadec.megalexa.model.blocks.Block
import com.hexadec.megalexa.model.blocks.BlockFeedRss
import com.hexadec.megalexa.model.blocks.BlockTextBox
import com.hexadec.megalexa.model.Workflow
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataInput
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList

object GatewayRequests{

    private const val api_URL= "https://jljampmpri.execute-api.eu-west-3.amazonaws.com/prod/"

    fun getJSONFile() : JSONObject {
        TODO()
    }

    //POST Requests Functions
    private fun postRequestToWrite(jSon_object: JSONObject, url: String){

           val myURL = URL(url)
           with(myURL.openConnection() as HttpsURLConnection){
               setRequestProperty("Content-Type", "application/json")
               requestMethod = "POST"
               doOutput = true
               val wr = OutputStreamWriter(outputStream)
               wr.write(jSon_object.toString())
               wr.flush()
               println("URL : $url")
               println("Response Code : $responseCode")
               BufferedReader(InputStreamReader(inputStream)).use {
                   val response = StringBuffer()
                   var inputLine = it.readLine()
                   while (inputLine != null) {
                       response.append(inputLine)
                       inputLine = it.readLine()
                   }
                   println("Response : $response")
               }
           }
    }

    private fun postRequestToRead(jSon_object: JSONObject, url: String) :JSONObject {
        val myURL = URL(url)
        with(myURL.openConnection() as HttpsURLConnection){
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            doOutput = true
            val wr = OutputStreamWriter(outputStream)
            wr.write(jSon_object.toString())
            wr.flush()
            var readResult: StringBuffer
            BufferedReader(InputStreamReader(inputStream)).use {
                readResult = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {

                    readResult.append(inputLine)
                    inputLine = it.readLine()
                }
                return JSONObject(readResult.toString())
            }
        }
    }

    private fun postRequestToReadArray(jSon_object: JSONObject, url: String) :JSONArray {
        val myURL = URL(url)
        with(myURL.openConnection() as HttpsURLConnection){
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            doOutput = true
            val wr = OutputStreamWriter(outputStream)
            wr.write(jSon_object.toString())
            wr.flush()
            var readResult: StringBuffer
            BufferedReader(InputStreamReader(inputStream)).use {
                readResult = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    readResult.append(inputLine)
                    inputLine = it.readLine()
                }
                return JSONArray(readResult.toString())
            }
        }
    }

    //USER FUNCTIONS
    fun saveUser(user: User){
        val resources="user/create"
        postRequestToWrite(user.toJSON(), api_URL+resources)
    }

    fun readUser(paramID: String) : JSONObject{
        val jSonObject= JSONObject()
        val resources="user/read"
        jSonObject.put("userID",paramID)
        return postRequestToRead(jSonObject, api_URL+resources)
    }
    //BLOCK FUNCTIONS
    fun saveBlock() {
        TODO()
    }

    fun deleteBlock() {
        TODO()
    }

    fun updateBlock() {
        TODO()
    }

    //WORKFLOW FUNCTIONS
    fun saveWorkflow(user: User, w: Workflow) {
        val workflow = JSONObject()
        workflow.put("userID", user.getId())
        workflow.put("workflowName", w.getName())
        val blocks = w.getBlocks()
        val workflowContent = JSONArray()
        for(item in w.getBlocks()){
            workflowContent.put(item.toJSON())
        }
        workflow.put("workflow", workflowContent)
        postRequestToWrite(workflow, api_URL + "workflow/create")
    }

    fun deleteWorkflow(workflow_name: String) {
        TODO()
    }

    fun updateWorkflow() {
        TODO()
    }

    fun readWorkflow(user : User) : ArrayList<Workflow>{
        val listWorkflow : ArrayList<Workflow> = ArrayList()
        val userID = JSONObject()
        userID.put("userID", user.getId())
        val resources="workflow/read"
        val workflowListJSON = postRequestToRead(userID, api_URL + resources )
        val create = 0
        val modify = 0
        val counter = 0
        for(workflow in workflowListJSON.keys()){
            //listWorkflow.add(Workflow(workflow,create,modify,counter))
        }
        return listWorkflow
    }

    fun readBlocks(user: User, workflow: Workflow) : ArrayList<Block>?{
        val blocksList : ArrayList<Block> = ArrayList<Block>()
        val toPass = JSONObject()
        toPass.put("userID", user.getId())
        toPass.put("workflow", workflow.getName())
        val resources = "block/read"
        val blocks = postRequestToReadArray(toPass, api_URL + resources)
        for(i in 0..blocks.length()-1){
            val item = blocks.getJSONObject(i)
            when(item.getString("blockType")){
                //ADD BLOCK
                //"textToSpeech"-> blocksList.add(BlockTextBox(item.getJSONObject("config").getString("textToSpeech")))
                //"FeedRSS" -> blocksList.add(BlockFeedRss(item.getJSONObject("config").getString("url")))

            }
        }
        workflow.setBlocks(blocksList)
        return blocksList
    }
}

