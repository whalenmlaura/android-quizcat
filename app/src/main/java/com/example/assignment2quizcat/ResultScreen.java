package com.example.assignment2quizcat;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.os.Bundle;
import android.widget.TextView;


public class ResultScreen extends AppCompatActivity {

    TextView resultTxt;
    ImageView quizcatLogo;
    Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // to get name and the score from homeScreen
        extras = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.result_screen);

        resultTxt = this.findViewById(R.id.resultTxt);
        quizcatLogo = this.findViewById(R.id.quizCatImg2);

        // set text based on the score
        if(extras.getInt("score") == 10){
            resultTxt.setText("Wow "+extras.getString("name")+"! You scored "+extras.getInt("score")+"/10! You're a cat expert!");
        } else if(extras.getInt("score") >= 8) {
            resultTxt.setText("Great job "+extras.getString("name")+"! You scored "+extras.getInt("score")+"/10! You really know your cat breeds!");
        } else if(extras.getInt("score") >= 5) {
            resultTxt.setText("Not bad "+extras.getString("name")+"! You scored "+extras.getInt("score")+"/10! You're pretty good with your cat breeds!");
        } else if(extras.getInt("score") >= 2) {
            resultTxt.setText("Come on "+extras.getString("name")+"... You scored "+extras.getInt("score")+"/10... You can do better...");
        } else {
            resultTxt.setText(extras.getString("name")+"... You scored "+extras.getInt("score")+"/10... Are you even a cat person?");
        }

    }

}



