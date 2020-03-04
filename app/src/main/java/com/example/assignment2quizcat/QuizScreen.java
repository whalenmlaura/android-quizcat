package com.example.assignment2quizcat;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class QuizScreen extends AppCompatActivity {

    Button answerOneBtn;
    Button answerTwoBtn;
    Button answerThreeBtn;
    Button answerFourBtn;
    TextView questionTxt;
    Button nextBtn;

    int score = 0;
    String currentQuestion;
    Bundle extras;

    HashMap<String, String> answersHash = new HashMap<String, String>();
    ArrayList<String> catDescriptions = new ArrayList<String>();
    ArrayList<String> catBreeds = new ArrayList<String>();
    ArrayList<Button> answerButtons = new ArrayList<Button>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // to get name variable from homeScreen
        extras = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.quiz_screen);

        answerOneBtn = this.findViewById(R.id.answerOneBtn);
        answerTwoBtn = this.findViewById(R.id.answerTwoBtn);
        answerThreeBtn = this.findViewById(R.id.answerThreeBtn);
        answerFourBtn = this.findViewById(R.id.answerFourBtn);
        questionTxt = this.findViewById(R.id.questionTxt);
        nextBtn = this.findViewById(R.id.nextBtn);

        // add the buttons to the button ArrayList
        answerButtons.add(answerOneBtn);
        answerButtons.add(answerTwoBtn);
        answerButtons.add(answerThreeBtn);
        answerButtons.add(answerFourBtn);


        // load catDescriptions and get the first question and display it
        questionLoader();
        questionSetup();

        // onClick listener calls
        answerOneBtn.setOnClickListener(answerClick);
        answerTwoBtn.setOnClickListener(answerClick);
        answerThreeBtn.setOnClickListener(answerClick);
        answerFourBtn.setOnClickListener(answerClick);
        nextBtn.setOnClickListener(nextClick);
    }


    private void questionLoader(){
        // load file
        String line = ""; // to store data
        BufferedReader bufferRead = null;
        InputStream inputStream;
        HashMap<String, String> map = new HashMap<String, String>();

        try {
            // open questions.txt
            inputStream = getResources().openRawResource(R.raw.questions);
            bufferRead = new BufferedReader(new InputStreamReader(inputStream));
        } catch(Exception e) {
            Log.println(Log.ERROR, "Error", e.getMessage());
        }

        try {
            while((line=bufferRead.readLine()) != null) {
                // separate questions from answers by "-"
                String[] str = line.split("-");
                // store both in answersHash, the questions in one array, and the catBreeds in another
                answersHash.put(str[0], str[1]);
                catDescriptions.add(str[0]);
                catBreeds.add(str[1]);
            }
        } catch(IOException e) {
            Log.println(Log.ERROR, "Error", e.getMessage());
        }

    }

    private void shuffle(ArrayList<String> collection){
        Collections.shuffle(collection);
    }

    private String getQuestion(){
        // shuffle, get the cat description, and then remove the cat description from the array
        shuffle(catDescriptions);
        String questionText = catDescriptions.get(0);
        catDescriptions.remove(0);
        return questionText;
    }

    private void questionSetup(){
        ArrayList<String> currentOptions = new ArrayList<String>();

        // store the ca description in the currentQuestion variable and display it
        currentQuestion = getQuestion();
        questionTxt.setText(currentQuestion);

        // shuffle the catBreeds
        shuffle(catBreeds);

        // grab answer and add to currentOptions array
        currentOptions.add(answersHash.get(currentQuestion));

        // grab first three from suffled catBreeds and verify that non of the first three are the answer
        for(int i = 0; i < catBreeds.size(); i++){
            if(!currentOptions.contains(catBreeds.get(i))){
                currentOptions.add(catBreeds.get(i));
            }
            if(currentOptions.size() == 4) {
                break;
            }
        }

        // shuffle currentOptions and set the text of the buttons
        shuffle(currentOptions);
        for(int i = 0; i < currentOptions.size(); i++){
            answerButtons.get(i).setText(currentOptions.get(i));
        }
    }

    // return true or false if selected button is correct answer using hash map
    private boolean validateAnswer(String selectedAnswer){
        return selectedAnswer.equals(answersHash.get(currentQuestion));
    }

    // reset the buttons to original text color
    private void buttonReset(){
        for(int i = 0; i <answerButtons.size(); i++) {
            answerButtons.get(i).setTextColor(Color.argb(255, 27, 73, 101));
        }
    }

    View.OnClickListener answerClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button clickedButton = findViewById(view.getId());
            clickedButton.getText();

            // if the next button is invisible (i.e. an answer hasn't already been choosen)
            if(nextBtn.getVisibility() == View.INVISIBLE) {
                // if the answer is correct, change the font to green, and increment the score
                if(validateAnswer(clickedButton.getText().toString())){
                    clickedButton.setTextColor(Color.argb(255,0,215,0));
                    score += 1;
                } else{
                    // is answer is incorrect, change the font to red
                    clickedButton.setTextColor(Color.RED);
                }
                // set the next button to visible
                nextBtn.setVisibility(View.VISIBLE);
            }

        }
    };

    View.OnClickListener nextClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            // if there are still catDescriptions left, go to next question
            if(catDescriptions.size() != 0){
                buttonReset();
                nextBtn.setVisibility(View.INVISIBLE);
                questionSetup();
            } else {
                // else, go to result screen
                Intent i = new Intent("ResultScreen");
                extras.putInt("score", score);
                i.putExtras(extras);
                startActivityForResult(i, 1);
            }
        }
    };

}


