package ir.kianlight.kianlightAdmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DecimalFormat;
import java.util.List;

import ir.kianlight.kianlightAdmin.article.NewArticle;


public class RVAdapterArticle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //consteraktor ra misazim

    private List<String> ID, ImageItems, NameItems, Image1Items, Image2Items, Image3Items, Image4Items, Image5Items, Image6Items, ImageArrayNItems;

    private List<String> IdItems, DescriptionItems, CategoryItems;


    //neshan dahandehe noe layout
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    //tarife interface
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private RecyclerView recyclerView;
    private Context context;
    private String Mode;
    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");
    private SharedPreferences list_food_number;


    public RVAdapterArticle(final Context context, List<String> NameItems, List<String> IdItems, List<String> ID, RecyclerView recyclerViewlist) {
        this.context = context;

        this.ImageItems = ImageItems;
        this.Image1Items = Image1Items;
        this.Image2Items = Image2Items;
        this.Image3Items = Image3Items;
        this.Image4Items = Image4Items;
        this.Image5Items = Image5Items;
        this.Image6Items = Image6Items;

        this.ID = ID;
        this.NameItems = NameItems;
        this.DescriptionItems = DescriptionItems;
        this.IdItems = IdItems;
        this.CategoryItems = CategoryItems;
        this.Mode = Mode;
        this.mRecyclerViewlist = recyclerViewlist;


//------------moshakhas kardane inke aya be entehaye list resideheim ya na---------------
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerViewlist.getLayoutManager();
        mRecyclerViewlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;

                }
            }
        });
//------------------------------------------------------------

    }

    @Override
    public int getItemViewType(int position) {
//moshakas kardane layout nemayesh-----viewtype
        return NameItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//----------taein noe holder ba tavajoh be viewtype
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.itemview_layout_article, parent, false);
            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, parent, false);
            LoadingViewHolder vh2 = new LoadingViewHolder(view);
            return vh2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //itemhaei ke bayad neshan dadeh shavad bayad inja vared shavad
        //check kardane inke kodam halate nemayesh darhale ejrast
        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            //Log.i("mohsenjamali", "onBindViewHolder22: " + NameItems.get(position));
            myViewHolder.name.setText(changeNumber(NameItems.get(position)));
            // glide

          /*  final String link = "http://ghomashe.com" + ImageItems.get(position);

            Glide.with(context)
                    .load(link)
                    .override(300, 300)
                    .error(R.mipmap.no_picture)
                    .placeholder(R.mipmap.no_picture)
                    .centerCrop()
                    .into(((MyViewHolder) holder).pic);
            //end glide

*/

            myViewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, NewArticle.class);
                        intent.putExtra("title", NameItems.get(position));
                        intent.putExtra("text", IdItems.get(position));
                        intent.putExtra("id", ID.get(position));

                        SharedPreferences pic_writer = context.getSharedPreferences("picture", 0);
                        SharedPreferences.Editor edit = pic_writer.edit();
                        edit.clear();
                        edit.putString("Editable?", "yes");
                        edit.putString("id", IdItems.get(position) );
                        edit.apply();
                        context.startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(context, "خطایی پیش آمده است لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final MaterialDialog delete_alert = new MaterialDialog.Builder(context)
                            .customView(R.layout.alert_delete, false)
                            .autoDismiss(false)
                            .show();
                    TextView delete_alert_no = (TextView) delete_alert.findViewById(R.id.alert_delete_no);
                    TextView delete_alert_yes = (TextView) delete_alert.findViewById(R.id.alert_delete_yes);
                    TextView danger = (TextView) delete_alert.findViewById(R.id.danger);

                    danger.setVisibility(View.GONE);


                    delete_alert_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            delete_alert.dismiss();


                        }
                    });
                    delete_alert_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            delete_alert.dismiss();
                            DateVolley volley = new DateVolley();

                            volley.article(context, "delete", "delete", "", "", "",ID.get(position) , recyclerView);

                        }
                    });
                }
            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
//tedad itemhara baraye nemayesh midahim
//goftim agar be entehaye list , meghdare null residim 0 bargardon vagar na tedade itemharo bargardon
        return NameItems == null ? 0 : NameItems.size();
    }


    //tarife holderhaye recyclerview
// ma chondota layout toye recyclerview darim pad bayad vase dotash holder tarif konim
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar1);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        //bayad ajzaye layout ro tarif konim

        TextView name, edit, delete;
        ImageView pic;

        // TextView card_txt = findViewById(R.id.card_basket_txt);
        private MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.title);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            // pic = itemView.findViewById(R.id.pic_item);

        }
    }

    //-----------------in method ro ham tarif mikonim--------
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    //-----inja ham moshakas mikonim le load etelaatw jadid bayad anjam beshe ya kheir
    public void setLoaded() {
        isLoading = false;
    }


    private String changeNumber(String num) {
        num = num.replaceAll("0", "۰");
        num = num.replaceAll("1", "۱");
        num = num.replaceAll("2", "۲");
        num = num.replaceAll("3", "۳");
        num = num.replaceAll("4", "۴");
        num = num.replaceAll("5", "۵");
        num = num.replaceAll("6", "۶");
        num = num.replaceAll("7", "۷");
        num = num.replaceAll("8", "۸");
        num = num.replaceAll("9", "۹");
        return num;
    }

    private String changeNumberToEN(String num) {
        num = num.replaceAll("۰", "0");
        num = num.replaceAll("۱", "1");
        num = num.replaceAll("۲", "2");
        num = num.replaceAll("۳", "3");
        num = num.replaceAll("۴", "4");
        num = num.replaceAll("۵", "5");
        num = num.replaceAll("۶", "6");
        num = num.replaceAll("۷", "7");
        num = num.replaceAll("۸", "8");
        num = num.replaceAll("۹", "9");
        return num;
    }


}
