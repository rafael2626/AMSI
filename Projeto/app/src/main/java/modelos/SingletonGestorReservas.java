package modelos;

import android.content.Context;

import com.example.projeto.R;

import java.util.ArrayList;

public class SingletonGestorReservas
{
    private static SingletonGestorReservas instance = null;
    private ArrayList<Reserva> reservas;
    private  RerservaBDHelper reservaBD = null;
    public static synchronized SingletonGestorReservas getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorReservas(context);
        return instance;
    }

    public SingletonGestorReservas(Context context) {
        //gerarDadosDinamicos();
        reservas = new ArrayList<>();


        reservaBD = new RerservaBDHelper(context);
    }

    private void gerarDadosDinamicos() {
        reservas = new ArrayList<>();
        reservas.add(new Reserva(1, 10,true, true));
        reservas.add(new Reserva(2, 5 , false , false));
        reservas.add(new Reserva(5, 7,false,true));



    public ArrayList<Reserva> getReservasBD() {
        return reservas;
    }

    public Reserva getReservassBD(int id) {
        for (Reserva r : reservas)
            if (r.getId() == id)
                return r;
        return null;
    }

    //
    public void adicionarReservaBD(Reserva r) {
        reservas.add(r);
    }

    public void editarLivroBD(Reserva r) {
        Reserva reservaAux = getReservassBD(r.getId());
        if (reservaAux != null) {
            reservaAux.setAndar(r.getAndar());
            reservaAux.setMini_bar(r.getMini_Bar());

        }
    }

    public void removerLivroBD(int id) {
        Reserva reservaAux = getLivrosBD(id);
        if (livroAux != null) {
            livros.remove(livroAux);

        }
    }
}

}
