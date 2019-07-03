package com.xyj.modules.pay.entity;

import com.xyj.enums.CommonStatusEnum;
import com.xyj.utils.IdGenUtils;

import java.util.Date;

/**
 * 基类.
 * 
 */
public class BaseEntity {

	protected String id = IdGenUtils.get32UUID();// 主键ID.
	protected String status;// 状态 CommonStatusEnum
	protected String creator = "SYS";// 创建人.
	protected Date createTime = new Date();// 创建时间.
	protected String editor;// 修改人.
	protected Date editTime;// 修改时间.
	protected String remark;// 描述

	public void preInsert(String userId) {
		this.id = IdGenUtils.get32UUID();
		this.createTime = new Date();
		this.creator = userId;
		this.status = CommonStatusEnum.ACTIVE.getStatusCode();
		this.preUpdate(userId);
	}

	public void preUpdate(String userId) {
		this.editTime = new Date();
		this.editor = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
