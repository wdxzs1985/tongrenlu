<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
<div class="row fileupload-buttonbar">
    <div class="col-md-6 col-lg-6">
        <!-- The fileinput-button span is used to style the file input field as button -->
        <span class="btn btn-success fileinput-button">
            <i class="glyphicon glyphicon-plus "></i>
            <span>添加文件</span>
            <input type="file" name="files[]" multiple accept="image/jpeg,image/png">
        </span>
        <button type="submit" class="btn btn-primary start">
            <i class="glyphicon glyphicon-upload "></i>
            <span>开始上传</span>
        </button>
        <button type="reset" class="btn btn-warning cancel">
            <i class="glyphicon glyphicon-ban-circle "></i>
            <span>全部取消</span>
        </button>
        <button type="button" class="btn btn-danger delete">
            <i class="glyphicon glyphicon-trash "></i>
            <span>全部删除</span>
        </button>
    </div>
    <!-- The global progress information -->
    <div class="col-md-6 col-lg-6 fileupload-progress fade">
        <!-- The global progress bar -->
        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
        </div>
        <!-- The extended global progress information -->
        <div class="progress-extended">&nbsp;</div>
    </div>
</div>
<!-- The table listing the files available for upload/download -->
<table role="presentation" class="table table-striped table-hover"><tbody class="files"></tbody></table>