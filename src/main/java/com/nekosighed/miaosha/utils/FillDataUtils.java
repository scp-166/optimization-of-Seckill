package com.nekosighed.miaosha.utils;

import org.springframework.beans.BeanUtils;

public class FillDataUtils {
    /**
     * 将 module 的属性映射生成一个 DO
     *
     * @param model
     * @param DOClz
     * @param <M>
     * @param <D>
     * @return
     */
    public static <M, D> D fillModelToDo(M model, Class<D> DOClz) {
        if (model == null) {
            return null;
        }
        D doEntity;
        try {
            doEntity = DOClz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(model, doEntity);
        return doEntity;
    }

    /**
     * 将 Do 的属性映射生成一个 model
     *
     * @param doEntity
     * @param mClz
     * @param <M>
     * @param <D>
     * @return
     */
    public static <M, D> M fillDoToModel(D doEntity, Class<M> mClz) {
        if (doEntity == null) {
            return null;
        }
        M model;
        try {
            model = mClz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(doEntity, model);
        return model;
    }

    public static <M, V> V fillModelToVo(M model, Class<V> vclz){
        if (model == null){
            return null;
        }
        V viewObject;
        try {
            viewObject = vclz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(model, viewObject);
        return viewObject;
    }


    /**
     * 将 VO 的属性映射生成一个 model
     *
     * @param viewObject
     * @param mclz
     * @param <V>
     * @param <M>
     * @return
     */
    public static <V, M> M fillVoToModel(V viewObject, Class<M> mclz) {
        if (viewObject == null) {
            return null;
        }
        M model;
        try {
            model=  mclz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(viewObject, model);
        return model;
    }
}
