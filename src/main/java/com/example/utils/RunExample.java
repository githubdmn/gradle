package com.example.utils;

import com.example.utils.math.AddUtil;
import com.example.utils.message.MessageUtil;

public class RunExample {
    public static void run() {
        MessageUtil.printMessage();
        int result = AddUtil.add(5, 3);
        System.out.println("Result of addition: " + result);
    }
}
