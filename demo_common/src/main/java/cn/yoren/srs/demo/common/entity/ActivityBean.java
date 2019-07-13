package cn.yoren.srs.demo.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;


import java.sql.Timestamp;
import java.util.Date;

/**
 * 线下兑换码活动表
 * 
 * @author zsq
 * @date 2019-06-17 10:34:48
 */
@TableName("ee_activity")
public class ActivityBean{

	/**
	 * 活动ID
	 */
	@TableId
	private Long id;
	/**
	 * 活动code
	 */
	private String activityCode;
	/**
	 * 活动名称
	 */
	private String activityName;
	/**
	 * 活动开始时间
	 */
	private Date activityTimeStart;
	/**
	 * 活动结束时间
	 */
	private Date activityTimeEnd;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
	/**
	 * 修改人
	 */
	private String updateUser;
	/**
	 * 状态0:已删除 1:未公开 2：已公开
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 版本号
	 */
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getActivityTimeStart() {
		return activityTimeStart;
	}

	public void setActivityTimeStart(Date activityTimeStart) {
		this.activityTimeStart = activityTimeStart;
	}

	public Date getActivityTimeEnd() {
		return activityTimeEnd;
	}

	public void setActivityTimeEnd(Date activityTimeEnd) {
		this.activityTimeEnd = activityTimeEnd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
