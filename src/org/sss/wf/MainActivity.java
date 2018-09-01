package org.sss.wf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Button ingredientsButton = (Button)findViewById(R.id.ingredientsButton);
        //Button wineButton = (Button)findViewById(R.id.wineButton);
    }

    public void clickHandler(View view) {
        switch (view.getId()) {
            case R.id.foodButton:
                Intent intent = new Intent(view.getContext(), SearchByTypeActivity.class);
                startActivity(intent);
                break;
            case R.id.wineButton:
                break;
        }
    }
}
