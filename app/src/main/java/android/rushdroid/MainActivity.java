package android.rushdroid;

import android.content.Intent;
import android.os.Bundle;
import android.rushdroid.model.Model;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  static String[] numbers;
  @Override
  protected void onCreate(@NonNull Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final Model game = ((GameApplication) getApplication()).game();

    int size = game.size();
    numbers = new String[size];
    for (int i = 0; i < size; i += 1) {
      numbers[i] = String.valueOf(i);
    }

    GridView gridView = (GridView) findViewById(R.id.gridview);
    gridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numbers));
    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        game.selectPuzzle(position);
        Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
      }
    });
    //  this.setContentView(R.layout.activity_game);
  }

/*  @Override
  public boolean onCreateOptionsMenu(@NonNull Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }*/

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }
}
