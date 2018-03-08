package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class MallDefinition implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3144682634156418872L;


    private Long id;
    private String name;
    private String icon;
    private Long countryId;
    private String country;
}