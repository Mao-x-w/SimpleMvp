package com.weknowall.cn.wuwei.utils.image;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.weknowall.app_common.encode.MD5;
import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.app_presenter.entity.general.Image;

import java.io.File;
import java.io.FileOutputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: JiYu
 * Date: 2016-08-18
 * Time: 15-59
 */

public class ImageLoader {

	/**
	 * 图片加载配置类
	 */
	public static class Options {

		public enum Scale {
			Normal, Thump
		}

		private Transformation TRANSFORM_DEFAULT = UnitTransformation.get();
		private Transformation<Bitmap>[] transformation;
		private int width = 0;
		private int height = 0;
		private int animRes = 0;
		private int errorRes = 0;
		private int loadingRes = 0;
		private boolean centerCrop = false;
		private boolean fitCenter = false;
		private ImageLoadingListener listener;
		private Scale scale;

		public Options size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		/**
		 * 图片加载失败后需要显示的图片
		 * @param errorRes 图片资源
		 */
		public Options error(int errorRes) {
			this.errorRes = errorRes;
			return this;
		}

		/**
		 * 图片正在加载时需要显示的图片
		 * @param loadingRes 图片资源
		 */
		public Options loading(int loadingRes) {
			this.loadingRes = loadingRes;
			return this;
		}

		/**
		 * 图片加载完成时的动画
		 * @param animRes 资源文件
		 */
		public Options anim(int animRes) {
			this.animRes = animRes;
			return this;
		}

		/**
		 * 图片显示方式{@link ImageView.ScaleType}设置为CenterCrop
		 */
		public Options centerCrop() {
			this.centerCrop = true;
			return this;
		}

		public Options fitCenter() {
			this.fitCenter = true;
			return this;
		}

		/**
		 * 图片加载比例
		 * @param scale 比例
		 */
		public Options scale(Scale scale) {
			this.scale = scale;
			return this;
		}

		/**
		 * 图片加载回调
		 * @param loadingListener 回调监听
		 */
		public Options listener(ImageLoadingListener loadingListener) {
			this.listener = loadingListener;
			return this;
		}

		public Options transform(Transformation<Bitmap>... transformation) {
			this.transformation = transformation;
			return this;
		}
	}

	/**
	 * 生成一个默认的Options
	 */
	public static Options defaultOptions() {
		return new Options().scale(Options.Scale.Thump).loading(com.weknowall.cn.wuwei.R.drawable.image_loading).error(com.weknowall.cn.wuwei.R.drawable.image_loading_error);
	}

	/**
	 * 根据图片资源的宽高,以及图片展示的宽度计算图片展示的高度
	 * @param width        图片资源宽度
	 * @param height       图片资源高度
	 * @param displayWidth 图片展示宽度
	 */
	public static int calculateDisplayHeight(int width, int height, int displayWidth) {
		return (int) ((float) (height * displayWidth) / (float) width);
	}

	/**
	 * 根据图片资源的宽高,以及屏幕宽度计算图片展示的高度,即该图片默认以屏幕宽度展示
	 * @param width  图片资源宽度
	 * @param height 图片资源高度
	 */
	public static int calculateDisplayHeight(int width, int height) {
		return calculateDisplayHeight(width, height, DeviceHelper.getScreenWidth());
	}

	public static void display(Object object, Image imageModel, ImageView imageView) {
		display(object, imageModel, imageView, defaultOptions());
	}

	/**
	 * 加载图片并显示在ImageView上
	 * @param object     Context
	 * @param imageModel 图片Model
	 * @param imageView  图片View
	 * @param options    加载Options
	 */
	public static void display(Object object, @NonNull Image imageModel, ImageView imageView, Options options) {
		if (imageView == null) {
			return;
		}
		//  设置一下图片宽高  防止内存溢出
		if (options.width == 0) {
			if (imageView.getWidth() != 0) {
				options.width = imageView.getWidth();
			} else {
				options.width = DeviceHelper.getScreenWidth() / 2;
			}
		}
		if (options.height == 0) {
			if (imageView.getHeight() != 0) {
				options.height = imageView.getHeight();
			} else {
				options.height = DeviceHelper.getScreenHeight() / 2;
			}
		}
		//		final Target target = getImageLoadingTarget(imageView, options);
		final Image.Source source = imageModel.getSource() == null ? Image.Source.Net : Image.Source.File;
		String url = imageModel.getSource() == Image.Source.Net ? options.scale == Options.Scale.Normal ? imageModel.getUrl() : imageModel.getThumbNailUrl() : imageModel
				.getUrl();
		if (!url.contains("http")){
			url="https://www.weknowall.cn"+url;
		}
		getGenericRequestBuilder(getContext(object), url, source, getRequestManager(object), options).into(imageView);
	}

	/**
	 * 直接使用url进行显示图片
	 * @param object    context
	 * @param url       图片url
	 * @param imageView 要展示到的ImageView
	 */
	public static void display(Object object, String url, ImageView imageView) {
		display(object, url, imageView, defaultOptions());
	}

