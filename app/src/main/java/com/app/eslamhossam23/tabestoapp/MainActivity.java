package com.app.eslamhossam23.tabestoapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.eslamhossam23.tabestoapp.Models.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Food> foodList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter(this, R.layout.list_item, foodList);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Button button = customAdapter.buttons.get(position);
                final TextView descriptionTextView = customAdapter.descriptions.get(position);
                Food food = foodList.get(position);
                if(button.getVisibility() == View.INVISIBLE){
                    for(Button button1 : customAdapter.buttons){
                        button1.setVisibility(View.INVISIBLE);
                    }
                    for(TextView description : customAdapter.descriptions){
                        description.setText("");
                    }
                    descriptionTextView.setText(food.getDescription());
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                         descriptionTextView.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });
                    valueAnimator.start();
                    button.setVisibility(View.VISIBLE);
                    ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0f, 1f);
                    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            button.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });
                    valueAnimator2.start();
                }else {
                    descriptionTextView.setText("");
                    button.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initList() {
        foodList.add(new Food("Fish fillet", R.drawable.food_1_small, 14.99));
        foodList.add(new Food("Chicken burger", R.drawable.food_2_small, 9.94));
        foodList.add(new Food("Snacks", R.drawable.food_3_small, 7.95));
        foodList.add(new Food("Thai food", R.drawable.food_4_small, 17.80));
        foodList.add(new Food("Healthy food", R.drawable.food_5_small, 19.99));
    }

    class CustomAdapter extends ArrayAdapter {
        public List<Button> buttons = new ArrayList<>();
        public List<TextView> descriptions = new ArrayList<>();

        public CustomAdapter(Context context, int resource, List<Food> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }
            Food currentFood = foodList.get(position);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.listImage);
            imageView.setImageDrawable(getDrawable(currentFood.getIdImage()));
            TextView nameTextView = (TextView) itemView.findViewById(R.id.name);
            nameTextView.setText(currentFood.getName());
            TextView priceTextView = (TextView) itemView.findViewById(R.id.price);
            priceTextView.setText(Double.toString(currentFood.getPrice()) + "€");
            TextView descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            if(!descriptions.contains(descriptionTextView)){
                descriptions.add(descriptionTextView);
            }
            final Button button = (Button) itemView.findViewById(R.id.commander);
            if(!buttons.contains(button)){
                buttons.add(button);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url  = new URL("http://www.testsite.com");
                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                httpURLConnection.setDoOutput(true);
                                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                                httpURLConnection.setRequestMethod("POST");
                                JSONObject food = new JSONObject();
                                food.put("Name",foodList.get(position).getName());
                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                                outputStreamWriter.write(food.toString());
                                outputStreamWriter.flush();
                                final String message = "Vous avez choisi " + foodList.get(position).getName();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (java.io.IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(button.getVisibility() == View.INVISIBLE){
//                        button.setVisibility(View.VISIBLE);
//                    }else{
//                        button.setVisibility(View.INVISIBLE);
//                    }
//                }
//            });

            return itemView;
        }
    }


}
