package nova.samplenotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.tistory.webnautes.notification.R;


//알림 기능 적용을 위한 예제 코드

public class MainActivity extends Activity {

    //알림을 생성하는 버튼
    Button buttonNotify = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //알림 버튼과 클릭 리스너 세팅
        buttonNotify = (Button) findViewById(R.id.button);
        buttonNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                NotificationSomethings();
            }
        });
    }


    //클라이언트가 알림을 생성하는 메소드
    public void NotificationSomethings() {


        //리소스 변수화
        Resources res = getResources();








//
//        //알림에 들어갈 인텐트를 정의.
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        //@@@ 시작하기를 원하는 액티비티의 인텐트를 만든다.


        //알림이 전달되면 들어갈 화면
        Intent intentNotice = new Intent(this, NoticeActivity.class);
        //알림으로 들어간 화면에서 전달할 값
        intentNotice.putExtra("notificationId", 9999);

        Intent intentMain = new Intent(this, MainActivity.class);

        //@@@ TaskStackBuilder 를 만들고, 위의 인텐트를 추가한다 -> 백스택에 추가됨.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        //@@@ 액티비티 스택 순서 : 메인->알림. 알림에서 뒤로가기 할 경우 -> 메인으로 감

        //메인 액티비티 추가
        stackBuilder.addNextIntentWithParentStack(intentMain);

        //알림 액티비티 추가
        stackBuilder.addNextIntentWithParentStack(intentNotice);

        //@@@ 액티비티 스택을 포함한 PendingIntent 를 생성한다.
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //알림 빌더를 통해 알림을 만든다.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


        builder.setContentTitle("타이틀")
                .setContentText("서브타이틀")
                .setTicker("상태바 한줄 메시지")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)

                    //notification 의 priority 를 설정한다.
                    //priority 가 HIGH 이상일 경우, 현재 화면의 상단에 바로 알림을 세팅한다.
                    //DEFAULT 이하일 경우, 푸시 알림 메세지 목록에 보이게 된다.
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1234, builder.build());
    }
}
