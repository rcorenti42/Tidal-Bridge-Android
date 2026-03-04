# TIDAL Bridge Android

Android background service that listens to TIDAL playback metadata and streams track information through WebSocket.

This application is designed to work with the companion project **Tidal-Presence (Rust)**, which converts this data into a Discord Rich Presence.

The service runs continuously in the background and automatically reconnects after device reboot.

---

## Features

- Listen to **TIDAL playback metadata**
- Send track data in **real time via WebSocket**
- Designed to run **24/7 in background**
- **Automatic reconnection**
- Works on **local network or VPN**
- Compatible with **Android 12+**

---

## Architecture

    TIDAL Android App
            │
            │ Track metadata
            ▼
    WebSocket Client
            │
            │ JSON
            ▼
    TIDAL Presence Server (Rust)
            │
            ▼
    Discord Rich Presence

---

## Data Sent

The Android app sends JSON messages containing playback information.

Example:

```json
{
  "title": "Song Title",
  "artist": "Artist Name",
  "duration": 245000,
  "position": 42000,
  "playing": true
}
```

Fields:

| Field    | Description                    |
| -------- | ------------------------------ |
| title    | Track title                    |
| artist   | Artist name                    |
| duration | Track duration in milliseconds |
| position | Current playback position      |
| playing  | Playback state                 |

---

## Network Requirements

The Android app connects to the **Tidal-Presence (Rust) Server** via WebSocket.

Exemple:

    ws://192.168.1.42:3000

Since the system is designed for **local network usage**, remote connections require a VPN such as **WireGuard**.

Exemple topology:

    Android(12+) Phone (WiFi/4G/5G)
            │
            │ WireGuard VPN
            ▼
    Home Network
            │
            ▼
    Tidal-Presence (Rust)

---

## Requirements

- Android 12+
- TIDAL installed
- WireGuard (optional for remote usage)

---

## Running

1. Install the APK on your device.
2. Configure the WebSocket server address.
3. Start the background service.
4. Launch TIDAL and play music.

The application will automatically stream playback data.

---

## Notes

This application is intended **for personal use** and was built to integrate TIDAL with Discord Rich Presence, which is not natively supported.

---
---
