package edu.skku.swp3.test2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;


public class DetailActivity extends Activity {
    private TextView mTextView_num;
    private TextView mTextView_rcmd;
    private ImageView mImageView;
    private Spinner mSpinner;
    private ProgressBar mProgressBar;

    private BarChart barChart;


    public static String[] day= {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomation);

        mTextView_num = (TextView) findViewById(R.id.tv_peoplenum);
        mTextView_rcmd = (TextView) findViewById(R.id.textView2);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        barChart = (BarChart) findViewById(R.id.graph);

        setBarChart();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, day);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                setBarChart();
            }
            @Override
            public void onNothingSelected(AdapterView arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    // 가로축 시간 9칸
    // 세로축 인원
    // 참고 https://www.numetriclabz.com/android-bar-chart-using-mpandroidchart-library-tutorial/
    public void setBarChart(){
        //TODO Implement Drawing BarChart
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i =0; i < 9; i++){
            entries.add(new BarEntry(i+10,i));
        }
        BarDataSet dataSet = new BarDataSet(entries,"People Number");

        ArrayList<String> labels = new ArrayList<>();
        for(int i=0; i <= 24; i+=3){
            labels.add(Integer.toString(i));
        }

        BarData data = new BarData(labels, dataSet);
        barChart.setData(data);
    }
}
