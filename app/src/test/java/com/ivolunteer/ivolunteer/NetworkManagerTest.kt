package com.ivolunteer.ivolunteer

import com.ivolunteer.ivolunteer.resources.NetworkManager
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NetworkManagerTest {
    @Test
    fun valid_post() {
        val body = JSONObject()
        body.put("username", "aya")
        body.put("password", "Aya1231!")

        assertEquals(4, 2 + 2)
    }
}