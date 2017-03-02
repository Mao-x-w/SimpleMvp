package com.weknowall.app_data.mapper;

import com.weknowall.app_common.utils.MapperImpl;
import com.weknowall.app_data.entity.PageListEntity;
import com.weknowall.app_domain.entity.PageListModel;

/**
 * User: laomao
 * Date: 2017-01-23
 * Time: 10-27
 */

public abstract class PageListEntityMapper<RPE,RPM,RPEL extends PageListEntity<RPE>,RPML extends PageListModel<RPM>,RPEMA extends MapperImpl<RPE,RPM>> extends MapperImpl<RPEL,RPML> {

    private RPEMA mRpema;

    public PageListEntityMapper(RPEMA rpema) {
        mRpema = rpema;
    }

    @Override
    public RPEL transform(RPML rpml) {
        RPEL rpel=createPageListEntity();
        rpel.setTotalPage(parseString(rpml.getTotalPage()));
        rpel.setCurrentPage(parseString(rpml.getCurrentPage()));
        rpel.setItems(mRpema.transform(rpml.getItems()));
        return super.transform(rpml);
    }

    @Override
    public RPML transformTo(RPEL rpel) {
        RPML rpml=createPageListModel();
        rpml.setCurrentPage(parseInteger(rpel.getCurrentPage()));
        rpml.setTotalPage(parseInteger(rpel.getTotalPage()));
        rpml.setItems(mRpema.transformTo(rpel.getItems()));
        return super.transformTo(rpel);
    }


    protected abstract RPML createPageListModel();

    public abstract RPEL createPageListEntity();

}
