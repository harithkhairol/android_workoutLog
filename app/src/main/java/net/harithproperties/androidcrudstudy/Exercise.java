package net.harithproperties.androidcrudstudy;

/**
 * Created by Pika on 9/7/2017.
 */

public class Exercise {

    String exerciseid = null;

    String exercise = null;
    String weight = null;
    String rep = null;
    String date = null;
    String time = null;





    public Exercise(String Sexercise, String Sweight, String Srep, String Sdate, String Stime , String Sexerciseid) {

        super();


        this.exercise = Sexercise;

        this.weight = Sweight;

        this.rep = Srep;

        this.date = Sdate;

        this.time = Stime;

        this.exerciseid = Sexerciseid;

    }



    public String getExerciseid() {

        return exerciseid;

    }


    public String getExercise() {

        return exercise;

    }
    public void setExercise(String Exercise2) {

        this.exercise = Exercise2;

    }

    public String getWeight() {

        return weight;

    }
    public void setWeight(String Weight2) {

        this.weight = Weight2;

    }

    public String getRep() {

        return rep;

    }
    public void setRep(String Rep2) {

        this.rep = Rep2;

    }

    public String getDate() {

        return date;

    }
    public void setDate(String Date2) {

        this.date = Date2;

    }

    public String getTime() {

        return time;

    }
    public void setTime(String Time2) {

        this.time = Time2;

    }


    @Override
    public String toString() {

        return  exercise + " " + weight + " " + rep + " " + date + " " + time   ;

    }

}