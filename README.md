# itech-api
## 快速開始
itech - 榮獲 【專題人氣獎第二名】 【研討會嘉獎】

demo: https://itech.iskwen.com
(測試賬號：test，密碼：test123456)

前端：https://github.com/S-kwen/itech-view

admin：https://github.com/S-kwen/itech-admin-view
## 簡介
一個便捷的雲科大校園整合平台
## 動機
  在雲科大的校園生活中，學生經常透過各式各樣的網站找尋需要的資訊以及功能平台，對於雲科新鮮人而言，需要利用多種網頁才能獲得大量的生活資訊以利迅速融入雲科生活，因此本組決定在此基礎上進行網站多功能整合，另外再添加部分相關的功能。
    
  此外，根據LINE官方統計，去年秋季，LINE台灣用戶數已突破2100萬，基本上每個人都擁有自己的Line賬號，Line在學生族群中間使用率極高，本組在分析科技潮流之後，為了使網站在行動裝置上也能快速被使用，決定將網站連結及部分簡易功能轉移至LINE官方帳號中，為使用者提供便利。

## 特性
* (1)	多功能整合平台：

    將學生平時生活常用功能統整至一個系統內，例如宿舍郵件以及包裹查詢、宿舍網路流量查詢、校園幫幫（完成任務賺取佣金）、留言板、學期行事曆、雲科校浪圖鑒、天氣/ 空品查詢、雲科周邊公車時刻表、雲科教職員分機查詢等功能整合至系統內。
* (2)	擁有完整交易評價功能：

    使用者在使用校園幫幫（完成任務賺取酬金）過程中，如果遇到被棄單或是有疑慮等狀況，在目前Facebook的交易社團中並沒有管道可以進行回饋，也因此無法爭取自身權益及說明此次交易狀況，以提醒下一位使用者。為了杜絕此狀況發生，本系統建立了完善的交易評價功能，讓買賣雙方能對彼此有更多的了解，使得交易能更加地放心。同時，本系統對於評分過低之使用者也有封鎖帳號等相對應之處理。
* (3)	貼近雲科生活之功能：

    除了學校生活相關的功能整合，本系統也添加了擁有雲科特色的功能，例如校園流浪犬圖鑑、雲科行事曆、教職員分機查詢，協助使用者融入雲科的校園生活。
    
* (4)	不需要下載任何的APP：

    在幾乎人人都有一個LINE帳戶的環境下，透過LINE Bot連結網站，如果欲以行動裝置使用本專題系統，只須有LINE帳戶，無須下載任何APP即可使用。
### 功能模塊
```
├─主頁
│  ├─控制台
├─用戶
│  ├─個人中心
│  ├─登錄記錄
│  ├─交易明細
│  ├─信息中心
├─流量監控
│  ├─流量記錄
│  ├─監控管理
│  ├─告警記錄
├─任務幫幫
│  ├─發起任務
│  ├─任務市場
│  ├─我的任務
│  ├─接任記錄
├─校園
│  ├─宿舍郵件中心
├─系統
│  ├─留言板
│  ├─意見反饋
└─
```
## 技術
* SpringBoot
* Mybatis
* Druid
* JWT
* Layui
* Bootsharp
* BootsharpTable
* LineBot
* upyun CDN
* Google Analytics
## iTech LINE Bot
![](https://upload.cc/i1/2021/04/06/lWg6cs.png
)

## 商業模式
![](https://upload.cc/i1/2021/04/06/OsUA45.jpg)
* 本系統獲利方式主要來自廣告收益以及收取使用者在平台上交易成交之手續費。廣告收益預計以社團宣傳、協助學校單位進行校園活動宣傳、以及代校園附近店家宣傳為主要來源。平台成交之手續費率則會依照交易類型而有所不同。
## 預期達成目標
* 本系統「iTech」，主要提供使用者進行多種校園生活功能，也能透過LINE Bot在行動裝置中執行以及其附加功能，我們希望使雲科學生僅使用一個網站及LINE Bot就能搞定校園生活大小事。本網站及LINE官方帳號也會結合廣告投放以及收取交易平台成交手續費來達到商業行銷模式。
* 本系統將使用者劃分為：管理者、網站使用者、一般使用者。
    * 管理者能夠登入後台管理系統及管理使用者介面
    * 網站使用者能夠使用網站及LINE官方帳號內所有功能
    * 一般使用者僅能夠使用LINE官方帳號內不需要帳號綁定之功能
    
除了目前現有功能以及計畫內功能，以下部分為問卷調查中調查對象提供之功能，為了提供更完整的資訊平台，以下擴展功能未來也可納入本系統。
* 敬請期待：
    * (1)	上課提醒：輸入課程後可以跳出此課程的上課時間、上課地點及授課教師等。
    * (2)	課外課程相關資訊：可以藉由LINE Bot推送課外課程資訊，如多益衝刺班、日文班、證照考取衝刺班等。
    * (3)	宿舍公告通知：使用者可以透過LINE Bot接收寒暑假期宿舍住宿申請公告、相關演講及活動、下學期宿舍抽籤時程等宿舍公告。
    * (4)	三餐隨機推薦：提供不知道吃什麼的使用者餐廳或料理推薦，以學校周邊店家為主。
    * (5)	獎學金公告：校內或校外各類型獎學金訊息推播。
    
## Contact Author
石桂華(Skwen) https://blog.iskwen.com
# END
* Thank you for watching.