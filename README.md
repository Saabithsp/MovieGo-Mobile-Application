# 🎬 Movie Go

## 🌟 Overview
Movie Go is a collaborative Android app project built by our team to help users explore movies easily and view detailed information. The app connects multiple APIs for real-time data, uses Glide for image loading, GSON for JSON parsing, and integrates Firebase for a reliable backend.

## 👥 Team Members
- M.S.M.Saabith
- B.A.Binuli Vihara
- M.Farhan
- S.S.Weeraman

## ✨ Features
- **🚀 Efficient Image Loading:** Glide for optimized image loading and caching.
- **🎞️ Movie Data Fetching:** Real-time movie information from multiple APIs.
- **📄 JSON Parsing:** GSON for fast and reliable JSON parsing.
- **☁️ Firebase Integration:** Backend services powered by Firebase.
- **🖼️ User-Friendly Interface:** Smooth navigation and search features.

## ⚙️ Prerequisites
- **📲 Android Studio:** Latest version.
- **💻 OS Compatibility:** PC compatible with Android Studio.
- **☕ JDK:** Latest Java Development Kit (JDK).
- **🔥 Firebase Account:** For backend functionality.

## 🚀 Installation

1. **📥 Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/movie-go.git
   ```

2. **💻 Open the Project in Android Studio:**
   - Launch Android Studio.
   - Select **Open an existing Android Studio project**.
   - Navigate to the cloned repository and open it.

3. **🔧 Configure Firebase:**
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project or select an existing one.
   - Add your Android app to the Firebase project.
   - Download the `google-services.json` file.
   - Place the `google-services.json` file in the `app` directory of your project.

4. **🔑 Add API Keys:**
   - Open the `gradle.properties` file in the root of your project.
   - Add your API keys:
     ```properties
     API_KEY=your_api_key
     FIREBASE_API_KEY=your_firebase_api_key
     ```

5. **▶️ Build and Run:**
   - Connect your Android device or start an emulator.
   - Click **Run** in Android Studio.

## 📚 Usage
- Open Movie Go on your device.
- Browse or search through the list of movies.
- Tap a movie to view its details, including images, descriptions, and more.

## 🛠️ Libraries and Technologies
- **🖼️ Glide:** For image loading and caching.  
  [Glide Documentation](https://github.com/bumptech/glide)
- **📄 GSON:** For JSON parsing.  
  [GSON Documentation](https://github.com/google/gson)
- **🔥 Firebase:** For backend services.  
  [Firebase Documentation](https://firebase.google.com/docs)

## 🤝 Contributing
We welcome contributions! To get started:
1. **Fork** the repository.
2. **Create a new branch** for your feature or fix:
   ```bash
   git checkout -b feature-name
   ```
3. **Commit** your changes:
   ```bash
   git commit -m "Description of feature or fix"
   ```
4. **Push** the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a **pull request**.
