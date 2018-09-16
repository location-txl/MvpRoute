package com.location.mvp.mvproutelibrary.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.location.mvp.mvproutelibrary.utils.LogUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 项目名称: MvpRoute
 * 类描述:  Recycler的基础适配器
 * 创建人: location
 * 创建时间: 2018/5/25 0025 23:26
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements AdapterList.ChangeListener<DataBean> {
	/**
	 * 当数据没有时显示即使已经添加了头布局尾布局
	 */
	public static final int EMPTY_MODLE_HEADERS = 0x001;
	/**
	 * 当没有数据 并且头尾布局都没有的情况下显示
	 */
	public static final int EMPTY_MODLE_NOMAL = 0x002;
	/**
	 * 列表数据
	 */
	protected List<T> data;
	/**
	 * 存储空View
	 */
	private View emptyView;
	/**
	 * 头尾布局点击事件
	 */
	private OnHeaderClickListener onHeaderClickListener;

	private int emptyModle = EMPTY_MODLE_HEADERS;
	/**
	 * 存储头布局
	 */
	private AdapterList<DataBean> headerList = new AdapterList<>();
	/**
	 * 存储尾部局
	 */
	private ArrayList<DataBean> footerList = new ArrayList<>();

	//头布局的缓存集合
	private SparseArray<DataBean> cacheHeaders;

	//布局数组
	protected @LayoutRes
	int layouts;

	/**
	 * 标记type类型
	 */
	public static final int TYPE_EMPTY = -999999999;
	/**
	 * 默认类型
	 */
	private final int TYPE_NOMAL = 0;

	/**
	 * 存储多布局
	 */
	private SparseIntArray spLayout;

	/**
	 * 存储子View的点击事件
	 */
	private SparseArray<OnChildClickListener> listenerSparseArray;


	/**
	 * 是否绘制头尾布局分割线
	 */
	private boolean isDrawHeaderFooterLine = true;


	/**
	 * 是否绘制头尾布局分割线  默认绘制
	 *
	 * @param isDrawHeaderFooterLine
	 */
	public void setDrawHeaderFooterLine(boolean isDrawHeaderFooterLine) {
		this.isDrawHeaderFooterLine = isDrawHeaderFooterLine;
	}

	public boolean isDrawHeaderFooterLine() {
		return isDrawHeaderFooterLine;
	}


	public BaseAdapter(@LayoutRes int layout) {
		this(null, layout, null);
	}

	public BaseAdapter(Collection<T> data, @LayoutRes int layout) {
		this(data, layout, null);
	}


	public BaseAdapter(Collection<T> data, @LayoutRes int layout, OnItemClickListener listener) {
		this.data = new ArrayList<>();
		if (data != null) {
			this.data.addAll(data);
		}
		this.layouts = layout;
		this.listener = listener;
		spLayout = new SparseIntArray();
		spLayout.put(TYPE_NOMAL, this.layouts);
		headerList.addChangeListener(this);
	}


	private OnItemClickListener listener;

	/**
	 * 绑定布局
	 *
	 * @param type
	 * @param layouts
	 * @return
	 */
	public BaseAdapter addType(@IntRange(from = 0) int type, @LayoutRes int layouts) {
		spLayout.put(type, layouts);
		return this;
	}

	/**
	 * view的点击事件
	 *
	 * @param listener
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}


	/**
	 * 头尾布局点击事件
	 * 参数查看{@link OnHeaderClickListener }
	 *
	 * @param onHeaderClickListener
	 */
	public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
		this.onHeaderClickListener = onHeaderClickListener;
	}

	/**
	 * 添加字view的点击事件
	 *
	 * @param ids      子view的id
	 * @param listener 点击回调事件
	 */
	public void setOnChildClickListener(@IdRes int ids, @NonNull OnChildClickListener listener) {
		if (listenerSparseArray == null) {
			listenerSparseArray = new SparseArray<>();
		}
		listenerSparseArray.put(ids, listener);
	}

	public void setData(Collection<T> data) {
		this.data = new ArrayList<>(data);
	}


	/**
	 * @param parent
	 * @param viewType
	 * @return
	 * @see ViewHolder
	 */
	@Override
	public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		if (getHeaderData(viewType) != null) {
			DataBean headerData = getHeaderData(viewType);
			view = LayoutInflater.from(parent.getContext()).inflate(headerData.getLayout(), parent, false);
			return new ViewHolder(view);
		}

		if (getFooterData(viewType) != null) {
			DataBean footerData = getFooterData(viewType);
			view = LayoutInflater.from(parent.getContext()).inflate(footerData.getLayout(),
					parent, false);
			return new ViewHolder(view);
		}
		if (viewType == TYPE_EMPTY) {
			return new ViewHolder(emptyView);
		}
		view = LayoutInflater.from(parent.getContext()).inflate(spLayout.get(viewType), parent, false);
		ViewHolder holder = new ViewHolder(view, listener, listenerSparseArray, getHeaderCount());
		return holder;
	}

	/**
	 * 更新头布局内容
	 *
	 * @param data    数据源
	 * @param layouts 刷新的头布局id
	 */
	public void updateHeader(Object data, @LayoutRes int layouts) {
		updateHeader(Collections.singletonList(data), layouts, 0);
	}

	/**
	 * 如果有多个相同的头布局 更新头布局内容
	 * 如果  数据集合和布局索引个数不匹配  将不会刷新布局
	 *
	 * @param datas   数据集合
	 * @param layouts 布局
	 * @param index   布局索引 相对于布局
	 */
	public void updateHeader(List datas, @LayoutRes int layouts, @IntRange(to = 0) Integer... index) {
		refreshHeader(datas, layouts, index);
	}


		public void updateAllHeader(List datas, @LayoutRes int layouts) {
		Integer[] index = new Integer[datas.size()];
		for(int i=0;i<datas.size();i++){
			index[i] = i;
		}
		refreshHeader(datas, layouts, index);
	}

	private void refreshHeader(List datas, @LayoutRes int layouts, Integer[] index) {
		if (index==null||datas.size() != index.length) {
			return;
		}

		Iterator<Object> dataiter = datas.iterator();
		List<Integer> indexs = new ArrayList<>();
		indexs.addAll(Arrays.asList(index));
		Collections.sort(indexs);
		Iterator<Integer> iterator = indexs.iterator();
		int indexHeader = iterator.next();
		int count = headerList.size();
		int headerIndex = 0;
		for (int i = 0; i < count; i++) {
			DataBean dataBean = headerList.get(i);
			if (dataBean.getLayout() == layouts && headerIndex == indexHeader) {
				headerIndex++;
				dataBean.setResponse(dataiter.next());
				notifyItemChanged(i);
				if (!iterator.hasNext()) {
					return;
				} else {
					indexHeader = iterator.next();
				}
			} else if (dataBean.getLayout() == layouts) {
				headerIndex++;
			}
		}
	}

	/**
	 * @param holder
	 * @param position
	 * @see #conver(ViewHolder, Object, int) {@link #onBindFooterViewHolder(ViewHolder, Object, int)}
	 * {@link #onBindHeaderViewHolder(ViewHolder, Object, int)}
	 */
	@Override
	public final void onBindViewHolder(ViewHolder holder, int position) {
		if (getItemViewType(position) == TYPE_EMPTY) {
			return;
		}
		if (isHeaderPos(position)) {
			onBindHeaderViewHolder(holder, headerList.get(position).getResponse(), headerList.get
					(position).getLayout());
			if (onHeaderClickListener != null) {
				holder.registListener(holder.getItemViewType(), onHeaderClickListener, headerList.get(position).getResponse(), getIndex(headerList, position), true);
			}
			return;
		}
		if (isFooterPos(position)) {
			onBindFooterViewHolder(holder, footerList.get(position - headerList.size() - data.size()).getResponse(), footerList.get
					(position - headerList.size() - data.size()).getLayout());
			if (onHeaderClickListener != null) {
				holder.registListener(holder.getItemViewType(), onHeaderClickListener, headerList.get(position - headerList.size() - data.size()).getResponse(), getIndex(headerList, position - headerList.size() - data.size()), false);
			}
			return;
		}
		if ((position - headerList.size()) > data.size() - 1) {
			conver(holder, null, getItemViewType((position)));
		} else {
			conver(holder, data.get((position - headerList.size())), getItemViewType((position)));
		}
	}


	/**
	 * 正常绑定数据
	 *
	 * @param holder   控制器ViewHolder
	 * @param data     数据源 有可能为空
	 * @param viewType 当前的type类型 多布局时使用
	 */
	public abstract void conver(ViewHolder holder, @Nullable T data, int viewType);

	/**
	 * 解决GrildLayoutManager问题
	 *
	 * @param recyclerView
	 */
	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager) {
			final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
			final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

			gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {
					if (isHeaderPos(position)) {
						return gridLayoutManager.getSpanCount();
					} else if (isFooterPos(position)) {
						return gridLayoutManager.getSpanCount();
					}
					if (spanSizeLookup != null) {
						return spanSizeLookup.getSpanSize(position);
					}
					return 1;
				}
			});
			gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
		}
	}

	/**
	 * 解决StaggeredGridLayoutManager问题
	 *
	 * @param holder
	 */
	@Override
	public void onViewAttachedToWindow(ViewHolder holder) {
		super.onViewAttachedToWindow(holder);
		int position = holder.getLayoutPosition();
		if (isHeaderPos(position) || isFooterPos(position)) {
			ViewGroup.LayoutParams lp = holder.getItemView().getLayoutParams();
			if (lp != null
					&& lp instanceof StaggeredGridLayoutManager.LayoutParams) {

				StaggeredGridLayoutManager.LayoutParams p =
						(StaggeredGridLayoutManager.LayoutParams) lp;

				p.setFullSpan(true);
			}
		}
	}

	/**
	 * 下面两个方法按需重写
	 *
	 * @param viewHolder 控制器ViewHolder
	 * @param response   用到时 强转为你所需要的实体类
	 * @param layout     添加多个头布局时  可以判断layout来控制视图
	 */
	public void onBindHeaderViewHolder(ViewHolder viewHolder, @Nullable Object response, @LayoutRes int
			layout) {
	}

	//用法参见header
	public void onBindFooterViewHolder(ViewHolder viewHolder, @Nullable Object response, @LayoutRes int
			layout) {
	}

	@CallSuper
	@Override
	public int getItemCount() {
		if (emptyModle == EMPTY_MODLE_NOMAL) {
			if (data.isEmpty() && data.size() == 0 && emptyView != null && headerList.isEmpty() && footerList.isEmpty()) {
				return 1;
			}
		} else {
			if (data.isEmpty() && data.size() == 0 && emptyView != null) {
				return 1;
			}
		}

		return data.size() + getHeaderCount() + getFooterCount();
	}

	/**
	 * 添加头布局 尾布局
	 *
	 * @param layout 头尾布局的layoutID
	 */
	public final void addHeaderView(Object data, @LayoutRes int layout) {
		DataBean dataBean = new DataBean(data, layout);
		headerList.add(dataBean);
	}

	public final void addFooterView(Object data, @LayoutRes int layout) {
		DataBean dataBean = new DataBean(data, layout);
		footerList.add(dataBean);
	}

	/**
	 * 添加头布局
	 *
	 * @param index  要添加的头布局的索引
	 * @param data   数据源
	 * @param layout 布局
	 */
	public void addHeaderView(int index, Object data, @LayoutRes int layout) {
		if (index > headerList.size()) {
			if (cacheHeaders == null) {
				cacheHeaders = new SparseArray<>();
			}
			cacheHeaders.put(index, new DataBean(data, layout));
		} else {
			headerList.add(index, new DataBean(data, layout));
		}
	}

	public final void addHeaderView(@LayoutRes int layout) {
		headerList.add(new DataBean(layout));
	}

	public final void addFooterView(@LayoutRes int layout) {
		footerList.add(new DataBean(layout));
	}

	/**
	 * 无数据时视图显示模式
	 * 默认模式为   {@link #EMPTY_MODLE_HEADERS}  当数据没有时显示
	 * 即使已经添加了头布局尾布局
	 * 可选模式     {@link #EMPTY_MODLE_NOMAL}  当没有数据 并且头尾布局都没有的情况下显示
	 *
	 * @param modle {@link #emptyModle}
	 */
	public void setEmptyModle(@EmptyModle int modle) {
		this.emptyModle = modle;
	}


	/**
	 * 当没有数据时会显示此View
	 *
	 * @param view
	 */
	public void setEmptyView(@NonNull View view) {
		this.emptyView = view;
		RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
		this.emptyView.setLayoutParams(params);
	}


	/**
	 * 加入final 禁止子类重写
	 * 如果需要多布局
	 *
	 * @param position
	 * @return
	 */
	@Override
	public final int getItemViewType(int position) {
		if (emptyModle == EMPTY_MODLE_NOMAL) {
			if (data.isEmpty() && data.size() == 0 && emptyView != null && headerList.isEmpty() && footerList.isEmpty())
				return TYPE_EMPTY;
		} else {
			if (data.isEmpty() && data.size() == 0 && emptyView != null) return TYPE_EMPTY;
		}

		if (isHeaderPos(position)) {
			return headerList.get(position).getLayout();
		}
		if (isFooterPos(position)) {
			return footerList.get(position - data.size() - headerList.size()).getLayout();
		}
		int dataPosition = position - getHeaderCount();
		LogUtils.i("position==>" + position + "\nheadercount===>" + getHeaderCount());
		if (dataPosition >= data.size()) {
			return TYPE_NOMAL;
		}
		T t = data.get(dataPosition);
		if (t instanceof MulitTypeListener) {
			return ((MulitTypeListener) t).getItemType();
		}
		return TYPE_NOMAL;
	}
    public T getItem(int position){
		T t = data.get(position);
		return t;
	}
	public List<T> getItemList(){
		return data;
	}

	/**
	 * 增加数据
	 *
	 * @param items
	 */
	public final void loadItem(Collection<T> items) {
		int size = data.size() + headerList.size();
		data.addAll(items);
		notifyItemRangeInserted(size, items.size());
		compatibilityDataSizeChanged(items.size());
	}


	/**
	 * 增加一条数据
	 *
	 * @param t
	 */
	public final void loadItem(T t) {
		data.add(t);
		notifyItemInserted(data.size() + headerList.size());
		compatibilityDataSizeChanged(1);
	}


	/**
	 * 刷新本地数据
	 */
	public final void refresh() {
		notifyDataSetChanged();
	}

	public void refresh(@IntRange(from = 0) int position){
		notifyItemChanged(getHeaderCount()+position);
	}
	/**
	 * 重新设置数据
	 *
	 * @param response
	 */
	public final void refresh(Collection<T> response) {
		data.clear();
		data.addAll(response);
		notifyDataSetChanged();
	}

	/**
	 * 删掉对应的索引
	 *
	 * @param position
	 */
	public final void remove(@IntRange(from = 0) int position) {
		if (position >= data.size()) return;
		int size = data.size();
		data.remove(position);
		notifyItemRemoved(position + headerList.size());
		compatibilityDataSizeChanged(0);
	}

	/**
	 * 清除数据
	 */
	public final void clear() {
		data.clear();
		notifyDataSetChanged();
	}

	private void compatibilityDataSizeChanged(int newDataSize) {
		int size = data.isEmpty() ? 0 : data.size();
		if (size == newDataSize) notifyDataSetChanged();
	}


	public final boolean isHeaderPos(int position) {
		if (headerList.isEmpty()) {
			return false;
		}
		return getHeaderCount() > position;
	}

	public final boolean isFooterPos(int position) {
		if (footerList.isEmpty()) {
			return false;
		}
		return position >= headerList.size() + data.size();
	}

	public final int getHeaderCount() {
		return headerList.size();
	}

	public final int getFooterCount() {
		return footerList.size();
	}


	/**
	 * 获取头布局所对应的数据
	 *
	 * @param type
	 * @return
	 */
	private @Nullable
	DataBean getHeaderData(int type) {
		for (DataBean dataBean : headerList) {
			if (dataBean.getLayout() == type) {
				return dataBean;
			}
		}
		return null;
	}


	/**
	 * 获取尾部局所对应的data
	 *
	 * @param type
	 * @return
	 */
	private @Nullable
	DataBean getFooterData(int type) {
		for (DataBean dataBean : footerList) {
			if (dataBean.getLayout() == type) {
				return dataBean;
			}
		}
		return null;
	}

	/**
	 * 获取当前相同头尾部所出现的次数
	 *
	 * @param dataBeans
	 * @param position
	 * @return
	 */
	private int getIndex(List<DataBean> dataBeans, int position) {
		int dex = 0;
		int layout = dataBeans.get(position).getLayout();
		for (int i = 0; i < position; i++) {
			int curLayout = dataBeans.get(i).getLayout();
			if (curLayout == layout) {
				dex++;
				continue;
			}
		}
		return dex;
	}


	public T getData(@IntRange(from = 0) int position) {
		if (position >= data.size()) return null;
		return data.get(position);
	}


	/**
	 * group分组时  获取分组的最后一项的索引
	 *
	 * @param indexPosition
	 * @return
	 */
	public int getlastGroupPosition(int indexPosition) {
		if (indexPosition >= data.size()) return -1;
		if (indexPosition == data.size() - 1) return indexPosition;
		int groupId = ((MulitGroupListener) data.get(indexPosition)).bindAdapterGroupId();
		for (int i = indexPosition + 1; i < data.size(); i++) {
			int id = ((MulitGroupListener) data.get(i)).bindAdapterGroupId();
			if (groupId != id) {
				return i - 1;
			}
		}
		return data.size() - 1;
	}

	/**
	 * 集合状态增加
	 *
	 * @param index
	 * @param data
	 */
	@Override
	public void add(int index, DataBean data, int count) {
		if(cacheHeaders!=null&&cacheHeaders.size()>0){
			int size = cacheHeaders.size();
			for (int i = 0; i < size; i++) {
				int keyAt = cacheHeaders.keyAt(i);
				DataBean value = cacheHeaders.get(keyAt);
				if (keyAt == count) {
					cacheHeaders.remove(keyAt);
					headerList.add(keyAt, value);
					notifyItemInserted(keyAt);
					break;
				}
			}
		}

	}

	/**
	 * 集合状态删除
	 *
	 * @param index
	 * @param data
	 */
	@Override
	public void remove(int index, DataBean data) {

	}


	@IntDef({EMPTY_MODLE_NOMAL, EMPTY_MODLE_HEADERS})
	@Retention(RetentionPolicy.SOURCE)
	public @interface EmptyModle {
	}
}
