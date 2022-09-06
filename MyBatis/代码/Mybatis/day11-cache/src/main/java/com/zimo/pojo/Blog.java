package com.zimo.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Blog  implements Serializable {
    private  String id;
    private  String title;
    private  String author;
    private Date createTime;
    private int views;



}
