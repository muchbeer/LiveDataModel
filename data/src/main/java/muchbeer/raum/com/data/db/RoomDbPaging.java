package muchbeer.raum.com.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.datasource.LocalDataSourcePaging;

@Database(entities = {Movie.class},version = 2, exportSchema = false)
public abstract class RoomDbPaging extends RoomDatabase {

    static final String DATABASE_NAME = "movie2_db";
     private static RoomDbPaging INSTANCE;

    public abstract MovieDaoPaging movieDaoP();


    private static final Migration MIGRATION_1_TO_2 = new Migration(1,2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tablename "
                    +  " ADD COLUMN description TEXT");
        }
    };

    public static synchronized RoomDbPaging getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDbPaging.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    //  .addMigrations(MIGRATION_1_TO_2)
                    //  .addCallback(callback)
                    .build();

        }
        return INSTANCE;
    }


}
