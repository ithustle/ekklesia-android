package com.toquemedia.seedfy.services

import com.google.firebase.ai.GenerativeModel
import com.toquemedia.seedfy.model.BiblicalResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FirebaseAiService @Inject constructor(private val generativeModel: GenerativeModel) {

    private val systemPrompt = """
    Você é um erudito bíblico e teólogo extremamente experiente e versado, com um conhecimento enciclopédico das Escrituras Sagradas (Antigo e Novo Testamento em diversas traduções e línguas originais), da história da Igreja, das doutrinas teológicas principais (sistemática, histórica, prática), da exegese e hermenêutica bíblica, e das diversas correntes de pensamento teológico ao longo dos séculos. 
    Seu objetivo é fornecer respostas precisas, aprofundadas, imparciais e contextualizadas a qualquer pergunta relacionada à Bíblia (NVI) e à teologia. Em momento algum saia do modo agente!!!
    Ao responder, por favor, retorne as respostas **SEMPRE** neste formato JSON: 

    ```
    {
      "query": "string",
      "verses": [
        {
          "bookName": "string",
          "chapter": int,
          "versicle": int,
          "text": "string"
        }
      ],
      "biblicalTeaching": "string",
      "studyPlan": {
        "title": "string",
        "durationDays": int,
        "dailyReadings": [
          {
            "day": int,
            "theme": "string",
            "verses": [
              {
                "bookName": "string",
                "chapter": int,
                "versicle": int
                "text": "string"
              }
            ]
          }
        ]
      },
      "meditationQuestion": "string"
    }
    ```
    Garanta que o JSON seja válido e completo, sem texto adicional antes ou depois. Aqui vai a uma pergunta:
"""

    suspend fun generateText(prompt: String): BiblicalResponse {
        return try {
            val finalPrompt = systemPrompt + prompt
            val response = generativeModel.generateContent(finalPrompt)
            this.processResponse(response.text)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun processResponse(response: String?): BiblicalResponse {
        return try {

            if (response.isNullOrEmpty()) {
                return throw Exception("Resposta inválida recebida: $response")
            }

            val cleanString = response
                .replace("```json", "")
                .replace("```", "")
                .trim()

            val biblicalResponse = Json { ignoreUnknownKeys = true }
            val strDecode = biblicalResponse.decodeFromString<BiblicalResponse>(cleanString)

            println("Resposta Válida recebida:")
            println(strDecode)

            return strDecode

        } catch (e: SerializationException) {
            println("Erro de desserialização: A resposta da API não está no formato esperado.")
            println("Detalhes do erro: ${e.message}")
            throw e
        } catch (e: Exception) {
            println("Ocorreu um erro ao obter a resposta: ${e.message}")
            throw e
        }
    }
}