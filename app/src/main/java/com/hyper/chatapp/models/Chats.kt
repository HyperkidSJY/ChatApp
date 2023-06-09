package com.hyper.chatapp.models

class Chats {
    var message : String? = null
    var senderuid : String? = null
    var timestamp : String? = null

    constructor(){}

    constructor(message : String, senderuid : String,timestamp : String){
        this.message = message
        this.senderuid = senderuid
        this.timestamp = timestamp
    }
}