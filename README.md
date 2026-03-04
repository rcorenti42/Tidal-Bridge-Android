![Android](https://img.shields.io/badge/Android-12%2B-3DDC84?logo=android&logoColor=white) ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white) ![WebSocket](https://img.shields.io/badge/WebSocket-real--time-blue) ![TIDAL](https://img.shields.io/badge/TIDAL-integration-black?logo=tidal&logoColor=white) ![Discord](https://img.shields.io/badge/Discord-Rich%20Presence-5865F2?logo=discord&logoColor=white)

# TIDAL Bridge Android

Android service that captures **TIDAL playback metadata** and streams it in real time over **WebSocket**.

This project is designed to work with **TIDAL Presence** *(https://github.com/rcorenti42/Tidal-Presence)*, a Rust application that converts playback data into **Discord Rich Presence**.

---

## Features

- Real-time **TIDAL playback detection**
- Sends playback metadata through **WebSocket**
- **Lightweight background service**
- Automatic **connection retry**
- Compatible with **local network or VPN**
- Designed to work with **TIDAL Presence**

---

## Architecture

    TIDAL Android App
            │
            │ Playback metadata
            ▼
    Android Listener
            │
            │ WebSocket JSON
            ▼
    TIDAL Presence
            │
            ▼
    Discord Rich Presence

---

## Data Format

Playback information is sent as JSON messages.

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

Field description:

| Field    | Description                    |
| -------- | ------------------------------ |
| title    | Track title                    |
| artist   | Artist name                    |
| duration | Track duration in milliseconds |
| position | Current playback position      |
| playing  | Playback state                 |

---

## Network Setup

The Android app connects to the **TIDAL Presence** server.

Exemple:

    ws://192.168.1.42:3000

Since the system is designed for **LAN communication**, remote usage requires a VPN.

Exemple topology:

    Android Phone (WiFi/4G/5G)
            │
            │ WireGuard VPN
            ▼
    Home Network
            │
            ▼
    TIDAL Presence

---

## Requirements

- Android 12+
- TIDAL installed
- WebSocket server (TIDAL Presence)

Optional:
- WireGuard for remote connectivity

---

## Running

1. Install the APK.
2. Configure the WebSocket server address.
3. Start the listener service.
4. Play music in TIDAL.

Track metadata will be streamed automatically.

---

## Companion Project

This project is designed to work with:

**TIDAL Presence** *(https://github.com/rcorenti42/Tidal-Presence)*

A Rust application that converts playback data into Discord Rich Presence.

---

## Disclamer

This project is an **unofficial integration** with TIDAL and is not affiliated with TIDAL or Discord.

---
---
