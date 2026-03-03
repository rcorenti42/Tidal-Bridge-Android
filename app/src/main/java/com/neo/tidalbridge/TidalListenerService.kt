package com.neo.tidalbridge

import android.content.ComponentName
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import org.json.JSONObject

class TidalListenerService : NotificationListenerService() {

    private var currentTitle: String? = null
    private var currentArtist: String? = null
    private var duration: Long = 0
    private var basePosition: Long = 0
    private var lastUpdateTime: Long = 0
    private var isPlaying: Boolean = false

    private val handler = Handler(Looper.getMainLooper())
    private val interval = 1000L

    private val ticker = object : Runnable {
        override fun run() {
            if (currentTitle != null) {

                val position = if (isPlaying) {
                    val delta = SystemClock.elapsedRealtime() - lastUpdateTime
                    basePosition + delta
                } else {
                    basePosition
                }

                val json = JSONObject().apply {
                    put("title", currentTitle)
                    put("artist", currentArtist)
                    put("duration", duration)
                    put("position", position)
                    put("playing", isPlaying)
                }

                Log.d("TIDAL_JSON", json.toString())
            }

            handler.postDelayed(this, interval)
        }
    }

    override fun onListenerConnected() {
        Log.d("TIDAL", "Listener connected")
        handler.post(ticker)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName != "com.aspiro.tidal") return

        val manager = getSystemService(MediaSessionManager::class.java)
        val component = ComponentName(this, TidalListenerService::class.java)
        val controllers: List<MediaController> = manager.getActiveSessions(component)

        for (controller in controllers) {

            if (controller.packageName != "com.aspiro.tidal") continue

            val metadata = controller.metadata ?: continue
            val playbackState = controller.playbackState ?: continue

            currentTitle = metadata.getString(android.media.MediaMetadata.METADATA_KEY_TITLE)
            currentArtist = metadata.getString(android.media.MediaMetadata.METADATA_KEY_ARTIST)
            duration = metadata.getLong(android.media.MediaMetadata.METADATA_KEY_DURATION)

            basePosition = if (playbackState.position >= 0)
                playbackState.position
            else
                basePosition

            lastUpdateTime = playbackState.lastPositionUpdateTime
            isPlaying = playbackState.state == PlaybackState.STATE_PLAYING
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.aspiro.tidal") {
            currentTitle = null
            currentArtist = null
            duration = 0
            basePosition = 0
            isPlaying = false

            val json = JSONObject().apply {
                put("title", JSONObject.NULL)
                put("artist", JSONObject.NULL)
                put("duration", 0)
                put("position", 0)
                put("playing", false)
            }

            Log.d("TIDAL_JSON", json.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(ticker)
    }
}