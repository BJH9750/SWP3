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


public class DetailActivity extends Activity {
    private TextView mTextView_num;
    private TextView mTextView_rcmd;
    private ImageView mImageView;
    private Spinner mSpinner;
    private ProgressBar mProgressBar;
    private BarChart barChart;

    public static String[] day= {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    public static String select_item = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomation);

        mTextView_num = findViewById(R.id.tv_peoplenum);
        mTextView_rcmd = findViewById(R.id.textView2);
        mSpinner = findViewById(R.id.spinner);
        mProgressBar = findViewById(R.id.progressBar);
        barChart = findViewById(R.id.graph);

        setBarChart();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, day);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onNothingSelected(AdapterView arg0) {
                // TODO Auto-generated method stub
            }
        });

    }



    public void setBarChart(){
        //TODO Implement Drawing BarChart


    }
}