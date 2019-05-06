package com.hexadec.megalexa.model

import com.hexadec.megalexa.model.blocks.Block
import com.hexadec.megalexa.util.GatewayRequests
import java.util.*
import kotlin.collections.ArrayList

class HexaDec {
    private var workflowList  = ArrayList<Workflow>()
    private  lateinit var user : User

    fun saveUser(user: User){
        this.user = user
        GatewayRequests.saveUser(user)
    }

    fun addWorkflow(wl: Workflow){
        workflowList.add(wl)
    }

    fun getWorkflowList(): ArrayList<Workflow> {
        return workflowList
    }

    fun getUser() : User{
        return user
    }

    fun setUser(user: User){
        this.user = user
    }

    fun loadWorkflow() : ArrayList<Workflow>{
        workflowList = GatewayRequests.readWorkflow(user)
        return workflowList
    }

    fun saveWorkflow(workflowName: String, blockList: ArrayList<Block>,create:Date,modify:Date,counter:Int){
        var workflow = Workflow(workflowName,create,modify,counter)
        workflow.setBlocks(blockList)
        workflowList.add(workflow)
        GatewayRequests.saveWorkflow(user, workflow)
    }

    fun getBlock(user: User, w: String) : ArrayList<Block>? {
        for(item in workflowList){
            if(item.getName() == w){
                return item.getBlocks(user)
            }
        }
        return null
    }

    fun isWorkflowIn(wl: String) : Boolean{
        var isIn  = false
        for(workflow in workflowList){
            if(workflow.getName() == wl){
                isIn = true
            }
        }
        return isIn
    }

}