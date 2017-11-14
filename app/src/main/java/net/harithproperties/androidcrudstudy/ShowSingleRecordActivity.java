package net.harithproperties.androidcrudstudy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowSingleRecordActivity extends AppCompatActivity {

    String IDholder;
    private TextView txtExercise, txtWeight, txtRep,txtDate,txttime;
    SQLiteDatabase sqLiteDatabase;
    DBHelper sqLiteHelper;
    Cursor cursor;
    Button Delete, Edit;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    private AlertDialog.Builder build;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        txtExercise=(TextView)findViewById(R.id.txtExercise);
        txtWeight=(TextView)findViewById(R.id.txtWeight);
        txtRep=(TextView)findViewById(R.id.txtRep);
        txtDate=(TextView)findViewById(R.id.txtDate);
        txttime=(TextView)findViewById(R.id.txttime);

        Delete = (Button)findViewById(R.id.buttonDelete);
        Edit = (Button)findViewById(R.id.buttonEdit);

        sqLiteHelper = new DBHelper(this);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //invoking AlertDialog box
                build = new AlertDialog.Builder(ShowSingleRecordActivity.this);
                build.setTitle("Delete exercise " + txtExercise.getText());
                build.setMessage("Are you sure you want to delete the exercise?");

                //user select UPDATE
                build.setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                OpenSQLiteDataBase();

                                SQLiteDataBaseQueryHolder = "DELETE FROM "+DBHelper.TABLE_NAME+" WHERE exerciseid = "+IDholder+"";

                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                                sqLiteDatabase.close();

                                finish();
                                Toast.makeText(
                                        getApplicationContext(),
                                        txtExercise.getText()+
                                                " is deleted.", Toast.LENGTH_SHORT).show();


                            }
                        });//end UPDATE

                //user select DELETE
                build.setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });//end DELETE
                AlertDialog alert = build.create();
                alert.show();





            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),UpdateRecord.class);

                intent.putExtra("EditID", IDholder);

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {

        ShowSingleRecordInTextView();

        super.onResume();
    }

    public void ShowSingleRecordInTextView() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        IDholder = getIntent().getStringExtra("exerciseid");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE exerciseid = " + IDholder + "", null);

        if (cursor.moveToFirst()) {

            do {

                //get data from field and transfer to EditText
                txtExercise.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.EXERCISE)));
                txtWeight.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.WEIGHT)));
                txtRep.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.REP)));
                txtDate.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.DATE)));
                txttime.setText(cursor.getString(cursor.getColumnIndex(sqLiteHelper.TIME)));
            }
            while (cursor.moveToNext());

            cursor.close();

        }
    }

    public void OpenSQLiteDataBase(){

        sqLiteDatabaseObj = openOrCreateDatabase(sqLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
}