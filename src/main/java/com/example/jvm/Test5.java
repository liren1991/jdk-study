package com.example.jvm;

import com.google.gson.Gson;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Test5 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        getItem(list, 100);
        System.out.println("落地经过路线总长度：" +
                list.stream().mapToInt(Integer::intValue).sum());
    }

    private static void getItem(List<Integer> list, Integer var) {
        list.add(var);
        var = var >> 1;
        if (var == 0)
            return;
        else {
            System.out.println("下次反弹的高度: " + var);
            getItem(list, var);
        }
    }


    private static void getDivisor(List<Integer> list, Integer var) {
        int size = list.size();
        // 质数
        if (CollectionUtils.isEmpty(list)) {
            boolean isTrue = true;
            for (int i = 2; i <= var >> 1; i++) {
                if (var % i == 0) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                list.add(1);
                list.add(var);
                return;
            }
        }

        // 合数
        for (int i = 2; i <= var >> 1; i++) {
            if (var % i == 0) {
                list.add(i);
                getDivisor(list, var / i);
                break;
            }
        }
        if (size == list.size())
            list.add(var);
        return;
    }
}
