package modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ReservaBDHelper extends SQLiteOpenHelper  {
    private static  final String DB_NAME="dbReserva";
    private static  final String TABLE_RESERVA="reservas";
    private static  final String ID="id",PESSOA="pessoa",ANDAR="andar",MINI_BAR="mini_bar",SUITE="suite";

    private static  final int DB_VERSION=1;
    private  final SQLiteDatabase db;

    public ReservaBDHelper(Context context) {
        super(context, DB_NAME,  null, DB_VERSION);
        this.db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableReserva="CREATE TABLE "+ TABLE_RESERVA +" ("+
                ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+
                PESSOA + "INTEGER NOT NULL ," +
                ANDAR +" INTEGER NOT NULL ," +
                SUITE +" INTEGER NOT NULL," +
                MINI_BAR+" INTEGER NOT NULL) ;" ;
        db.execSQL(createTableReserva);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String deleteTableReserva="DROP TABLE IF EXISTS "+ TABLE_RESERVA ;
        db.execSQL(deleteTableReserva);
        this.onCreate(db);

    }
    /*CRUD*/

    //INSERT
    public  Reserva adicionarReservaBD(Reserva r)
    {
        ContentValues values = new ContentValues();

        values.put(PESSOA,r.getPessoas());
        values.put(ANDAR,r.getAndar());
        values.put(MINI_BAR,r.getMini_Bar());
        values.put(SUITE,r.getSuite());

        // insert devolve o id do registo -1 em caso de error
        long id=this.db.insert(TABLE_RESERVA,null,values);
        if(id>-1)
        {
            r.setId((int)id);
            return  r;
        }
        return null;

    }
    //UPDATE
    public  boolean eidtarLivroBD(Reserva r)
    {
        ContentValues values = new ContentValues();

        values.put(PESSOA,r.getPessoas());
        values.put(ANDAR,r.getAndar());
        values.put(MINI_BAR,r.getMini_Bar());
        values.put(SUITE,r.getSuite());

        // update devolve o numero de linhas atualizadas
        int nlinhas=this.db.update(TABLE_RESERVA, values , ID + "= ?",new String[]{r.getId()+""});

        return nlinhas>0;

    }
    //DELETE
    public  boolean removerLivroBD(int id)
    {
        int nlinhas=this.db.delete(TABLE_RESERVA , ID + "= ?",new String[]{id+""});

        return nlinhas>0;

    }    //SELECT

    public ArrayList<Reserva> getAllLivrosBD()

    {
        ArrayList<Reserva> livros = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_RESERVA,new  String[]{ID , PESSOA ,ANDAR , MINI_BAR, SUITE}, null,null ,null, null,null);
        if(cursor.moveToFirst())
        {
            do{
                Reserva r = new Reserva(cursor.getInt(1),cursor.getInt(2),cursor(3),cursor.getString(4
                ),cursor.getString(5));
                r.setId(cursor.getInt(0));
                livros.add(r);
            }while (cursor.moveToNext());
        }

        return livros;

    }
}
