package com.boot.yuntechlife.task;

import com.boot.yuntechlife.entity.school.YuntechExpress;
import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.system.Performance;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.school.YuntechExpressService;
import com.boot.yuntechlife.service.flow.YuntechFlowWarnService;
import com.boot.yuntechlife.service.system.BaiduStatisticsService;
import com.boot.yuntechlife.service.system.PerformanceService;
import com.boot.yuntechlife.util.BaiduCloudApiUtil;
import com.boot.yuntechlife.util.HttpUtil;
import com.boot.yuntechlife.util.WindowsInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: skwen
 * @Description: MultithreadScheduleTask-多線程任務
 * @Date: 2020-03-08
 */

@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MultithreadScheduleTask {
    @Value("${baiduStatistics.enable}")
    private boolean baiduStatisticsEnable;
    @Value("${yuntechExpress.sleepDays}")
    private int[] sleepDays;
    @Value("${yuntechExpress.sleepTimes}")
    private int[] sleepTimes;
    @Value("${yuntechExpress.enable}")
    private boolean yuntechExpressEnable;
    @Value("${performance.serviceId.client}")
    private int serviceId;
    @Autowired
    BaiduCloudApiUtil baiduCloudApiUtil;
    @Autowired
    BaiduStatisticsService baiduStatisticsService;
    @Autowired
    YuntechFlowWarnService yuntechFlowWarnService;
    @Autowired
    YuntechExpressService yuntechExpressService;
    @Autowired
    private WindowsInfoUtil windowsInfoUtil;
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    HttpUtil httpUtil;

    @Async
    @Scheduled(fixedDelay = 600000)
    public void getBaiduStatisticsInfo() throws ParseException {
        if (!baiduStatisticsEnable) {
            return;
        }
        BaiduStatistics baiduStatistics = baiduCloudApiUtil.getStatisticsInfo();
        baiduStatisticsService.insertOne(baiduStatistics);
    }

    @Async
    @Scheduled(fixedDelay = 120000)
    public void getYuntechExpress() throws IOException, ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (!yuntechExpressEnable || ifExistByLists(sleepDays, cal.get(Calendar.DAY_OF_WEEK)) || ifExistByLists(sleepTimes, cal.get(Calendar.HOUR_OF_DAY))) {
            return;
        }
        String regular = "<td data-dateformat=\"YYYY-M-D hh:mm\">([\\s\\S]*?)</td>[\\S\\s]*?<td>([\\S\\s]*?)</td>[\\S\\s]*?<td>([\\s\\S]*?)</td>[\\S\\s]*?<td>([\\s\\S]*?)</td>[\\S\\s]*?<td>([\\S\\s]*?)</td>";
        String dataA = httpUtil.get("http://140.125.203.246/area-a", null);
        String dataB = httpUtil.get("http://140.125.203.246/area-b", null);
        String dataC = httpUtil.get("http://140.125.203.246/area-c", null);
        String dataD = httpUtil.get("http://140.125.203.246/area-d", null);
        String dataE = httpUtil.get("http://140.125.203.246/area-e", null);
        String dataG = httpUtil.get("http://140.125.203.246/area-g", null);
        Pattern pattern = Pattern.compile(regular);
        Matcher datas = pattern.matcher(dataA + dataB + dataC + dataD + dataE + dataG);
        String lists = "";
        while (datas.find()) {
//            1:上架時間
//            2:物流名稱
//            3:物流單號
//            4:姓名
//            5:宿舍
            YuntechExpress yuntechExpress = new YuntechExpress();
            lists += datas.group(3) + datas.group(4) + datas.group(5) + "\n";
            yuntechExpress.setState(1);
            yuntechExpress.setRegion(getRegionByid(datas.group(5).substring(0, 1)));
            yuntechExpress.setExpress_name(datas.group(2).replaceAll("\\u3000", ""));
            yuntechExpress.setExpress_num(datas.group(3));
            yuntechExpress.setAddressee(datas.group(4));
            yuntechExpress.setDormitory(datas.group(5));
            int count = yuntechExpressService.getCount(yuntechExpress);
            if (count == 0) {
                yuntechExpress.setReceipt_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datas.group(1) + ":00"));
                yuntechExpressService.insertOne(yuntechExpress);
            }
        }
        if (dataA != null && dataA != "" && dataB != null && dataB != "" && dataC != null && dataC != "" && dataD != null && dataD != "" && dataE != null && dataE != "" && dataG != null && dataG != "") {
            User user = new User();
            user.setState(1);
            List<YuntechExpress> select = yuntechExpressService.getListByTask(user);
            for (int i = 0; i < select.size(); i++) {
                if (lists.indexOf(select.get(i).getExpress_num() + select.get(i).getAddressee() + select.get(i).getDormitory()) == -1) {
                    YuntechExpress yuntechExpress = new YuntechExpress();
                    yuntechExpress.setId(select.get(i).getId());
                    yuntechExpress.setState(2);
                    yuntechExpressService.updateOne(yuntechExpress);
                }
            }
        }
    }

    public int getRegionByid(String str) {
        switch (str) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            default:
                return 0;
        }
    }

    public boolean ifExistByLists(int[] lists, int hours) {
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] == hours) {
                return true;
            }
        }
        return false;
    }

    @Async
    @Scheduled(fixedDelay = 1000*60*5)
    public void addPerformanceByPerformance() {
        Performance performance = new Performance();
        performance.setService_id(serviceId);
        performance.setCpu(windowsInfoUtil.getCpu());
        performance.setRam(windowsInfoUtil.getRam());
        performanceService.insertOne(performance);
    }
//
//   @Async
//    @Scheduled(fixedDelay = 2000)
//    public void second() {
//        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//    }
}
