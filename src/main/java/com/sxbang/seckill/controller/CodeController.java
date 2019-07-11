package com.sxbang.seckill.controller;

import com.sxbang.seckill.base.controller.BaseApiController;
import com.sxbang.seckill.base.result.Result;
import com.sxbang.seckill.model.User;
import com.sxbang.seckill.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @author kaneki
 * @date 2019/6/28 17:30
 */
@Controller
public class CodeController extends BaseApiController {
    @Autowired
    private CodeUtil codeUtil;

    @GetMapping("/verifyCode")
    @ResponseBody
    public Result<String> getVerifyCod(HttpServletResponse response) {
        try {
            BufferedImage image  = codeUtil.createVerifyCode();
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
