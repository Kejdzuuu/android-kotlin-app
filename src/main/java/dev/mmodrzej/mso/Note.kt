package dev.mmodrzej.mso

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var content: String = "",
                var date: Date = Date())