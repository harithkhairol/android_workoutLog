package net.harithproperties.androidcrudstudy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddNewRecord extends AppCompatActivity {
	private EditText txtExercise, txtWeight, txtRep;
	static TextView txtDate,txttime;
	private Button btnsave;
	private DBHelper mHelper;
	private SQLiteDatabase dataBase;
	Boolean EditTextEmptyHold;


	private String id,exercise,rep,date,weight,time;//to hold the data strings

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_record);
		
		mHelper=new DBHelper(this);
		dataBase = mHelper.getWritableDatabase();
		
		//initiate all textbox container to hold the data for the staff
		txtExercise=(EditText)findViewById(R.id.txtExercise);
		txtWeight=(EditText)findViewById(R.id.txtWeight);
		txtRep=(EditText)findViewById(R.id.txtRep);
		txtDate=(TextView) findViewById(R.id.txtDate);
		txttime=(TextView)findViewById(R.id.txttime);


		btnsave=(Button)findViewById(R.id.btnsavenew);
		btnsave.setOnClickListener( new OnClickListener(){
			public void onClick(View v){


				CheckEditTextStatus();

				saveData();
			}
		});//end btnsave setOnCLickCListener

		txtDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				showTruitonDatePickerDialog(v);

			}
		});

		txttime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTruitonTimePickerDialog(v);

			}
		});
	}

	public void showTruitonDatePickerDialog(View v){
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}


	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

		@Override
		public Dialog onCreateDialog(Bundle savedInstancState){

			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(getActivity(), this, year, month, day);

		}

		public void onDateSet(DatePicker view, int year, int month, int day){
			txtDate.setText(day+ "/" + (month+1) + "/" + year);

		}

	}

	public void showTruitonTimePickerDialog(View v){
		DialogFragment newFragment= new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");

	}

	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
		}
		public void onTimeSet(TimePicker view, int hourOfDay, int minute){

			txttime.setText(hourOfDay + ":" +minute);
		}

	}


	public void CheckEditTextStatus(){

		//capture amendment
		exercise=txtExercise.getText().toString();
		rep=txtRep.getText().toString();
		date=txtDate.getText().toString();
		weight=txtWeight.getText().toString();
		time=txttime.getText().toString();

		if(TextUtils.isEmpty(exercise) || TextUtils.isEmpty(rep) || TextUtils.isEmpty(date) || TextUtils.isEmpty(weight)  || TextUtils.isEmpty(time)){

			EditTextEmptyHold = false ;

		}
		else {

			EditTextEmptyHold = true ;
		}
	}

	public void saveData(){

		if(EditTextEmptyHold == true)
		{

			ContentValues values=new ContentValues();

			values.put(DBHelper.EXERCISE,exercise);
			values.put(DBHelper.WEIGHT,weight);
			values.put(DBHelper.DATE,date );
			values.put(DBHelper.REP,rep );
			values.put(DBHelper.TIME,time );

			System.out.println("");

			//save new record to the database into database
			dataBase.insert(DBHelper.TABLE_NAME, null, values);

			//close database
			dataBase.close();
			finish();

			Toast.makeText(AddNewRecord.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show();

		}
		else {

			Toast.makeText(AddNewRecord.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

		}

	}
	
	//save new record


}