	/**
	 * 显示头像专用
	 * @param object    context
	 * @param url       图片url
	 * @param imageView 头像ImageView
	 */
	public static void displayCircle(Object object, String url, ImageView imageView) {
		displayCircle(object, url, imageView, defaultOptions().loading(com.weknowall.cn.wuwei.R.drawable.image_loading_circle).error(com.weknowall.cn.wuwei.R.drawable.image_loading_circle_error));
	}

	/**
	 * 显示圆形图形
	 * @param object    context
	 * @param url       图片url
	 * @param imageView 头像ImageView
	 * @param options   options
	 */
	public static void displayCircle(Object object, String url, ImageView imageView, Options options) {
		display(object, url, imageView, options.transform(new CropCircleTransformation(getBitmapPool(object))).centerCrop());
	}

	/**
	 * 显示圆角图片
	 * @param radius 圆角角度
	 */
	public static void displayRound(Object object, String url, ImageView imageView, int radius) {
		displayRound(object, url, imageView, radius, defaultOptions());
	}

	public static void displayRound(Object object, String url, ImageView imageView, int radius, Options options) {
		BitmapPool pool = getBitmapPool(object);
		display(object, url, imageView, options.transform(new CenterCrop(pool), new RoundedCornersTransformation(pool, radius, 0)));
	}

	/**
	 * 根据url进行显示图片
	 * @param object    context
	 * @param url       图片url
	 * @param imageView 头像ImageView
	 * @param options
	 */
	public static void display(Object object, String url, ImageView imageView, Options options) {
		Image image = new Image();
		image.setUrl(url);
		image.setThumbNailUrl(url);
		display(object, image, imageView, options);
	}

	public static void display(Object object, File file, ImageView imageView) {
		display(object, file, imageView, defaultOptions());
	}

	/**
	 * 显示图片文件
	 * @param object    object
	 * @param file      文件
	 * @param imageView 图片View
	 * @param options   op
	 */
	public static void display(Object object, File file, ImageView imageView, Options options) {
		Image image = new Image();
		image.setSource(Image.Source.File);
		image.setUrl(file.getAbsolutePath());
		image.setThumbNailUrl(file.getAbsolutePath());
		display(object, image, imageView, options);
	}

	/**
	 * 加载图片Bitmap
	 * @param _content context obj
	 * @param imageUrl image 网络地址
	 * @param options  options
	 */
	public static Observable<Bitmap> load(final Object _content, final String imageUrl, final Options options) {
		Image model = new Image();
		model.setUrl(imageUrl);
		return load(_content, model, options.scale(Options.Scale.Normal));
	}

