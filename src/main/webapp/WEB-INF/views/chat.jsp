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
    <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/myStyle.css"/>">
    <script src="<spring:url value="/resources/scripts/sockjs.min.js"/>"></script>
    <script src="<spring:url value="/resources/scripts/stomp.min.js"/>"></script>
    <script src="<spring:url value="/resources/scripts/jquery-3.3.1.min.js"/>"></script>
    <script type="text/javascript">
        var stompClient = null;
        var UIDConversation = null;
        var userName = '${username}';
        var currentUserId = '${userId}';
        console.log(userName);

        function connect(chatUID) {
            var socket = new SockJS('/hello');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/chat/' + chatUID, function (message) {
                    showMessage(JSON.parse(message.body).name,
                        JSON.parse(message.body).msg,
                        JSON.parse(message.body).formatDate);
                });
            });
        }

        function sendMessage() {
            var message = $('#write_msg').val();

            var options = {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                hour: 'numeric',
                minute: 'numeric'
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
                $('#lastMsg').val('');
                $('#lastMsg').val(msg);
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
                        connect(UIDConversation);
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
                                    <p id="lastMsg">Тут наверное будет последнее сообщение</p>
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
