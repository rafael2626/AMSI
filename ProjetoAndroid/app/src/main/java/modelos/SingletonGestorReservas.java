package modelos;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Listeners.LoginListener;
import Listeners.ReservaListener;
import Listeners.ReservasListener;
import utils.ReservaJsonParser;
import vistas.DetalhesReservaActivity;

public class SingletonGestorReservas
{
    private static final String mUrlAPIReservas = "http://amsi.dei.estg.ipleiria.pt/api/reservas";
    private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";
    private static final String TOKEN = "AMSI-TOKEN";
    private static SingletonGestorReservas instance = null;
    private static RequestQueue volleyQueue = null;
    private ArrayList<Reserva> reservas;

    private ReservaBDHelper reservasBD = null;
    private ReservasListener reservasListener;
    private ReservaListener reservaListener;
    private LoginListener loginListener;

    public SingletonGestorReservas(Context context) {
        //gerarDadosDinamicos();
        reservas = new ArrayList<>();

        reservasBD = new ReservaBDHelper(context);
    }

    public static synchronized SingletonGestorReservas getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorReservas(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    public void setReservasListener( ReservasListener reservasListener) {
        this.reservasListener = reservasListener;
    }

    public void setReservaListener(ReservaListener reservaListener) {
        this.reservaListener = reservaListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public Reserva getReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id)
                return r;
        }
        return null;
    }

    //region Accesso BDLocal

    public ArrayList<Reserva> getReservasBD() {
        reservas = reservasBD.getAllReservaBD();

        return reservas;
    }

    public void adicionarReservaBD(Reserva r) {
        reservasBD.adicionarReservaBD(r);
    }


    public void adicionarReservasBD(ArrayList<Reserva> reservas) {
        // Apagar todos os livros da BD
        reservasBD.removerAllReservasBD();

        // Adicionar novos livros
        for (Reserva r : reservas) {
            reservasBD.adicionarReservaBD(r);
        }
    }



    public void editarReservaBD(Reserva r) {
        Reserva reservaAux = getReserva(r.getId());

        if (reservaAux != null) {
            reservasBD.editarRerservaBD(r);
        }
    }

    public void removerReservaBD(int id) {
        Reserva reservaAux = getReserva(id);
        if (reservaAux != null)
            reservasBD.removerReservaBD(id);
    }

    //endregion

    //region AcessoAPI

    public void adicionarReservaAPI(final Reserva r, final Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á Internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIReservas, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarReservaBD(ReservaJsonParser.parserJsonReserva(response));

                    // Informar e Atualizar as vistas
                    if (reservaListener != null)
                        reservaListener.onRefreshDetalhes(DetalhesReservaActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("token", TOKEN);
                    params.put("pessoas", r.getPessoas()+ "");
                    params.put("andar", r.getAndar()+ "");
                    params.put("minibar", r.getMini_Bar()+ true);
                    params.put("suite", r.getSuite() + "");


                    return params;
                }
            };

            volleyQueue.add(request);
        }
    }

    public void editarLivroAPI(final Reserva r, final Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á Internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.PUT, mUrlAPIReservas + "/" + r.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editarReservaBD(r);

                    // Informar e Atualizar as vistas
                    if (reservaListener != null)
                        reservaListener.onRefreshDetalhes(DetalhesReservaActivity.EDIT);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("token", TOKEN);
                    params.put("pessoas", r.getPessoas()+ "");
                    params.put("andar", r.getAndar()+ "");
                    params.put("minibar", r.getMini_Bar()+ true);


                    return params;
                }
            };

            volleyQueue.add(request);
        }
    }

    public void removerLivroAPI(final Reserva r, final Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á Internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.DELETE, mUrlAPIReservas + "/" + r.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removerReservaBD(r.getId());

                    // Informar e Atualizar as vistas
                    if (reservaListener != null)
                        reservaListener.onRefreshDetalhes(DetalhesReservaActivity.REMOVE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(request);
        }
    }

    public void getAllLivrosAPI(final Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á Internet", Toast.LENGTH_SHORT).show();

            // Informar e Atualizar as vistas
            if (reservasListener != null)
                reservasListener.onRefreshListaReservas(reservasBD.getAllReservaBD());
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIReservas, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reservas = ReservaJsonParser.parserJsonReservas(response);

                    adicionarReservasBD(reservas);

                    // Informar e Atualizar as vistas
                    if (reservasListener != null)
                        reservasListener.onRefreshListaReservas(reservas);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(request);
        }
    }

    //endregion

    public void loginAPI(final String email, final String password, final Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "Sem ligação á Internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest request = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // TODO: Criar método com parse da resposta (JsonParser na dir utils)

                    if (loginListener != null)
                        loginListener.onValidateLogin(TOKEN, email, context);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("email", email);
                    params.put("password", password);

                    return params;
                }
            };

            volleyQueue.add(request);
        }
    }
}