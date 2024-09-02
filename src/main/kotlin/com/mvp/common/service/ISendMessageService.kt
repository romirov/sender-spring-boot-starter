package com.mvp.common.service

import com.mvp.common.dao.IMessage

interface ISendMessageService {
    fun sendMessage(message: IMessage)
}