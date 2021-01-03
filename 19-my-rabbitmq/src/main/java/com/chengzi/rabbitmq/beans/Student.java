package com.chengzi.rabbitmq.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  Student implements Serializable {
    String username;
    Integer age;
}