	/**
	 * 加载图片Bitmap
	 * @param _content context obj
	 * @param image    image bean
	 * @param options  options
	 */
	public static Observable<Bitmap> load(final Object _content, final Image image, final Options options) {
		return Observable.create(new Observable.OnSubscribe<Bitmap>() {
			@Override
			public void call(final Subscriber<? super Bitmap> sb) {
				{
					Image.Source source = image == null ? Image.Source.Net : image.getSource();
					String url = image == null ? "" : image.getUrl();
					Target target = new SimpleTarget<Bitmap>() {
						@Override
						public void onLoadFailed(Exception e, Drawable errorDrawable) {
							sb.onError(e);
						}

						@Override
						public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
							sb.onNext(resource);
							sb.onCompleted();
						}
					};
					getGenericRequestBuilder(getContext(_content), url, source, getRequestManager(_content), options).into(target);
				}
			}
		}).observeOn(AndroidSchedulers.mainThread());
	}

	public static Observable<String> download(Context context, String url) {
		return download(context, url, Environment.getExternalStorageDirectory() + "/meishij/");
	}

	/**
	 * 下载图片
	 * @param context context
	 * @param url     图片地址
	 * @param saveDir 图片保存目录
	 */
	public static Observable<String> download(Context context, String url, String saveDir) {
		return Observable.<String>create(sb -> {
			try {
				Bitmap b = Glide.with(context).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
				File dir = new File(saveDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File save = new File(dir, MD5.encode(url) + ".jpg");
				if (save.exists()) {
					sb.onNext(save.getAbsolutePath());
					sb.onCompleted();
					return;
				}
				FileOutputStream fos = null;
				fos = new FileOutputStream(save);
				b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
				b.recycle();
				MediaStore.Images.Media.insertImage(context.getContentResolver(), save.getAbsolutePath(), save.getName(), null);
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + save.getAbsolutePath())));
				sb.onNext(save.getAbsolutePath());
				sb.onCompleted();
			} catch (Exception e) {
				e.printStackTrace();
				sb.onError(e);
			}
		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}


	static GenericRequestBuilder getGenericRequestBuilder(Context context, String url, Image.Source source, RequestManager manager, Options options) {
		/** 非空判断 */
		if (TextUtils.isEmpty(url)) {
			url = "";
		}
		DrawableTypeRequest request = (source == null || source == Image.Source.Net) ? manager.load(url) : manager.load(new File(url));
		if (options.centerCrop) {
			request.bitmapTransform(new CenterCrop(context));
			//			request.centerCrop();
		}
		if (options.fitCenter) {
			//			request.fitCenter();
			request.bitmapTransform(new FitCenter(context));
		}
		if (options.loadingRes != 0) {
			request.placeholder(options.loadingRes);
		}
		if (options.errorRes != 0) {
			request.error(options.errorRes);
		}
		if (options.animRes != 0) {
			request.animate(options.animRes);
		}
		if (options.transformation == null) {
			BitmapTransformation transformation = options.fitCenter ? new FitCenter(context) : new CenterCrop(context);
			request.bitmapTransform(options.TRANSFORM_DEFAULT, transformation);
		} else {
			request.bitmapTransform(options.transformation);
		}
		return request.diskCacheStrategy(source == Image.Source.Net ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE).override(options.width, options.height);
	}

	/**
	 * 根据 Content Object创建对应生命周期的RequestManager
	 * @param context Content Object
	 * @return RequestManager
	 */
	static RequestManager getRequestManager(Object context) {
		if (context == null)
			return null;
		try {
			RequestManager manager = null;
			if (context instanceof Context) {
				if (context instanceof Activity) {
					if (context instanceof FragmentActivity) {
						manager = Glide.with((FragmentActivity) context);
					} else {
						manager = Glide.with((Activity) context);
					}
				} else {
					manager = Glide.with((Context) context);
				}
			} else if (context instanceof android.support.v4.app.Fragment) {
				manager = Glide.with((android.support.v4.app.Fragment) context);
			} else if (context instanceof Fragment) {
				manager = Glide.with((Fragment) context);
			}
			return manager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static Context getContext(Object context) {
		if (context == null)
			return null;
		if (context instanceof Context) {
			return (Context) context;
		} else if (context instanceof android.support.v4.app.Fragment) {
			return ((android.support.v4.app.Fragment) context).getContext();
		} else if (context instanceof Fragment) {
			return ((Fragment) context).getActivity();
		}
		return null;
	}

	public static BitmapPool getBitmapPool(Object context) {
		BitmapPool pool = null;
		if (context == null)
			return null;
		if (context instanceof Context) {
			if (context instanceof Activity) {
				if (context instanceof FragmentActivity) {
					pool = Glide.get((FragmentActivity) context).getBitmapPool();
				} else {
					pool = Glide.get((Activity) context).getBitmapPool();
				}
			} else {
				pool = Glide.get((Context) context).getBitmapPool();
			}
		} else if (context instanceof android.support.v4.app.Fragment) {
			pool = Glide.get(((android.support.v4.app.Fragment) context).getContext()).getBitmapPool();
		} else if (context instanceof Fragment) {
			pool = Glide.get(((Fragment) context).getActivity()).getBitmapPool();
		}
		return pool;
	}

	/**
	 * 用来获取图片Bitmap的target
	 * @param options 图片
	 * @return 一个可以加载Bitmap的Target
	 */
	static Target<Bitmap> getImageBitmapTarget(final Options options) {
		return new SimpleTarget<Bitmap>(options.width > 0 ? options.width : SimpleTarget.SIZE_ORIGINAL, options.height > 0 ? options.height : SimpleTarget.SIZE_ORIGINAL) {
			@Override
			public void onStart() {
				if (options.listener != null)
					options.listener.onStart();
			}

			@Override
			public void onLoadFailed(Exception e, Drawable errorDrawable) {
				if (options.listener != null) {
					options.listener.onError();
					options.listener.onFinish();
				}
			}

			@Override
			public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
				if (options.listener != null) {
					options.listener.onSuccess(resource);
					options.listener.onFinish();
				}
			}
		};
	}

	/**
	 * 用来为ImageView加载图片的target
	 * @param imgView ImageView
	 * @param options options
	 */
	static ViewTarget<ImageView, Bitmap> getImageLoadingTarget(final ImageView imgView, final Options options) {
		return new BitmapImageViewTarget(imgView) {
			@Override
			public void onStart() {
				if (options.listener != null)
					options.listener.onStart();
			}

			@Override
			public void onLoadStarted(Drawable placeholder) {
				if (options.loadingRes != 0)
					super.onLoadStarted(placeholder);
			}

			@Override
			public void onLoadFailed(Exception e, Drawable errorDrawable) {
				if (options.errorRes != 0)
					super.onLoadFailed(e, errorDrawable);
				if (options.listener != null) {
					options.listener.onError();
					options.listener.onFinish();
				}
			}

			@Override
			public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
				super.onResourceReady(resource, glideAnimation);
				if (options.listener != null) {
					options.listener.onSuccess(resource);
					options.listener.onFinish();
				}
			}
		};
	}

	/**
	 * 图片加载回调
	 */
	public static class ImageLoadingListener {
		public void onStart() {
		}

		public void onSuccess(Bitmap bitmap) {
		}

		public void onError() {
		}

		public void onFinish() {
		}
	}
}
