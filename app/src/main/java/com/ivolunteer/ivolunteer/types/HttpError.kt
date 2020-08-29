package com.ivolunteer.ivolunteer.types

class HttpError {
    var type: String

    var title: String

    var status: Int

    var traceId: String

    constructor(type: String, title: String, status: Int, traceId: String) {
        this.type = type
        this.title = title
        this.status = status
        this.traceId = traceId
    }
}