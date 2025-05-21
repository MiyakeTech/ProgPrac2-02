package jp.ac.gifu_u.s.miyake_ren.myapplication;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera cam;

    public CameraView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        cam = Camera.open(0);                    // 0:背面
        try { cam.setPreviewDisplay(holder); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void surfaceChanged(
            @NonNull SurfaceHolder holder, int format, int w, int h) {
        cam.startPreview();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        cam.setPreviewCallback(null);
        cam.stopPreview();
        cam.release();
        cam = null;
    }
}
