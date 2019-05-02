<%@ page import="java.util.Date" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
<head>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css"
          rel="stylesheet"/>
    <style>
        .container {
            max-width: 1170px;
            margin: auto;
        }

        img {
            max-width: 100%;
        }

        .inbox_people {
            background: #f8f8f8 none repeat scroll 0 0;
            float: left;
            overflow: hidden;
            width: 40%;
            border-right: 1px solid #c4c4c4;
        }

        .inbox_msg {
            border: 1px solid #c4c4c4;
            clear: both;
            overflow: hidden;
        }

        .top_spac {
            margin: 20px 0 0;
        }

        .recent_heading {
            float: left;
            width: 40%;
        }

        .srch_bar {
            display: inline-block;
            text-align: right;
            width: 60%;
            padding:
        }

        .headind_srch {
            padding: 10px 29px 10px 20px;
            overflow: hidden;
            border-bottom: 1px solid #c4c4c4;
        }

        .recent_heading h4 {
            color: #05728f;
            font-size: 21px;
            margin: auto;
        }

        .srch_bar input {
            border: 1px solid #cdcdcd;
            border-width: 0 0 1px 0;
            width: 80%;
            padding: 2px 0 4px 6px;
            background: none;
        }

        .srch_bar .input-group-addon button {
            background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
            border: medium none;
            padding: 0;
            color: #707070;
            font-size: 18px;
        }

        .srch_bar .input-group-addon {
            margin: 0 0 0 -27px;
        }

        .chat_ib h5 {
            font-size: 15px;
            color: #464646;
            margin: 0 0 8px 0;
        }

        .chat_ib h5 span {
            font-size: 13px;
            float: right;
        }

        .chat_ib p {
            font-size: 14px;
            color: #989898;
            margin: auto
        }

        .chat_img {
            float: left;
            width: 11%;
        }

        .chat_ib {
            float: left;
            padding: 0 0 0 15px;
            width: 88%;
        }

        .chat_people {
            overflow: hidden;
            clear: both;
        }

        .chat_list {
            border-bottom: 1px solid #c4c4c4;
            margin: 0;
            padding: 18px 16px 10px;
        }

        .inbox_chat {
            height: 550px;
            overflow-y: scroll;
        }

        .active_chat {
            background: #ebebeb;
        }

        .incoming_msg_img {
            display: inline-block;
            width: 6%;
        }

        .received_msg {
            display: inline-block;
            padding: 0 0 0 10px;
            vertical-align: top;
            width: 92%;
        }

        .received_withd_msg p {
            background: #ebebeb none repeat scroll 0 0;
            border-radius: 3px;
            color: #646464;
            font-size: 14px;
            margin: 0;
            padding: 5px 10px 5px 12px;
            width: 100%;
        }

        .time_date {
            color: #747474;
            display: block;
            font-size: 12px;
            margin: 8px 0 0;
        }

        .received_withd_msg {
            width: 57%;
        }

        .mesgs {
            float: left;
            padding: 30px 15px 0 25px;
            width: 60%;
        }

        .sent_msg p {
            background: #05728f none repeat scroll 0 0;
            border-radius: 3px;
            font-size: 14px;
            margin: 0;
            color: #fff;
            padding: 5px 10px 5px 12px;
            width: 100%;
        }

        .outgoing_msg {
            overflow: hidden;
            margin: 26px 0 26px;
        }

        .sent_msg {
            float: right;
            width: 46%;
        }

        .input_msg_write input {
            background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
            border: medium none;
            color: #4c4c4c;
            font-size: 15px;
            min-height: 48px;
            width: 100%;
        }

        .type_msg {
            border-top: 1px solid #c4c4c4;
            position: relative;
        }

        .msg_send_btn {
            background: #05728f none repeat scroll 0 0;
            border: medium none;
            border-radius: 50%;
            color: #fff;
            cursor: pointer;
            font-size: 17px;
            height: 33px;
            position: absolute;
            right: 0;
            top: 11px;
            width: 33px;
        }

        .messaging {
            padding: 0 0 50px 0;
        }

        .msg_history {
            height: 516px;
            overflow-y: auto;
        }
    </style>

    <script src="<spring:url value="/resources/scripts/sockjs.min.js"/>"></script>
    <script src="<spring:url value="/resources/scripts/stomp.min.js"/>"></script>
    <script src="<spring:url value="/resources/scripts/jquery-3.3.1.min.js"/>"></script>
    <script type="text/javascript">
        var stompClient = null;
        var UIDConversation = null;
        var userName = '${username}';
        var currentUserId = '${userId}';
        console.log(userName);

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
        }

        function connect() {
            var socket = new SockJS('/hello');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/chat', function (message) {
                    showMessage(JSON.parse(message.body).name,
                        JSON.parse(message.body).msg,
                        JSON.parse(message.body).formatDate);
                });
            });
        }

        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
        }

        function sendMessage() {
            var message = $('#write_msg').val();

            var options = {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                hour: 'numeric',
                minute: 'numeric',
                timezone: 'UTC'
            };

            var date = new Date().toLocaleString("ru", options);

            printMsg(message, date);
            stompClient.send("/app/hello", {}, JSON.stringify({'name': userName, 'msg': message, 'conversationUID' : UIDConversation}));
            $('#write_msg').val('');
        }

        function showMessage(name, msg, formatDate) {
            if (userName !== name) {
                $('.msg_history').append('<div class="outgoing_msg">\n' +
                    '                        <div class="sent_msg">\n' +
                    '                            <p id="server_mgs_response">' + msg + '</p>\n' +
                    '                            <span class="time_date">' + formatDate + '</span></div>\n' +
                    '                    </div>');
            }
        }

        function printMsg(msg, formatDate) {
            $('.msg_history').append('<div class="incoming_msg">\n' +
                '                        <div class="incoming_msg_img"><img src="https://ptetutorials.com/images/user-profile.png"\n' +
                '                                                           alt="sunil"></div>\n' +
                '                        <div class="received_msg">\n' +
                '                            <div class="received_withd_msg">\n' +
                '                                <p id="user_msg">' + msg + '</p>\n' +
                '                                <span class="time_date">' + formatDate + '</span></div>\n' +
                '                        </div>\n' +
                '                    </div>');
        }

        $(document).ready(function () {
            $('.chat_list').click(function () {
                var idClickUser = this.id;
                UIDConversation = currentUserId + idClickUser;

                $.ajax({
                    type: 'POST',
                    url: '/chat/check',
                    data: {
                        idClickUser: idClickUser,
                        currentUserId: currentUserId,
                        UIDConversation: UIDConversation
                    },
                    success: function (data) {
                        console.log(data);
                        $.each(data, function (key, value) {
                            if (value.name === userName) {
                                printMsg(value.msg, value.formatDate);
                            } else {
                                showMessage(value.name, value.msg, value.formatDate);
                            }
                        });
                    }
                });
            });
        });
    </script>

