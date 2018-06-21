package com.location.mvp.mvp_route_demo.modle.bean;

import java.util.List;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/6/5 14:24
 * description：
 */

public class PictureBean  {

	/**
	 * error : false
	 * results : [{"_id":"5b15ec20421aa97e45f15aae","createdAt":"2018-06-05T09:49:20.355Z","desc":"2018-06-05","publishedAt":"2018-06-05T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fs02a9b0nvj30sg10vk4z.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b14aaa9421aa93df569c6f1","createdAt":"2018-06-04T10:57:45.583Z","desc":"2018-06-04","publishedAt":"2018-06-04T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fryyn63fm1j30sg0yagt2.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b1026ba421aa9029895ba44","createdAt":"2018-06-01T00:45:46.820Z","desc":"2018-06-02","publishedAt":"2018-06-01T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frv03m8ky5j30iz0rltfp.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b0d6946421aa97f0308836b","createdAt":"2018-05-29T22:52:54.29Z","desc":"2018-05-31","publishedAt":"2018-05-31T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b0d6895421aa97f0308836a","createdAt":"2018-05-29T22:49:57.62Z","desc":"2018-05-30","publishedAt":"2018-05-30T13:22:16.505Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frslibvijrj30k80q678q.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b0c2bc3421aa97f0624f447","createdAt":"2018-05-29T00:18:11.714Z","desc":"2018-05-29","publishedAt":"2018-05-29T15:38:50.405Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frrifts8l5j30j60ojq6u.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b0b5839421aa97f00f67c5c","createdAt":"2018-05-28T09:15:37.475Z","desc":"2018-05-28","publishedAt":"2018-05-28T18:51:58.793Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frqscr5o00j30k80qzafc.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b06dc9c421aa97f03088341","createdAt":"2018-05-24T23:39:08.401Z","desc":"2018.5.25-1","publishedAt":"2018-05-25T10:30:37.805Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frmuto5qlzj30ia0notd8.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b02e1cd421aa96031463fe4","createdAt":"2018-05-21T23:12:13.646Z","desc":"2018.5.25","publishedAt":"2018-05-24T11:03:54.588Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frjd77dt8zj30k80q2aga.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b02e163421aa9602d6abd36","createdAt":"2018-05-21T23:10:27.865Z","desc":"2018.5.23","publishedAt":"2018-05-23T00:22:29.342Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frjd4var2bj30k80q0dlf.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b02c939421aa97aa11c2350","createdAt":"2018-05-21T21:27:21.498Z","desc":"2018.5.22","publishedAt":"2018-05-22T10:30:57.698Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frja502w5xj30k80od410.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b01a404421aa96acde08ec9","createdAt":"2018-05-21T00:36:20.855Z","desc":"2018.5.21","publishedAt":"2018-05-21T01:11:33.975Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fri9zqwzkoj30ql0w3jy0.jpg","used":true,"who":null},{"_id":"5aff4645421aa95f55cab5e8","createdAt":"2018-05-19T00:00:00.0Z","desc":"2018-05-19","publishedAt":"2018-05-19T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frg40vozfnj30ku0qwq7s.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e6","createdAt":"2018-05-18T00:00:00.0Z","desc":"2018-05-18","publishedAt":"2018-05-18T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frevscw2wej30je0ps78h.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e5","createdAt":"2018-05-17T00:00:00.0Z","desc":"2018-05-17","publishedAt":"2018-05-17T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepozc5taj30qp0yg7aq.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5df","createdAt":"2018-05-16T00:00:00.0Z","desc":"2018-05-16","publishedAt":"2018-05-16T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepq6mfvdj30p00wcwmq.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e7","createdAt":"2018-05-15T00:00:00.0Z","desc":"2018-05-15","publishedAt":"2018-05-15T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepqtwifwj30no0ti47n.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e0","createdAt":"2018-05-14T00:00:00.0Z","desc":"2018-05-14","publishedAt":"2018-05-14T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepr2rhxvj30qo0yjth8.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e4","createdAt":"2018-05-13T00:00:00.0Z","desc":"2018-05-13","publishedAt":"2018-05-13T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1freprc128lj30sg15m12u.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e2","createdAt":"2018-05-12T00:00:00.0Z","desc":"2018-05-12","publishedAt":"2018-05-12T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1freprk6sd7j30sg15h7d2.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e3","createdAt":"2018-05-11T00:00:00.0Z","desc":"2018-05-11","publishedAt":"2018-05-11T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1freprsjjwmj30sg15dth0.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5e1","createdAt":"2018-05-10T00:00:00.0Z","desc":"2018-05-10","publishedAt":"2018-05-10T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1freps07ubij30sg1dgwr7.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5de","createdAt":"2018-05-09T00:00:00.0Z","desc":"2018-05-09","publishedAt":"2018-05-09T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1freps89wc7j30no0uk45j.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5dd","createdAt":"2018-05-08T00:00:00.0Z","desc":"2018-05-08","publishedAt":"2018-05-08T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepsi3o15j30k80oidkd.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5db","createdAt":"2018-05-06T00:00:00.0Z","desc":"2018-05-06","publishedAt":"2018-05-06T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepspsrhyj30ku0qsjuc.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5da","createdAt":"2018-05-05T00:00:00.0Z","desc":"2018-05-05","publishedAt":"2018-05-05T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepsy47grj30qo0y97en.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aff4645421aa95f55cab5dc","createdAt":"2018-05-04T00:00:00.0Z","desc":"2018-05-04","publishedAt":"2018-05-04T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frept5di16j30p010g0z9.jpg","used":true,"who":"lijinshanmx"},{"_id":"5aa5cc6a421aa9103ed33c7c","createdAt":"2018-03-12T08:40:10.360Z","desc":"3-12","publishedAt":"2018-03-12T08:44:50.326Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fp9qm6nv50j20u00miacg.jpg","used":true,"who":"daimajia"},{"_id":"5a8e0c41421aa9133298a259","createdAt":"2018-02-22T08:18:09.547Z","desc":"2-22","publishedAt":"2018-02-22T08:24:35.209Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1foowtrkpvkj20sg0izdkx.jpg","used":true,"who":"代码家"},{"_id":"5a7b93d2421aa90d2cd3d7f8","createdAt":"2018-02-08T08:03:30.905Z","desc":"2-8","publishedAt":"2018-02-08T08:13:24.479Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20180208080314_FhzuAJ_Screenshot.jpeg","used":true,"who":"daimajia"}]
	 */

