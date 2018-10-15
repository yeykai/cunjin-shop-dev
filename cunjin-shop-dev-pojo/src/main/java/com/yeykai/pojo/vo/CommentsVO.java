package com.yeykai.pojo.vo;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class CommentsVO {
    private String id;

    /**
     * 视频id
     */
    private String goodsId;

    /**
     * 留言者，评论的用户id
     */
    private String fromUserId;

    private Date createTime;

    /**
     * 评论内容
     */
    private String comment;
    
    private String avatar_url;
    private String nickname;
    private String toNickname;
    private String timeAgoStr;
    

    public String getTimeAgoStr() {
		return timeAgoStr;
	}

	public void setTimeAgoStr(String timeAgoStr) {
		this.timeAgoStr = timeAgoStr;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	/**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取视频id
     *
     * @return video_id - 视频id
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 设置视频id
     *
     * @param videoId 视频id
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取留言者，评论的用户id
     *
     * @return from_user_id - 留言者，评论的用户id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * 设置留言者，评论的用户id
     *
     * @param fromUserId 留言者，评论的用户id
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取评论内容
     *
     * @return comment - 评论内容
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置评论内容
     *
     * @param comment 评论内容
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

	public String getToNickname() {
		return toNickname;
	}

	public void setToNickname(String toNickname) {
		this.toNickname = toNickname;
	}
}