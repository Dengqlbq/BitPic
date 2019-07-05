package com.deng.bitpic.utils;

import com.deng.bitpic.constant.KeywordConstant;
import com.deng.bitpic.constant.ThumbnailsConstant;
import com.deng.bitpic.dataobject.admin.PicCheck;
import com.deng.bitpic.dataobject.user.Collections;
import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.dataobject.user.OrderMaster;
import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.dataobject.user.Wish;
import com.deng.bitpic.dto.WishDTO;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.form.PicInfoCreateForm;
import com.deng.bitpic.vo.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 转换器（没有全部使用BeanUtils.copyProperty是为了节省不必要的开销）
 * @author: Deng
 * @create: 2019-01-25
 */
@Slf4j
public class Converter {

    /**
     * Wish -> WishVO
     * @param wish 对象
     * @return 对象
     */
    public static WishVO wish2WishVO(Wish wish) {
        return new WishVO(wish.getAuthorId(), wish.getNumber(), wish.getType(), wish.getPrice());
    }

    /**
     * WishList -> WishVOList
     * @param wishList 对象
     * @return 对象
     */
    public static List<WishVO> wish2WishVO(List<Wish> wishList) {
        return wishList.stream().map(e -> wish2WishVO(e)).collect(Collectors.toList());
    }

    /**
     * wishDTO 数组字符串 -> wishDTOList
     * @param wishListString wishDTO 数组字符串
     * @return 对象
     */
    public static List<WishDTO> wishListString2WishDTOList(String wishListString) {
        List<WishDTO> wishDTOList;
        try {
            wishDTOList = new Gson().fromJson(wishListString, new TypeToken<List<WishDTO>>() {}.getType());
        } catch (Exception e) {
            log.error("[对象转换] {}", e);
            throw new BitPicException(ResultEnum.PARAMS_ERROR);
        }

        return wishDTOList;
    }

    /**
     * Collections -> CollectionsVO
     * @param collections 对象
     * @return 对象
     */
    public static CollectionsVO collections2CollectionsVO(Collections collections) {
        return new CollectionsVO(collections.getAuthorId(), collections.getNumber());
    }

    /**
     * CollectionsList -> CollectionsV)List
     * @param collectionsList 对象
     * @return 对象
     */
    public static List<CollectionsVO> collections2CollectionsVO(List<Collections> collectionsList) {
        return collectionsList.stream().map(e -> collections2CollectionsVO(e)).collect(Collectors.toList());
    }

    /**
     * PicInfo -> PicInfoListVO
     * @param picInfo 对象
     * @return 对象
     */
    public static PicInfoListVO picInfo2PicInfoListVO(PicInfo picInfo) {
        PicInfoListVO vo = new PicInfoListVO();
        BeanUtils.copyProperties(picInfo, vo);
        Integer[] size = picInfo.getSizePixel()[0];
        // 原图大小转略缩图大小
        size[0] = Math.round(size[0] * ThumbnailsConstant.SMALL_SCALE);
        size[1] = Math.round(size[1] * ThumbnailsConstant.SMALL_SCALE);

        vo.setSize(size);
        return vo;
    }

    /**
     * PicInfoList -> PicInfoListVOList
     * @param picInfoList 对象
     * @return 对象
     */
    public static List<PicInfoListVO> picInfo2PicInfoListVO(List<PicInfo> picInfoList) {
        return picInfoList.stream().map(e -> picInfo2PicInfoListVO(e)).collect(Collectors.toList());
    }

    /**
     * PicInfo -> PicInfoDetailVO
     * @param picInfo 对象
     * @return 对象
     */
    public static PicInfoDetailVO picInfo2PicInfoDetailVO(PicInfo picInfo) {
        PicInfoDetailVO dto = new PicInfoDetailVO();
        BeanUtils.copyProperties(picInfo, dto);
        return dto;
    }

    /**
     * PicInfoCreateForm -> PicInfo
     * @param form 对象
     * @return 对象
     */
    public static PicInfo picInfoForm2PicInfo(PicInfoCreateForm form) {
        PicInfo picInfo = new PicInfo();
        picInfo.setAuthorId(form.getUserId());
        picInfo.setPriceStandard(form.getPriceStandard());
        picInfo.setPricePlus(form.getPricePlus());
        String[] keywords = form.getKeywords().split(KeywordConstant.SPLIT_REGEX);
        picInfo.setKeywords(keywords);

        return picInfo;
    }

    /**
     * User -> UserVO
     * @param user 对象
     * @return 对象
     */
    public static UserVO user2UserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * PicCheck -> PicCheckVO
     * @param picCheck 对象
     * @return 对象
     */
    public static PicCheckVO picCheck2PicCheckVO(PicCheck picCheck) {
        PicCheckVO vo = new PicCheckVO();
        BeanUtils.copyProperties(picCheck, vo);
        return vo;
    }

    /**
     * PicCheckList -> PicCheckVOList
     * @param picCheckList 对象列表
     * @return 对象列表
     */
    public static List<PicCheckVO> picCheckList2PicCheckVOList(List<PicCheck> picCheckList) {
        return picCheckList.stream().map(e -> picCheck2PicCheckVO(e)).collect(Collectors.toList());
    }

    /**
     * PicInfo -> PicInfoAuthorVO
     * @param picInfo 对象
     * @return 对象
     */
    public static PicInfoAuthorVO picInfo2PicInfoAuthorVO(PicInfo picInfo) {
        PicInfoAuthorVO vo = new PicInfoAuthorVO();
        BeanUtils.copyProperties(picInfo, vo);
        return vo;
    }

    /**
     * PicInfoList -> PicInfoAuthorVOList
     * @param picInfoList 对象列表
     * @return 对象列表
     */
    public static List<PicInfoAuthorVO> picInfoList2PicInfoAuthorVOList(List<PicInfo> picInfoList) {
        return picInfoList.stream().map(e -> picInfo2PicInfoAuthorVO(e)).collect(Collectors.toList());
    }
}
