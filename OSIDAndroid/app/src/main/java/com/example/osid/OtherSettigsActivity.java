package com.example.osid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

public class OtherSettigsActivity extends AppCompatActivity {

    ImageButton back;
    Switch activeComida, activeInsulina, activeCatether;
    //TODO notificaciones -> CAMBIO DE REPOSITORIO
    //TODO notificaciones -> CAMBIO DE CATETER
    //TODO notificaciones -> HORAS DE INGERIR COMIDAS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_settigs);

        back = findViewById(R.id.goBack_other_settings);
        activeComida = findViewById(R.id.switch_notificaciones_comida);
        activeInsulina = findViewById(R.id.switch_notificaciones_insulina);
        activeCatether = findViewById(R.id.switch_notificaciones_cambio_catether);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //onBackPressed();
                SendNotificationUpdate("Cambia tu repositorio","HEY");
            }
        });
    }
    public void SendNotificationUpdate(String message, String title){
        NotificationCompat.Builder mBuilder;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int icon = R.mipmap.ic_launcher;
        Intent intent = new Intent(this, SettingsUpdateVariables.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

       /* mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{100,250,100,500})
                .setAutoCancel(true);

        notificationManager.notify(1,mBuilder.build());*/
        Notification notification = new Notification(icon,
                message,System.currentTimeMillis());
        notificationManager.notify(0,notification);
    }
}
