package Listeners;

import android.content.Context;

public interface LoginListener {
    void onValidateLogin(final String token, final String email, final Context context);
}

