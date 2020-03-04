package com.example.assignment2quizcat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class HomeScreen extends AppCompatActivity {

    TextView quizCatTxt;
    TextView enterNameTxt;
    EditText nameTxtEdit;
    TextView invalidNameTxt;
    Button continueBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.home_screen);

        quizCatTxt = this.findViewById(R.id.quizCatTxt);
        enterNameTxt = this.findViewById(R.id.enterNameTxt);
        nameTxtEdit = this.findViewById(R.id.nameEditTxt);
        invalidNameTxt = this.findViewById(R.id.invalidNameTxt);
        continueBtn = this.findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(toQuizScreen);
    }

    // name validation using regex
    private boolean nameValidation(String name) {
        return name.matches("[a-zA-Z]+");
    };

    // continue button onClick
    View.OnClickListener toQuizScreen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // assign inputted name to variable
            String name = nameTxtEdit.getText().toString();

            // if the name is valid
            if (nameValidation(name)) {
                Intent i = new Intent("QuizScreen");
                // create bundle to store name for results screen
                Bundle extras = new Bundle();
                extras.putString("name", name);
                i.putExtras(extras);
                startActivityForResult(i, 1);
                // set error message to invisible in case it was triggered
                invalidNameTxt.setVisibility(View.INVISIBLE);
            } else {
                // set the error message visible
                invalidNameTxt.setVisibility(View.VISIBLE);
            }
        }
    };

} // end HomeScreen

