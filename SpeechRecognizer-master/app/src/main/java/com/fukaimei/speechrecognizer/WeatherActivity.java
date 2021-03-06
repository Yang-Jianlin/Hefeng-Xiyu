package com.fukaimei.speechrecognizer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.fukaimei.speechrecognizer.gson.Forecast;
import com.fukaimei.speechrecognizer.gson.Weather;
import com.fukaimei.speechrecognizer.service.AutoUpdateService;
import com.fukaimei.speechrecognizer.util.HttpUtil;
import com.fukaimei.speechrecognizer.util.JsonParser;
import com.fukaimei.speechrecognizer.util.Utility;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.fukaimei.speechrecognizer.ChooseAreaFragment.autoid;

public class WeatherActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    public SwipeRefreshLayout swipeRefresh;

    private ScrollView weatherLayout;

    private Button navButton;

    private TextView titleCity;

   // private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;   //????????????

    private TextView pm25Text;  //????????????

    private TextView fengscText;  //??????

    private TextView visionText;  //?????????

    private TextView airText;  //????????????

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;

    private String mWeatherId;
    public int time=1,count=1;

    private ImageView image;

    private ImageView imageforcast;
    //????????????
    private Button btnPlay;
    public SystemTTS systemTTS;

    private static String autoidd;
    public static String speechstr="null";
    public String citytimeid="null";
    public static int flag=0;

    public boolean isFirstRunVoice;//???????????????????????????APP
    SharedPreferences.Editor editorvoice;//???????????????????????????APP
    public static int flagvoice=0;
    public int startstop=0;

