package com.chengzi.ssm.aspect;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 9:44
 */
public class UserServiceImpl  {
    //@Override
    public void doSome() {
        System.out.println("执行dosome");
    }

    //@Override
    public void doOther(int a, int b) {
        System.out.println("执行doOhter");
    }

    //@Override
    public String doThird() {
        System.out.println("执行doThird");
        return "333";
    }
}
