package com.service

import com.dao.NormalizedUrlDao
import com.dao.OrginalUrlDao
import com.model.DataRecord
import com.model.DataStore
import java.sql.SQLException


object DatabaseService {
    /*
    Persisting a hashmap of dataRecords into database
        Input:
            Hashmap of dataRecords

        Output:

 */
    fun save(heap: HashMap<String, DataRecord>) {
        var c = 0
        val con = DBConnection.getConnection()
        try {
            con?.autoCommit = false
            NormalizedUrlDao.setConnection(con)
            heap.forEach {
                NormalizedUrlDao.add(it.value)
            }
            NormalizedUrlDao.flush()
            OrginalUrlDao.setConnection(con)
            heap.forEach {
                OrginalUrlDao.Add(it.value)
            }
            OrginalUrlDao.flush()
            con?.commit()
        } catch (e: SQLException){
            con?.rollback()
        } finally {
            con?.close()
        }
        DataStore.recordsArray.removeAll(DataStore.recordsArray)
    }

}