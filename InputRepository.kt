package com.tvbox.kmmapper.data.repository

import com.tvbox.kmmapper.data.dao.AppProfileDao
import com.tvbox.kmmapper.data.dao.KeyMappingDao
import com.tvbox.kmmapper.data.models.AppProfile
import com.tvbox.kmmapper.data.models.KeyMapping
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que proporciona acceso a los datos de remapeos y perfiles
 */
class InputRepository(
    private val keyMappingDao: KeyMappingDao,
    private val appProfileDao: AppProfileDao
) {

    // Operaciones con remapeos de teclas
    fun getActiveMappings(): Flow<List<KeyMapping>> =
        keyMappingDao.getAllActiveMappings()

    suspend fun getMappingByKeyCode(keyCode: Int): KeyMapping? =
        keyMappingDao.getMappingByKeyCode(keyCode)

    suspend fun saveMapping(mapping: KeyMapping) {
        keyMappingDao.insertMapping(mapping)
    }

    suspend fun deleteMapping(mapping: KeyMapping) {
        keyMappingDao.deleteMapping(mapping)
    }

    suspend fun deleteAllMappings() {
        keyMappingDao.deleteAllMappings()
    }

    suspend fun updateMappingStatus(keyCode: Int, enabled: Boolean) {
        keyMappingDao.updateMappingStatus(keyCode, enabled)
    }

    // Operaciones con perfiles de aplicaciones
    fun getActiveProfiles(): Flow<List<AppProfile>> =
        appProfileDao.getAllActiveProfiles()

    suspend fun getProfileByPackage(packageName: String): AppProfile? =
        appProfileDao.getProfileByPackage(packageName)

    suspend fun getProfileById(profileId: Int): AppProfile? =
        appProfileDao.getProfileById(profileId)

    suspend fun saveProfile(profile: AppProfile): Long =
        appProfileDao.insertProfile(profile)

    suspend fun updateProfile(profile: AppProfile) {
        appProfileDao.updateProfile(profile)
    }

    suspend fun deleteProfile(profile: AppProfile) {
        appProfileDao.deleteProfile(profile)
    }

    suspend fun deleteAllProfiles() {
        appProfileDao.deleteAllProfiles()
    }

    suspend fun updateProfileStatus(profileId: Int, enabled: Boolean) {
        appProfileDao.updateProfileStatus(profileId, enabled)
    }
}
