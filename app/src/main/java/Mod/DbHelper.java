package Mod;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NOMBRE = "producto.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLA_PRODUCTO= "producto";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override


    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = " CREATE TABLE producto ( " +
                "id INTEGER NOT NULL," +
                "nombre	TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "costo	NUMERIC," +
                "precio	NUMERIC," +
                "stock	NUMERIC," +
                "fecha	TEXT," +
                "PRIMARY KEY(id));";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void noQuery( String sql){
        this.getWritableDatabase().execSQL(sql);
    }

    public Cursor query(String sql){
      return  this.getReadableDatabase().rawQuery(sql,null);
    }
}
