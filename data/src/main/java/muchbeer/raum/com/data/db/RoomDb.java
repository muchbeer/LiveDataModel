package muchbeer.raum.com.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import muchbeer.raum.com.data.model.Movie;

@Database(entities = {Movie.class},version = 1)
public abstract class RoomDb extends RoomDatabase {

    static final String DATABASE_NAME = "movie_db";
    private static RoomDb INSTANCE;
    public abstract MovieDao movieDao();

    private static final Migration MIGRATION_1_TO_2 = new Migration(1,2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tablename "
                    +  " ADD COLUMN description TEXT");
        }
    };

    public static synchronized RoomDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDb.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                  //  .addMigrations(MIGRATION_1_TO_2)
                  //  .addCallback(callback)
            .build();
        }
        return INSTANCE;
    }

    //addCallBack used to add dummy data
    //addMigrations is when you add other field and want to change the version
}
