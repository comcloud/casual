// ==UserScript==
// @name         高校考试网
// @namespace    http://tampermonkey.net/
// @version      0.7
// @description  开心考试
// @author       Brains
// @match        http://www.gaoxiaokaoshi.com/*
// @run-at       document-end
// @grant        GM_addStyle
// @grant        unsafeWindow
// @grant        GM_xmlhttpRequest
// @require      https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js
// @license      MIT
// ==/UserScript==
var $ = window.jQuery;
var out = {};
var setting = {
    flag: true, 
    time: 100
};
var nextFlag = setting.flag;
var url = window.location.pathname;
pannel();


if (localStorage.getItem('题库') && Object.keys(JSON.parse(localStorage.getItem('题库'))).length > 0) {
    out = JSON.parse(localStorage.getItem('题库'));
    logger('log', '目前题库已收录：' + Object.keys(out).length + '题。')

} else {
    logger('log', '题库为空,请先进行试题分析建立题库。')
    localStorage.setItem('题库', '{}')
}

if(url == '/Loginb.aspx'){
    judge();
}
if (url == '/ExamList/ExamPage/ExamDo.aspx' || url == "/ExamList/ExamPage/ExamTmStepDo.aspx") {
    logger('log', '开始答题！')
    if (!setting.flag) $('.info .next').show();
    doTest()
    console.log("答题：" + location.pathname)
} else if (url == '/ExamList/ExamPage/viewExam.aspx' || url == '/ExamList/ExamPage/ViewExam.aspx') {
    logger('log', '开始更新题库！')
    saveAnswers()
} else if (url == "/ExamList/TkTest.aspx") {
    tkTest();
    console.log("tkTest：" + location.pathname)
} else {
    $('.mypanel').css({ 'left': '10px', 'top': '200px' });
}

