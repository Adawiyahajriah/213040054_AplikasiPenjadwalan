package id.ac.unpas.aplikasipenjadwalan.dataimport android.content.Contextimport androidx.room.Databaseimport androidx.room.Roomimport androidx.room.RoomDatabaseimport id.ac.unpas.aplikasipenjadwalan.data.dao.UserDaoimport id.ac.unpas.aplikasipenjadwalan.data.entity.User@Database(entities = [User::class], version = 1)abstract class AppDatabase : RoomDatabase() {    abstract fun userDao(): UserDao    companion object {        @Volatile        private var instance: AppDatabase? = null        fun getInstance(context: Context): AppDatabase {            return instance ?: synchronized(this) {                val newInstance = Room.databaseBuilder(                    context.applicationContext,                    AppDatabase::class.java,                    "app-Database"                )                    .fallbackToDestructiveMigration()                    .allowMainThreadQueries()                    .build()                instance = newInstance                newInstance            }        }    }}