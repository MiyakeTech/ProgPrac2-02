package jp.ac.gifu_u.s.miyake_ren.myapplication;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.*;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {


    // センサを管理するマネージャ
    private SensorManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Button b = (Button) findViewById(R.id.button_finish1);
        b.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(new MyView(this));

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "アプリを終了しました", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void onResume() {
        super.onResume();
        // 明るさセンサ(TYPE_LIGHT)のリストを取得
        List<Sensor> sensors;
        sensors = manager.getSensorList(Sensor.TYPE_LIGHT);
        // ひとつ以上見つかったら、最初のセンサを取得してリスナーに登録
        if (!sensors.isEmpty()) {
            Sensor sensor = sensors.get(0);
            manager.registerListener(
                    this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onPause() {
        super.onPause();
        // 一時停止の際にリスナー登録を解除
        manager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent arg0) {
        // 明るさセンサが変化したとき
        if (arg0.sensor.getType() == Sensor.TYPE_LIGHT) {
            // 明るさの値（単位ルクス）を取得
            float intensity = arg0.values[0];
            // 結果をテキストとして表示
            String str = intensity + "ルクス";
            TextView textview =
                    (TextView) findViewById(R.id.status_text);

            if (textview != null) {
                textview.setText(str);
            }
        }
    }


}