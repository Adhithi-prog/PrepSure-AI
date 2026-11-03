package com.example.data.local

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val age: Int,
    val preferredLanguageCode: String,
    val caregiverName: String,
    val caregiverPhone: String,
    val clinicName: String,
    val knownConditions: String,
    val emergencyPhone: String
)

@Entity(tableName = "checklist_progress", primaryKeys = ["instructionId", "itemId"])
data class ChecklistProgressEntity(
    val instructionId: String,
    val itemId: String,
    val isCompleted: Boolean,
    val completedAtEpoch: Long?
)

@Entity(tableName = "saved_instructions")
data class SavedInstructionEntity(
    @PrimaryKey val id: String,
    val titleEn: String,
    val titleTa: String,
    val categoryName: String,
    val doctorOrClinicName: String,
    val originalText: String,
    val simplifiedTextEn: String,
    val simplifiedTextTa: String,
    val fastingHours: Int,
    val chaperoneRequired: Boolean,
    val uploadedAtEpoch: Long
)

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey val id: String,
    val procedureNameEn: String,
    val procedureNameTa: String,
    val hospitalName: String,
    val doctorName: String,
    val scheduledDateEpoch: Long,
    val department: String,
    val notes: String
)

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)
}

@Dao
interface ChecklistDao {
    @Query("SELECT * FROM checklist_progress WHERE instructionId = :instructionId")
    fun getProgressForInstruction(instructionId: String): Flow<List<ChecklistProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setChecklistItemStatus(progress: ChecklistProgressEntity)

    @Query("DELETE FROM checklist_progress WHERE instructionId = :instructionId")
    suspend fun resetProgress(instructionId: String)
}

@Dao
interface InstructionDao {
    @Query("SELECT * FROM saved_instructions ORDER BY uploadedAtEpoch DESC")
    fun getAllSavedInstructions(): Flow<List<SavedInstructionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstruction(instruction: SavedInstructionEntity)

    @Query("SELECT * FROM saved_instructions WHERE id = :id LIMIT 1")
    suspend fun getInstructionById(id: String): SavedInstructionEntity?
}

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY scheduledDateEpoch ASC")
    fun getAppointments(): Flow<List<AppointmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAppointment(appointment: AppointmentEntity)

    @Delete
    suspend fun deleteAppointment(appointment: AppointmentEntity)
}

@Database(
    entities = [
        UserProfileEntity::class,
        ChecklistProgressEntity::class,
        SavedInstructionEntity::class,
        AppointmentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PrepSureDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun checklistDao(): ChecklistDao
    abstract fun instructionDao(): InstructionDao
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: PrepSureDatabase? = null

        fun getDatabase(context: Context): PrepSureDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrepSureDatabase::class.java,
                    "prepsure_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
