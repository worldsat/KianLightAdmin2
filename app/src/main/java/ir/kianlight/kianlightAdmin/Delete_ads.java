package ir.kianlight.kianlightAdmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import ir.kianlight.kianlightAdmin.category.Category;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Delete_ads extends AppCompatActivity {
    MaterialDialog wait;
    public Context context;
    private Button Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_ads);

        //toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.app_bar_show_ads);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Button back_icon = (Button) findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delete_ads.this, Category.class);
                Delete_ads.this.startActivity(intent);
            }
        });
        TextView title_toolbar = (TextView) findViewById(R.id.title_toolbar);
        title_toolbar.setText("حذف آگهی");
        //end toolbar

        Bundle address = getIntent().getExtras();
        delete_ads(address.getString("id"));
        Home = (Button) findViewById(R.id.btn_home_delete_banner);


    }

    private void delete_ads(final String ID) {

        String url = "http://baghshemshad.ir/delete_banners.php";
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Delete_ads.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("ok")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //            wait.dismiss();
                                    //   Toast.makeText(Delete_ads.this, "آگهی شما حذف شد", Toast.LENGTH_LONG).show();
                                    // Intent intent = new Intent(Delete_ads.this, My_ads.class);
                                    // Delete_ads.this.startActivity(intent);
                                }
                            });
                        } else if (response.equals("no")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    wait.dismiss();
                                    Toast.makeText(Delete_ads.this, "خطا در حذف آگهی شما !", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else if (response.equals("null")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    wait.dismiss();
                                    Toast.makeText(Delete_ads.this, "خطا در اجرا کد !", Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        wait.dismiss();
                        Toast.makeText(Delete_ads.this, "خطایی بوجود آمده است لطفا دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", ID);

                return params;
            }

        };


        MyRequestQueue.add(stringRequest);


    }

    public void btn_home(View v) {
        Intent intent = new Intent(Delete_ads.this, Category.class);
        Delete_ads.this.startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
