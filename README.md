# Welcome to SmartCrops 🌱

[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=flat-square&logo=android&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

Welcome to the **SmartCrops** repository. This Android application serves as a UI/UX prototype and simulator for a smart greenhouse monitoring system.

Developed as part of a business and technology proposal during the Leader Formation Program at **Queen Mary University of London (QMUL)**, the app demonstrates how IoT and AI can be integrated to automate irrigation, predict crop growth, and assist farmers in real-time.

🎥 **[Watch the Demo on YouTube](YOUR_YOUTUBE_LINK_HERE)**

---

## 📚 About The Project

| Feature                | Details |
| ---------------------- | ------- |
| 🎯 **Purpose**         | An agricultural simulator to monitor crop health, control greenhouse variables, and receive AI-driven farming advice. |
| ⚙️ **Architecture**     | Built entirely with a modern declarative UI using Jetpack Compose and a single-activity architecture. |
| 🤖 **AI Integration**   | Features "TomaBot", an integrated chatbot powered by the Gemini API to answer crop-related questions. |
| 🔗 **IoT Simulation**   | Proposes hardware integration with visual states for connected/disconnected crops and active growth phases. |

---

## 🚀 Tech Stack

### Android & UI

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)

- **Kotlin & Jetpack Compose:** The entire user interface is built declaratively, utilizing Compose animations, bottom sheets, and custom UI states.
- **CameraX:** Implemented for the hardware pairing screen, allowing users to scan QR codes on their physical SmartCrop devices.
- **Coil:** Asynchronous image loading for crop pictures and community forum feeds.

### API & Networking

- **OkHttp:** Used to handle asynchronous REST API POST requests directly to Google's Gemini generative language models.

---

## 🔧 Highlighted Features

| Feature | Description |
|--------|------------|
| **Crop Dashboard** | View a grid or list of your active crops, displaying their connection status, current health, and specific growth phases. |
| **TomaBot (AI Assistant)** | A dedicated chat interface connected to the Gemini API, designed to provide immediate troubleshooting and agricultural advice. |
| **Environmental Simulator** | Interactive sliders to adjust and simulate greenhouse variables like Temperature, Humidity, Pressure, Oxygen Level, and Light Intensity. |
| **Hardware Pairing** | A camera preview interface built with CameraX to simulate scanning and linking new IoT sensors to the app. |
| **Community Forum** | A built-in social feed for users to share their crop progress, like posts, and leave comments. |

---

## 📸 Screenshots

- ![Home Dashboard](assets/HomeDashboard.jpeg)
- ![Crop Details & Simulator](assets/CropDetails.jpeg)
- ![TomaBot AI](assets/TomaBot.jpeg)
- ![Community Forum](assets/CommunityForum.jpeg)

---

## 🛠️ How to Run Locally

### 1. Clone the repository
```bash
git clone https://github.com/MexboxLuis/SmartCrops.git
cd SmartCrops
```

### 2. Open the project

Launch Android Studio, select **Open an existing project**, and navigate to the cloned `SmartCrops` folder.

### 3. API Key Configuration (Optional but Recommended)

The app uses the Gemini API for the TomaBotScreen. For local testing:

1. Open `app/src/main/java/com/example/smartcropsapp/screens/TomaBotScreen.kt`.
2. Locate the `apiKey` variable inside the `sendMessageToGeminiApi` function.
3. Replace it with your own Google Gemini API key if needed.

### 4. Build and Run

Click **Sync Project with Gradle Files**.  
Once the build is successful, select your emulator or physical device (Android 8.0 / API 26+) and click **Run (Shift + F10)**.

> ⚠️ Note: The app will request Camera permissions when accessing the QR Scanner screen.
