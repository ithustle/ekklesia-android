# Seedfy (Ekklesia) - Aplicação Mobile Cristã

<div align="center">
  <img src="https://play-lh.googleusercontent.com/yQChfa9XKlaXMIYTk8w8QwChjT8_SH-_2d2SS-kesw0TLQK1nxtw54bDcoZ09freZJgKrtg4f__is-31Vg=w96-h32-rw" alt="Seedfy Logo" width="200"/>
  
  [![Google Play](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/details?id=com.toquemedia.ekklesia)
  [![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
  [![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
  [![Firebase](https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase)](https://firebase.google.com)
</div>

## 📖 Sobre a Aplicação

**Seedfy** é uma plataforma cristã inovadora onde a fé ganha vida digital. Desenvolvida em Kotlin com Jetpack Compose, oferece uma experiência completa para leitura bíblica, criação de devocionais, participação em comunidades e crescimento espiritual através da tecnologia.

### 🎯 Missão
Conectar cristãos ao redor do mundo, facilitando o estudo bíblico, a criação de conteúdo devocional e o fortalecimento da fé através de uma comunidade digital vibrante.

---

## 📱 Capturas de Tela

### 📚 Interface Bíblica
<div align="center">
  <img src="./assets/screenshots/testamentos.png" alt="Tela dos Testamentos" width="250"/>
  <img src="./assets/screenshots/genesis-chapter.png" alt="Capítulo de Gênesis" width="250"/>
</div>

*Interface de navegação pelos testamentos (Antigo e Novo) e capítulos bíblicos com livros como Gênesis, Êxodo, Levítico, etc.*

### 🏠 Tela Inicial e Comunidades
<div align="center">
  <img src="./assets/screenshots/home-verse.png" alt="Versículo do Dia" width="250"/>
  <img src="./assets/screenshots/community-feed.png" alt="Feed da Comunidade" width="250"/>
</div>

*Versículo do dia (Salmos 9:9) e feed das comunidades cristãs com posts de usuários como Kwanza Online e Neuza Nascimento*

### 🤖 Busca Inteligente com IA
<div align="center">
  <img src="./assets/screenshots/ai-search.png" alt="Busca com IA" width="250"/>
</div>

*Sistema de busca inteligente com IA respondendo "Qual o significado da Páscoa?" com versículos relevantes e planos de estudo*

---

## 🚀 Principais Funcionalidades

### 📖 **Bíblia Digital Completa**
- ✅ Acesso offline à Bíblia (NVI)
- ✅ Navegação intuitiva por testamentos, livros e capítulos
- ✅ Sistema de anotações pessoais em versículos
- ✅ Marcação de versículos favoritos
- ✅ Interface responsiva e acessível

### 🤖 **Assistente Bíblico com IA**
- ✅ Perguntas e respostas sobre temas bíblicos
- ✅ Geração automática de planos de estudo personalizados
- ✅ Sugestões de versículos relacionados
- ✅ Contexto histórico e teológico
- ✅ Perguntas para meditação e reflexão

### 🎬 **Criação de Devocionais**
- ✅ Editor completo de devocionais
- ✅ Gravação de vídeos explicativos (até 2 minutos)
- ✅ Personalização com cores e temas
- ✅ Compartilhamento em comunidades
- ✅ Biblioteca pessoal de devocionais

### 👥 **Comunidades Cristãs**
- ✅ Criação e participação em comunidades temáticas
- ✅ Feed de atividades e interações
- ✅ Compartilhamento de devocionais e reflexões
- ✅ Sistema de likes e comentários
- ✅ Conexão global entre fiéis

### 📚 **Planos de Leitura Bíblica**
- ✅ Planos personalizados gerados por IA
- ✅ Acompanhamento de progresso
- ✅ Leitura diária estruturada
- ✅ Metas e recordatórios

---

## 🛠️ Arquitetura e Tecnologias

### **Frontend**
- **Kotlin** - Linguagem principal
- **Jetpack Compose** - UI moderna e declarativa
- **Material Design 3** - Design system atualizado
- **Navigation Compose** - Navegação entre telas
- **Compose State Management** - Gerenciamento de estado

### **Backend e Serviços**
- **Firebase Auth** - Autenticação de usuários
- **Firestore** - Banco de dados NoSQL
- **Firebase Storage** - Armazenamento de mídia
- **Firebase AI (Gemini)** - Inteligência artificial
- **Firebase Cloud Messaging** - Notificações push

### **Arquitetura**
- **MVVM** - Model-View-ViewModel
- **Hilt** - Injeção de dependências
- **Room** - Banco de dados local
- **Retrofit** - Cliente HTTP
- **Coroutines & Flow** - Programação assíncrona

### **Mídia e Câmera**
- **CameraX** - Captura de vídeo
- **Media3** - Reprodução de vídeo
- **Coil** - Carregamento de imagens

---

## 📁 Estrutura do Projeto

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
├── 📁 model/                  # Modelos de dados
│   ├── BibleType.kt
│   ├── CommunityWithMembers.kt
│   ├── UserType.kt
│   └── WorshipEntity.kt
│
├── 📁 repository/             # Repositórios
│   ├── AuthRepositoryImpl.kt
│   ├── BibleRepositoryImpl.kt
│   └── WorshipRepositoryImpl.kt
│
├── 📁 services/               # Serviços externos
│   ├── FirebaseAiService.kt
│   ├── CommunityService.kt
│   └── BunnyService.kt
│
├── 📁 ui/
│   ├── 📁 composables/        # Componentes reutilizáveis
│   │   ├── EkklesiaTextField.kt
│   │   ├── EkklesiaButton.kt
│   │   └── VerseToAnnotation.kt
│   │
│   ├── 📁 screens/            # Telas da aplicação
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
│   ├── 📁 navigation/         # Navegação
│   └── 📁 theme/             # Tema e cores
│
└── 📁 utils/                 # Utilitários
    ├── mocks/
    └── AlarmScheduler.kt
```

---

## 🎨 Principais Componentes UI

### **Telas Principais**
- `TestamentScreen` - Seleção de testamentos
- `VersesScreen` - Leitura de capítulos
- `SearchAIScreen` - Busca inteligente
- `CreateWorshipScreen` - Criação de devocionais
- `BiblePlanScreen` - Planos de leitura
- `MyWorshipScreen` - Devocionais pessoais

### **Componentes Reutilizáveis**
- `VerseToAnnotation` - Exibição de versículos
- `EkklesiaTextField` - Campo de texto customizado
- `EkklesiaButton` - Botão padrão da aplicação
- `VideoPlayer` - Reprodutor de vídeo
- `CameraPreviewScreen` - Interface de gravação

---

## 🔧 Configuração e Instalação

### **Pré-requisitos**
- Android Studio Hedgehog+ (2023.1.1)
- JDK 17
- Android SDK 34
- Conta Firebase configurada

### **Dependências Principais**

```kotlin
// UI e Compose
implementation("androidx.compose.ui:ui:$compose_version")
implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
implementation("androidx.compose.material3:material3:$material3_version")
implementation("androidx.activity:activity-compose:$activity_compose_version")

// Navegação
implementation("androidx.navigation:navigation-compose:$nav_version")
implementation("androidx.hilt:hilt-navigation-compose:$hilt_nav_version")

// Arquitetura
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
implementation("com.google.dagger:hilt-android:$hilt_version")

// Firebase
implementation("com.google.firebase:firebase-auth:$firebase_auth_version")
implementation("com.google.firebase:firebase-firestore:$firestore_version")
implementation("com.google.firebase:firebase-storage:$storage_version")
implementation("com.google.firebase:firebase-ai:$firebase_ai_version")

// Mídia
implementation("androidx.camera:camera-camera2:$camerax_version")
implementation("androidx.camera:camera-lifecycle:$camerax_version")
implementation("androidx.camera:camera-view:$camerax_version")
implementation("androidx.media3:media3-ui:$media3_version")

// Utilitários
implementation("io.coil-kt:coil-compose:$coil_version")
implementation("androidx.room:room-runtime:$room_version")
implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
```

### **Configuração do Firebase**
1. Criar projeto no [Firebase Console](https://console.firebase.google.com)
2. Adicionar aplicação Android
3. Baixar `google-services.json` e colocar em `app/`
4. Configurar Authentication, Firestore, Storage e AI

---

## 📊 Funcionalidades Detalhadas

### **Sistema de IA Bíblica**
```kotlin
// Exemplo de uso do FirebaseAiService
class FirebaseAiService @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    suspend fun generateText(prompt: String): BiblicalResponse {
        val response = generativeModel.generateContent(prompt)
        return processResponse(response.text)
    }
}
```

### **Criação de Devocionais**
- Editor rico com formatação
- Gravação de vídeo integrada
- Upload automático para Firebase Storage
- Compartilhamento em tempo real

### **Sistema de Comunidades**
- Feed em tempo real
- Interações sociais (likes, comentários)
- Moderação de conteúdo
- Notificações push

---

## 🎯 Roadmap e Funcionalidades Futuras

### **Versão 1.1** 
- [ ] Stories bíblicos
- [ ] Chat entre usuários
- [ ] Modo escuro avançado
- [ ] Widgets para tela inicial

### **Versão 1.2**
- [ ] Integração com calendário
- [ ] Lembretes de leitura
- [ ] Estatísticas de progresso
- [ ] Offline sync melhorado

### **Versão 2.0**
- [ ] Planos de estudo em grupo
- [ ] Live streams de devocionais
- [ ] Marketplace de conteúdo
- [ ] Suporte multi-idioma

---

## 👨‍💻 Desenvolvimento

### **Como Contribuir**
1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

### **Padrões de Código**
- Seguir Kotlin Coding Conventions
- Usar Compose best practices
- Implementar testes unitários
- Documentar APIs públicas

---

## 📄 Licença e Informações

### **Desenvolvido por**
**Toque Média, Lda** - Especialistas em soluções digitais

### **Disponibilidade**
- 📱 [Google Play Store](https://play.google.com/store/apps/details?id=com.toquemedia.ekklesia)
- 🎯 Classificação: +12 anos
- 📊 10+ downloads
- ⭐ Avaliação: Em crescimento

### **Privacidade e Segurança**
- ✅ Dados encriptados em trânsito
- ✅ Nenhum dado compartilhado com terceiros
- ✅ Possibilidade de solicitação de exclusão de dados
- ✅ Coleta mínima de informações pessoais

---

## 📞 Suporte

### **Contato**
- 📧 Email: [suporte@toquemedia.com](mailto:suporte@toquemedia.com)
- 🌐 Website: [www.toquemedia.com](https://www.toquemedia.com)
- 📱 Google Play: [Página do desenvolvedor](https://play.google.com/store/apps/developer?id=Toque+M%C3%A9dia,+Lda)

### **FAQ**
**P: A aplicação funciona offline?**
R: Sim, a Bíblia e devocionais salvos ficam disponíveis offline.

**P: Como gravar vídeos devocionais?**
R: Vá na seção da Bíblia, escolha um versículo e selecione "Criar devocional".

**P: Posso criar comunidades privadas?**
R: Atualmente as comunidades são públicas, mas comunidades privadas estão no roadmap.

---

<div align="center">
  <h3>🌱 Plante sementes espirituais. Cresça em comunidade. 🌱</h3>
  
  *"Porque onde estão dois ou três reunidos em meu nome, ali eu estou no meio deles."*  
  **Mateus 18:20**
  
  ---
  
  **Desenvolvido com ❤️ para a comunidade cristã mundial**
</div>
