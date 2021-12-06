package modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ReservaBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbReservas";
    private static final String TABLE_RESERVAS = "reservas";
    private static final String ID = "id", ANDAR = "andar", PESSOAS = "pessoas", MINI_BAR = "mini_bar", SUITE = "suite";
    private static final int DB_VERSION = 1;
    private final SQLiteDatabase db;

    public ReservaBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableReserva = "CREATE TABLE " + TABLE_RESERVAS + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                ANDAR + " INTEGER NOT NULL, " +
                PESSOAS + " INTEGER NOT NULL, " +
                MINI_BAR + " BOOLEAN NOT NULL, " +
                SUITE + " BOOLEAN NOT NULL);";

        sqLiteDatabase.execSQL(createTableReserva);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String deleteTableReserva = "DROP TABLE IF EXISTS " + TABLE_RESERVAS;
        sqLiteDatabase.execSQL(deleteTableReserva);

        onCreate(sqLiteDatabase);
    }

    public void adicionarReservaDB(Reserva r) {

        ContentValues values = new ContentValues();
        values.put(ID, r.getId());
        values.put(ANDAR, r.getAndar());
        values.put(PESSOAS, r.getPessoas());
        values.put(SUITE, r.getSuite());
        values.put(MINI_BAR, r.getMini_Bar());


        this.db.insert(TABLE_RESERVAS, null, values);
    }

    public Boolean editarRerservaDB(Reserva r) {

        ContentValues values = new ContentValues();
        values.put(ID, r.getId());
        values.put(ANDAR, r.getAndar());
        values.put(PESSOAS, r.getPessoas());
        values.put(SUITE, r.getSuite());
        values.put(MINI_BAR, r.getMini_Bar());


        //update devolve o numero de linhas atualizadas
        int nlinhas = this.db.update(TABLE_RESERVAS, values, ID + "=?", new String[]{l.getId() + ""});

        return (nlinhas > 0);
    }

    public Boolean removerReserva(int id) {

        //delete devolve o numero de linhas eliminadas
        int nlinhas = this.db.delete(TABLE_RESERVAS, ID + "=?", new String[]{id + ""});

        return (nlinhas > 0);
    }

    public void removerAllReservas() {
        this.db.delete(TABLE_RESERVAS, null, null);
    }


    public ArrayList<Reserva> getAllReservaDB(){

        ArrayList<Reserva> Reservas = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_RESERVAS, new String[]{ID,ANDAR,PESSOAS,SUITE,MINI_BAR,},null,null,null,null,null);

        if(cursor.moveToFirst()){
            do {
                Reserva r = new Reserva(cursor.getInt(0),cursor.getInt(1),cursor.getExtras().getBoolean(MINI_BAR,true), cursor.getString(3),cursor.getString(4),cursor.getString(5));
                reservas.add(r);
            }while (cursor.moveToNext());
        }
        return reservas;
    }
}
