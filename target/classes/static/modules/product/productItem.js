var commonQueryParams = [];
var typeFormLayer;
var allotUserLayer;
var allotMenuLayer;
$(function () {
    onloadTypeList();
})
function colseLayer() {
    layer.close(typeFormLayer);
}
function statusFormatter(value,row,index) {
    if(value==0){
        return "启用";
    }else if(value==1){
        return "停用";
    }
}

/**
 * 操作
 */
function operateFormatter(value,row,index) {
    var text='';
    text+='<a style="margin: 3px;" href="#" class="btn btn-white btn-sm" onclick="showDetail(\''+row.id+'\');" title="查看"><i class="fa fa-pencil"></i> 查看</a>';
    text+='<a style="margin: 3px;" href="#" class="btn btn-white btn-sm" onclick="addForm(\''+row.id+'\',\'编辑产品\');" title="编辑"><i class="fa fa-pencil"></i> 编辑</a>';
    text+='<a style="margin: 3px;" href="#" class="btn btn-white btn-sm" onclick="deleteItem(\''+row.id+'\');" title="删除"><i class="glyphicon glyphicon-trash"></i> 删除</a>';
    return text;
}

function searchParam(params) {
    var queryParam={};
    var pageNum = (params.offset / params.limit) + 1;
    var pagination = {
        page: pageNum,
        rows: params.limit
    };
   /* var sort = {
        field: params.sort,
        sort: params.order
    };*/
    /*var queryParam = {
        pagination: pagination,
        sort: sort
    };*/
    //组装查询参数
    var buildParam = {};
    if (commonQueryParams) {
        for (var i = 0; i < commonQueryParams.length; i++) {
            buildParam[commonQueryParams[i].name] = commonQueryParams[i].value.trim();
        }
        queryParam = $.extend(pagination, buildParam);
    }
    return queryParam;
}

// 查询
function searchListener(){
    var params = $('#user_search').serializeArray();
    commonQueryParams = [];
    if (params != "") {
        $.each(params, function() {
            commonQueryParams.push({name: this.name, value: this.value});
        });
    }
    $("#item_list_table").bootstrapTable('selectPage',1);  //跳转到第一页，防止其他页查询第一次不显示数据的问题。
    $("#item_list_table").bootstrapTable('refresh');       //无参数刷新
}

function onKeyDown(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==27){ // 按 Esc
        //要做的事情
    }
    if(e && e.keyCode==113){ // 按 F2
        //要做的事情
    }
    if(e && e.keyCode==13){ // enter 键
        //searchListener();
    }
}

// 清空
function clearSearchListener(){

    $('#user_search')[0].reset();
    $("#user_search input").val('');
    searchListener();
}

function deleteItem(id) {
    if(!id){
        layer.alert("请选择删除数据");
        return;
    }
    layer.confirm('你确定删除该产品？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/productItem/delete/"+id,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        //var mylay = parent.layer.getFrameIndex("userFormLayer");
                        // parent.layer.close(mylay);
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#item_list_table').bootstrapTable('refresh');
                    } else {
                        layer.alert("删除失败");
                    }
                },
                error: function (result) {
                    layer.alert("删除失败，"+result.mess);
                }
            });
        }
    });
}

function batchDeleteRole() {
    var data=$("#item_list_table").bootstrapTable('getSelections');
    var ids=[];
    if(!data||data.length==0){
        layer.alert("请选择删除的数据！");
        return;
    }else{
        for(var i=0;i<data.length;i++){
            var row=data[i];
            ids.push(row.id);
        }
    }
    layer.confirm('你确定删除所选的角色？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                //dataType: "json",
                //cache: false,
                url: "/sys/role/batchDelete",
                data:{ids:ids},
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        $('#item_list_table').bootstrapTable('refresh');
                    } else {
                        layer.alert("删除失败");
                    }
                },
                error: function (result) {
                    layer.alert("删除失败，"+result.mess);
                }
            });
        }
    });
}

