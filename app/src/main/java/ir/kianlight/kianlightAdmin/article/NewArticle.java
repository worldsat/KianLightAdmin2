package ir.kianlight.kianlightAdmin.article;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ir.kianlight.kianlightAdmin.DateVolley;
import ir.kianlight.kianlightAdmin.R;
import ir.kianlight.kianlightAdmin.category.Category;

public class NewArticle extends AppCompatActivity {
    private Uri mCropImageUri;
    private EditText title_edt, text_edt;
    private ImageView image_category;
    private Bundle address;
    String id = "";
    private SharedPreferences pic_reader;
    DateVolley volley;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_article);


        //toolbar
        Toolbar my_toolbar = findViewById(R.id.app_bar_show_ads);
        setSupportActionBar(my_toolbar);
        Button back_icon = findViewById(R.id.back_icon_rtl);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewArticle.this, Category.class);
                NewArticle.this.startActivity(intent);
            }
        });
        TextView title_toolbar = findViewById(R.id.title_toolbar_rtl);
        title_toolbar.setText("ایجاد مقاله جدید");
        //toolbar end
        address = getIntent().getExtras();
        title_edt = findViewById(R.id.title_edt);
        text_edt = findViewById(R.id.text_edt);

        Button send_cat_btn = findViewById(R.id.send_cat_btn);
        pic_reader = getApplicationContext().getSharedPreferences("picture", 0);
        volley = new DateVolley();
        if (pic_reader.getString("Editable?", "nothing to show").equals("yes")) {
           // if (address.getString("is_edit", "nothing").equals("true")) {
                edit_ads();
                title_toolbar.setText("ویرایش مقاله");
                id = address.getString("id", "");
           // } else {
           //     id = "";
           //     if (pic_reader.getString("pic_category_edit", " ").equals("true")) {
           //         title_toolbar.setText("ویرایش مقاله");
           //         id = pic_reader.getString("id", " ");
             //   }
          //  }
        }

        final SharedPreferences pic_reader = getApplicationContext().getSharedPreferences("picture", 0);
        final String pic_category = pic_reader.getString("pic_category", "");

        send_cat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*    if (address.getString("is_edit", "nothing").equals("true")) {

                        volley.article(NewArticle.this, "category", "update", "", pic_category, title_edt.getText().toString(), id);
                    } else {
                        //  if (pic_reader.getString("pic_category_edit", " ").equals("true")) {
                        //      volley.article(NewArticle.this, "category", "update", "", pic_category, title_edt.getText().toString(), id);

                        //   } else {
                        volley.article(NewArticle.this, "save", "send", title_edt.getText().toString(), pic_category, text_edt.getText().toString(), id);
                        //   }
                    }

*/
//                if (address.getString("is_edit", "nothing").equals("true")) {

   //                 volley.article(NewArticle.this, "category", "update", "", pic_category, title_edt.getText().toString(), id);
     //           } else if (pic_reader.getString("Editable?", "nothing to show").equals("yes")) {

       //             id = pic_reader.getString("id", " ");
       //             volley.article(NewArticle.this, "category", "update", "", pic_category, title_edt.getText().toString(), id);

       //         } else {

                    volley.article(NewArticle.this, "save", "send", title_edt.getText().toString(), pic_category, text_edt.getText().toString(), id,recyclerView);
        //        }
            }
        });


    }

    private void edit_ads() {

        title_edt.setText(address.getString("title", ""));
        text_edt.setText(address.getString("text", ""));

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewArticle.this, Category.class);
        NewArticle.this.startActivity(intent);
    }
}