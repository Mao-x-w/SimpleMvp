package com.weknowall.app_data.repository;

import com.weknowall.app_data.mapper.general.GitUserEntityMapper;
import com.weknowall.app_data.store.general.GeneralDataStoreFactory;
import com.weknowall.app_data.store.general.IGeneralDataStore;
import com.weknowall.app_domain.entity.general.GitUserModel;
import com.weknowall.app_domain.repository.IGeneralRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * User: laomao
 * Date: 2017-01-17
 * Time: 19-20
 */
@Singleton
public class GeneralRepositoryImpl extends RepositoryImpl<IGeneralDataStore, GeneralDataStoreFactory> implements IGeneralRepository {

    private GitUserEntityMapper mGMapper;

    @Inject
    public GeneralRepositoryImpl(GeneralDataStoreFactory dataStoreFactory, GitUserEntityMapper gMapper) {
        super(dataStoreFactory);
        mGMapper = gMapper;
    }

    @Override
    public Observable<List<GitUserModel>> getGitUsers() {
        return getNetDataStore().getGitUsers().map(mGMapper::transformTo);
    }
}
