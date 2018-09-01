package org.sss.wf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by IntelliJ IDEA.
 * User: wcherry
 * Date: 3/19/12
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchByTypeActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbytype);

    }

    public void clickHandler(View view) {
        switch (view.getId()) {
            case R.id.ingredientsButton:
                Intent intent = new Intent(view.getContext(), IngredientsActivity.class);
                startActivity(intent);
                break;
            case R.id.recipeButton:
                break;
        }
    }

}