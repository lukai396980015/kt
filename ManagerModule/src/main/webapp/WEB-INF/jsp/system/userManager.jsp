<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%String basepath = request.getContextPath();%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/css/bootstrap.min.css" /> --%>
<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/font-awesome/4.5.0/css/font-awesome.min.css" /> --%>

<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/css/jquery-ui.min.css" /> --%>
<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/css/bootstrap-datepicker3.min.css" /> --%>
<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/css/ui.jqgrid.min.css" /> --%>

<!-- text fonts -->
<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/css/fonts.googleapis.com.css" /> --%>

<!-- ace styles -->
<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/css/ace.min.css" /> --%>

<!--[if lte IE 9]>
	<link rel="stylesheet" href="assets/css/ace-part2.min.css" />
<![endif]-->
<%-- <link rel="stylesheet" href="<%=basepath%>/static/assets/css/ace-rtl.min.css" /> --%>

<%-- <script src="<%=basepath%>/static/assets/js/ace-extra.min.js"></script> --%>
<%-- <script src="<%=basepath%>/static/assets/js/jquery-2.1.4.min.js"></script> --%>
<%-- <script src="<%=basepath%>/static/assets/js/bootstrap-datepicker.min.js"></script> --%>
<%-- <script src="<%=basepath%>/static/assets/js/jquery.jqGrid.min.js"></script> --%>
<%-- <script src="<%=basepath%>/static/assets/js/grid.locale-en.js"></script> --%>
<!-- ace scripts -->
<%-- <script src="<%=basepath%>/static/assets/js/ace-elements.min.js"></script> --%>
<%-- <script src="<%=basepath%>/static/assets/js/ace.min.js"></script> --%>

		<!--[if IE]>
<script src="assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='<%=basepath%>/static/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<%-- <script src="<%=basepath%>/static/assets/js/bootstrap.min.js"></script> --%>
<script type="text/javascript">
$("#grid-table").jqGrid({
	subGrid : true,
	url: "<%=basepath%>/userManagerController/listUser",
	datatype: "json",
	mtype : "post",
	height: 250,
	colNames:[' ', '用户id','用户名称','用户昵称', '用户号码', '年龄','性别','账号','状态','密码','md5加密混淆（暂时没用）'],
	colModel:[
		{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
			formatter:'actions', 
			formatoptions:{ 
				keys:true,
				delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
			}
		},
		{name:'userId',name:'userId', width:60, sorttype:"int", editable: true},
		{name:'userName',name:'userName',width:90, editable:true},
		{name:'userNick',name:'userNick', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
		{name:'userPhone',name:'userPhone', width:70, editable: true},
		{name:'userAge',name:'userAge', width:90, editable: true},
		{name:'userSex',name:'userSex', width:150, sortable:false,editable: true}, 
		{name:'userAccount',name:'userAccount', width:150, sortable:false,editable: true},
		{name:'userStatus',name:'userStatus', width:150, sortable:false,editable: true}, 
		{name:'userPassword',name:'userPassword', width:150, sortable:false,editable: true}, 
		{name:'userSalt',index:'userSalt', width:150, sortable:false,editable: true} 
	], 

	viewrecords : true,
	rowNum:10,
	rowList:[10,20,30],
	pager : "#grid-pager",
	altRows: true,
	sortname:'user_name',
	sortorder:'desc',
	//toppager: true,
	
	multiselect: true,
	//multikey: "ctrlKey",
    multiboxonly: true,

	loadComplete : function() {
		var table = this;
		setTimeout(function(){
// 			styleCheckbox(table);
			
// 			updateActionIcons(table);
//分页工具条图标
			updatePagerIcons(table);
// 			enableTooltips(table);
		}, 0);
	},

// 	editurl: "./dummy.php",//nothing is saved
	caption: "用户信息列表"

	//,autowidth: true,


	/**
	,
	grouping:true, 
	groupingView : { 
		 groupField : ['name'],
		 groupDataSorted : true,
		 plusicon : 'fa fa-chevron-down bigger-110',
		 minusicon : 'fa fa-chevron-up bigger-110'
	},
	caption: "Grouping"
	*/

});

function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
}

//navButtons
jQuery("#grid-table").jqGrid('navGrid',"#grid-pager",
	{ 	//navbar options
		edit: true,
		editicon : 'ace-icon fa fa-pencil blue',
		add: true,
		addicon : 'ace-icon fa fa-plus-circle purple',
		del: true,
		delicon : 'ace-icon fa fa-trash-o red',
		search: true,
		searchicon : 'ace-icon fa fa-search orange',
		refresh: true,
		refreshicon : 'ace-icon fa fa-refresh green',
		view: true,
		viewicon : 'ace-icon fa fa-search-plus grey',
	},
	{
		//edit record form
		//closeAfterEdit: true,
		//width: 700,
		recreateForm: true,
		beforeShowForm : function(e) {
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
			style_edit_form(form);
		}
	},
	{
		//new record form
		//width: 700,
		closeAfterAdd: true,
		recreateForm: true,
		viewPagerButtons: false,
		beforeShowForm : function(e) {
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
			.wrapInner('<div class="widget-header" />')
			style_edit_form(form);
		}
	},
	{
		//delete record form
		recreateForm: true,
		beforeShowForm : function(e) {
			var form = $(e[0]);
			if(form.data('styled')) return false;
			
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
			style_delete_form(form);
			
			form.data('styled', true);
		},
		onClick : function(e) {
			//alert(1);
		}
	},
	{
		//search form
		recreateForm: true,
		afterShowSearch: function(e){
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			style_search_form(form);
		},
		afterRedraw: function(){
			style_search_filters($(this));
		}
		,
		multipleSearch: true,
		/**
		multipleGroup:true,
		showQuery: true
		*/
	},
	{
		//view record form
		recreateForm: true,
		beforeShowForm: function(e){
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
		}
	}
)


function beforeDeleteCallback(e) {
	alert("删除");
}

function pickDate( cellvalue, options, cell ) {
	setTimeout(function(){
		$(cell) .find('input[type=text]')
			.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
	}, 0);
}

</script>
<title>Insert title here</title>
</head>
<body>
	<table id="grid-table"></table>

	<div id="grid-pager"></div>
</body>
</html>