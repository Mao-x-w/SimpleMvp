package com.weknowall.app_presenter.mapper;

import com.weknowall.app_common.utils.MapperImpl;
import com.weknowall.app_domain.entity.PageListModel;
import com.weknowall.app_presenter.entity.PageList;

/**
 * User: laomao
 * Date: 2017-01-20
 * Time: 12-23
 */

public abstract class PageListMapper<RP,RPM,RPL extends PageList<RP>,RPML extends PageListModel<RPM>,RPMA extends MapperImpl<RP,RPM>> extends MapperImpl<RPL,RPML> {

    private RPMA mRPMapper;

    public PageListMapper(RPMA rPMapper) {
        mRPMapper = rPMapper;
    }

    @Override
    public RPL transform(RPML rpml) {
        RPL rpl=createPageList();
        rpl.setCurrentPage(rpml.getCurrentPage());
        rpl.setTotalPage(rpml.getTotalPage());
        rpl.setItems(mRPMapper.transform(rpml.getItems()));
        return rpl;
    }

    @Override
    public RPML transformTo(RPL rpl) {
        RPML rpml=createPageListModel();
        rpml.setCurrentPage(rpl.getCurrentPage());
        rpml.setTotalPage(rpl.getTotalPage());
        rpml.setItems(mRPMapper.transformTo(rpl.getItems()));
        return rpml;
    }

    protected abstract RPML createPageListModel();

    public abstract RPL createPageList();

}
