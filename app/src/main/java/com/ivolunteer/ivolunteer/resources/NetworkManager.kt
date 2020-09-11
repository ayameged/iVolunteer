package com.ivolunteer.ivolunteer.resources

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type


class NetworkManager {
    val apiAddress = "http://192.168.1.17:2315/api/"

    inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object: TypeToken<T>(){}.type)
    }

    fun postJsonObject(path: String, body: JSONObject, callback: (response: JSONObject, error: JSONObject) -> Unit) {
         Fuel.post(apiAddress + path)
            .jsonBody(body.toString()).response { request, response, result ->
                val (bytes, error) = result

                var resp = JSONObject()
                var respErr = JSONObject()

                if (error != null) {
                    respErr = JSONObject(String(error.response.data))
                } else if (bytes != null) {
                    resp = JSONObject(String(bytes))
                }

                callback(resp, respErr)
            }
    }

    inline fun <reified T>post(path: String, body: JSONObject, crossinline callback: (response: T?, statusCode: Int, error: JSONObject) -> Unit) {
        val token = StorageManager.instance.get<String>(StorageTypes.TOKEN.toString())

        Fuel.post(apiAddress + path)
            .header("Authorization", "Bearer " + token)
            .jsonBody(body.toString()).response { request, response, result ->
                var (bytes, error) = result
                var respErr = JSONObject()

                val collectionType: Type = object : TypeToken<T?>() {}.type

                if (error != null && String(error.response.data) != "") {
                    respErr = JSONObject(String(error.response.data))
                }

                if (bytes == null) {
                    bytes = "{}".toByteArray()
                }

                val resp = Gson().fromJson<T>(String(bytes), collectionType)
//                try{
                    callback(resp, response.statusCode, respErr)
//                } catch(e: Exception) {
//                    print(e)
//                }
            }
    }

    inline fun <reified T>put(path: String, body: JSONObject, crossinline callback: (response: T?, statusCode: Int, error: JSONObject) -> Unit) {
        val token = StorageManager.instance.get<String>(StorageTypes.TOKEN.toString())

        Fuel.put(apiAddress + path)
            .header("Authorization", "Bearer " + token)
            .jsonBody(body.toString()).response { request, response, result ->
                var (bytes, error) = result
                var respErr = JSONObject()

                val collectionType: Type = object : TypeToken<T?>() {}.type

                if (error != null) {
                    respErr = JSONObject(String(error.response.data))
                }

                if (bytes == null) {
                    bytes = "{}".toByteArray()
                }

                val resp = Gson().fromJson<T>(String(bytes), collectionType)
                try{
                    callback(resp, response.statusCode, respErr)
                } catch(e: Exception) {
                    print(e)
                }
            }
    }

    inline fun <reified T>get(path: String, crossinline callback: (response: T?, statusCode: Int, error: JSONObject) -> Unit) {
        val token = StorageManager.instance.get<String>(StorageTypes.TOKEN.toString())

        Fuel.get(apiAddress + path)
            .header("Authorization", "Bearer " + token)
            .response { request, response, result ->
                var (bytes, error) = result
                var respErr = JSONObject()

                val collectionType: Type = object : TypeToken<T?>() {}.type

                if (error != null) {
                    try {
                        respErr = JSONObject(String(error.response.data))
                    } catch (e: JSONException) {
                        print(e)
                    }
                }

                if (bytes == null) {
                    bytes = "{}".toByteArray()
                }

                val resp = Gson().fromJson<T>(String(bytes), collectionType)
                try{
                    callback(resp, response.statusCode, respErr)
                } catch(e: Exception) {
                    print(e)
                }
            }
    }

    companion object {
        val instance = NetworkManager()
    }
}