function dateFormat1(longTypeDate){
    var dateType1 = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateType1+= date.getFullYear();   //年
    dateType1+= "-" + getMonth(date); //月
    dateType1 += "-" + getDay(date);   //日
    return dateType1;
}

function datetimeFormat1(longTypeDate){
    var datetimeType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    datetimeType+= date.getFullYear();   //年
    datetimeType+= "-" + getMonth(date); //月
    datetimeType += "-" + getDay(date);   //日
    datetimeType+= "&nbsp;&nbsp;" + getHours(date);   //时
    datetimeType+= ":" + getMinutes(date);      //分
    datetimeType+= ":" + getSeconds(date);      //分
    return datetimeType;
}

function datetimeFormat(longTypeDate){
    var datetimeType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    datetimeType+= date.getFullYear();   //年
    datetimeType+= "-" + getMonth(date); //月
    datetimeType += "-" + getDay(date);   //日
    datetimeType+= " " + getHours(date);   //时
    datetimeType+= ":" + getMinutes(date);      //分
    datetimeType+= ":" + getSeconds(date);      //分
    return datetimeType;
}
//返回 01-12 的月份值
function getMonth(date){
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if(month<10){
        month = "0" + month;
    }
    return month;
}
//返回01-30的日期
function getDay(date){
    var day = "";
    day = date.getDate();
    if(day<10){
        day = "0" + day;
    }
    return day;
}
//返回小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){
        hours = "0" + hours;
    }
    return hours;
}
//返回分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){
        minute = "0" + minute;
    }
    return minute;
}
//返回秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){
        second = "0" + second;
    }
    return second;
}

/**
 *  今天2019-1-1
 * @returns {string}
 */
function todayDateFormat(){
    var dateType1 = "";
    var date = new Date();
    dateType1+= date.getFullYear();   //年
    dateType1+= "-" + (date.getMonth() + 1); //月
    dateType1 += "-" + date.getDate();   //日
    return dateType1;
}

/**
 * 今天 2019-01-01
 * @returns {string}
 */
function todayDateFormat1(){
    var dateType1 = "";
    var date = new Date();
    dateType1+= date.getFullYear();   //年
    dateType1+= "-" + getMonth(date); //月
    dateType1 += "-" + getDay(date);   //日
    return dateType1;
}
