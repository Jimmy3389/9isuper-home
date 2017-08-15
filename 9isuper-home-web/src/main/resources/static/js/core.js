$(function() {
	refreshHead();
});

function refreshHead() {
	setInterval(refreshNotification, 10000);// 注意函数名没有引号和括弧！
}

function refreshNotification() {
	var onlineStatus = "1";
	$.ajax({
		url : "dashboard/notf",
		timeout : 1000, // 超时时间设置，单位毫秒
		type : "GET",
		dataType : "JSON",
		cache: false,
		success : function(data) {
			var inboxMailCount = data.inboxCount;
			var newMessageCount = data.newMsgCount;
			var notificationCount = data.notifiCount;
			
			$("#dashboard-online-status").text("在线");
			$("#dashboard-online-ico").attr("class", "status-icon green");
			if (inboxMailCount > 0) {
				$("#notification-email-badge").text(inboxMailCount);
				$("#notification-email-badge").attr('style','visibility:');
				$("#mycenter-email-badge").text(inboxMailCount);
				$("#mycenter-email-badge").attr('style','visibility:');
			}else{
				$("#notification-email-badge").attr('style','visibility:hidden');
				$("#mycenter-email-badge").attr('style','visibility:hidden');
			}
			
			if (inboxMailCount > 0) {
				$("#notification-notice-badge").text(notificationCount);
				$("#notification-notice-badge").attr('style','visibility:');
			}else{
				$("#notification-notice-badge").attr('style','visibility:hidden');
			}
			
			if (newMessageCount > 0) {
				$("#chat-message-count").text(newMessageCount);
				$("#chat-message-count").attr('style','visibility:');
			}else{
				$("#chat-message-count").attr('style','visibility:hidden');
			}
			
			onlineStatus = "1";
		},
		complete : function(XMLHttpRequest,status){ // 请求完成后最终执行参数
		　　　　if(status=='timeout'){// 超时,status还有success,error等值的情况
		 　　　　　    $("#dashboard-online-status").text("离线");
				$("#dashboard-online-ico").attr("class", "status-icon red");
		　　　　}
			 if(status=='error'){// 超时,status还有success,error等值的情况
		 　　　　　    $("#dashboard-online-status").text("异常");
				$("#dashboard-online-ico").attr("class", "status-icon yellow");
		　　　　}
		　　}
	});
}

