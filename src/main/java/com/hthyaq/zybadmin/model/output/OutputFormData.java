package com.hthyaq.zybadmin.model.output;

import lombok.Data;

@Data
public class OutputFormData<T> {
    private T data;
    private String dbOperate;
    private String ClassName;
}
