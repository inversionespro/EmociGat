package com.inversionespro.emocigat;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

public class EmociGatAccessibilityService extends AccessibilityService implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private boolean isTtsReady = false;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        tts = new TextToSpeech(this, this);
        Log.d("EmociGat", "Servicio de accesibilidad conectado con éxito");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
            event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            
            CharSequence text = event.getText().toString();
            if (text != null && text.length() > 10) {
                analizarEmocionYLeer(text.toString());
            }
        }
    }

    private void analizarEmocionYLeer(String mensaje) {
        String lower = mensaje.toLowerCase();
        
        // Detección emocional básica local y gratuita
        if (lower.contains("excelente") || lower.contains("genial") || lower.contains("jajaja") || lower.contains("amor")) {
            hablar("Qué gran momento detecté en pantalla.");
        } else if (lower.contains("enojo") || lower.contains("odio") || lower.contains("terrible") || lower.contains("mal")) {
            hablar("Vaya, esto parece un poco tenso por aquí.");
        }
    }

    private void hablar(String texto) {
        if (isTtsReady && tts != null) {
            tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(new Locale("es", "ES"));
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("EmociGat", "Idioma no soportado para TTS");
            } else {
                isTtsReady = true;
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Obligatorio por el sistema de Android
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
