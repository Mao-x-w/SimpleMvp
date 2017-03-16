# SimpleMvp

本文是对自己使用的项目SimpleMvp的讲解，该项目是在git大神的android10的项目Android-CleanArchitecture基础上进行的更改，有兴趣的可以直接看该大神的源码：（https://github.com/android10/Android-CleanArchitecture）

本文主要讲解如何使用该Mvp框架，对于运用的Dagger2,Rxjava,Retrofit,Lamada技术不做讲解，不了解的哥们可以先自己学习一下。
这里推荐一些学习资料：

Dagger2 使用详解：http://www.jianshu.com/p/94d47da32656

给 Android 开发者的 RxJava 详解：https://gank.io/post/560e15be2dca930e00da1083

 RxJava+Retrofit+OkHttp深入浅出：http://blog.csdn.net/wzgiceman/article/details/51939574



## mvp介绍

### Domain层
作为Presenter和Data层的桥梁，定义Repository接口，由Data层实现。定义UseCase类，从Repository接口获取数据，也就是从其实现类Data层获取数据。

### Data层
依赖于Domain层，直接从服务器获取需要的数据，并通过实现Domain层的接口，将数据传递到Domain层中。

### Presenter层
依赖于Domain层，通过使用Dagger2的依赖注入技术，将Domain层的UseCase注入到Presenter中，同时定义接口，用于向UI层返回数据。

### UI层
依赖于Presenter层，将Presenter通过依赖注入方式注入到需要请求网络的地方（如：Activity，Adapter,Fragment等等），并实现Presenter层中定义的接口，以便拿到数据

