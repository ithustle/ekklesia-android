# Seedfy (Ekklesia) - Christian Mobile App

<div align="center">
  <img src="https://play-lh.googleusercontent.com/yQChfa9XKlaXMIYTk8w8QwChjT8_SH-_2d2SS-kesw0TLQK1nxtw54bDcoZ09freZJgKrtg4f__is-31Vg=w96-h32-rw" alt="Seedfy Logo" width="200"/>
  
  [![Google Play](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/details?id=com.toquemedia.ekklesia)
  [![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
  [![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
  [![Firebase](https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase)](https://firebase.google.com)
  
  <br/>
  
  **📖 Language / Idioma:**
  [🇺🇸 English](./README_EN.md) | [🇵🇹 Português](./README.md)
</div>

## 📖 About the Application

**Seedfy** is an innovative Christian platform where faith comes to digital life. Developed in Kotlin with Jetpack Compose, it offers a complete experience for Bible reading, devotional creation, community participation, and spiritual growth through technology.

### 🎯 Mission
Connect Christians around the world, facilitating Bible study, devotional content creation, and faith strengthening through a vibrant digital community.

---

## 📱 Screenshots

### 📚 Bible Interface
<div align="center">
  <img src="./assets/screenshots/testamentos.png" alt="Testament Screen" width="250"/>
  <img src="./assets/screenshots/genesis-chapter.png" alt="Genesis Chapter" width="250"/>
</div>

*Navigation interface through testaments (Old and New) and biblical chapters with books like Genesis, Exodus, Leviticus, etc.*

### 🏠 Home Screen and Communities
<div align="center">
  <img src="./assets/screenshots/home-verse.png" alt="Verse of the Day" width="250"/>
  <img src="./assets/screenshots/community-feed.png" alt="Community Feed" width="250"/>
</div>

*Verse of the day (Psalms 9:9) and Christian community feed with user posts from Kwanza Online and Neuza Nascimento*

### 🤖 AI-Powered Smart Search
<div align="center">
  <img src="./assets/screenshots/ai-search.png" alt="AI Search" width="250"/>
</div>

*Smart search system with AI answering "What is the meaning of Easter?" with relevant verses and study plans*

---

## 🚀 Key Features

### 📖 **Complete Digital Bible**
- ✅ Offline access to the Bible (NIV)
- ✅ Intuitive navigation through testaments, books, and chapters
- ✅ Personal annotation system for verses
- ✅ Favorite verse bookmarking
- ✅ Responsive and accessible interface

### 🤖 **AI Bible Assistant**
- ✅ Questions and answers about biblical themes
- ✅ Automatic generation of personalized study plans
- ✅ Related verse suggestions
- ✅ Historical and theological context
- ✅ Questions for meditation and reflection

### 🎬 **Devotional Creation**
- ✅ Complete devotional editor
- ✅ Explanatory video recording (up to 2 minutes)
- ✅ Customization with colors and themes
- ✅ Community sharing
- ✅ Personal devotional library

### 👥 **Christian Communities**
- ✅ Creation and participation in thematic communities
- ✅ Activity and interaction feed
- ✅ Devotional and reflection sharing
- ✅ Likes and comments system
- ✅ Global connection between believers

### 📚 **Bible Reading Plans**
- ✅ AI-generated personalized plans
- ✅ Progress tracking
- ✅ Structured daily reading
- ✅ Goals and reminders

---

## 🛠️ Architecture and Technologies

### **Frontend**
- **Kotlin** - Main language
- **Jetpack Compose** - Modern and declarative UI
- **Material Design 3** - Updated design system
- **Navigation Compose** - Screen navigation
- **Compose State Management** - State management

### **Backend and Services**
- **Firebase Auth** - User authentication
- **Firestore** - NoSQL database
- **Firebase Storage** - Media storage
- **Firebase AI (Gemini)** - Artificial intelligence
- **Firebase Cloud Messaging** - Push notifications

### **Architecture**
- **MVVM** - Model-View-ViewModel
- **Hilt** - Dependency injection
- **Room** - Local database
- **Retrofit** - HTTP client
- **Coroutines & Flow** - Asynchronous programming

### **Media and Camera**
- **CameraX** - Video capture
- **Media3** - Video playback
- **Coil** - Image loading

---

## 📁 Project Structure

```
app/src/main/java/com/toquemedia/seedfy/
├── 📁 dao/                    # Data Access Objects
│   ├── AppDatabase.kt
│   ├── BibleDao.kt
│   ├── LikeDao.kt
│   └── WorshipDao.kt
│
├── 📁 di/                     # Dependency Injection
│   └── modules/
│       ├── AppModules.kt
│       └── DatabaseModule.kt
│
├── 📁 model/                  # Data models
│   ├── BibleType.kt
│   ├── CommunityWithMembers.kt
│   ├── UserType.kt
│   └── WorshipEntity.kt
│
├── 📁 repository/             # Repositories
│   ├── AuthRepositoryImpl.kt
│   ├── BibleRepositoryImpl.kt
│   └── WorshipRepositoryImpl.kt
│
├── 📁 services/               # External services
│   ├── FirebaseAiService.kt
│   ├── CommunityService.kt
│   └── BunnyService.kt
│
├── 📁 ui/
│   ├── 📁 composables/        # Reusable components
│   │   ├── EkklesiaTextField.kt
│   │   ├── EkklesiaButton.kt
│   │   └── VerseToAnnotation.kt
│   │
│   ├── 📁 screens/            # Application screens
│   │   ├── 📁 bible/
│   │   │   ├── TestamentScreen.kt
│   │   │   ├── verses/VersesScreen.kt
│   │   │   ├── search/SearchAIScreen.kt
│   │   │   └── worship/CreateWorshipScreen.kt
│   │   │
│   │   ├── 📁 community/
│   │   │   ├── feed/FeedPost.kt
│   │   │   └── create/CreateCommunityScreen.kt
│   │   │
│   │   ├── 📁 biblePlan/
│   │   │   ├── BiblePlanScreen.kt
│   │   │   └── BiblePlanItem.kt
│   │   │
│   │   └── 📁 profile/
│   │       ├── MyWorshipScreen.kt
│   │       └── WorshipCard.kt
│   │
│   ├── 📁 navigation/         # Navigation
│   └── 📁 theme/             # Theme and colors
│
└── 📁 utils/                 # Utilities
    ├── mocks/
    └── AlarmScheduler.kt
```

---

## 🎨 Main UI Components

### **Main Screens**
- `TestamentScreen` - Testament selection
- `VersesScreen` - Chapter reading
- `SearchAIScreen` - Smart search
- `CreateWorshipScreen` - Devotional creation
- `BiblePlanScreen` - Reading plans
- `MyWorshipScreen` - Personal devotionals

### **Reusable Components**
- `VerseToAnnotation` - Verse display
- `EkklesiaTextField` - Custom text field
- `EkklesiaButton` - App standard button
- `VideoPlayer` - Video player
- `CameraPreviewScreen` - Recording interface

---

## 🔧 Setup and Installation

### **Prerequisites**
- Android Studio Hedgehog+ (2023.1.1)
- JDK 17
- Android SDK 34
- Configured Firebase account

### **Main Dependencies**

```kotlin
// UI and Compose
implementation("androidx.compose.ui:ui:$compose_version")
implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
implementation("androidx.compose.material3:material3:$material3_version")
implementation("androidx.activity:activity-compose:$activity_compose_version")

// Navigation
implementation("androidx.navigation:navigation-compose:$nav_version")
implementation("androidx.hilt:hilt-navigation-compose:$hilt_nav_version")

// Architecture
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
implementation("com.google.dagger:hilt-android:$hilt_version")

// Firebase
implementation("com.google.firebase:firebase-auth:$firebase_auth_version")
implementation("com.google.firebase:firebase-firestore:$firestore_version")
implementation("com.google.firebase:firebase-storage:$storage_version")
implementation("com.google.firebase:firebase-ai:$firebase_ai_version")

// Media
implementation("androidx.camera:camera-camera2:$camerax_version")
implementation("androidx.camera:camera-lifecycle:$camerax_version")
implementation("androidx.camera:camera-view:$camerax_version")
implementation("androidx.media3:media3-ui:$media3_version")

// Utilities
implementation("io.coil-kt:coil-compose:$coil_version")
implementation("androidx.room:room-runtime:$room_version")
implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
```

### **Firebase Configuration**
1. Create project in [Firebase Console](https://console.firebase.google.com)
2. Add Android application
3. Download `google-services.json` and place in `app/`
4. Configure Authentication, Firestore, Storage and AI

---

## 📊 Detailed Features

### **Biblical AI System**
```kotlin
// Example of FirebaseAiService usage
class FirebaseAiService @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    suspend fun generateText(prompt: String): BiblicalResponse {
        val response = generativeModel.generateContent(prompt)
        return processResponse(response.text)
    }
}
```

### **Devotional Creation**
- Rich editor with formatting
- Integrated video recording
- Automatic upload to Firebase Storage
- Real-time sharing

### **Community System**
- Real-time feed
- Social interactions (likes, comments)
- Content moderation
- Push notifications

---

## 🎯 Roadmap and Future Features

### **Version 1.1** 
- [ ] Biblical stories
- [ ] User chat
- [ ] Advanced dark mode
- [ ] Home screen widgets

### **Version 1.2**
- [ ] Calendar integration
- [ ] Reading reminders
- [ ] Progress statistics
- [ ] Improved offline sync

### **Version 2.0**
- [ ] Group study plans
- [ ] Devotional live streams
- [ ] Content marketplace
- [ ] Multi-language support

---

## 👨‍💻 Development

### **How to Contribute**
1. Fork the project
2. Create a branch for your feature
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

### **Code Standards**
- Follow Kotlin Coding Conventions
- Use Compose best practices
- Implement unit tests
- Document public APIs

---

## 📄 License and Information

### **Developed by**
**Toque Média, Lda** - Digital solutions specialists

### **Availability**
- 📱 [Google Play Store](https://play.google.com/store/apps/details?id=com.toquemedia.ekklesia)
- 🎯 Rating: +12 years
- 📊 10+ downloads
- ⭐ Review: Growing

### **Privacy and Security**
- ✅ Data encrypted in transit
- ✅ No data shared with third parties
- ✅ Data deletion request possibility
- ✅ Minimal personal information collection

---

## 📞 Support

### **Contact**
- 📧 Email: [suporte@toquemedia.com](mailto:suporte@toquemedia.com)
- 🌐 Website: [www.toquemedia.com](https://www.toquemedia.com)
- 📱 Google Play: [Developer page](https://play.google.com/store/apps/developer?id=Toque+M%C3%A9dia,+Lda)

### **FAQ**
**Q: Does the app work offline?**
A: Yes, the Bible and saved devotionals are available offline.

**Q: How to record devotional videos?**
A: Go to the Bible section, choose a verse and select "Create devotional".

**Q: Can I create private communities?**
A: Currently communities are public, but private communities are on the roadmap.

---

<div align="center">
  <h3>🌱 Plant spiritual seeds. Grow in community. 🌱</h3>
  
  *"For where two or three gather in my name, there am I with them."*  
  **Matthew 18:20**
  
  ---
  
  **Developed with ❤️ for the global Christian community**
</div>
