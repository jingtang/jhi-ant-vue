package com.aidriveall.cms.service.standalone.mobile.util;

import com.github.bingoohuang.patchca.background.SingleColorBackgroundFactory;
import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.service.AbstractCaptchaService;
import com.github.bingoohuang.patchca.service.Captcha;
import com.github.bingoohuang.patchca.text.renderer.BestFitTextRenderer;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

import java.awt.*;

/**
 * Created by liamjung on 2018/1/19.
 */
public class CaptchaUtil {

    private static final CaptchaFactory CAPTCHA_FACTORY = new CaptchaFactory();

    public static Captcha create() {

        return CAPTCHA_FACTORY.getCaptcha();
    }

    private static class CaptchaFactory extends AbstractCaptchaService {

        CaptchaFactory() {
            this.wordFactory = new WordFactory();
            this.fontFactory = new RandomFontFactory();
            this.textRenderer = new BestFitTextRenderer();
            this.backgroundFactory = new SingleColorBackgroundFactory();
            this.colorFactory = new SingleColorFactory(new Color(25, 60, 170));
            this.filterFactory = new CurvesRippleFilterFactory(this.colorFactory);
            this.width = 150;
            this.height = 50;
        }
    }

    private static class WordFactory extends RandomWordFactory {

        WordFactory() {
            this.characters = "absdekmnwx2345678";
            this.minLength = 4;
            this.maxLength = 4;
        }
    }
}
