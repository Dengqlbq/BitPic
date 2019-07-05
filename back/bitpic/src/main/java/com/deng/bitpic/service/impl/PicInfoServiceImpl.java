package com.deng.bitpic.service.impl;

import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.enums.CheckEnum;
import com.deng.bitpic.enums.PicInfoEnum;
import com.deng.bitpic.repository.PicInfoRepository;
import com.deng.bitpic.service.PicInfoService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 图片作品
 * @author: Deng
 * @create: 2019-01-11
 */
@Slf4j
@Service
public class PicInfoServiceImpl implements PicInfoService {

    @Autowired
    private PicInfoRepository repository;

    @Override
    public PicInfo save(PicInfo picInfo) {
        return repository.save(picInfo);
    }

    @Override
    public List<PicInfo> queryByAuthorId(String userId, Pageable pageable) {
        return repository.queryByAuthorIdAndCheckStatus(userId, CheckEnum.STATUS_ACCEPT.getCode(), pageable).getContent();
    }

    @Override
    public PicInfo queryByNumber(String number) {
        return repository.queryByNumber(number);
    }

    @Override
    public Integer deleteByNumber(String number) {
        return repository.deleteByNumber(number);
    }

    @Override
    public List<PicInfo> queryByKeywordsMatches(String keyword, Pageable pageable) {
        return repository.queryPicInfoByKeywordsMatchesAndCheckStatus(keyword, CheckEnum.STATUS_ACCEPT.getCode(), pageable).getContent();
    }

    @Override
    public List<PicInfo> query(Pageable pageable, String keyword, String format, Integer direction, String category, Boolean group) {
        List<PicInfo> picInfoList = queryByKeywordsMatches(keyword, pageable);
        if (picInfoList.size() == 0) {
            return null;
        }
        return filter(picInfoList, format, direction, category, group);
    }

    /** TODO：增加状态筛选 */
    @Override
    public List<PicInfo> filter(List<PicInfo> picInfoList, String format, Integer direction, String category, Boolean group) {
        // 对应参数
        boolean f, d, c;
        List<PicInfo> pl = new ArrayList<>(picInfoList);
        Iterator<PicInfo> iterator = pl.iterator();
        while (iterator.hasNext()) {
            PicInfo picInfo = iterator.next();

            // 过滤格式
            if (!StringUtil.isNullOrEmpty(format)) {
                f = false;
                for (String fo : picInfo.getFormats()) {
                    if (fo.equals(format)) {
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    iterator.remove();
                    continue;
                }
            }

            // 过滤方向
            if (direction != null) {
                d = false;
                for (Integer dr : picInfo.getDirections()) {
                    if (dr.equals(direction)) {
                        d = true;
                        break;
                    }
                }
                if (!d) {
                    iterator.remove();
                    continue;
                }
            }

            // 过滤类别，类别放在关键词里
            if (!StringUtil.isNullOrEmpty(category)) {
                c = false;
                for (String kw : picInfo.getKeywords()) {
                    if (kw.equals(category)) {
                        c = true;
                        break;
                    }
                }
                if (!c) {
                    iterator.remove();
                    continue;
                }
            }

            // 过滤套图
            if (group != null && !group.equals(picInfo.getGroup())) {
                iterator.remove();
            }
        }

        return pl;
    }

    @Override
    public void incrLike(String number) {
        PicInfo picInfo = repository.queryByNumber(number);
        if (picInfo != null) {
            Integer like = picInfo.getLike();
            like ++;
            picInfo.setLike(like);
            picInfo.setUpdateTime(new Date());
            save(picInfo);
        }
    }

    @Override
    public void incrCollected(String number) {
        PicInfo picInfo = queryByNumber(number);
        if (picInfo != null) {
            Integer coll = picInfo.getCollection();
            coll ++;
            picInfo.setCollection(coll);
            picInfo.setUpdateTime(new Date());
            save(picInfo);
        }
    }

    @Override
    public void decrCollected(String number) {
        PicInfo picInfo = queryByNumber(number);
        if (picInfo != null) {
            Integer coll = picInfo.getCollection();
            coll = coll > 0 ? coll - 1 : 0;
            picInfo.setCollection(coll);
            picInfo.setUpdateTime(new Date());
            save(picInfo);
        }
    }

    @Override
    public void decrCollected(List<String> numbers) {
        for (String number : numbers) {
            decrCollected(number);
        }
    }

    @Override
    public void incrSale(String number, Integer type) {
        PicInfo picInfo = queryByNumber(number);
        Integer count;
        if (picInfo != null) {
            if (type.equals(PicInfoEnum.AUTHORIZATION_STANDARD.getCode())) {
                count = picInfo.getSaleStandard();
                count ++;
                picInfo.setSaleStandard(count);
            } else {
                count = picInfo.getSalePlus();
                count ++;
                picInfo.setSalePlus(count);
            }

            save(picInfo);
        }
    }
}
