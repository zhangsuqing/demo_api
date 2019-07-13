/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.yoren.srs.demo.core.impl;

import cn.yoren.srs.demo.common.entity.SysCaptchaBean;
import cn.yoren.srs.demo.core.service.SysCaptchaService;
import cn.yoren.srs.demo.domain.dao.SysCaptchaMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0 2018-02-10
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl implements SysCaptchaService {

    @Resource
    SysCaptchaMapper sysCaptchaMapper;
    @Override
    public Page queryPage(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<SysCaptchaBean> queryList(Map<String, Object> params) {
        return null;
    }

    @Override
    public String getCaptcha(String uuid) {
        //生成文字验证码
        Integer code = new Random().nextInt(9999 - 1000 + 1) + 1000;

        SysCaptchaBean captchaEntity = new SysCaptchaBean();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code.toString());
        //5分钟后过期
        Date date = new Date();
        captchaEntity.setExpireTime(new Date(date.getTime() + 300000));
        sysCaptchaMapper.insert(captchaEntity);
        return code.toString();
    }

    @Override
    public boolean validate(String uuid, String code) {
        SysCaptchaBean captchaEntity = sysCaptchaMapper.selectByPrimaryKey(uuid);
        if(captchaEntity == null){
            return false;
        }

        //删除验证码
        sysCaptchaMapper.deleteByPrimaryKey(uuid);

        if(captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()){
            return true;
        }

        return false;
    }
}
