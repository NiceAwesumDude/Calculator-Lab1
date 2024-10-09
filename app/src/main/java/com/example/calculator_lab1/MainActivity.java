package com.example.calculator_lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Text fields for the input and output
     */
    TextView data, result;
    /**
     * Fields for all the buttons
     */
    MaterialButton buttonC, buttonOpen, buttonClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;
    /**
     * Field to track first button press
     */
    Boolean first = true;

    /**
     * Actions performed when the calculator app is launched: create UI, create text fields, assign button IDs.
     * @param savedInstanceState Data to reload UI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Format UI
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set text field variables
        data = findViewById(R.id.data_text);
        result = findViewById(R.id.result_text);

        // Assign button IDs to variables
        assignID(buttonC, R.id.button_c);
        assignID(buttonOpen, R.id.button_open);
        assignID(buttonClose, R.id.button_close);
        assignID(buttonDivide, R.id.button_divide);
        assignID(buttonMultiply, R.id.button_multiply);
        assignID(buttonPlus, R.id.button_add);
        assignID(buttonMinus, R.id.button_subtract);
        assignID(buttonEquals, R.id.button_equals);
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonAC, R.id.button_ac);
        assignID(buttonDot, R.id.button_decimal);
    }

    /**
     * Function to easily assign button IDs
     * @param btn The button variable to be associated
     * @param id The ID of the Button in the UI
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * Function to calculate equations from a string using imported Library
     * @param input String to be used for calculation
     * @return Result is returned in string form if equation is valid. Otherwise "Error" is returned.
     */
    String calculate(String input) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, input, "Javascript", 1, null).toString();
        } catch (Exception e) {
            return "Error";
        }
    }

    /**
     * Function to perform an action when a button is clicked
     * @param view Button that was clicked
     */
    @Override
    public void onClick(View view) {
        // Fields display Student ID and last name on application launch.
        // Set fields to default values for calculation if not already done so.
        if (first) {
            data.setText("");
            result.setText("0");
            first = false;
        }

        // Declare variables to pull values from the button that was clicked, store the value, and respond appropriately
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String input = data.getText().toString();

        // Switch statement to perform correct action upon unique button presses
        switch (buttonText) {
            case "AC":
                input = "";
                data.setText("");
                result.setText("0");
                break;
            case "C":
                input = input.substring(0, input.length()-1);
                break;
            case "=":
                result.setText(calculate(input));
                break;
            default:
                input = input + buttonText;
                break;
        }

        // Set data field to most recent state
        data.setText(input);
    }
}