	private boolean error;
	private List<ResultsBean> results;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public List<ResultsBean> getResults() {
		return results;
	}

	public void setResults(List<ResultsBean> results) {
		this.results = results;
	}

	public static class ResultsBean {
		/**
		 * _id : 5b15ec20421aa97e45f15aae
		 * createdAt : 2018-06-05T09:49:20.355Z
		 * desc : 2018-06-05
		 * publishedAt : 2018-06-05T00:00:00.0Z
		 * source : web
		 * type : 福利
		 * url : http://ww1.sinaimg.cn/large/0065oQSqly1fs02a9b0nvj30sg10vk4z.jpg
		 * used : true
		 * who : lijinshanmx
		 */

		private String _id;
		private String createdAt;
		private String desc;
		private String publishedAt;
		private String source;
		private String type;
		private String url;
		private boolean used;
		private String who;

		public String get_id() {
			return _id;
		}

		public void set_id(String _id) {
			this._id = _id;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getPublishedAt() {
			return publishedAt;
		}

		public void setPublishedAt(String publishedAt) {
			this.publishedAt = publishedAt;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public boolean isUsed() {
			return used;
		}

		public void setUsed(boolean used) {
			this.used = used;
		}

		public String getWho() {
			return who;
		}

		public void setWho(String who) {
			this.who = who;
		}
	}
}
