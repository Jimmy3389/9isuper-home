package com.isuper.soft.home.domain.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import com.isuper.soft.home.domain.utils.IdGen;

@MappedSuperclass
public class IdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 数据库中的表的主键 */
	// A.容器自动生成---GeneratorType.AUTO
	//
	// B.使用数据库的自动增长字段生成---GenerationType.IDENTITY JPA
	// 容器将使用数据库的自增长字段为新增加的实体对象赋唯一值。这种情况下需要数据库提供对自增长字段的支持，SQL
	// Server、MySQL、DB2、Derby等支持。
	//
	// C.根据数据库序列号（Sequence）生成 ---GenerationType.SEQUENCE
	// 表示使用数据库的序列号为新增加的实体对象赋唯一值。这种情况下需要数据库提供对序列号的支持常用的数据库中，Oracle支持。
	//
	// D.使用数据库表的字段生成---GenerationType.TABLE
	// 表示使用数据库中指定表的某个字段记录实体对象的标识，通过该字段的增长为新增加的实体对象赋唯一值
	//
	// 比较特殊的地方
	// 1. 使用UUID（两个不同实现版本hibernate和OpenJPA有点不同）
	// OpenJPA
	// @GeneratedValue(strategy=GenerationType.AUTO, generator = "uuid")
	// Hibernate(Eclipse会提示错误，但是程序是可以运行的)
	// @GenericGenerator(name = "test", strategy = "uuid")
	// @GeneratedValue(generator = "test")
	//
	// 其实这两种办法我感觉都不是特别好，因为他们跟实现有关系，将来如果要迁移的话会比较麻烦，所以可以直接用Java.util.UUID
	// user.setUserId(UUID.randomUUID().toString());
	//
	// 2.使用@GeneratedValue(strategy=GenerationType.IDENTITY)
	// 需要在数据库（Derby）中这样定义字段
	// USER_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,
	// INCREMENT BY 1)
	//
	// 3.如果使用数据库表的字段生成---GenerationType.TABLE
	// 对于Hibernate,需要创建生成主键的表，但是OpenJPA不需要，如果没有会自动生成。
	// 代码如下：
	// @TableGenerator(name = "test111", table = "IDTABLE",
	// pkColumnName = "KEYID", valueColumnName = "KEYVALUE", pkColumnValue =
	// "TestUSER_ID")
	// @GeneratedValue(strategy=GenerationType.TABLE, generator="test111")
	@Id
	@Column(length = 40, updatable = false)
	private String id;

	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", updatable = false)
	private Date createDate;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(this.id)) {
			this.id = IdGen.uuid();
		}
		this.createDate = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
