package com.education.android.mainquizapp_final

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey
import java.util.*

@Entity
data class Question(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var title: String = "", var isSolved: Boolean = false)