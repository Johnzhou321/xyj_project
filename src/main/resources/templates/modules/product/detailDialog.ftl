<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>产品详情</title>
    <#include "/common/base.ftl"/>
    <#--<link href="/static/hPlus/css/plugins/iCheck/custom.css" rel="stylesheet">-->
    <style type="text/css">
        .show{
            display: inline-block;
        }
        .hide{
            display: none;
        }
        .fileImg{
            width: 250px;
            height: 160px;
            margin-left: 12px;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <#--<div class="ibox-title">
                    <h5><i class="fa fa-user"></i>&nbsp;用户信息</h5>
                </div>-->
                <div class="ibox-content container">
                    <form class="form-horizontal" id="item_form" enctype="multipart/form-data">
                        <div class="form-group" style="display: none">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input type="text" name="id" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>分类名称</label>
                            <div class="col-sm-10">
                                <input type="text" disabled placeholder="请输入产品名称" name="name" class="form-control">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">所属分类</label>

                            <div class="col-sm-10">
                                <select id="typeId" disabled class="form-control m-b" name="typeId">
                                    <option value="null">--请选择--</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>价格</label>
                            <div class="col-sm-10">
                                <input type="text" disabled placeholder="请输入价格" name="price" class="form-control">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>状态</label>
                            <div class="col-sm-10">
                                <select class="form-control m-b" disabled name="status">
                                    <option value="">--请选择--</option>
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>产品图片</label>
                            <div id = "imgDiv">
                                <input id="upload_file" type="file" name="files" multiple data-preview-file-type="any">
                            </div>

                        </div>
                        <div class="form-group text-right" style="width: 100%;position: relative">
                            <div class="col-sm-4 col-sm-offset-2" style="width: 100%;position: absolute;right: 0px;bottom: -30px;">
                                <button id="saveUserBtn" class="btn btn-primary" type="submit">保存</button>
                                <button class="btn btn-white" onclick="closeLayer()" type="button">关闭</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<#--<script src="/static/hPlus/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>-->

<script type="text/javascript">
    $(function () {
        onloadTypeList();
    });
    function onloadTypeList() {
        $('#item_form #typeId').empty();
        $.ajax({
            type: "POST",
            dataType: "json",
            cache: false,
            url: "/productType/typeAllList",
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

    
    function closeLayer() {
        parent.colseLayer();
    }





</script>
</body>

</html>
