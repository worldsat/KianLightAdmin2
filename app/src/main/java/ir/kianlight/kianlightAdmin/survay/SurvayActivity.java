package ir.kianlight.kianlightAdmin.survay;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import ir.kianlight.kianlightAdmin.R;

public class SurvayActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survay);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        ProgressBar  progressBar = findViewById(R.id.progressBar2);

        getGraph getGraph = new getGraph();
        getGraph.connect(this, progressBar);


    }


    public void Graph(Context context, final int str0, final int str1, final int str2, final int str3, final int str4, final int str5) {


        GraphView graph = ((Activity) context).findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, str0),
                new DataPoint(2, str1),
                new DataPoint(3, str2),
                new DataPoint(4, str3),
                new DataPoint(5, str4),
                new DataPoint(6, str5),
                new DataPoint(7, 0)

        });

        //set lable
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{" ","دوستان", "نمایشگاه", "موتور جستجو", "تلگرام", "اینستاگرام", "پیام کوتاه"," "});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });

        series.setSpacing(50);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        graph.addSeries(series);
    }
}
