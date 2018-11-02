package com.location.mvp.mvp_route_demo;

import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/28 21:57
 * description：
 */

public class TestBean {

	/**
	 * result : {"unitList":[{"unitId":1,"unitName":"Unit 1 School","lessonList":[{"lessonId":1,"lessonName":"Lesson 1"},{"lessonId":2,"lessonName":"Lesson 2"}]},{"unitId":2,"unitName":"Unit 2 Face","lessonList":[{"lessonId":1,"lessonName":"Lesson 1"},{"lessonId":2,"lessonName":"Lesson 2"}]},{"unitId":3,"unitName":"Unit 3 Animals","lessonList":[{"lessonId":1,"lessonName":"Lesson 1"},{"lessonId":2,"lessonName":"Lesson 2"}]},{"unitId":4,"unitName":"Unit 4 Numbers","lessonList":[{"lessonId":1,"lessonName":"Lesson 1"},{"lessonId":2,"lessonName":"Lesson 2"}]},{"unitId":5,"unitName":"Unit 5 Colours","lessonList":[{"lessonId":1,"lessonName":"Lesson 1"},{"lessonId":2,"lessonName":"Lesson 2"}]},{"unitId":6,"unitName":"Unit 6 Fruit","lessonList":[{"lessonId":1,"lessonName":"Lesson 1"},{"lessonId":2,"lessonName":"Lesson 2"}]}]}
	 * error :
	 * code : 200
	 */

	private ResultBean result;
	private String error;
	private int code;

	public ResultBean getResult() {
		return result;
	}

	public void setResult(ResultBean result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static class ResultBean {
		private List<UnitListBean> unitList;

		public List<UnitListBean> getUnitList() {
			return unitList;
		}

		public void setUnitList(List<UnitListBean> unitList) {
			this.unitList = unitList;
		}

		public static class UnitListBean {
			/**
			 * unitId : 1
			 * unitName : Unit 1 School
			 * lessonList : [{"lessonId":1,"lessonName":"Lesson 1"},{"lessonId":2,"lessonName":"Lesson 2"}]
			 */

			private int unitId;
			private String unitName;
			private List<LessonListBean> lessonList;

			public int getUnitId() {
				return unitId;
			}

			public void setUnitId(int unitId) {
				this.unitId = unitId;
			}

			public String getUnitName() {
				return unitName;
			}

			public void setUnitName(String unitName) {
				this.unitName = unitName;
			}

			public List<LessonListBean> getLessonList() {
				return lessonList;
			}

			public void setLessonList(List<LessonListBean> lessonList) {
				this.lessonList = lessonList;
			}

			public static class LessonListBean {
				/**
				 * lessonId : 1
				 * lessonName : Lesson 1
				 */

				private int lessonId;
				private String lessonName;

				public int getLessonId() {
					return lessonId;
				}

				public void setLessonId(int lessonId) {
					this.lessonId = lessonId;
				}

				public String getLessonName() {
					return lessonName;
				}

				public void setLessonName(String lessonName) {
					this.lessonName = lessonName;
				}
			}
		}
	}
}