![image](http://omh5gy7p4.bkt.clouddn.com/mvp_desc.png)

## mvp模式好处
* 分离了视图逻辑和业务逻辑，降低了耦合，可以将业务逻辑写在Domain层中，而视图逻辑可以写到Presenter层中，对于UI层，只用于展示数据
* 简化了UI层的代码，原来一个Activity很容易就上千行代码。使用MVP之后，Activity大大的瘦身了。
* 提供了代码的可读性，方便后期维护

## 撸代码
介绍了这么多，现在来看看具体在项目中的应用。在本项目中对代码做了高度封装，就不介绍封装方式了，有兴趣可以自己研究，主要介绍如何使用本项目完成一次网络请求。

### 项目结构

![image](http://omh5gy7p4.bkt.clouddn.com/mvp_structure.png)

其中app对应为UI层，app_common层相当于一个工具类层，将所有层等能用到的放到了这一层，可以忽略不管这个。而app_domain,app_data,app_presenter分别为domain层，data层，presenter层

### domain层代码
![image](http://omh5gy7p4.bkt.clouddn.com/mvp_domain_constructure.png)

首先定义IGeneralRepository接口：

```
public interface IGeneralRepository extends IRepository {

    Observable<List<GitUserModel>> getGitUsers();

}
```
该接口主要目的是衔接Data层和Domain层，Data层有特定类实现类了该接口，从而调用该接口中的方法能返回Data层数据，详细讲解在Data层。

IGeneralRepository接口衔接了Data层，而和Presenter层的交互是通过UseCase进行的：

```
@Singleton
public class GitUsersUseCase extends UseCaseImpl<IGeneralRepository,Object,List<GitUserModel>> {

    /**
     * 构造参数
     *
     * @param repository
     * @param workThread   工作线程
     * @param resultThread 结果执行线程
     */
    @Inject
    public GitUsersUseCase(IGeneralRepository repository, ThreadExecutor workThread, PostExecutionThread resultThread) {
        super(repository, workThread, resultThread);
    }

    @Override
    protected Observable<List<GitUserModel>> buildUseCaseObservable(Object... objects) {
        return getRepository().getGitUsers();
    }
}
```
在该类中通过getRepository().getGitUsers()获取到了IGeneralRepository的实现类中返回的数据，那么只要Presenter层拿到该UseCase对象，就相当于和Domain层建立了连接，而Presenter是依赖Doamin层，所以拿到对象很容易，不过我们这里使用的是Dagger2依赖注入拿到的UseCase对象，关于Dagger2的使用这里不做介绍。以后有时间单讲。

Domain层不仅连接了Data层和Presenter层，同时，它也起到了一个约束的作用，所有请求和Data有关的代码都是这样做，这时，即使不写代码注释，思路依然很清闲，扩展性很强。

### data层代码
由于data层的数据来源很多，服务器、本地Json、数据库、内存等，这里我单独总结了一下，见个人博客：http://laomao.space/2017/03/15/SimpleMvp-Data/

### presenter层代码

![image](http://omh5gy7p4.bkt.clouddn.com/mvp_presenter_constructure.png)

通过dagger2得到Domain层中的UseCase对象。
```
@Module
public class GeneralModule {

    @Provides
    @Named(Constants.NAMED_GIT_USERS)
    UseCase<Object,List<GitUserModel>> provideGitUsersUseCase(IGeneralRepository repository, ThreadExecutor executor, PostExecutionThread thread){
        return new GitUsersUseCase(repository,executor,thread);
    }

}
```
接着定义一个接口IGitUsersView用于连接UI层和Presenter层

```
public interface IGitUsersView extends ILoadingView{

    void onGetGitUsers(List<GitUser> users);

}
```

这个时候只需要一个中间者，将从UseCase中获取到的数据，传递给接口中的onGetGitUsers(List<GitUser> users)，那么实现了该接口的UI层就能拿到了数据，这个中间者就是Presenter

```
public class GitUsersPresenter extends LoadingPresenter<Object,Object,List<GitUserModel>,List<GitUser>,IGitUsersView> {

    private GitUserMapper mGMapper;

    @Inject
    public GitUsersPresenter(
            @Named(Constants.NAMED_GIT_USERS)
            @NonNull UseCase<Object, List<GitUserModel>> useCase, GitUserMapper gMapper) {
        super(useCase);
        mGMapper = gMapper;
    }

    @Override
    public void initialize(Object... objects) {
        execute(objects);
    }

    @Override
    public void onNext(List<GitUserModel> gitUserModels) {
        super.onNext(gitUserModels);
        getView().onGetGitUsers(mGMapper.transform(gitUserModels));
    }
}
```
可以看到在onNext方法中，返回了数据，具体怎么返回在GitUsersPresenter的父类中封装的呢，有兴趣自己研究，通过getView拿到IGitUsersView接口对象，并把数据传递给onGetGitUsers()方法。

### UI层代码

![image](http://omh5gy7p4.bkt.clouddn.com/mvp_ui_constructure.png)

首先使用@Component注解一个连接器GeneralComponent
```
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = {ActivityModule.class, GeneralModule.class})
public interface GeneralComponent extends ActivityComponent{
    void inject(GitUsersActivity activity);
}
```

它的作用是让那些想要使用Dagger2依赖注入的地方提供一个通道，而那些要使用地方来这里登记一下void inject(GitUsersActivity activity);因为我们在GitUsersActivity中要使用Presenter，所以这里将这个Activity登记一下。
```
public class GitUsersActivity extends BaseActivity implements IGitUsersView {
    @Inject
    GitUsersPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_users);

        // 初始化网络请求
        DaggerGeneralComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build().inject(this);
        mPresenter.setView(this);

        // 发起网络请求
        mPresenter.initialize();
    }

    /**
     * 网络请求返回数据
     * @param users
     */
    @Override
    public void onGetGitUsers(List<GitUser> users) {
        // 处理网络返回数据

    }
}

```

这里把GitUsersPresenter注入到了GitUsersActivity中，虽然注入了，但GitUsersActivity不知道去哪里找，所以需要将GitUsersActivity在GeneralComponent中登记一下，DaggerGeneralComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).build().inject(this);
这个时候就可以使用Presenter实例了，使用mPresenter.setView(this)给Presenter设置View对象，这里用this是因为activity实现了Presenter层的接口IGitUserView，这样Presenter层就能拿到接口的实例对象，从而给UI层返回数据。最后通过mPresenter.initialize()发起网络请求，当然如果有参数可以在initialize()方法中添加。最后数据就通过实现了IGitUserView接口的onGetGitUsers(List<GitUser> users)方法返回。


至此，就成功的完成了一次网络请求。刚开始学习使用MVP,是很痛苦的，莫名其妙的加大了好多工作量，但是，如果你能坚持下来，你会发现后期维护起来非常容易。

自从使用了MVP，妈妈再也不用担心我打产品经理了。

哈哈哈哈哈。。。。。。

文笔太差，有不足地方请指正






