package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 猜你喜欢 关键字
 * Created by 施长成 on 2015/10/12 0012.
 */

@Entity
@Table(name="t_keywords")
public class KeyWords extends BaseEntity<Long> {

	private static final long serialVersionUID = 387220739464606992L;

	/**
     * 关键字名称
     */
    private String name;

    /**
     * 该关键字是否启用
     */
    private Boolean state;

    @Column(name="f_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="f_state")
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}
