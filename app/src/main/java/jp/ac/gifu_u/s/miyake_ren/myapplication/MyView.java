package jp.ac.gifu_u.s.miyake_ren.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.widget.*;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class MyView extends View {
    // イベント発生時の X 座標、Y 座標を保存するための動的配列
    private ArrayList array_x, array_y;
    private ArrayList array_status;
    // コンストラクタ
    public MyView(Context context) {
        super(context);
        array_x = new ArrayList();
        array_y = new ArrayList();
        array_status = new ArrayList();
    }

    // タッチパネルを操作した時に呼ばれるメソッド
    @Override
    public boolean onTouchEvent(MotionEvent event) {
// 座標を取得
        int x = (int) event.getX();
        int y = (int) event.getY();
// イベントに応じて動作を変更
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // タッチパネルが押されたとき
            case MotionEvent.ACTION_POINTER_DOWN:
                array_x.add(new Integer(x)); // 座標を配列に保存
                array_y.add(new Integer(y)); // 線の描画はしない(false)
                array_status.add(new Boolean(false));
                invalidate(); // 画面を強制的に再描画
                break;
            case MotionEvent.ACTION_MOVE:
                array_x.add(new Integer(x)); // 座標を配列に保存
                array_y.add(new Integer(y)); // 線の描画をする(true)
                array_status.add(new Boolean(true));
                invalidate(); // 画面を強制的に再描画
                break;
            case MotionEvent.ACTION_UP: // タッチパネルから離れたとき
            case MotionEvent.ACTION_POINTER_UP:
                array_x.add(new Integer(x)); // 座標を配列に保存
                array_y.add(new Integer(y)); // 線の描画をする(true)
                array_status.add(new Boolean(true));
                invalidate(); // 画面を強制的に再描画
                break;
        }
        return true;
    }

    // ビューの描画を行うときに呼ばれるメソッド
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 背景を白色で塗りつぶす
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), p);
        // 描画用の Paint オブジェクトを用意
        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);

        // 配列内の座標を読み出して線（軌跡）を描画
        for (int i = 1; i < array_status.size(); i++) {
        // 描画するように(true)状態値が与えられているとき
        // 一度離してしてから次に押されるまでの移動分は描画しない
            if ((Boolean) array_status.get(i)) {
                // 開始点の終了点の座標の値を取得
                int x1 = (Integer) array_x.get(i - 1);
                int x2 = (Integer) array_x.get(i);
                int y1 = (Integer) array_y.get(i - 1);
                int y2 = (Integer) array_y.get(i);
// 線を描画
                canvas.drawLine(x1, y1, x2, y2, p);
            }
        }
    }
}
