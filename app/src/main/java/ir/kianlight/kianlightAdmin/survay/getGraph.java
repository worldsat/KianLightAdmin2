package ir.kianlight.kianlightAdmin.survay;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getGraph {

    public void connect(final Context context,final ProgressBar progressBar) {

        progressBar.setVisibility(View.VISIBLE);
        String url = "http://www.kianlight.ir/kianlight/admin/getGraph.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array2 = jsonRootObject.optJSONArray("Data");
                            JSONObject person = array2.getJSONObject(0);

                            int a, b, c, d, e, f;

                            a = person.getInt("a");
                            b = person.getInt("b");
                            c = person.getInt("c");
                            d = person.getInt("d");
                            e = person.getInt("e");
                            f = person.getInt("f");

                            SurvayActivity survayActivity = new SurvayActivity();
                            survayActivity.Graph(context, a, b, c, d, e, f);
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.i("mohsenjamali", "token error: " + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);
                         Toast.makeText(context, "خطا در دریافت اطلاعات", Toast.LENGTH_LONG).show();
                        Log.i("mohsenjamali", "onErrorResponse: " + error);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                // params.put("email", username);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
