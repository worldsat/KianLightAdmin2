package ir.kianlight.kianlightAdmin.article;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ir.kianlight.kianlightAdmin.DateVolley;
import ir.kianlight.kianlightAdmin.MainActivity;
import ir.kianlight.kianlightAdmin.R;

public class Article extends AppCompatActivity {
    private TextView tryagain_txt;
    private Button tryagain_btn;
    private ProgressBar progressBarOne;
    private RecyclerView recyclerView;
    private DateVolley datavolly;
    private SwipeRefreshLayout refreshLayout;
    private MaterialDialog wait;
    private String url;
    private int rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        //toolbar
        Toolbar my_toolbar = findViewById(R.id.app_bar_show_ads);
        setSupportActionBar(my_toolbar);
        Button back_icon = findViewById(R.id.back_icon_rtl);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Article.this, MainActivity.class);
                Article.this.startActivity(intent);
            }
        });
        TextView title_toolbar = findViewById(R.id.title_toolbar_rtl);
        title_toolbar.setText("مقاله");
        //toolbar end
        datavolly = new DateVolley();
        //refresh
        refreshLayout = findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CheckNet();

                //Download data from net and update list
                refreshLayout.setRefreshing(false);
            }
        });
        //end refresh
        recyclerView = findViewById(R.id.View);
        tryagain_txt = findViewById(R.id.try_again_txt);
        tryagain_btn = findViewById(R.id.try_again_btn);
        progressBarOne = findViewById(R.id.progressBarOne);
        tryagain_txt.setVisibility(View.GONE);
        tryagain_btn.setVisibility(View.GONE);


        tryagain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarOne.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tryagain_txt.setVisibility(View.GONE);
                tryagain_btn.setVisibility(View.GONE);

                CheckNet();
            }
        });
        CheckNet();

        //khali kardan aks category dar SharedPreferences
        SharedPreferences pic_writer = getApplicationContext().getSharedPreferences("picture", 0);
        pic_writer.edit().clear().apply();
        //end


        TextView new_article = findViewById(R.id.new_article);
        new_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Article.this, NewArticle.class);
                Article.this.startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Article.this, MainActivity.class);
        Article.this.startActivity(intent);
    }

    private void CheckNet() {


        boolean connect;

        ConnectivityManager connectivityManager = (ConnectivityManager) Article.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connect = true;
        } else {
            connect = false;
        }

        if (connect) {
            try {

                recyclerView.setVisibility(View.VISIBLE);
                tryagain_txt.setVisibility(View.GONE);
                tryagain_btn.setVisibility(View.GONE);


                //datavolly.article(Article.this, "title","get","","","","", recyclerView, tryagain_txt, tryagain_btn, progressBarOne);
                datavolly.article(Article.this, "title", "get", "", "", "", "", recyclerView);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tryagain_txt.setVisibility(View.VISIBLE);
                    tryagain_btn.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    tryagain_txt.setText("عدم دسترسی به اینترنت !");
                    progressBarOne.setVisibility(View.GONE);
                }
            }, 1000);


        }
    }
}
