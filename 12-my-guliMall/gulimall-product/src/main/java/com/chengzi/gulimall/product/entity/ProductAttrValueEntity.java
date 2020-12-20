package com.chengzi.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spu????ֵ
 * 
 * @author chengli
 * @email 570197298@qq.com@gmail.com
 * @date 2020-12-20 15:51:46
 */
@Data
@TableName("pms_product_attr_value")
public class ProductAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * ??Ʒid
	 */
	private Long spuId;
	/**
	 * ????id
	 */
	private Long attrId;
	/**
	 * ??????
	 */
	private String attrName;
	/**
	 * ????ֵ
	 */
	private String attrValue;
	/**
	 * ˳?
	 */
	private Integer attrSort;
	/**
	 * ????չʾ???Ƿ?չʾ?ڽ????ϣ?0-?? 1-?ǡ?
	 */
	private Integer quickShow;

}
