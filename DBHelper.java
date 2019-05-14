package pereira.juliane.construindosempre.Controller;

/**
 * Created by Anderson on 02/05/2019.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ConstruindoSempre.db";
    public static final String OBRAS_TABLE_NAME = "obras";
    public static final String OBRAS_COLUMN_ID = "id";
    public static final String OBRAS_COLUMN_NOME = "nome";
    public static final String OBRAS_COLUMN_ENDERECO = "endereco";
    public static final String OBRAS_COLUMN_RESPONSAVEL = "responsavel";
    public static final String OBRAS_COLUMN_TIPO = "tipo";
    public static final String OBRAS_COLUMN_DATA = "data";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    //criando tabela SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table obras " +
                        "(id integer primary key, nome text,endereco text,responsavel text, tipo text,data text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS obras");
        onCreate(db);
    }

    public boolean insertObra (String nome, String endereco, String responsavel, String tipo,String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", nome);
        contentValues.put("endereco", endereco);
        contentValues.put("responsavel", responsavel);
        contentValues.put("tipo", tipo);
        contentValues.put("data", data);
        db.insert("obras", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from obras where id="+id+" order by "+OBRAS_COLUMN_NOME+ " asc", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, OBRAS_TABLE_NAME);
        return numRows;
    }

    public boolean updateObra (Integer id, String nome, String endereco, String responsavel, String tipo,String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", nome);
        contentValues.put("endereco", endereco);
        contentValues.put("responsavel", responsavel);
        contentValues.put("tipo", tipo);
        contentValues.put("data", data);
        db.update("obras", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteObra (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("obras",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllObras() {
        ArrayList<String> array_list = new ArrayList<String>();
        try {
            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from obras order by " + OBRAS_COLUMN_NOME + " asc", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex(OBRAS_COLUMN_NOME)));
                res.moveToNext();
            }

        } catch (Exception e) {

        }
        return array_list;
    }
}
