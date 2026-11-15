package com.inversionespro.emocigat;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class EmociGatFloatingView {

    private WindowManager windowManager;
    private View floatingView;
    private WindowManager.LayoutParams params;

    public EmociGatFloatingView(Context context) {
        // Inicializar la vista flotante del gatito
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        
        LayoutInflater inflater = LayoutInflater.from(context);
        floatingView = inflater.inflate(android.R.layout.activity_list_item, null);

        // Configuración de los permisos de ventana flotante sobre otras apps
        int layoutFlag;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 100;
        params.y = 100;
    }

    public void mostrar() {
        try {
            if (floatingView.getWindowToken() == null) {
                windowManager.addView(floatingView, params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ocultar() {
        try {
            if (floatingView.getWindowToken() != null) {
                windowManager.removeView(floatingView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
