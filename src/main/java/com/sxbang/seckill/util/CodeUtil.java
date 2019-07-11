package com.sxbang.seckill.util;

import com.sxbang.seckill.redis.CodeRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author kaneki
 * @date 2019/6/28 17:04
 */
@Service
public class CodeUtil {

    @Autowired
    private  CodeRedis codeRedis;

    public  BufferedImage createVerifyCode() {
        // 定义 图像宽高
        int width = 80;
        int height = 32;
        int point =50;
        // create the image
        // 生成一个内存中图像对象 ，宽高，类型
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图像的graphics 对象，利用它就可以画图
        Graphics graphics = image.getGraphics();
        //设置背景颜色
        graphics.setColor(new Color(0xDCDCDC));
        //背景颜色的填充
        graphics.fillRect(0, 0, width, height);
        // draw the border
        //黑色的边框
        graphics.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        //随机数
        Random rdm = new Random();
        for (int i = 0; i < point; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            graphics.drawOval(x, y, 0, 0);
        }
        // generate a random code
        //生成我们的验证码
        String verifyCode = generateVerifyCode(rdm);
        //验证码的颜色
        graphics.setColor(new Color(0, 100, 0));
        //字体
        graphics.setFont(new Font("Candara", Font.BOLD, 24));
        //将 这个String 类型验证码 写在 图片上
        graphics.drawString(verifyCode, 8, 24);
        //将 这个String 类型验证码 写在 图片上
        graphics.dispose();
        //把验证码存到redis中
        //计算这个数学公式验证码的值
        int rnd = calc(verifyCode);
        System.out.println(rnd);
        //将这个计算的值放入 redis中等待 对比
        codeRedis.putString("code",String.valueOf(rnd),60,false);
        //输出图片
        return image;

    }

    private static  char [] chars = {'+','-','*'};

    private static String generateVerifyCode(Random rdm) {
        // 生成这个验证码
        // 生成三个随机数（10以内）
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        // 生成两个运算符
        // n 表示 10以内，不包括10的整数 0-9
        char charOne = chars[rdm.nextInt(3)];
        char charTwo = chars[rdm.nextInt(3)];
        // 对其进行拼接，获得数学表达式
        return ""+num1+charOne+num2+charTwo+num3;
    }


    private static int calc(String exp) {
        // 计算结果
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
