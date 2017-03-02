package com.weknowall.app_domain.entity;

/**
 * User: laomao
 * Date: 2017-01-19
 * Time: 12-28
 */

public class Listable {
    public static final int PAGE_SIZE_DEFAULT = 20;
    private static final int FIRST_PAGE = 0;

    private int page=FIRST_PAGE;
    private int pageSize = PAGE_SIZE_DEFAULT;
    private int realPage;
    private String id;
    private int loadType = LoadType.GET;

    public interface LoadType {
        int GET = 1;
        int REFRESH = 2;
        int MORE = 3;
    }

    public Listable() {
        loadType = LoadType.GET;
        page = FIRST_PAGE;
    }

    public <T extends Listable> T load(int type) {
        this.loadType = type;
        if (type != LoadType.MORE) {
            this.page = FIRST_PAGE;
            this.realPage = FIRST_PAGE;
        }
        return (T) this;
    }

    public <T extends Listable> T more() {
        load(LoadType.MORE);
        return (T) this;
    }

    public <T extends Listable> T get() {
        load(LoadType.GET);
        return (T) this;
    }

    public <T extends Listable> T refresh() {
        load(LoadType.REFRESH);
        return (T) this;
    }

    /**
     * 判断是否是首次加载数据
     * @return
     */
    public boolean isFirstInitial(){
        return loadType==LoadType.GET;
    }

    /**
     * 判断是否是加载更多
     * @return
     */
    public boolean isLoadingMore(){
        return loadType==LoadType.MORE;
    }

    /**
     * 判断是否是第一页
     * @return
     */
    public boolean isFirstPage(){
        return loadType!=LoadType.MORE;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRealPage() {
        return realPage;
    }

    public void setRealPage(int realPage) {
        this.realPage = realPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }
}