function judge() {
    document.getElementById('btnSubmit').onmousedown = function() {
        let id = document.getElementById('name').value;
        GM_xmlhttpRequest({
            url: 'http://47.95.221.79:8080/loginController/' + id,
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            onload: data => {
                if (JSON.parse(data.responseText).message === 200) {
                    GM_xmlhttpRequest({
                        url: 'http://47.95.221.79:8080/loginController/theAnswer',
                        method: 'GET',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        onload: data => {
                            var json = JSON.parse(data.responseText);
                            localStorage.removeItem("题库");
                            localStorage.setItem("题库", JSON.stringify(json.data).replace(/^\"|\\|\"$/g,""));
                            $('#Button1').click();
                        }
                    });
                } else {
                    localStorage.removeItem("题库");
                    $('#Button1').click();
                }
            },
        })
        alert("确定进入")
    }
}

function doTest() {
    try {
        var questions = $('.exam_list dt');
        var num = 0;
        var i = 0;
        var timer = setInterval(function() {
            if (nextFlag) {
                logger('log', '正在完成第：' + (i + 1) + '道题。');
                var question = $(questions[i]).text();
                //console.log(question)
                try {
                    question = question.match(/\.(.*)\(/)[1].replace(/^\s+|\s+$/g, "");;
                } catch (e) {
                    logger('warn', '网址规则不匹配，答题失败。');
                    nextFlag = false;
                    return;
                }

                if (out[question]) {
                    num++;
                    var answers = out[question];
                    var choices = $('label[for^=tm_' + (i + 1) + '_');
                    var outChoices = "";
                    //console.log(answers);
                    for (var j = 0; j < choices.length; j++) {
                        if (answers.includes($(choices[j]).text().match(/、(.*)/)[1])) {
                            if (setting.flag) {
                                $(choices[j]).click();

                            } else {
                                switch (j) {
                                    case 0:
                                        outChoices += 'A'
                                        break;
                                    case 1:
                                        outChoices += 'B'
                                        break;
                                    case 2:
                                        outChoices += 'C'
                                        break;
                                    case 3:
                                        outChoices += 'D'
                                        break;
                                    case 4:
                                        outChoices += 'E'
                                        break;
                                    case 5:
                                        outChoices += 'F'
                                        break;
                                    case 6:
                                        outChoices += 'G'
                                        break;
                                    case 7:
                                        outChoices += 'H'
                                        break;


                                }

                            }

                        }
                    }


                    if (!setting.flag) {
                        nextFlag = false
                        if (outChoices != "") {
                            $('.askMe a').text(out[question]);
                            logger('info', '第' + (i + 1) + '题答案为：'+ outChoices);

                        } else {
                            logger('info', '第' + (i + 1) + '题没有答案。');

                        }
                    }
                    i++;
                    if (i >= questions.length) {
                        clearInterval(timer);
                        if(setting.flag) logger('log', '答题完成，有答案的有：' + num + '道题。');
                        $('.info .next').hide();
                    }
                }
            }
        }, setting.time)
        } catch (e) {
            logger('warn', '网址规则不匹配，答题失败。');

        }
}

function saveAnswers() {
    try {
        var ddtms = $("div[id^=ddTm_]");
        //console.log(ddtms)
        var num = 0;
        for (var i = 0; i < ddtms.length; i++) {
            var div = $(ddtms[i]);
            var question = div.children('dt').text().match(/\d\.(.*)\(/)[1].replace(/^\s+|\s+$/g, "");;
            //console.log(question)
            var choices = div.find('.green:last').text();
            //console.log(choices)
            var answers = [];
            for (var j = 0; j < choices.length; j++) {
                switch (choices[j]) {
                    case '对':
                        answers.push('对');
                        answers.push('正确');
                        break;
                    case '错':
                        answers.push('错');
                        answers.push('错误');
                        break;
                    case 'A':
                        answers.push($('label[for=tm_' + (i + 1) + '_0').text().slice(2))
                        break;
                    case 'B':
                        answers.push($('label[for=tm_' + (i + 1) + '_1').text().slice(2))
                        break;
                    case 'C':
                        answers.push($('label[for=tm_' + (i + 1) + '_2').text().slice(2))
                        break;
                    case 'D':
                        answers.push($('label[for=tm_' + (i + 1) + '_3').text().slice(2))
                        break;
                    case 'E':
                        answers.push($('label[for=tm_' + (i + 1) + '_4').text().slice(2))
                        break;
                    case 'F':
                        answers.push($('label[for=tm_' + (i + 1) + '_5').text().slice(2))
                        break;
                    case 'G':
                        answers.push($('label[for=tm_' + (i + 1) + '_6').text().slice(2))
                        break;
                    case 'H':
                        answers.push($('label[for=tm_' + (i + 1) + '_7').text().slice(2))
                        break;
                }

            }
            //console.log(answers)
            if (!out[question]) {
                //console.log(question)
                //console.log(out[question])
                num++;
                out[question] = answers;

            } else if (!isSame(out[question], answers)) {
                console.log(out[question])
                console.log(answers)
                num++;
                out[question] = answers.concat(out[question]);
            }
            out[question] = unique1(out[question]);




        }
        logger('log', '已更新：' + num + '题，目前题库已收录：' + Object.keys(out).length + '题。')
        localStorage.setItem('题库', JSON.stringify(out))

    } catch (e) {
        logger('warn', '网址规则不匹配，更新题库失败。')

    }



}

function tkTest() {
    try {
        var radios;
        var timer = setInterval(function() {
            radios = $('.ExamRadio');
            if (radios.length == 0) {
                logger("info", "请先选择题库分类，并搜索。")
            } else {
                var num = 0;
                var page = $('.fright:last').find('li:first').text().match(/(\d*)\//)[1];
                console.log(page)
                logger("info", "开始分析第" + page + "页。")
                clearInterval(timer);
                setTimeout(function() {
                    var questions = $(".exam_list dt");
                    for (var i = 0; i < questions.length; i++) {
                        var question = $(questions[i]).text().match(/\d\.(.*)\s*/)[1].replace(/^\s+|\s+$/g, "");
                        //console.log(question);
                        var next = $(questions[i]).nextAll()
                        var rightAnswers = $(next[1]).text().match(/：([A-Za-z|对|错|正确|错误]*)/)[1];
                        //console.log(rightAnswers);
                        var choices = $(next[0]).children('div');
                        var answers = []
                        for (var j = 0; j < rightAnswers.length; j++) {
                            let answer;
                            switch (rightAnswers[j]) {
                                case '对':
                                    answers.push('对');
                                    answers.push('正确');
                                    break;
                                case '错':
                                    answers.push('错');
                                    answers.push('错误');
                                    break;
                                case 'a':
                                    answer = $(choices[0]).text().match(/[A-Z]、(.*)/)[1];
                                    answers.push(answer);
                                    break;
                                case 'b':
                                    answer = $(choices[1]).text().match(/[A-Z]、(.*)/)[1];
                                    answers.push(answer);
                                    break;
                                case 'c':
                                    answer = $(choices[2]).text().match(/[A-Z]、(.*)/)[1];
                                    answers.push(answer);
                                    break;
                                case 'd':
                                    answer = $(choices[3]).text().match(/[A-Z]、(.*)/)[1];
                                    answers.push(answer);
                                    break;
                            }
                        }
                        if (!out[question] && answers.length != 0) {
                            //console.log(question)
                            //console.log(answers)
                            num++;
                            out[question] = answers;

                        } else if (!isSame(out[question], answers)) {
                            //console.log(out[question])
                            //console.log(answers)
                            num++;
                            out[question] = answers.concat(out[question]);
                        }
                        out[question] = unique1(out[question]);
                    }

                    logger('log', '已更新：' + num + '题，目前题库已收录：' + Object.keys(out).length + '题。')
                    localStorage.setItem('题库', JSON.stringify(out));
                    $('#PageSplit1_BtnNext')[0].click();

                }, 1000)



            }
        })
        } catch (e) {
            logger('warn', '网址规则不匹配，更新题库失败。');
        }






}

// 判断数组是否相等
function isSame(arg1, arg2) {
    let bol = true;
    if (Object.keys(arg1).length != Object.keys(arg2).length) {
        return false;
    }
    for (let key in arg1) {
        if (typeof arg1[key] == 'object') {
            bol = isSame(arg1[key], arg2[key])
            if (!bol) {
                break;
            }
        } else if (arg1[key] != arg2[key]) {
            bol = false;
            break;
        }
    }
    return bol
}

//去重
function unique1(arr) {
    var hash = [];
    for (var i = 0; i < arr.length; i++) {
        if (hash.indexOf(arr[i]) == -1) {
            hash.push(arr[i]);
        }
    }
    return hash;
}

// Logger
function logger(type, msg) {
    $(".info p").text(msg);
    msg = "[高校考试]： " + msg;
    switch (type) {
        case 'warn':
            console.warn(msg);
            break;
        case 'log':
            console.log(msg);
            break;
        case 'info':
            console.info(msg);
            break;
    }
}
//面板
function pannel() {
    GM_addStyle('.mypanel {position: fixed;top: 100px;right: 10px; width: 300px;height: 200px;background-color: rgba(70, 196, 38, 0.6);z-index: 999999;border-radius: 5%;}');
    GM_addStyle('.answers, .askMe, .info {height: 70px;line-height: 70px;flote:left;padding-left: 10px;;border-bottom: rgba(0, 0, 0, .2) dashed 2px;font-size: 14px;color:#fff}');
    GM_addStyle('.askMe a {color:pink !important;font-size: 17px}');
    GM_addStyle('.append_0 {position: absolute;left:50%;top:50%;transform: translate(-50%, -50%);display:none ;width : 420px;height : 250px;background-color: rgba(70, 196, 38, 0.6);z-index: 9999999;font-size: 14px;color:#fff}');
    GM_addStyle('.append_0 h2 {text-align: center;}');
    GM_addStyle('.btn_ls, .append_1 {margin: 10px}')
    GM_addStyle('.append {margin: 1px;width: 70px;height:30px;float:right;}')
    GM_addStyle('.next {position: absolute;right:20px;bottom:-15px;display:none ;}')
    GM_addStyle(' .append_1, .append_2, .append_3 {width: 50px;height:30px;float:right;}');
    var html = '<div class="mypanel">'
    html += '<div class="answers">我是可爱的标题。(>‿◠)✌ <a href="javascript:" class="append">导入\\出题库</a></div>'
    html += '<div class="askMe">'
    html += '<a href="http://wpa.qq.com/msgrd?v=3&uin=201826658&site=qq&menu=yes" target="_blank">如果有bug，点这反馈。 (゜-゜)つロ </a>'
    html += '</div>'
    html += '<div class="info"><p>info</p> <a href="javascript:" class="next">下一题</a></div>'
    html += '</div>'
    html += '<div class="append_0"> <h2 >题库导入\\出<button class="append_1">关闭</button></h2><p class="btn_ls">规则，文件以txt格式上传，格式为:<br>{"题目1":["答案"],["题目2"]:["答案1","答案2"]<br>注意其中的{}[],:""为英语字符<br>例如：<br>{"近代中国最基本的国情是______。":["半殖民地半封建社会"],党的基本路线最主要的内容是______。":["一个中心","两个基本点"]}</p>'
    html += '<div class="btn_ls">导入题库 ： <input type="file"  name="file" accept=".txt" id="fileId" /><button class="append_2">导入</button></div>'
    html += '<div class="btn_ls">导出题库：<button class="append_3">导出</button></div>'
    html += '</div>'
    $("body").append(html);
    $('.append').click(function() {
        $(".append_0").toggle();
        $(".mypanel").toggle();
    })
    $('.append_1').click(function() {
        $(".append_0").toggle();
        $(".mypanel").toggle();
    })
    $('.append_2').click(function() {
        try {
            var objFile = $("#fileId");
            if (objFile.value == "") {
                alert("不能空")
            }
            var files = $('#fileId').prop('files'); //获取到文件列表
            //console.log(files.length);
            if (files.length == 0) {
                alert('请选择文件');
            } else {
                var num = 0;
                var reader = new FileReader(); //新建一个FileReader
                reader.readAsText(files[0], "UTF-8"); //读取文件
                reader.onload = function(evt) { //读取完文件之后会回来这里
                    var fileString = evt.target.result; // 读取文件内容
                    try {
                        var fileJson = JSON.parse(fileString);
                    } catch (err) {
                        alert('上传失败！请严格按照文件格式要求上传！');
                        return;

                    }

                    for (var key in fileJson) {
                        var value = fileJson[key];
                        //console.log(key);
                        if (!out[key]) {
                            num++;
                            out[key] = value
                        } else if (!isSame(out[key], value)) {
                            num++;
                            console.log(out[key])
                            console.log(value)
                            out[key] = value.concat(out[key]);
                        }
                        out[key] = unique1(out[key]);

                    }
                    alert('已更新：' + num + '题，目前题库已收录：' + Object.keys(out).length + '题。')
                    localStorage.setItem('题库', JSON.stringify(out))


                }

            }

        } catch (err) {
            alert('上传失败！请严格按照文件格式要求上传！')

        }



    })
    $('.append_3').click(function() {
        //var content  = JSON.stringify(out);

        exportRaw('题库.txt', JSON.stringify(out));







    })
    $('.info .next').click(function() {
        nextFlag = true;
        //console.log("sb")



    })




}

function fakeClick(obj) {
    var ev = document.createEvent("MouseEvents");
    ev.initMouseEvent("click", true, false);
    obj.dispatchEvent(ev);
}

function exportRaw(name, data) {
    var urlObject = window.URL || window.webkitURL || window;
    var export_blob = new Blob([data]);
    var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = name;
    fakeClick(save_link);
}
