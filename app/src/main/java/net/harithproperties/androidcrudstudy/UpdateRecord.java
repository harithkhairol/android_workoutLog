package net.harithproperties.androidcrudstudy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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

public class UpdateRecord extends AppCompatActivity {
	private EditText txtExercise, txtWeight, txtRep;
	static TextView txtDate,txttime;
	private Button btnsave;
	private DBHelper mHelper;
	private SQLiteDatabase dataBase;
	private String id,exercise,rep,date,dept,time;//to hold the data strings
	private AlertDialog.Builder build;
	Boolean EditTextEmptyHold;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_record);
		
		mHelper=new DBHelper(this);
		dataBase = mHelper.getWritableDatabase();
		
		//capture sent parazmeter from previous screen
		id=getIntent().getExtras().getString("EditID");
		
		//initiate all textbox container to hold the data for the staff
		txtExercise=(EditText)findViewById(R.id.txtExercise);
		txtWeight=(EditText)findViewById(R.id.txtWeight);
		txtRep=(EditText)findViewById(R.id.txtRep);
		txtDate=(TextView)findViewById(R.id.txtDate);
		txttime=(TextView)findViewById(R.id.txttime);

		//display field record inside the textboxes
		displayData();
		
		btnsave=(Button)findViewById(R.id.btnsave);
		btnsave.setOnClickListener( new OnClickListener(){
			public void onClick(View v){

				//invoking AlertDialog box
				build = new AlertDialog.Builder(UpdateRecord.this);
				build.setTitle("Update exercise " + txtExercise.getText());
				build.setMessage("Are you sure you want to update the exercise?");

				//user select UPDATE
				build.setNegativeButton("Update",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {
								//capture amendment
								exercise=txtExercise.getText().toString();
								rep=txtRep.getText().toString();
								date=txtDate.getText().toString();
								dept=txtWeight.getText().toString();
								time=txttime.getText().toString();

								CheckEditTextStatus();
								saveData();

								Toast.makeText(
										getApplicationContext(),
										txtExercise.getText()+
												" is updated.", Toast.LENGTH_SHORT).show();


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
		DialogFragment newFragment = new UpdateRecord.DatePickerFragment();
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
		DialogFragment newFragment= new UpdateRecord.TimePickerFragment();
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





	//display single record of data from stafid
	private void displayData() {
		
		//the SQL command to fetched all records from the table
		String sql="SELECT * FROM "
				+ DBHelper.TABLE_NAME +" WHERE exerciseid='"+id+"';";
		Cursor mCursor = dataBase.rawQuery(sql, null);
		
		//fetch the record
		if (mCursor.moveToFirst()) {
			//fetch each field and transfer to textbox
			rep=mCursor.getString(mCursor.getColumnIndex(DBHelper.REP));
			date=mCursor.getString(mCursor.getColumnIndex(DBHelper.DATE));
			exercise=mCursor.getString(mCursor.getColumnIndex(DBHelper.EXERCISE));
			time=mCursor.getString(mCursor.getColumnIndex(DBHelper.TIME));

			//get data from field and transfer to EditText
			txtExercise.setText(exercise);
			txtWeight.setText(mCursor.getString(mCursor.getColumnIndex(DBHelper.WEIGHT)));
			txtRep.setText(rep);
			txtDate.setText(date);
			txttime.setText(time);

		}



		else{
			//do something here if no record fetched from database
			txtExercise.setText(sql);
		}
	}//end displayData

	public void CheckEditTextStatus(){

		//capture amendment
		exercise=txtExercise.getText().toString();
		rep=txtRep.getText().toString();
		date=txtDate.getText().toString();
		dept=txtWeight.getText().toString();
		time=txttime.getText().toString();

		if(TextUtils.isEmpty(exercise) || TextUtils.isEmpty(rep) || TextUtils.isEmpty(date) || TextUtils.isEmpty(dept)  || TextUtils.isEmpty(time)){

			EditTextEmptyHold = false ;

		}
		else {

			EditTextEmptyHold = true ;
		}
	}
	
	//save updated data
	private void saveData(){

		if(EditTextEmptyHold == true)
		{

			//dataBase=mHelper.getWritableDatabase();
			ContentValues values=new ContentValues();

			values.put(DBHelper.EXERCISE,exercise);
			values.put(DBHelper.DATE,date );
			values.put(DBHelper.REP,rep );
			values.put(DBHelper.WEIGHT,dept);
			values.put(DBHelper.TIME,time);

			System.out.println("");

			//update database with new data
			dataBase.update(DBHelper.TABLE_NAME, values, DBHelper.EXERCISEID+"="+id, null);

			//close database
			dataBase.close();
			finish();

			Toast.makeText(UpdateRecord.this,"Data Update Successfully", Toast.LENGTH_LONG).show();

		}
		else {

			Toast.makeText(UpdateRecord.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

		}

		
		
	}

}
