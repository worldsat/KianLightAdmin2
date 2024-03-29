package ir.kianlight.kianlightAdmin.comment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class UpdateComment {

    private MaterialDialog wait;

    public void Update(final Context context, final String ItemId, final String ItemVisible) {
        wait = new MaterialDialog.Builder(context)
                .cancelable(false)
                .content("لطفا منتظر بمانید")
                .progress(true, 0)
                .build();
        wait.show();
        final SharedPreferences sp = context.getSharedPreferences("Token", 0);
        String JsonUrl = "http://www.kianlight.ir/kianlight/admin" + "/updateComment.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                switch (response) {
                    case "ok":

                        Toast.makeText(context, "انجام شد", Toast.LENGTH_SHORT).show();
                        wait.dismiss();

                        break;
                    case "error":

                        Toast.makeText(context, "خطا در انجام دستور", Toast.LENGTH_SHORT).show();
                        wait.dismiss();

                        break;

                }
                Intent intent = new Intent(context, CommentActivity.class);
                context.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("mohsenjamali", "onErrorResponsePlusItem: " + error);
                wait.dismiss();

                Toast.makeText(context, "خطای سرور", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> Data = new HashMap<>();
                Data.put("Api_Token", sp.getString("token", "nothing"));
                Data.put("visible", ItemVisible);
                Data.put("id", ItemId);
                return Data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue Request = Volley.newRequestQueue(context);
        Request.add(stringRequest);
    }
}
