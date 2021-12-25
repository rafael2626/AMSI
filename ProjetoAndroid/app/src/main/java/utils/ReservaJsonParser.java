package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import modelos.Reserva;

public class ReservaJsonParser {
    public static ArrayList<Reserva> parserJsonReservas(JSONArray response) {
        ArrayList<Reserva> reservas = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject reserva = (JSONObject) response.get(i);
                int id = reserva.getInt("id");
                int pessoas = reserva.getInt("pessoas");
                int andar = reserva.getInt("andar");
                boolean minibar = reserva.getBoolean("minibar");
                boolean suite = reserva.getBoolean("suite");
                Reserva auxReserva = new Reserva(id, pessoas, andar, minibar, suite);

                reservas.add(auxReserva);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    // Receber uma reserva
    public static Reserva parserJsonReserva(String response) {

        Reserva auxReserva = null;

        try {
            JSONObject reserva = new JSONObject(response);
            int id = reserva.getInt("id");
            int pessoas = reserva.getInt("pessoas");
            int andar = reserva.getInt("andar");

            boolean minibar = reserva.getBoolean("minibar");
            boolean suite = reserva.getBoolean("suite");
            auxReserva = new Reserva (id, pessoas, andar, minibar, suite);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return auxReserva;
    }

    public static String parserJsonLogin() {

        return null;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
