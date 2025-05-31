package com.toquemedia.seedfy.utils.mocks

import com.toquemedia.seedfy.model.BiblicalResponse
import com.toquemedia.seedfy.model.DailyReading
import com.toquemedia.seedfy.model.StudyPlan
import com.toquemedia.seedfy.model.VerseType

object AiResponseMock {
    fun get() = BiblicalResponse(
        query = "Qual o significado da Páscoa?",
        verses = listOf(
            VerseType("Êxodo", 12, 1, "Disse o Senhor a Moisés e a Arão na terra do Egito..."),
            VerseType("Êxodo", 12, 14, "E este dia vos será por memorial, e celebrá-lo-eis como festa ao Senhor..."),
            VerseType("1 Coríntios", 5, 7, "Alimpai-vos, pois, do fermento velho, para que sejais uma nova massa, assim como estais sem fermento. Porque Cristo, nossa Páscoa, foi sacrificado por nós.")
        ),
        biblicalTeaching = "A Páscoa é uma festa judaica que comemora a libertação dos israelitas da escravidão no Egito. No Novo Testamento, Jesus Cristo é identificado como o Cordeiro Pascal, cujo sacrifício nos liberta do pecado. A Páscoa aponta para a redenção e a nova vida em Cristo, sendo um memorial da graça de Deus.",
        studyPlan = StudyPlan(
            title = "Estudo sobre a Páscoa",
            durationDays = 3,
            dailyReadings = listOf(
                DailyReading(
                    1, "A Páscoa no Antigo Testamento", listOf(
                        VerseType("Êxodo", 12, 1, "Disse o Senhor a Moisés e a Arão na terra do Egito..."),
                        VerseType("Êxodo", 12, 20, "Nenhuma coisa levedada comereis; em todas as vossas habitações comereis pães ázimos.")
                    )
                ),
                DailyReading(
                    2, "Jesus como Cordeiro Pascal", listOf(
                        VerseType("João", 1, 29, "No dia seguinte, João viu a Jesus, que vinha para ele, e disse: Eis o Cordeiro de Deus, que tira o pecado do mundo!"),
                        VerseType("1 Coríntios", 5, 7, "Alimpai-vos, pois, do fermento velho, para que sejais uma nova massa, assim como estais sem fermento. Porque Cristo, nossa Páscoa, foi sacrificado por nós.")
                    )
                ),
                DailyReading(
                    3, "O Significado da Páscoa para Nós Hoje", listOf(
                        VerseType("Gálatas", 3, 13, "Cristo nos resgatou da maldição da lei, fazendo-se maldição por nós; porque está escrito: Maldito todo aquele que for pendurado no madeiro;"),
                        VerseType("Romanos", 6, 4, "De sorte que fomos sepultados com ele pelo batismo na morte; para que, como Cristo foi ressuscitado dentre os mortos pela glória do Pai, assim andemos nós também em novidade de vida.")
                    )
                )
            )
        ),
        meditationQuestion = "Como a celebração da Páscoa e o sacrifício de Jesus me inspiram a viver uma vida de gratidão e liberdade em Cristo?"
    )
}