package id.idzhami.screening_test.data.sqLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getDoubleOrNull
import id.idzhami.screening_test.data.models.event_model
import id.idzhami.screening_test.data.models.person_models

class DbAdapter(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_PERSON($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT , $COL_IMAGE BLOB);")
        db.execSQL("CREATE TABLE $TABLE_EVENTS($COL_ID_EVENT INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TITLE_NAME TEXT , $COL_DESCRIPTION TEXT,$COL_DATE_TIME TEXT, $COL_LATITUDE INTEGER, $COL_LONGTITUDE INTEGER);")
    }

    private fun dropDb(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE $TABLE_PERSON;")
        db.execSQL("DROP TABLE $TABLE_EVENTS;")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        this.dropDb(db)
        onCreate(db)
    }

    inner class TablePersons {
        fun add(caller_name: String, caller_image: ByteArray): Long {
            val db = writableDatabase;
            val newContentoffline = ContentValues()
            newContentoffline.put(COL_NAME, caller_name)
            newContentoffline.put(COL_IMAGE, caller_image)
            return db.insert(TABLE_PERSON, null, newContentoffline)
        }

        fun delete(id: String): Int {
            val db = writableDatabase
            return db.delete(TABLE_PERSON, "caller_id = ?", arrayOf(id))
        }

        fun update(id: String, name: String, image: ByteArray) {
            val db = writableDatabase
            val newContentoffline = ContentValues()
            newContentoffline.put(COL_ID, id)
            newContentoffline.put(COL_NAME, name)
            newContentoffline.put(COL_NAME, image)
            db.update(TABLE_PERSON, newContentoffline, "$COL_ID = ?", arrayOf(id))
        }

        fun getData(id: String): person_models? {
            val db = writableDatabase
            val res = db.rawQuery("SELECT * FROM ${TABLE_PERSON} WHERE $COL_ID = ?", arrayOf(id))

            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    val model =
                        person_models(
                            res.getString(0),
                            res.getString(1),
                            res.getBlob(2)
                        )
                    return model
                }
            }
            res.close()
         return null
        }

    }

    inner class TableEvents {
        fun initDummy() {
            val dummyArr = listOf(
                event_model(
                    1,
                    "Test 1",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "23 Jan 2022 08:24 AM",
                    -6.8902647,
                    107.6146987
                ),
                event_model(
                    2,
                    "Test 2",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "23 Feb 2022 08:24 AM",
                    -6.890997,107.6137116
                ),
                event_model(
                    3,
                    "Test 3",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "23 May 2022 08:24 AM",
                    null,
                    null
                ),
                event_model(
                    4,
                    "Test 4",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    "23 April 2022 08:24 AM",
                    null,
                    null
                )
            )
            for (i in dummyArr) {
                add(i)
            }
        }

        fun add(title: String, desc: String, dateEvent: String, lat: Long, lng: Long) {
            val db = writableDatabase;
            val newContentoffline = ContentValues()
            newContentoffline.put(COL_TITLE_NAME, title)
            newContentoffline.put(COL_DESCRIPTION, desc)
            newContentoffline.put(COL_DATE_TIME, dateEvent)
            newContentoffline.put(COL_LATITUDE, lat)
            newContentoffline.put(COL_LONGTITUDE, lng)
            db.insert(TABLE_EVENTS, null, newContentoffline)
        }

        fun add(model: event_model) {
            val db = writableDatabase;
            val newContentoffline = ContentValues()
            newContentoffline.put(COL_ID_EVENT, model.Id)
            newContentoffline.put(COL_TITLE_NAME, model.Title)
            newContentoffline.put(COL_DESCRIPTION, model.Description)
            newContentoffline.put(COL_DATE_TIME, model.DateTime)
            newContentoffline.put(COL_LATITUDE, model.Lat)
            newContentoffline.put(COL_LONGTITUDE, model.Lng)
            db.insert(TABLE_EVENTS, null, newContentoffline)
        }

            fun update(id: String, lat: Double, lgn: Double) {
            val db = writableDatabase
            val newContentoffline = ContentValues()
            newContentoffline.put(COL_ID_EVENT, id)
            newContentoffline.put(COL_LATITUDE, lat)
            newContentoffline.put(COL_LONGTITUDE, lgn)
            db.update(TABLE_EVENTS, newContentoffline, "$COL_ID_EVENT = ?", arrayOf(id))
        }

        fun getAllData(): ArrayList<event_model> {
            val db = writableDatabase
            val res = db.rawQuery("SELECT * FROM ${TABLE_EVENTS}", null)
            val eventList = ArrayList<event_model>()
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    val model =
                        event_model(
                            res.getInt(0),
                            res.getString(1),
                            res.getString(2),
                            res.getString(3),
                            res.getDoubleOrNull(4),
                            res.getDoubleOrNull(5)
                        )

                    eventList.add(model)
                    res.moveToNext()
                }
            }
            res.close()
            return eventList
        }

    }

    companion object {
        private val DATABASE_NAME = "Person.db"
        private val TABLE_PERSON = "tblPerson"
        private val COL_ID = "caller_id"
        private val COL_NAME = "caller_name"
        private val COL_IMAGE = "image_user"

        private val TABLE_EVENTS = "tbEvents"
        private val COL_ID_EVENT = "id_event"
        private val COL_TITLE_NAME = "title_name"
        private val COL_DESCRIPTION = "description"
        private val COL_DATE_TIME = "date_time"
        private val COL_LATITUDE = "latitude"
        private val COL_LONGTITUDE = "longtitude"
    }

}