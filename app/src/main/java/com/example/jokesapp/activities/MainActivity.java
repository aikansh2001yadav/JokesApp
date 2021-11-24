package com.example.jokesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.arasthel.asyncjob.AsyncJob;
import com.example.jokesapp.utils.JokesData;
import com.example.jokesapp.R;
import com.example.jokesapp.utils.ShakeDetector;
import com.example.jokesapp.controller.CardStackDataAdapter;
import com.example.jokesapp.controller.LikeIsClicked;
import com.example.jokesapp.model.CustomJokeManager;
import com.example.jokesapp.model.Joke;
import com.example.jokesapp.model.JokeManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LikeIsClicked {

    /**
     * Stores the custom joke types made by the user
     */
    private HashSet<String> customJokeTypes;
    /**
     * Stores the reference to the floating action button to add new jokes
     */
    private FloatingActionButton floatingActionButton;
    /**
     * Stores the reference to the tab layout in which various tabItems lie
     */
    private TabLayout tabLayout;

    /**
     * Stores the item position which specifies a particular joke type in a spinner
     */
    private int itemPosition;

    /**
     * Stores the reference to sensor manager use to add shake functionality
     */
    private SensorManager mSensorManager;
    /**
     * Stores the reference to accelerometer to be used to add shake functionality
     */
    private Sensor mAccelerometer;
    /**
     * Stores the reference to shake detector object
     */
    private ShakeDetector mShakeDetector;

    /**
     * Stores the reference to spinner object which shows jokes types when a particular tab item is selected
     */
    private Spinner spinner;

    /**
     * Stores various types of yoMama jokes types which gets stored in the string array
     */
    private String[] yoMamaJokesTypeList = new String[]{"Yo Mama tall", "Yo Mama stupid", "Yo Mama ugly", "Yo Mama nasty", "Yo Mama hairy", "Yo Mama bald", "Yo Mama old", "Yo Mama poor", "Yo Mama short", "Yo Mama skinny", "Yo Mama fat", "Yo Mama like"};
    /**
     * Stores various types of hindi jokes types which gets stored in the string array
     */
    private String[] otherJokesTypeList = new String[]{"Chuck Norris"};

    /**
     * Stores particular jokes which are used to display in the cardstack
     */
    private ArrayList<String> jokesList = new ArrayList<>();

    /**
     * Stores the reference of CardStackView object which shows jokes
     */
    private CardStackView mCardStack;
    /**
     * Stores the reference of CardStackDataAdapter object
     */
    private CardStackDataAdapter cardStackDataAdapter;

    /**
     * Stores the reference of jokeManager object which manages favorite jokes
     */
    private JokeManager jokeManager;
    /**
     * Stores the reference of customJokeManager object which manages custom jokes made by the user
     */
    private CustomJokeManager customJokeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating AsyncJob.AsyncJobBuilder object which does some background work and after completing background work it does some work on UI
        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        // Do some background work
                        try {

                            //Creating and initialising Json Object which receives json data from local json file
                            JSONObject jsonObject = new JSONObject(loadJsonFromAssets());

                            //Stores various yoMamaTypes in the following string array object
                            String[] yoMamaTypes = new String[]{"fat", "stupid", "ugly", "nasty", "hairy", "bald", "old", "poor", "short", "skinny", "tall", "like"};
                            //Storing yoMama jokes in JokesData static field from jsonObject
                            for(String yoMamaType: yoMamaTypes)
                            {
                                JSONArray jsonArray = jsonObject.getJSONArray(yoMamaType);

                                if(jsonArray != null)
                                {
                                    for(int i = 0; i < jsonArray.length(); i++)
                                    {
                                        JokesData.addJoke(jsonArray.getString(i), (yoMamaType));
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                    }
                }).create().start();
    }

    /**
     * Displays the new alert dialog box which takes inputs from the user and add joke to the customJokeManager object
     */
    private void displayCreateNewJokeDialogBox() {
        //Creating and initialising AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //Inflating create_new_joke_layout and storing the view in view object
        final View view = getLayoutInflater().inflate(R.layout.create_new_joke_dialog_layout, null);
        //Setting view object as a view to the builder object
        builder.setView(view);
        //Adding the positive button in the alert dialog box
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Creating editText object which takes category name of the joke as the input
                EditText categoryEditText = view.findViewById(R.id.categoryEditText);
                //Creating editText object which takes joke text as the input
                EditText jokeEditText = view.findViewById(R.id.jokeEditText);
                //Storing categoryEditText in the category string object
                String category = categoryEditText.getText().toString();
                //Removing all whitespaces from category string
                category = category.replaceAll("\\s", "");
                //Storing jokeEditText in jokeText string object
                String jokeText = jokeEditText.getText().toString();
                //Adds custom joke to customJokeManager which takes category and jokeText as inputs
                addCustomJoke(category, jokeText);
            }
        })
                //Adding the negative button in the alert dialog box used to dismiss the alert dialog box
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    /**
     * Adds the custom joke to the customJokeManager which takes category and joketext as the inputs
     * @param category
     * @param jokeText
     */
    private void addCustomJoke(String category, String jokeText) {
        //Adding the joke to the customJokeManager
        customJokeManager.addJoke(category, jokeText);
        //Retrieving the update customJokeTypes categories from customJokeManager
        customJokeTypes = customJokeManager.retrieveCategories();
        //Adding updated categories in customJokeTypeArray
        String[] customJokeTypeArray = new String[customJokeTypes.size()];
        int i = 0;
        for(String jokeCategory: customJokeTypes)
        {
            customJokeTypeArray[i] = jokeCategory;
            i++;
        }
        //Setting spinner object
        settingSpinner(customJokeTypeArray);
    }

    /**
     * Shuffles jokesList and refresh cardStackView object
     */
    private void handleShakeEvent() {
        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        // Do some background work
                        try {
                            //if jokesList is not empty then shuffle jokesList
                            if(!jokesList.isEmpty())
                            {
                                Collections.shuffle(jokesList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        //Refresh cardStackView
                        refreshCardStackView();
                    }
                }).create().start();
    }

    /**
     * Refresh adapter and view on items selected from spinner
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //storing current position in itemPosition variable
        itemPosition = position;
        //Clearing customJokeTypes
        customJokeTypes.clear();
        //Clearing jokesList
        jokesList.clear();
        //Clearing references on cardStackDataAdapter and cardStackView object
        cardStackDataAdapter = null;
        mCardStack = null;
        //Refresh adapter and views which shows jokes of selected type
        refreshAdapter(itemPosition);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Parses and stores JsonData from jokes.json file and returns json string
     * @return json string
     */
    private String loadJsonFromAssets()
    {
        String json;
        try {
            //Creating and initialising inputStream object which takes inputs from jokes.json file
            InputStream inputStream = this.getAssets().open("jokes.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            //Reading buffer into inputStream object
            inputStream.read(buffer);
            //Closing inputStream object
            inputStream.close();
            //Creating jsonString
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //Returns json string
        return json;
    }

    @Override
    public void likeIsClicked(Joke joke) {
        //If joke is already liked then add joke to the jokeManager object otherwise remove joke from jokeManager object
        if(joke.isJokeLiked())
        {
            jokeManager.addJoke(joke);
        }
        else
        {
            jokeManager.removeJoke(joke);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflating menu layout
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Creating new intent passing context and target class in parameters
        Intent intent = new Intent(MainActivity.this, FavoriteJokesActivity.class);
        //Starting activity after passing intent
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Registering listener to the sensor manager
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregistering listener from the sensor manager
        mSensorManager.unregisterListener(mShakeDetector);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Refreshing adapter
        refreshAdapter(itemPosition);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Initialising views
        initialiseViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Removing reference from various objects
        cardStackDataAdapter = null;
        customJokeTypes = null;
        floatingActionButton = null;
        tabLayout = null;
        mSensorManager = null;
        mAccelerometer = null;
        mShakeDetector = null;
        spinner = null;
        mCardStack = null;
        jokeManager = null;
        customJokeManager = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Removing references from various objects
        yoMamaJokesTypeList = null;
        otherJokesTypeList = null;
        jokesList = null;
    }

    //Refreshing adapter
    public void refreshAdapter(int position)
    {
        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        // Do some background work
                        try {
                            //Retrieving jokesList on the basis of tab selected
                            if(tabLayout.getSelectedTabPosition() == 0)
                            {
                                jokesList = JokesData.retrieveJokes(yoMamaJokesTypeList[position]);
                            }
                            else if(tabLayout.getSelectedTabPosition() == 1)
                            {
                                jokesList = JokesData.retrieveJokes(otherJokesTypeList[position]);
                            }
                            else
                            {
                                customJokeTypes = customJokeManager.retrieveCategories();
                                if(!customJokeTypes.isEmpty())
                                {
                                    String[] customJokeTypeArray = new String[customJokeTypes.size()];
                                    int j = 0;
                                    for(String jokeCategory: customJokeTypes)
                                    {
                                        customJokeTypeArray[j] = jokeCategory;
                                        j++;
                                    }
                                    jokesList = customJokeManager.retrieveJokes(customJokeTypeArray[position]);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        //Refreshing cardStackView
                        refreshCardStackView();
                    }
                }).create().start();
    }

    /**
     * Initialising views and setting listeners on various objects
     */
    private void initialiseViews()
    {
        //Initialising jokeManager and customJokeManager objects
        jokeManager = new JokeManager(this);
        customJokeManager = new CustomJokeManager(this);

        //Initialising customJokeTypes HashSet
        customJokeTypes = new HashSet<>();

        //Initialising spinner object
        spinner = findViewById(R.id.jokesTypeSpinner);
        //Setting onItemSelectedListener on the spinner object
        spinner.setOnItemSelectedListener(this);

        //Initialising floatingActionButton object
        floatingActionButton = findViewById(R.id.floatingActionButton);

        //Hides the floatingActionButton
        floatingActionButton.hide();

        //Setting onClickListener on the floatingActionButton object
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Displays alert dialog box to create new joke
                displayCreateNewJokeDialogBox();
            }
        });

        //Initialising tabLayout object
        tabLayout = findViewById(R.id.tabs);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                handleShakeEvent();
            }
        });
        //Adding onTabSelectedListener to the tabLayout object
        tabLayout.addOnTabSelectedListener(new tabLayoutCustomListener());
        //Setting spinner object
        if(tabLayout.getSelectedTabPosition() == 0)
        {
            settingSpinner(yoMamaJokesTypeList);
        }
        else if(tabLayout.getSelectedTabPosition() == 1)
        {
            settingSpinner(otherJokesTypeList);
        }
        else
        {
            //Showing floatingActionButton
            floatingActionButton.show();
            customJokeTypes = customJokeManager.retrieveCategories();
            String[] customJokeTypeArray = new String[customJokeTypes.size()];
            int i = 0;
            for(String jokeCategory: customJokeTypes)
            {
                customJokeTypeArray[i] = jokeCategory;
                i++;
            }
            settingSpinner(customJokeTypeArray);
        }
    }

    /**
     * Setting arrayAdapter on the spinner object
     * @param jokesTypeArray
     */
    private void settingSpinner(String[] jokesTypeArray)
    {
        //Creating new arrayAdapter object and setting dropDownViewResource to the arrayAdapter object
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, jokesTypeArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting
        spinner.setAdapter(arrayAdapter);
    }

    /**
     * Refreshing cardStackView
     */
    private void refreshCardStackView()
    {
        //Initialising cardStackView object
        mCardStack = findViewById(R.id.card_stack_view);
        //Setting various properties on the cardStackLayoutManager
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(getApplicationContext());
        cardStackLayoutManager.setStackFrom(StackFrom.None);
        cardStackLayoutManager.setVisibleCount(1);
        cardStackLayoutManager.setTranslationInterval(8.0f);
        cardStackLayoutManager.setScaleInterval(0.95f);
        cardStackLayoutManager.setSwipeThreshold(0.3f);
        cardStackLayoutManager.setMaxDegree(20.0f);
        cardStackLayoutManager.setDirections(Direction.HORIZONTAL);
        cardStackLayoutManager.setCanScrollHorizontal(true);
        cardStackLayoutManager.setCanScrollVertical(true);
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        cardStackLayoutManager.setOverlayInterpolator(new LinearInterpolator());

        //Setting cardStackLayoutManager to the cardStackView object
        mCardStack.setLayoutManager(cardStackLayoutManager);
        //Creating and initialising cardStackDataAdapter
        cardStackDataAdapter = new CardStackDataAdapter(getApplicationContext(), jokesList, MainActivity.this::likeIsClicked);
        //Setting cardStackDataAdapter as an arrayAdapter to cardStackView object
        mCardStack.setAdapter(cardStackDataAdapter);
    }

    /**
     * Custom listener for the tabLayout
     */
    private class tabLayoutCustomListener implements TabLayout.OnTabSelectedListener{

        /**
         * Stores the reference of arrayAdapter
         */
        private ArrayAdapter<String> arrayAdapter;
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            //Setting spinner
            if(tab.getText().toString().equals("Yo Mama Jokes"))
            {
                //hiding floating action button
                floatingActionButton.hide();
                settingSpinner(yoMamaJokesTypeList);
            }
            //Setting spinner
            else if(tab.getText().toString().equals("Other Jokes"))
            {
                //hiding floating action button
                floatingActionButton.hide();
                settingSpinner(otherJokesTypeList);
            }
            else
            {
                //Showing floatingActionButton
                floatingActionButton.show();
                customJokeTypes = customJokeManager.retrieveCategories();
                //Setting spinner on custom joke types
                String[] customJokeTypeArray = new String[customJokeTypes.size()];
                int i = 0;
                for(String jokeCategory: customJokeTypes)
                {
                    customJokeTypeArray[i] = jokeCategory;
                    i++;
                }
                settingSpinner(customJokeTypeArray);
            }
            //Notifying data set changed on cardStackDataAdapter
            cardStackDataAdapter.notifyDataSetChanged();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }
}