</head>
<body>
<div>
    <button id="connect" onclick="connect();">Connect</button>
    <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
</div>
<div class="container">
    <h3 class=" text-center">Messaging</h3>
    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Recent</h4>
                    </div>
                    <div class="srch_bar">
                        <div class="stylish-input-group">
                            <input type="text" class="search-bar" placeholder="Search">
                            <span class="input-group-addon">
                <button type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>
                </span></div>
                    </div>
                </div>
                <div class="inbox_chat">
                    <c:forEach items="${allLoginUser}" var="user">
                        <div class="chat_list" id="${user.id}">
                            <div class="chat_people">
                                <div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png"
                                                           alt="sunil"></div>
                                <div class="chat_ib">
                                    <h5>${user.login}<span class="chat_date">19.08.19</span></h5>
                                    <p>Тут наверное будет последнее сообщение</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <%--<div class="chat_list active_chat">--%>
                    <%--<div class="chat_people">--%>
                    <%--<div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png"--%>
                    <%--alt="sunil"></div>--%>
                    <%--<div class="chat_ib">--%>
                    <%--<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>--%>
                    <%--<p>Test, which is a new approach to have all solutions--%>
                    <%--astrology under one roof.</p>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                </div>
            </div>
            <div class="mesgs">
                <div class="msg_history"></div>
                <div class="type_msg">
                    <div class="input_msg_write">
                        <input id="write_msg" type="text" class="write_msg" placeholder="Type a message"/>
                        <button class="msg_send_btn" onclick="sendMessage()" type="button"><i
                                class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