//??????
    private final static String TAG = WeatherActivity.class.getSimpleName();
    // ??????????????????
    private SpeechRecognizer mRecognize;
    // ????????????UI
    private RecognizerDialog mRecognizeDialog;
    // ???HashMap??????????????????
    private HashMap<String, String> mRecognizeResults = new LinkedHashMap<String, String>();

    //private EditText mResultText;
    private SharedPreferences mSharedPreferences;

    FindCity find=new FindCity();

    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SpeechUtility.createUtility(WeatherActivity.this, "appid=5a046888");
        super.onCreate(savedInstanceState);

        WelcomeActivity www=new WelcomeActivity();
        //???????????????????????????????????????????????????????????????????????????????????????
        SharedPreferences firstenter = this.getSharedPreferences("share", MODE_PRIVATE);
        www.isFirstRun = firstenter.getBoolean("isFirstRun", true);
        www.editor = firstenter.edit();
        www.editor.putBoolean("isFirstRun", false);
        www.editor.commit();

        //??????
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_voice_recognize);
        // ?????????????????????????????????
        soundPermissions();


        mSharedPreferences = getSharedPreferences("com.example.thirdsdk", Activity.MODE_PRIVATE);

        // ??????????????????UI?????????????????????SpeechRecognizer????????????????????????????????????????????????
        mRecognize = SpeechRecognizer.createRecognizer(this, mInitListener);
        // ???????????????Dialog?????????????????????UI???????????????????????????SpeechRecognizer
        // ??????UI?????????????????????assets???????????????????????????
        mRecognizeDialog = new RecognizerDialog(this, mInitListener);


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        // ??????????????????
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        //titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        fengscText = (TextView) findViewById(R.id.fengsc_txt);
        visionText = (TextView) findViewById(R.id.vision_txt);
        airText = (TextView) findViewById(R.id.air_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button) findViewById(R.id.nav_button);
        image = (ImageView) findViewById(R.id.changeimage);
        //imageforcast = (ImageView) findViewById(R.id.forcastchange);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // ????????????????????????????????????
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // ????????????????????????????????????
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Button button=(Button)findViewById(R.id.nav_shijian);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==1) {
                    Toast.makeText(WeatherActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    time=1;
                }
                if(count==2) {
                    Toast.makeText(WeatherActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    time=2;
                }
                if(count==3) {
                    Toast.makeText(WeatherActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    time=6;
                }
                if(count==4) {
                    Toast.makeText(WeatherActivity.this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                    time=12;
                }
                if(count==6) {
                    Toast.makeText(WeatherActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    time=24;
                }
                count++;
                if(count>6)
                    count=1;
            }

        });

        //????????????????????????????????????
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("share1", MODE_PRIVATE);
        isFirstRunVoice = sharedPreferences1.getBoolean("isFirstRunVoice", true);
        editorvoice = sharedPreferences1.edit();
        //????????????
        Button speechbutton=(Button) findViewById(R.id.nav_speech);
        speechbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //systemTTS.play("??????????????????");
                systemTTS.stop();
                startstop=0;
                int ret = 0; // ?????????????????????
                int resid = v.getId();
                if (resid == R.id.nav_speech) {  // ????????????????????????????????????????????????OnResult isLast=true ?????? onError
                    //mResultText.setText(null);// ??????????????????

                    if(isFirstRunVoice&&flagvoice==0) {
                        flagvoice=1;
                        systemTTS.play("?????????????????????????????????????????????????????????????????????????????????");
                        systemTTS.play("????????????????????????");
                        editorvoice.putBoolean("isFirstRunVoice", false);
                        editorvoice.commit();

                        try{
                            Thread.sleep(8500);
                        }catch (InterruptedException e) {}
                    }
                    else{}


                       mRecognizeResults.clear();
                       // ????????????
                       //resetParam();

                       boolean isShowDialog = mSharedPreferences.getBoolean("show_dialog", true);
                       if (isShowDialog) {
                           // ?????????????????????
                           //systemTTS.play("??????????????????");
                           mRecognizeDialog.setListener(mRecognizeDialogListener);
                           mRecognizeDialog.show();
                           showTip("??????????????????????????????????????????...");
                       } else {
                           // ????????????????????????
                           ret = mRecognize.startListening(mRecognizeListener);
                           if (ret != ErrorCode.SUCCESS) {
                               showTip("????????????,????????????" + ret);
                           } else {
                               showTip("??????????????????");
                           }
                       }
                   }

                //}
            }

        });

        //???????????????????????????
        Button skipButton=(Button)findViewById(R.id.ent_right);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.heweather.com"));
                startActivity(intent);
            }
        });

        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }

        //????????????
        initView();
        initData();
        initListener();
    }

    /**
     * ????????????id???????????????????????????
     */


    public void requestWeather(final String weatherId) {
        //String weatherUrl = "free-api.heweather.net/s6/weather/?location=" + weatherId + "&key=9f864871b4e74a72b14a028b68266c43"
        //String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
        //????????????????????????????????????????????????????????????????????????id

        if (autoid!=null) {
            SharedPreferences setting = getSharedPreferences("setting", 0);
            SharedPreferences.Editor editor = setting.edit();
            editor.putString("apiUrl", autoid).commit();
        }
        if(autoid==null) {
            SharedPreferences setting1 = getSharedPreferences("setting", 0);
            autoid = setting1.getString("apiUrl", "");
        }
        String weatherUrl = "https://free-api.heweather.net/s6/weather/?location=" + autoid + "&key=9f864871b4e74a72b14a028b68266c43";
        System.out.println(weatherUrl);
        System.out.println(weatherUrl);
        if (!speechstr.equals("null")) {
            weatherUrl = find.cityname_id(speechstr);
            flag=1;
        }
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }
    /**
     * ????????????????????????
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * ???????????????Weather????????????????????????
     */
    String cityName;
    String updateTime;
    String degree;
    String weatherInfo;
    String sportmm;
    private void showWeatherInfo(Weather weather) {
        cityName = weather.basic.cityName;
        updateTime = weather.update.updateTime.split(" ")[1];
        degree = weather.now.temperature;
        weatherInfo = weather.now.condtext;
        titleCity.setText(cityName);
        //titleUpdateTime.setText(updateTime);
        degreeText.setText(degree+ "???");
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        int j=0,k=0;  //??????????????????
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            if(k==0){
                dateText.setText(forecast.date.substring(5)+"  ??????");
            }
            else if (k==1){
                dateText.setText(forecast.date.substring(5)+"  ??????");
            }
            else
                dateText.setText(forecast.date);
            infoText.setText(forecast.condday);

            maxText.setText(forecast.temmax);
            minText.setText(forecast.temmin);
            forecastLayout.addView(view);

            //???????????????
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "chat";
                String channelName = "????????????";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                createNotificationChannel(channelId, channelName, importance);

                channelId = "subscribe";
                channelName = "????????????";
                importance = NotificationManager.IMPORTANCE_DEFAULT;
                createNotificationChannel(channelId, channelName, importance);
            }
            if(j==1&&(forecast.condday.substring(forecast.condday.length()-1,forecast.condday.length()).equals("???"))) {
                //showNotification(this, 1, "???????????????????????????");
                System.out.println("flag");
                System.out.println(flag);
                System.out.println(flag);
                System.out.println(flag);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this, "chat")
                        .setContentTitle("???????????????????????????")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.dayu)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dayu))
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);
            }
            j++;k++;
        }
        //if (weather.now != null) {
        aqiText.setText(weather.now.flwen);  //????????????
        pm25Text.setText(weather.now.xhum);   //????????????
        fengscText.setText(weather.now.windsc);
        visionText.setText(weather.now.vision);
        // }

        String[] lifestylesug=new String[8];
        for (int i = 0; i < weather.lifestyleList.size(); i++) {
            lifestylesug[i]=weather.lifestyleList.get(i).sugbrf+ "," + weather.lifestyleList.get(i).sugtxt;
            //System.out.println(s.getId()+"  "+s.getTitle()+"  "+s.getAuthor());
        }

        String air ="???????????????" + lifestylesug[7];
        String comfort = "????????????" + lifestylesug[0];
        String carWash = "???????????????" + lifestylesug[6];
        String sport = "???????????????" + lifestylesug[3];
        sportmm=sport;
        airText.setText(air);
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);

        if(weatherInfo.equals("???")){
            image.setImageResource(R.drawable.sun);
        }
        else if (weatherInfo.equals("??????")){
            image.setImageResource(R.drawable.duoyun);
        }
        else if (weatherInfo.equals("???")){
            image.setImageResource(R.drawable.yin);
        }
        else if(weatherInfo.equals("???")){
            image.setImageResource(R.drawable.zhongyu);
        }
        else if (weatherInfo.equals("??????")){
            image.setImageResource(R.drawable.xiaoyu);
        }
        else if (weatherInfo.equals("??????")){
            image.setImageResource(R.drawable.zhongyu);
        }
        else if (weatherInfo.equals("??????")){
            image.setImageResource(R.drawable.zhenyu);
        }
        else if (weatherInfo.equals("?????????")){
            image.setImageResource(R.drawable.leizhenyu);
        }
        else if (weatherInfo.equals("??????")| weatherInfo.equals("??????")){
            image.setImageResource(R.drawable.dayu);
        }
        else if (weatherInfo.equals("???")){
            image.setImageResource(R.drawable.xue);
        }
        else if (weatherInfo.substring(weatherInfo.length()-1,weatherInfo.length()).equals("???")){
            image.setImageResource(R.drawable.xue);
        }
        else {
            image.setImageResource(R.drawable.tianqi);
        }
    }


    //????????????
    private void initView() {
        btnPlay = (Button) findViewById(R.id.nav_voice);
    }


    private void initData() {
        systemTTS = SystemTTS.getInstance(this);
    }


    //????????????????????????????????????
    private void initListener() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String words = getWords();
                if(startstop==0) {
                    if (!TextUtils.isEmpty(words)) {
                        systemTTS.play(words);
                    }
                    startstop=1;
                }
                else {
                    systemTTS.stop();
                    startstop=0;
                }

            }
        });
    }


    private String getWords() {
        String city=cityName;
        String weatherName=weatherInfo;
        String wenduName=degree;
        String sportName=sportmm;
        String wenduNameChina=wenduName;

        if (wenduName.substring(0,1).equals("-")) {
            //wenduName = "??????" + wenduName.substring(1, wenduName.length());
            wenduNameChina = wenduName.substring(1, wenduName.length());
        }
        System.out.println("??????");
        System.out.println(wenduNameChina);
        System.out.println(wenduNameChina);
        System.out.println(wenduNameChina);
        int wenduChinese = Integer.parseInt(wenduNameChina);
        String wendushow= numberToChinese(wenduChinese);
        if (wenduName.substring(0,1).equals("-")) {
            wendushow="??????"+wendushow;
        }
        String etWeather="?????????????????????????????????" + " " + city + " " + weatherName + " " + "??????????????? " +  wendushow + " ???????????? ";
        String etSport="  ???      ???  ????????????????????????" + sportName;
        String etWords=etWeather+etSport;
        return etWords;
    }


    private static String nums[] = {"???", "???", "???", "???", "???", "???", "???", "???", "???", "???"};

    private static String pos_units[] = {"", "???", "???", "???"};

    private static String weight_units[] = {"", "???", "???"};

    /**
     * ????????????????????????
     *
     * @param num
     * @return
     */
    public static String numberToChinese(int num) {
        if (num == 0) {
            return "???";
        }

        int weigth = 0;//?????????
        String chinese = "";
        String chinese_section = "";
        boolean setZero = false;//??????????????????????????????????????????????????????????????????false
        while (num > 0) {
            int section = num % 10000;//????????????????????????
            if (setZero) {//?????????????????????????????????????????????????????????
                chinese = nums[0] + chinese;
            }
            chinese_section = sectionTrans(section);
            if (section != 0) {//????????????????????????
                chinese_section = chinese_section + weight_units[weigth];
            }
            chinese = chinese_section + chinese;
            chinese_section = "";

            setZero = (section < 1000) && (section > 0);
            num = num / 10000;
            weigth++;
        }
        if ((chinese.length() == 2 || (chinese.length() == 3)) && chinese.contains("??????")) {
            chinese = chinese.substring(1, chinese.length());
        }
        if (chinese.indexOf("??????") == 0) {
            chinese = chinese.replaceFirst("??????", "???");
        }

        return chinese;
    }

    /**
     * ????????????????????????
     * @param section
     * @return
     */
    public static String sectionTrans(int section) {
        StringBuilder section_chinese = new StringBuilder();
        int pos = 0;//??????????????????????????????
        boolean zero = true;//??????????????????????????????????????????????????????????????????
        while (section > 0) {
            int v = section % 10;//?????????????????????
            if (v == 0) {
                if (!zero) {
                    zero = true;//??????????????????????????????????????????????????????????????????
                    section_chinese.insert(0, nums[0]);
                }
            } else {
                zero = false;//?????????????????????????????????
                section_chinese.insert(0, pos_units[pos]);
                section_chinese.insert(0, nums[v]);
            }
            pos++;
            section = section / 10;
        }

        return section_chinese.toString();
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        // ??????????????????????????????
    }



    //??????
    //??????

    //??????????????????
    private InitListener mInitListener = new InitListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("??????????????????????????????" + code);
            }
        }
    };

    //???????????????
    private RecognizerListener mRecognizeListener = new RecognizerListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBeginOfSpeech() {
            // ??????????????????sdk??????????????????????????????????????????????????????????????????
            showTip("????????????");
        }

        @Override
        public void onError(SpeechError error) {
            // ????????????10118(???????????????)????????????????????????????????????????????????????????????????????????????????????
            // ????????????????????????????????????????????????????????????????????????????????????
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????
            showTip("????????????");
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);
            if (isLast) {
                // TODO ???????????????
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("????????????????????????????????????" + volume);
            Log.d(TAG, "?????????????????????"+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        mRecognizeResults.put(sn, text);
        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mRecognizeResults.keySet()) {
            resultBuffer.append(mRecognizeResults.get(key));
        }
        speechstr=resultBuffer.toString();
        swipeRefresh.setRefreshing(true);
        requestWeather(mWeatherId);
        System.out.println("find.n");
        System.out.println(find.n);
        if (find.n==1) {
            Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            systemTTS.play("????????????????????????????????????");
            find.n=1;
        }
        if (find.n==10){
            Toast.makeText(this, "???????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
        }
    }

    //??????UI?????????
    private RecognizerDialogListener mRecognizeDialogListener = new RecognizerDialogListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        //??????????????????
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }
    };

    private void showTip(final String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    // ???????????????????????????
    private void soundPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    /**
     * ??????onRequestPermissionsResult??????
     * ?????????????????????????????????,???????????????
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        systemTTS.destroy();
        mRecognize.cancel();
        mRecognize.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            systemTTS.stop();  //?????????????????????????????????
            startstop=0;  //????????????????????????
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
