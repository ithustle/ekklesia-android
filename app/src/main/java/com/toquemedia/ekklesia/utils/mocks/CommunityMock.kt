package com.toquemedia.ekklesia.utils.mocks

import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityMemberType
import com.toquemedia.ekklesia.model.CommunityWithMembers
import com.toquemedia.ekklesia.model.UserType

object CommunityMock {
    fun getAll(): List<CommunityEntity> {
        return listOf(
            CommunityEntity(
                id = "1",
                communityName = "Realidade Profética",
                communityDescription = "Uma comunidade para amantes de tecnologia e inovação para quem gosta de ir mais longe na área.",
                members = 2,
                communityImage = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAAA6ElEQVR4nO3QwQ3AIBDAsIP9d25XIC+EZE8QZc18w5l9O+AlZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBT+IYAHHLHkdEgAAAABJRU5ErkJggg==\n",
                email = "kwanzaonline@gmail.com"
            ),
            CommunityEntity(
                id = "2",
                communityName = "Nature Lovers",
                communityDescription = "Exploradores e amantes da natureza compartilham suas aventuras.",
                members = 19,
                communityImage = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAAA6ElEQVR4nO3QwQ3AIBDAsIP9d25XIC+EZE8QZc18w5l9O+AlZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBT+IYAHHLHkdEgAAAABJRU5ErkJggg==\n",
                email = "toqueplayapp@gmail.com"
            )
        )
    }

    fun getAllCommunityWithMembers(): List<CommunityWithMembers> {
        return listOf(
            CommunityWithMembers(
                community = CommunityEntity(
                    id = "1",
                    communityName = "Tech Enthusiasts",
                    communityDescription = "Uma comunidade para amantes de tecnologia e inovação para quem gosta de ir mais longe na área.",
                    members = 2,
                    communityImage = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAAA6ElEQVR4nO3QwQ3AIBDAsIP9d25XIC+EZE8QZc18w5l9O+AlZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBT+IYAHHLHkdEgAAAABJRU5ErkJggg==\n",
                    email = "kwanzaonline@gmail.com"
                ),
                allMembers = listOf<CommunityMemberType>(
                    CommunityMemberType(
                        id = "1",
                        user = UserType(
                            id = "1",
                            displayName = "João Silva",
                        )
                    ),
                    CommunityMemberType(
                        id = "2",
                        user = UserType(
                            id = "2",
                            displayName = "Elsa Tomás",
                        )
                    )
                )
            ),
            CommunityWithMembers(
                community = CommunityEntity(
                    id = "2",
                    communityName = "Nature Lovers",
                    communityDescription = "Exploradores e amantes da natureza compartilham suas aventuras.",
                    members = 19,
                    communityImage = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAAA6ElEQVR4nO3QwQ3AIBDAsIP9d25XIC+EZE8QZc18w5l9O+AlZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBT+IYAHHLHkdEgAAAABJRU5ErkJggg==\n",
                    email = "toqueplayapp@gmail.com"
                ),
                allMembers = listOf<CommunityMemberType>(
                    CommunityMemberType(
                        id = "1",
                        user = UserType(
                            id = "1",
                            displayName = "João Silva",
                        )
                    ),
                    CommunityMemberType(
                        id = "2",
                        user = UserType(
                            id = "2",
                            displayName = "Elsa Tomás",
                        )
                    )
                )
            ),
            CommunityWithMembers(
                community = CommunityEntity(
                    id = "3",
                    communityName = "Gamer Zone",
                    communityDescription = "Discussões sobre os últimos lançamentos e clássicos dos games.",
                    members = 10,
                    communityImage = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAAA6ElEQVR4nO3QwQ3AIBDAsIP9d25XIC+EZE8QZc18w5l9O+AlZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBWYFZgVmBT+IYAHHLHkdEgAAAABJRU5ErkJggg==\n",
                    email = "toqueplayapp@gmail.com"
                ),
                allMembers = listOf<CommunityMemberType>(
                    CommunityMemberType(
                        id = "1",
                        user = UserType(
                            id = "1",
                            displayName = "João Silva",
                        )
                    ),
                )
            )

        )
    }
}