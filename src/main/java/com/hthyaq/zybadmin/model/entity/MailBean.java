package com.hthyaq.zybadmin.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
}