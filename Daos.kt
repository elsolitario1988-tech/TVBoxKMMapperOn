package com.tvbox.kmmapper.data.dao

import androidx.room.*
import com.tvbox.kmmapper.data.models.AppProfile
import com.tvbox.kmmapper.data.models.KeyMapping
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones con remapeos de teclas
 */
@Dao
interface KeyMappingDao {
    @Query("SELECT * FROM key_mappings WHERE isEnabled = 1")
    fun getAllActiveMappings(): Flow<List<KeyMapping>>

    @Query("SELECT * FROM key_mappings WHERE originalKeyCode = :keyCode")
    suspend fun getMappingByKeyCode(keyCode: Int): KeyMapping?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMapping(mapping: KeyMapping)

    @Delete
    suspend fun deleteMapping(mapping: KeyMapping)

    @Query("DELETE FROM key_mappings")
    suspend fun deleteAllMappings()

    @Query("UPDATE key_mappings SET isEnabled = :enabled WHERE originalKeyCode = :keyCode")
    suspend fun updateMappingStatus(keyCode: Int, enabled: Boolean)
}

/**
 * DAO para operaciones con perfiles de aplicaciones
 */
@Dao
interface AppProfileDao {
    @Query("SELECT * FROM app_profiles WHERE isEnabled = 1")
    fun getAllActiveProfiles(): Flow<List<AppProfile>>

    @Query("SELECT * FROM app_profiles WHERE appPackage = :packageName")
    suspend fun getProfileByPackage(packageName: String): AppProfile?

    @Query("SELECT * FROM app_profiles WHERE id = :profileId")
    suspend fun getProfileById(profileId: Int): AppProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: AppProfile): Long

    @Update
    suspend fun updateProfile(profile: AppProfile)

    @Delete
    suspend fun deleteProfile(profile: AppProfile)

    @Query("DELETE FROM app_profiles")
    suspend fun deleteAllProfiles()

    @Query("UPDATE app_profiles SET isEnabled = :enabled WHERE id = :profileId")
    suspend fun updateProfileStatus(profileId: Int, enabled: Boolean)
}
