package modelos;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.projeto.R;

import java.util.ArrayList;

public class SingletonGestorReservas
{
    private static final String mUrlAPILivros = "http://amsi.dei.estg.ipleiria.pt/api/livros";
    private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";
    private static final String TOKEN = "AMSI-TOKEN";
    private static SingletonGestorReservas instance = null;
    private static RequestQueue volleyQueue = null;
    private ArrayList<Reserva> reservas;

   private ReservaBDHelper reservasBD = null;
   /* private LivrosListener livrosListener;
    private LivroListener livroListener;
    private LoginListener loginListener;*/


    public SingletonGestorReservas(Context  context) {

        reservas = new ArrayList<>();

        reservasBD = new ReservaBDHelper(context);
    }

    public static synchronized SingletonGestorReservas getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorReservas(context);
            volleyQueue = Volley.newRequestQueue(context);

        return instance;
    }

    public SingletonGestorReservas(Context context) {
        //gerarDadosDinamicos();
        reservas = new ArrayList<>();


        reservasBD = new ReservaBDHelper(context);
    }

    private void gerarDadosDinamicos() {
        reservas = new ArrayList<>();
        reservas.add(new Reserva(1, 10, true, true));
        reservas.add(new Reserva(2, 5, false, false));
        reservas.add(new Reserva(5, 7, false, true));

    }

    public ArrayList<Reserva> getReservasBD() {
            reservas = reservasBD.getAllReservaDB();

            return reservas;
    }

    public void adicionarReservaBD(Reserva r) {
        reservasBD.adicionarReservaDB(r);
    }



    public void adicionarReservaSBD(ArrayList<Reserva> reservas) {
        // Apagar todos os livros da BD
        reservasBD.removerAllReservas();

        // Adicionar novos livros
        for (Reserva l : reservas) {
            reservasBD.adicionarReservaDB(l);
        }
    }






    public Reserva getReserva(int id) {
        for (Reserva r : reservas)
            if (r.getId() == id)
                return r;
        return null;
    }

    //


    public ArrayList<Reserva> getLivrosBD() {
        reservas = reservasBD.getAllReservaDB();

        return reservas;
    }





    public void editarLivroBD(Reserva r) {
        Reserva reservaAux = getReservasBD(r.getId());
        if (reservaAux != null) {
            reservaAux.setAndar(r.getAndar());
            reservaAux.setMini_bar(r.getMini_Bar());

        }
    }
    public void adicionarLivrosBD(ArrayList<Livro> livros) {
        // Apagar todos os livros da BD
        reservasBD.re();

        // Adicionar novos livros
        for (Livro l : livros) {
            livrosBD.adicionarLivroBD(l);
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
