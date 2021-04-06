package com.boot.yuntechlife.task;

import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowWarn;
import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.system.MessageQueue;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowService;
import com.boot.yuntechlife.service.flow.YuntechFlowWarnService;
import com.boot.yuntechlife.service.system.MessageQueueService;
import com.boot.yuntechlife.service.system.SystemLogService;
import com.boot.yuntechlife.service.user.UserService;
import com.boot.yuntechlife.util.EmailUtil;
import com.boot.yuntechlife.util.LineUtil;
import com.boot.yuntechlife.util.SmsUtil;
import com.boot.yuntechlife.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

/**
 * @Author: skwen
 * @Description: SinglethreadScheduleTask-單線程任務
 * @Date: 2020-03-08
 */

@Component
@EnableScheduling // 1.开启定时任务
//@EnableAsync        // 不开启多线程
public class SinglethreadScheduleTask {
    @Value("${yuntechFlowWarn.enable}")
    private String yuntechFlowWarnEnable;
    @Value("${messageQueue.enable}")
    private String messageQueueEnable;
    @Autowired
    YuntechFlowWarnService yuntechFlowWarnService;
    @Autowired
    YuntechFlowService yuntechFlowService;
    @Autowired
    MessageQueueService messageQueueService;
    @Autowired
    LineUtil lineUtil;
    @Autowired
    Util util;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    SmsUtil smsUtil;
    @Autowired
    UserService userService;

    @Async
    @Scheduled(fixedDelay = 60000)
    public void disposeYuntechFlowWarn() {
        if (yuntechFlowWarnEnable.equals("false")) {
            return;
        }
        YuntechFlowWarn yuntechFlowWarn = new YuntechFlowWarn();
        List<YuntechFlowWarn> select = yuntechFlowWarnService.disposeWarn(yuntechFlowWarn);
        for (int i = 0; i < select.size(); i++) {
            YuntechFlow yuntechFlow = new YuntechFlow();
            yuntechFlow.setIp(select.get(i).getIp());
            yuntechFlow = yuntechFlowService.getByIp(yuntechFlow);
            yuntechFlowWarn.setUser_id(select.get(i).getUser_id());
            yuntechFlowWarn.setId(select.get(i).getId());
            if (yuntechFlow != null) {
                if (select.get(i).getMail_state() == 5) {
                    if (emailUtil.sendYuntechFlowWarn(select.get(i).getWarn_mail(),
                            "yuntechFlow-流量告警",
                            select.get(i).getIp(),
                            yuntechFlow.getIns_up(),
                            yuntechFlow.getIns_down(),
                            yuntechFlow.getExt_up(),
                            yuntechFlow.getExt_down(),
                            yuntechFlow.getFlow(),
                            yuntechFlow.getRatio(),
                            select.get(i).getWarn_value()
                    )) {
                        //發送成功
                        yuntechFlowWarn.setMail_state(1);
                    } else {
                        yuntechFlowWarn.setMail_state(2);
                    }
                }
                if (select.get(i).getPhone_state() == 5) {
                    User user = new User();
                    user.setId(select.get(i).getUser_id());
                    user = userService.getById(user);
                    if (user != null) {
                        if (user.getBalance() >= 2) {
                            DecimalFormat df = new DecimalFormat("######0.00");
                            if (smsUtil.sendSmsToTaiwan(select.get(i).getWarn_phone(),
                                    "【yuntechFlow】-流量告警\n[" + yuntechFlow.getIp() + "]當前使用流量" +
                                            df.format(yuntechFlow.getFlow()) + "Gb,已超預設值" +
                                            df.format(select.get(i).getWarn_value()) + "Gb，請注意節流!\n(本條2元)"
                            )) {
                                //發送成功
                                yuntechFlowWarn.setPhone_state(1);
                            } else {//發送失敗
                                yuntechFlowWarn.setPhone_state(2);
                            }
                        } else {//餘額不足
                            yuntechFlowWarn.setPhone_state(4);
                        }

                    } else {//查無此用戶
                        yuntechFlowWarn.setPhone_state(2);
                    }
                }
            } else {//查無此流量記錄
                yuntechFlowWarn.setMail_state(2);
                yuntechFlowWarn.setPhone_state(2);
            }
            yuntechFlowWarnService.updateOne(yuntechFlowWarn);
        }
    }

    @Async
    @Scheduled(fixedDelay = 10000)
    public void messageQueue(){
        if (messageQueueEnable.equals("false")) {
            return;
        }
        MessageQueue messageQueue = new MessageQueue();
        List<MessageQueue> select = messageQueueService.getList(messageQueue);
        if (select.size()>0){
            for (int i = 0; i < select.size(); i++) {
                messageQueue.setId(select.get(i).getId());
                if (select.get(i).getType() == 1) {
                    for (int ii = 0; ii < select.get(i).getRetry_num(); ii++) {
                        if (emailUtil.sendEasyMail(select.get(i).getTo(), select.get(i).getTitle(), select.get(i).getMsg())) {
                            messageQueue.setState(2);
                            messageQueue.setTry_num(ii + 1);
                            break;
                        } else {
                            messageQueue.setState(3);
                            messageQueue.setTry_num(ii + 1);
                        }
                    }
                    messageQueueService.updateOne(messageQueue);
                } else if (select.get(i).getType() == 2) {
                    for (int ii = 0; ii < select.get(i).getRetry_num(); ii++) {
                        if (smsUtil.sendSmsToTaiwan(select.get(i).getTo(), select.get(i).getMsg())) {
                            messageQueue.setState(2);
                            messageQueue.setTry_num(ii + 1);
                            break;
                        } else {
                            messageQueue.setState(3);
                            messageQueue.setTry_num(ii + 1);
                        }
                    }
                    messageQueueService.updateOne(messageQueue);
                }else if (select.get(i).getType() == 3) {
                    for (int ii = 0; ii < select.get(i).getRetry_num(); ii++) {
                        if (lineUtil.pushMessage(select.get(i).getTo(),"text", select.get(i).getMsg())) {
                            messageQueue.setState(2);
                            messageQueue.setTry_num(ii + 1);
                            break;
                        } else {
                            messageQueue.setState(3);
                            messageQueue.setTry_num(ii + 1);
                        }
                    }
                    messageQueueService.updateOne(messageQueue);
                }

            }
        }
    }

//    @Async
//    @Scheduled(fixedDelay = 2000)
//    public void line() throws IOException {
//        System.out.println(lineUtil.send("U141e6063b965e310baf930816c8f8f07","text","你好"));
//    }
//    @Async
//    @Scheduled(fixedDelay = 2000)
//    public void second() {
//        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//    }

}
