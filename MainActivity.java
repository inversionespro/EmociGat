package com.inversionespro.emocigat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interfaz sencilla y directa creada desde el código para el teléfono
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        TextView title = new TextView(this);
        title.setText("🐾 EmociGat Asistente");
        title.setTextSize(22f);
        title.setPadding(0, 0, 0, 30);
        layout.addView(title);

        TextView desc = new TextView(this);
        desc.setText("Para que el gatito pueda asomarse en WhatsApp y redes sociales, activa el permiso de ventana flotante.");
        desc.setTextSize(16f);
        desc.setPadding(0, 0, 0, 40);
        layout.addView(desc);

        Button btnPermission = new Button(this);
        btnPermission.setText("1. Activar Ventana Flotante");
        btnPermission.setOnClickListener(v -> requestOverlayPermission());
        layout.addView(btnPermission);

        Button btnAccessibility = new Button(this);
        btnAccessibility.setText("2. Activar Accesibilidad (Lectura)");
        btnAccessibility.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
        layout.addView(btnAccessibility);

        setContentView(layout);
    }

    private void requestOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            Toast.makeText(this, "¡Permiso ya concedido!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "¡Permiso concedido con éxito!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Se necesita el permiso para mostrar el gatito.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
