package com.fukaimei.speechrecognizer.gson;

import java.util.List;

/**
 * Created by YangJianlin on 2020/3/14.
 */

public class weather_gson {
    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.4052887","tz":"+8.00"}
         * update : {"loc":"2020-03-14 10:56","utc":"2020-03-14 02:56"}
         * status : ok
         * now : {"cloud":"2","cond_code":"100","cond_txt":"晴","fl":"8","hum":"12","pcpn":"0.0","pres":"1014","tmp":"12","vis":"16","wind_deg":"320","wind_dir":"西北风","wind_sc":"3","wind_spd":"12"}
         * daily_forecast : [{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2020-03-14","hum":"24","mr":"23:52","ms":"09:21","pcpn":"0.0","pop":"0","pres":"1014","sr":"06:25","ss":"18:21","tmp_max":"15","tmp_min":"2","uv_index":"7","vis":"25","wind_deg":"325","wind_dir":"西北风","wind_sc":"4-5","wind_spd":"26"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2020-03-15","hum":"49","mr":"00:00","ms":"10:00","pcpn":"0.0","pop":"0","pres":"1013","sr":"06:23","ss":"18:22","tmp_max":"16","tmp_min":"-1","uv_index":"9","vis":"25","wind_deg":"77","wind_dir":"东北风","wind_sc":"1-2","wind_spd":"7"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2020-03-16","hum":"29","mr":"01:00","ms":"10:44","pcpn":"0.0","pop":"1","pres":"1007","sr":"06:21","ss":"18:23","tmp_max":"12","tmp_min":"2","uv_index":"9","vis":"25","wind_deg":"203","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"1"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2020-03-17","hum":"20","mr":"02:03","ms":"11:34","pcpn":"0.0","pop":"0","pres":"1007","sr":"06:20","ss":"18:24","tmp_max":"16","tmp_min":"2","uv_index":"8","vis":"25","wind_deg":"331","wind_dir":"西北风","wind_sc":"3-4","wind_spd":"19"},{"cond_code_d":"100","cond_code_n":"101","cond_txt_d":"晴","cond_txt_n":"多云","date":"2020-03-18","hum":"21","mr":"02:59","ms":"12:27","pcpn":"0.0","pop":"0","pres":"995","sr":"06:18","ss":"18:25","tmp_max":"19","tmp_min":"3","uv_index":"5","vis":"25","wind_deg":"217","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"7"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2020-03-19","hum":"16","mr":"03:48","ms":"13:25","pcpn":"0.0","pop":"0","pres":"1009","sr":"06:17","ss":"18:26","tmp_max":"20","tmp_min":"4","uv_index":"5","vis":"25","wind_deg":"336","wind_dir":"西北风","wind_sc":"4-5","wind_spd":"29"},{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2020-03-20","hum":"19","mr":"04:29","ms":"14:24","pcpn":"0.0","pop":"0","pres":"1005","sr":"06:15","ss":"18:27","tmp_max":"18","tmp_min":"2","uv_index":"5","vis":"25","wind_deg":"200","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"1"}]
         * hourly : [{"cloud":"0","cond_code":"100","cond_txt":"晴","dew":"-14","hum":"22","pop":"0","pres":"1015","time":"2020-03-14 13:00","tmp":"13","wind_deg":"331","wind_dir":"西北风","wind_sc":"4-5","wind_spd":"31"},{"cloud":"0","cond_code":"100","cond_txt":"晴","dew":"-13","hum":"21","pop":"0","pres":"1015","time":"2020-03-14 16:00","tmp":"15","wind_deg":"346","wind_dir":"西北风","wind_sc":"3-4","wind_spd":"17"},{"cloud":"0","cond_code":"100","cond_txt":"晴","dew":"-10","hum":"27","pop":"0","pres":"1015","time":"2020-03-14 19:00","tmp":"12","wind_deg":"179","wind_dir":"南风","wind_sc":"1-2","wind_spd":"4"},{"cloud":"0","cond_code":"100","cond_txt":"晴","dew":"-10","hum":"33","pop":"0","pres":"1015","time":"2020-03-14 22:00","tmp":"7","wind_deg":"241","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"2"},{"cloud":"0","cond_code":"100","cond_txt":"晴","dew":"-11","hum":"38","pop":"0","pres":"1017","time":"2020-03-15 01:00","tmp":"4","wind_deg":"154","wind_dir":"东南风","wind_sc":"1-2","wind_spd":"2"},{"cloud":"2","cond_code":"100","cond_txt":"晴","dew":"-9","hum":"44","pop":"0","pres":"1016","time":"2020-03-15 04:00","tmp":"2","wind_deg":"134","wind_dir":"东南风","wind_sc":"1-2","wind_spd":"4"},{"cloud":"5","cond_code":"100","cond_txt":"晴","dew":"-7","hum":"38","pop":"0","pres":"1013","time":"2020-03-15 07:00","tmp":"3","wind_deg":"183","wind_dir":"南风","wind_sc":"1-2","wind_spd":"2"},{"cloud":"2","cond_code":"100","cond_txt":"晴","dew":"-6","hum":"35","pop":"0","pres":"1013","time":"2020-03-15 10:00","tmp":"9","wind_deg":"8","wind_dir":"北风","wind_sc":"1-2","wind_spd":"10"}]
         * lifestyle : [{"type":"comf","brf":"较不舒适","txt":"白天天气较凉，且风力较强，您会感觉偏冷，不很舒适，请注意添加衣物，以防感冒。"},{"type":"drsg","brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},{"type":"flu","brf":"易发","txt":"昼夜温差大，风力较强，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"},{"type":"sport","brf":"较适宜","txt":"天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动注意防风并适当增减衣物。"},{"type":"trav","brf":"适宜","txt":"天气较好，风稍大，但温度适宜，是个好天气哦。适宜旅游，您可以尽情地享受大自然的无限风光。"},{"type":"uv","brf":"强","txt":"紫外线辐射强，建议涂擦SPF20左右、PA++的防晒护肤品。避免在10点至14点暴露于日光下。"},{"type":"cw","brf":"适宜","txt":"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。"},{"type":"air","brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除。"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private NowBean now;
        private List<DailyForecastBean> daily_forecast;
        private List<LifestyleBean> lifestyle;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<LifestyleBean> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<LifestyleBean> lifestyle) {
            this.lifestyle = lifestyle;
        }

        public static class BasicBean {
            /**
             * cid : CN101010100
             * location : 北京
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.90498734
             * lon : 116.4052887
             * tz : +8.00
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2020-03-14 10:56
             * utc : 2020-03-14 02:56
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class NowBean {
            /**
             * cloud : 2
             * cond_code : 100
             * cond_txt : 晴
             * fl : 8
             * hum : 12
             * pcpn : 0.0
             * pres : 1014
             * tmp : 12
             * vis : 16
             * wind_deg : 320
             * wind_dir : 西北风
             * wind_sc : 3
             * wind_spd : 12
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class DailyForecastBean {
            /**
             * cond_code_d : 100
             * cond_code_n : 100
             * cond_txt_d : 晴
             * cond_txt_n : 晴
             * date : 2020-03-14
             * hum : 24
             * mr : 23:52
             * ms : 09:21
             * pcpn : 0.0
             * pop : 0
             * pres : 1014
             * sr : 06:25
             * ss : 18:21
             * tmp_max : 15
             * tmp_min : 2
             * uv_index : 7
             * vis : 25
             * wind_deg : 325
             * wind_dir : 西北风
             * wind_sc : 4-5
             * wind_spd : 26
             */

            private String cond_code_d;
            private String cond_code_n;
            private String cond_txt_d;
            private String cond_txt_n;
            private String date;
            private String hum;
            private String mr;
            private String ms;
            private String pcpn;
            private String pop;
            private String pres;
            private String sr;
            private String ss;
            private String tmp_max;
            private String tmp_min;
            private String uv_index;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCond_code_d() {
                return cond_code_d;
            }

            public void setCond_code_d(String cond_code_d) {
                this.cond_code_d = cond_code_d;
            }

            public String getCond_code_n() {
                return cond_code_n;
            }

            public void setCond_code_n(String cond_code_n) {
                this.cond_code_n = cond_code_n;
            }

            public String getCond_txt_d() {
                return cond_txt_d;
            }

            public void setCond_txt_d(String cond_txt_d) {
                this.cond_txt_d = cond_txt_d;
            }

            public String getCond_txt_n() {
                return cond_txt_n;
            }

            public void setCond_txt_n(String cond_txt_n) {
                this.cond_txt_n = cond_txt_n;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class HourlyBean {
            /**
             * cloud : 0
             * cond_code : 100
             * cond_txt : 晴
             * dew : -14
             * hum : 22
             * pop : 0
             * pres : 1015
             * time : 2020-03-14 13:00
             * tmp : 13
             * wind_deg : 331
             * wind_dir : 西北风
             * wind_sc : 4-5
             * wind_spd : 31
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String dew;
            private String hum;
            private String pop;
            private String pres;
            private String time;
            private String tmp;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getDew() {
                return dew;
            }

            public void setDew(String dew) {
                this.dew = dew;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class LifestyleBean {
            /**
             * type : comf
             * brf : 较不舒适
             * txt : 白天天气较凉，且风力较强，您会感觉偏冷，不很舒适，请注意添加衣物，以防感冒。
             */

            private String type;
            private String brf;
            private String txt;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }
}
