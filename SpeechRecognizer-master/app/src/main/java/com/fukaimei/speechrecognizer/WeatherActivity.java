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

    private TextView aqiText;   //体感温度

    private TextView pm25Text;  //空气湿度

    private TextView fengscText;  //风力

    private TextView visionText;  //能见度

    private TextView airText;  //空气状况

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;

    private String mWeatherId;
    public int time=1,count=1;

    private ImageView image;

    private ImageView imageforcast;
    //语音播报
    private Button btnPlay;
    public SystemTTS systemTTS;

    private static String autoidd;
    public static String speechstr="null";
    public String citytimeid="null";
    public static int flag=0;

    public boolean isFirstRunVoice;//判断是否第一次使用APP
    SharedPreferences.Editor editorvoice;//判断是否第一次使用APP
    public static int flagvoice=0;
    public int startstop=0;

//识别
    private final static String TAG = WeatherActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mRecognize;
    // 语音听写UI
    private RecognizerDialog mRecognizeDialog;
    // 用HashMap存储听写结果
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
        //判断第一次进入时，是否选择完城市，否则第二次进入会产生错误
        SharedPreferences firstenter = this.getSharedPreferences("share", MODE_PRIVATE);
        www.isFirstRun = firstenter.getBoolean("isFirstRun", true);
        www.editor = firstenter.edit();
        www.editor.putBoolean("isFirstRun", false);
        www.editor.commit();

        //识别
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_voice_recognize);
        // 定义获取录音的动态权限
        soundPermissions();


        mSharedPreferences = getSharedPreferences("com.example.thirdsdk", Activity.MODE_PRIVATE);

        // 初始化识别无UI识别对象，使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mRecognize = SpeechRecognizer.createRecognizer(this, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请将assets下文件拷贝到项目中
        mRecognizeDialog = new RecognizerDialog(this, mInitListener);


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        // 初始化各控件
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
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
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
                    Toast.makeText(WeatherActivity.this, "每一小时更新一次", Toast.LENGTH_SHORT).show();
                    time=1;
                }
                if(count==2) {
                    Toast.makeText(WeatherActivity.this, "每二小时更新一次", Toast.LENGTH_SHORT).show();
                    time=2;
                }
                if(count==3) {
                    Toast.makeText(WeatherActivity.this, "每六小时更新一次", Toast.LENGTH_SHORT).show();
                    time=6;
                }
                if(count==4) {
                    Toast.makeText(WeatherActivity.this, "每十二小时更新一次", Toast.LENGTH_SHORT).show();
                    time=12;
                }
                if(count==6) {
                    Toast.makeText(WeatherActivity.this, "每二十四小时更新一次", Toast.LENGTH_SHORT).show();
                    time=24;
                }
                count++;
                if(count>6)
                    count=1;
            }

        });

        //判断是否首次使用语音助手
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("share1", MODE_PRIVATE);
        isFirstRunVoice = sharedPreferences1.getBoolean("isFirstRunVoice", true);
        editorvoice = sharedPreferences1.edit();
        //语音识别
        Button speechbutton=(Button) findViewById(R.id.nav_speech);
        speechbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //systemTTS.play("试试说：杭州");
                systemTTS.stop();
                startstop=0;
                int ret = 0; // 函数调用返回值
                int resid = v.getId();
                if (resid == R.id.nav_speech) {  // 开始听写。如何判断一次听写结束：OnResult isLast=true 或者 onError
                    //mResultText.setText(null);// 清空显示内容

                    if(isFirstRunVoice&&flagvoice==0) {
                        flagvoice=1;
                        systemTTS.play("您好，我是小风语音，请说出城市名称，小风为您切换天气。");
                        systemTTS.play("试试说“北京”。");
                        editorvoice.putBoolean("isFirstRunVoice", false);
                        editorvoice.commit();

                        try{
                            Thread.sleep(8500);
                        }catch (InterruptedException e) {}
                    }
                    else{}


                       mRecognizeResults.clear();
                       // 设置参数
                       //resetParam();

                       boolean isShowDialog = mSharedPreferences.getBoolean("show_dialog", true);
                       if (isShowDialog) {
                           // 显示听写对话框
                           //systemTTS.play("试试说：杭州");
                           mRecognizeDialog.setListener(mRecognizeDialogListener);
                           mRecognizeDialog.show();
                           showTip("请开始说话…，例如北京，杭州...");
                       } else {
                           // 不显示听写对话框
                           ret = mRecognize.startListening(mRecognizeListener);
                           if (ret != ErrorCode.SUCCESS) {
                               showTip("听写失败,错误码：" + ret);
                           } else {
                               showTip("请开始说话…");
                           }
                       }
                   }

                //}
            }

        });

        //进入网站看详细数据
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

        //语音播报
        initView();
        initData();
        initListener();
    }

    /**
     * 根据天气id请求城市天气信息。
     */


    public void requestWeather(final String weatherId) {
        //String weatherUrl = "free-api.heweather.net/s6/weather/?location=" + weatherId + "&key=9f864871b4e74a72b14a028b68266c43"
        //String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
        //实现数据长期存储，方便再次进入程序，读取上次城市id

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
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }
    /**
     * 加载必应每日一图
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
     * 处理并展示Weather实体类中的数据。
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
        degreeText.setText(degree+ "℃");
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        int j=0,k=0;  //判断循环次数
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            if(k==0){
                dateText.setText(forecast.date.substring(5)+"  今天");
            }
            else if (k==1){
                dateText.setText(forecast.date.substring(5)+"  明天");
            }
            else
                dateText.setText(forecast.date);
            infoText.setText(forecast.condday);

            maxText.setText(forecast.temmax);
            minText.setText(forecast.temmin);
            forecastLayout.addView(view);

            //通知栏通知
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "chat";
                String channelName = "聊天消息";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                createNotificationChannel(channelId, channelName, importance);

                channelId = "subscribe";
                channelName = "订阅消息";
                importance = NotificationManager.IMPORTANCE_DEFAULT;
                createNotificationChannel(channelId, channelName, importance);
            }
            if(j==1&&(forecast.condday.substring(forecast.condday.length()-1,forecast.condday.length()).equals("雨"))) {
                //showNotification(this, 1, "明日有雨，带上雨伞");
                System.out.println("flag");
                System.out.println(flag);
                System.out.println(flag);
                System.out.println(flag);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this, "chat")
                        .setContentTitle("明日有雨，带上雨伞")
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
        aqiText.setText(weather.now.flwen);  //体感温度
        pm25Text.setText(weather.now.xhum);   //空气湿度
        fengscText.setText(weather.now.windsc);
        visionText.setText(weather.now.vision);
        // }

        String[] lifestylesug=new String[8];
        for (int i = 0; i < weather.lifestyleList.size(); i++) {
            lifestylesug[i]=weather.lifestyleList.get(i).sugbrf+ "," + weather.lifestyleList.get(i).sugtxt;
            //System.out.println(s.getId()+"  "+s.getTitle()+"  "+s.getAuthor());
        }

        String air ="空气状况：" + lifestylesug[7];
        String comfort = "舒适度：" + lifestylesug[0];
        String carWash = "洗车指数：" + lifestylesug[6];
        String sport = "运动建议：" + lifestylesug[3];
        sportmm=sport;
        airText.setText(air);
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);

        if(weatherInfo.equals("晴")){
            image.setImageResource(R.drawable.sun);
        }
        else if (weatherInfo.equals("多云")){
            image.setImageResource(R.drawable.duoyun);
        }
        else if (weatherInfo.equals("阴")){
            image.setImageResource(R.drawable.yin);
        }
        else if(weatherInfo.equals("雨")){
            image.setImageResource(R.drawable.zhongyu);
        }
        else if (weatherInfo.equals("小雨")){
            image.setImageResource(R.drawable.xiaoyu);
        }
        else if (weatherInfo.equals("中雨")){
            image.setImageResource(R.drawable.zhongyu);
        }
        else if (weatherInfo.equals("阵雨")){
            image.setImageResource(R.drawable.zhenyu);
        }
        else if (weatherInfo.equals("雷阵雨")){
            image.setImageResource(R.drawable.leizhenyu);
        }
        else if (weatherInfo.equals("大雨")| weatherInfo.equals("暴雨")){
            image.setImageResource(R.drawable.dayu);
        }
        else if (weatherInfo.equals("雪")){
            image.setImageResource(R.drawable.xue);
        }
        else if (weatherInfo.substring(weatherInfo.length()-1,weatherInfo.length()).equals("雪")){
            image.setImageResource(R.drawable.xue);
        }
        else {
            image.setImageResource(R.drawable.tianqi);
        }
    }


    //语音播报
    private void initView() {
        btnPlay = (Button) findViewById(R.id.nav_voice);
    }


    private void initData() {
        systemTTS = SystemTTS.getInstance(this);
    }


    //按一下播放，再按一下停止
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
            //wenduName = "零下" + wenduName.substring(1, wenduName.length());
            wenduNameChina = wenduName.substring(1, wenduName.length());
        }
        System.out.println("温度");
        System.out.println(wenduNameChina);
        System.out.println(wenduNameChina);
        System.out.println(wenduNameChina);
        int wenduChinese = Integer.parseInt(wenduNameChina);
        String wendushow= numberToChinese(wenduChinese);
        if (wenduName.substring(0,1).equals("-")) {
            wendushow="零下"+wendushow;
        }
        String etWeather="小风为您播报实时天气：" + " " + city + " " + weatherName + " " + "当前气温： " +  wendushow + " 摄氏度。 ";
        String etSport="  。      。  这里小风给您一些" + sportName;
        String etWords=etWeather+etSport;
        return etWords;
    }


    private static String nums[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    private static String pos_units[] = {"", "十", "百", "千"};

    private static String weight_units[] = {"", "万", "亿"};

    /**
     * 数字转汉字【新】
     *
     * @param num
     * @return
     */
    public static String numberToChinese(int num) {
        if (num == 0) {
            return "零";
        }

        int weigth = 0;//节权位
        String chinese = "";
        String chinese_section = "";
        boolean setZero = false;//下一小节是否需要零，第一次没有上一小节所以为false
        while (num > 0) {
            int section = num % 10000;//得到最后面的小节
            if (setZero) {//判断上一小节的千位是否为零，是就设置零
                chinese = nums[0] + chinese;
            }
            chinese_section = sectionTrans(section);
            if (section != 0) {//判断是都加节权位
                chinese_section = chinese_section + weight_units[weigth];
            }
            chinese = chinese_section + chinese;
            chinese_section = "";

            setZero = (section < 1000) && (section > 0);
            num = num / 10000;
            weigth++;
        }
        if ((chinese.length() == 2 || (chinese.length() == 3)) && chinese.contains("一十")) {
            chinese = chinese.substring(1, chinese.length());
        }
        if (chinese.indexOf("一十") == 0) {
            chinese = chinese.replaceFirst("一十", "十");
        }

        return chinese;
    }

    /**
     * 将每段数字转汉子
     * @param section
     * @return
     */
    public static String sectionTrans(int section) {
        StringBuilder section_chinese = new StringBuilder();
        int pos = 0;//小节内部权位的计数器
        boolean zero = true;//小节内部的置零判断，每一个小节只能有一个零。
        while (section > 0) {
            int v = section % 10;//得到最后一个数
            if (v == 0) {
                if (!zero) {
                    zero = true;//需要补零的操作，确保对连续多个零只是输出一个
                    section_chinese.insert(0, nums[0]);
                }
            } else {
                zero = false;//有非零数字就把置零打开
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
        // 需要开启通知声音权限
    }



    //识别
    //识别

    //初始化监听器
    private InitListener mInitListener = new InitListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };

    //听写监听器
    private RecognizerListener mRecognizeListener = new RecognizerListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);
            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
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
            Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
            systemTTS.play("小风已为您成功切换城市。");
            find.n=1;
        }
        if (find.n==10){
            Toast.makeText(this, "对不起，识别未成功，小风为您切换回初选城市", Toast.LENGTH_SHORT).show();
        }
    }

    //听写UI监听器
    private RecognizerDialogListener mRecognizeDialogListener = new RecognizerDialogListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        //识别回调错误
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }
    };

    private void showTip(final String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    // 定义录音的动态权限
    private void soundPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    /**
     * 重写onRequestPermissionsResult方法
     * 获取动态权限请求的结果,再开启录音
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(this, "用户拒绝了权限", Toast.LENGTH_SHORT).show();
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
            systemTTS.stop();  //返回键后，语音停止播报
            startstop=0;  //语音回到初始状态
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
