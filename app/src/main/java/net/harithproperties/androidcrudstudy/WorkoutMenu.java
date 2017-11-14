package net.harithproperties.androidcrudstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Pika on 14/8/2017.
 */

public class WorkoutMenu extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);

        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnView=(Button)findViewById(R.id.btnView);


        btnAdd.setOnClickListener(this);
        btnView.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAdd:
                Intent intent = new Intent(WorkoutMenu.this, AddNewRecord.class);
                this.startActivity(intent);
                break;

            case R.id.btnView:
                Intent intent2 = new Intent(WorkoutMenu.this, SearchSQLiteActivity.class);
                this.startActivity(intent2);
                break;



            default:
                break;
        }

    }


}