function addForm(id,type) {
    typeFormLayer= layer.open({
        type: 2,
        title: type,
        area: ['880px', '500px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        content: '/productItem/form',
        end: function () {
            $('#item_list_table').bootstrapTable('refresh');
        },
        success: function(layero, index){
            var body=layer.getChildFrame('body',index);
            var $form=$(body).find("#item_form");
            if(type!="新增产品"){
               /* if(type=="查看用户"){
                    $form.find("input").prop("readonly",true);
                    $form.find("#saveUserBtn").addClass("hide");
                }*/
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    cache: true,
                    url: "/productItem/view/"+id,
                    success: function (result) {
                        if(result){
                            for (i in result){
                                $form.find("input[name=\'"+i+"\']").not("input:radio").val(result[i]);
                                $form.find("textarea[name=\'"+i+"\']").val(result[i]);
                                $form.find("select[name=\'"+i+"\']").find("option[value=\'"+result[i]+"\']").prop("selected",true);
                                if (i == "fileIds"){
                                    var appendHtml = "";
                                    for (j in result[i]){
                                        //appendHtml += "<img class='fileImg' src='/productItem/getFileById/" + result[i][j]+"'/>";
                                        appendHtml += "<img class='fileImg' src='" + result[i][j] + "'/>";
                                    }
                                    $form.find("#imgDiv").html("").append(appendHtml);
                                }
                            }
                        }
                    },
                    error: function () {
                        layer.alert("数据加载失败!");
                    }
                });
            }
        }
    });
}

function showDetail(id) {
    typeFormLayer= layer.open({
        type: 2,
        title: '查看详情',
        area: ['880px', '500px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        content: '/productItem/detailDialog',
        // end: function () {
        //     $('#item_list_table').bootstrapTable('refresh');
        // },
        success: function(layero, index){
            var body=layer.getChildFrame('body',index);
            var $form=$(body).find("#item_form");
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/productItem/view/"+id,
                success: function (result) {
                    if(result){
                        for (i in result){
                            $form.find("input[name=\'"+i+"\']").not("input:radio").val(result[i]);
                            $form.find("textarea[name=\'"+i+"\']").val(result[i]);
                            $form.find("select[name=\'"+i+"\']").find("option[value=\'"+result[i]+"\']").prop("selected",true);
                            if (i == "fileIds"){
                                var appendHtml = "";
                                for (j in result[i]){
                                    appendHtml += "<img class='fileImg' src='/productItem/getFileById/" + result[i][j]+"'/>";
                                }
                                $form.find("#imgDiv").html("").append(appendHtml);
                            }
                        }
                    }
                },
                error: function () {
                    layer.alert("数据加载失败!");
                }
            });
        }
    });
}

function getSel(menuList,roleId,index,layero) {
    var ids=[];
    var checks=$(menuList).find('.checked');
    if(checks.length>0){
        for (var i=0;i<checks.length;i++){
            var check=checks[i];
            var a=$(check).children("input[name='id']").val();
            if(a){
                ids.push(a);
            }
        }
    }
    $.ajax({
        type: "POST",
        dataType: "json",
        cache: false,
        data:{roleId:roleId,ids:ids},
        url: "/sys/menu/saveMenuPermission",
        success: function (result) {
            layer.msg('保存成功！', {
                time: 2000 //20s后自动关闭
            });
            layer.close(layero,index);
        },
        error: function () {
            layer.alert("数据加载失败!");
        }
    });
}

var $table;

function InitMainTable ($tb,roleId,url,type) {
    //记录页面bootstrap-table全局变量$table，方便应用
    //$tb.empty();
    $table = $tb.bootstrapTable('destroy').bootstrapTable({
        url: url,                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: "false",              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: [5,10,20,50,100],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表,
        columns: [ {
            field: 'username',
            title: '用户名',
            sortable: true,hidden:true
        }, {
            field:'id',
            title: '操作',
            width: 180,
            align: 'center',
            valign: 'middle',
            formatter:function (value,row,index) {
                if(type==0){
                    return '<a class="btn btn-white btn-sm" onclick="allotUserById(\''+row.id+'\',\''+roleId+'\',0)"><i class="fa fa-mail-reply"></i> 移除</a>';
                }else{
                    return '<a class="btn btn-white btn-sm" onclick="allotUserById(\''+row.id+'\',\''+roleId+'\',1)"><i class="fa fa-share-square-o"></i> 分配</a>';
                }
            }
        }, ]
    });
}

function onloadTypeList() {
    $('#item_form #typeId').empty();
    $.ajax({
        type: "POST",
        dataType: "json",
        cache: false,
        url: "/productType",
        success: function (result) {
            if (result && result.length>0) {
                var option='<option value="null">--请选择--</option>';
                for (var i=0;i<result.length;i++){
                    var row=result[i];
                    option+='<option value="'+row.id+'">'+row.name+'</option>';
                }
                $('#item_form #typeId').append(option);
            }

        },
        error: function () {
            layer.alert("数据加载失败！");
        }
    });
}

